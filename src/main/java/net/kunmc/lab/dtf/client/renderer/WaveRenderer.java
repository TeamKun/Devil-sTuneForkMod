package net.kunmc.lab.dtf.client.renderer;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.kunmc.lab.dtf.client.shader.WaveShader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Matrix4f;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.client.shader.FramebufferConstants;
import net.minecraft.client.util.JSONBlendingMode;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL30;

public class WaveRenderer {
    private static final WaveRenderer INSTANCE = new WaveRenderer();
    private static final JSONBlendingMode RESET_BLEND_STATE = new JSONBlendingMode();

    private int depthCopyFbo;
    private int depthCopyColorBuffer;
    private int depthCopyDepthBuffer;

    private long currentStart;

    public static WaveRenderer getInstance() {
        return INSTANCE;
    }

    public void ping(final Vec3d pos) {
        currentStart = System.currentTimeMillis();
        WaveShader.getInstance().setCenter(pos);
    }

    public void onRender(RenderWorldLastEvent e) {
        final int adjustedDuration = computeScanGrowthDuration();
        final boolean shouldRender = currentStart > 0 && adjustedDuration > (int) (System.currentTimeMillis() - currentStart);
        if (shouldRender) {
            if (depthCopyFbo == 0) {
                createDepthCopyFramebuffer();
            }

            render(e.getMatrixStack().getLast().getMatrix(), e.getProjectionMatrix());
        } else {
            if (depthCopyFbo != 0) {
                deleteDepthCopyFramebuffer();
            }

            currentStart = 0;
        }
    }

    private void deleteDepthCopyFramebuffer() {
        WaveShader.getInstance().setDepthBuffer(0);

        GlStateManager.deleteFramebuffers(depthCopyFbo);
        depthCopyFbo = 0;

        TextureUtil.releaseTextureId(depthCopyColorBuffer);
        depthCopyColorBuffer = 0;

        TextureUtil.releaseTextureId(depthCopyDepthBuffer);
        depthCopyDepthBuffer = 0;
    }

    private void createDepthCopyFramebuffer() {
        final Framebuffer framebuffer = Minecraft.getInstance().getFramebuffer();

        depthCopyFbo = GlStateManager.genFramebuffers();

        // We don't use the color attachment on this FBO, but it's required for a complete FBO.
        depthCopyColorBuffer = createTexture(framebuffer.framebufferTextureWidth, framebuffer.framebufferTextureHeight, GL11.GL_RGBA8, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE);

        // Main reason why we create this FBO: readable depth buffer into which we can copy the MC one.
        depthCopyDepthBuffer = createTexture(framebuffer.framebufferTextureWidth, framebuffer.framebufferTextureHeight, GL30.GL_DEPTH_COMPONENT, GL11.GL_DEPTH_COMPONENT, GL11.GL_FLOAT);

        GlStateManager.bindFramebuffer(FramebufferConstants.GL_FRAMEBUFFER, depthCopyFbo);
        GlStateManager.framebufferTexture2D(FramebufferConstants.GL_FRAMEBUFFER, FramebufferConstants.GL_COLOR_ATTACHMENT0, GL11.GL_TEXTURE_2D, depthCopyColorBuffer, 0);
        GlStateManager.framebufferTexture2D(FramebufferConstants.GL_FRAMEBUFFER, FramebufferConstants.GL_DEPTH_ATTACHMENT, GL11.GL_TEXTURE_2D, depthCopyDepthBuffer, 0);
        GlStateManager.bindFramebuffer(FramebufferConstants.GL_FRAMEBUFFER, 0);

        WaveShader.getInstance().setDepthBuffer(depthCopyDepthBuffer);
    }

    private int createTexture(final int width, final int height, final int internalFormat, final int format, final int type) {
        final int texture = TextureUtil.generateTextureId();
        GlStateManager.bindTexture(texture);
        GlStateManager.texParameter(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
        GlStateManager.texParameter(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
        GlStateManager.texParameter(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
        GlStateManager.texParameter(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
        GlStateManager.texParameter(GL11.GL_TEXTURE_2D, GL14.GL_DEPTH_TEXTURE_MODE, GL11.GL_LUMINANCE);
        GlStateManager.texParameter(GL11.GL_TEXTURE_2D, GL14.GL_TEXTURE_COMPARE_MODE, GL14.GL_NONE);
        GlStateManager.texParameter(GL11.GL_TEXTURE_2D, GL14.GL_TEXTURE_COMPARE_FUNC, GL11.GL_LEQUAL);
        GlStateManager.texImage2D(GL11.GL_TEXTURE_2D, 0, internalFormat, width, height, 0, format, type, null);
        GlStateManager.bindTexture(0);
        return texture;
    }

    private void render(Matrix4f viewMatrix, Matrix4f projectionMatrix) {
        Minecraft mc = Minecraft.getInstance();
        Framebuffer framebuffer = mc.getFramebuffer();

        updateDepthTexture(framebuffer);

        final Matrix4f invertedViewMatrix = new Matrix4f(viewMatrix);
        invertedViewMatrix.invert();
        WaveShader.getInstance().setInverseViewMatrix(invertedViewMatrix);

        Matrix4f invertedProjectionMatrix = new Matrix4f(projectionMatrix);
        invertedProjectionMatrix.invert();
        WaveShader.getInstance().setInverseProjectionMatrix(invertedProjectionMatrix);

        Vec3d position = mc.gameRenderer.getActiveRenderInfo().getProjectedView();
        WaveShader.getInstance().setPosition(position);

        int adjustedDuration = computeScanGrowthDuration();
        float radius = computeRadius(currentStart, (float) adjustedDuration);
        WaveShader.getInstance().setRadius(radius);

        RESET_BLEND_STATE.apply();

        WaveShader.getInstance().bind();

        blit(framebuffer);

        WaveShader.getInstance().unbind();
    }

    public int computeScanGrowthDuration() {
        return 2000 * Minecraft.getInstance().gameSettings.renderDistanceChunks / 12;
    }

    public float computeRadius(final long start, final float duration) {

        final float r1 = Minecraft.getInstance().gameRenderer.getFarPlaneDistance();
        final float t1 = duration;
        final float b = 200;
        final float n = 1f / ((t1 + b) * (t1 + b) - b * b);
        final float a = -r1 * b * b * n;
        final float c = r1 * n;

        final float t = (float) (System.currentTimeMillis() - start);

        return 10 + a + (t + b) * (t + b) * c;
    }

    private void updateDepthTexture(final Framebuffer framebuffer) {
        GlStateManager.bindFramebuffer(GL30.GL_READ_FRAMEBUFFER, framebuffer.framebufferObject);
        GlStateManager.bindFramebuffer(GL30.GL_DRAW_FRAMEBUFFER, depthCopyFbo);
        GL30.glBlitFramebuffer(0, 0, framebuffer.framebufferTextureWidth, framebuffer.framebufferTextureHeight,
                0, 0, framebuffer.framebufferTextureWidth, framebuffer.framebufferTextureHeight,
                GL30.GL_DEPTH_BUFFER_BIT, GL30.GL_NEAREST);
    }

    private void blit(final Framebuffer framebuffer) {
        final int width = framebuffer.framebufferTextureWidth;
        final int height = framebuffer.framebufferTextureHeight;

        RenderSystem.depthMask(false);
        RenderSystem.disableDepthTest();

        setupMatrices(width, height);

        framebuffer.bindFramebuffer(false);

        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        buffer.pos(0, height, 0).tex(0, 0).endVertex();
        buffer.pos(width, height, 0).tex(1, 0).endVertex();
        buffer.pos(width, 0, 0).tex(1, 1).endVertex();
        buffer.pos(0, 0, 0).tex(0, 1).endVertex();
        tessellator.draw();

        restoreMatrices();

        RenderSystem.depthMask(true);
        RenderSystem.enableDepthTest();
    }

    private void setupMatrices(final int width, final int height) {
        RenderSystem.matrixMode(GL11.GL_PROJECTION);
        RenderSystem.pushMatrix();
        RenderSystem.loadIdentity();
        RenderSystem.ortho(0, width, height, 0, 1000, 3000);
        RenderSystem.matrixMode(GL11.GL_MODELVIEW);
        RenderSystem.pushMatrix();
        RenderSystem.loadIdentity();
        RenderSystem.translated(0, 0, -2000);
        RenderSystem.viewport(0, 0, width, height);
    }

    private void restoreMatrices() {
        RenderSystem.matrixMode(GL11.GL_PROJECTION);
        RenderSystem.popMatrix();
        RenderSystem.matrixMode(GL11.GL_MODELVIEW);
        RenderSystem.popMatrix();
    }
}
