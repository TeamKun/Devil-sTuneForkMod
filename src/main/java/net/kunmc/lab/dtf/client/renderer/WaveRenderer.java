package net.kunmc.lab.dtf.client.renderer;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.kunmc.lab.dtf.client.shader.WaveShader;
import net.kunmc.lab.dtf.client.wave.Wave;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WaveRenderer {
    private static final WaveRenderer INSTANCE = new WaveRenderer();
    private static final Minecraft mc = Minecraft.getInstance();
    private static final JSONBlendingMode RESET_BLEND_STATE = new JSONBlendingMode();
    private final Map<Integer, Wave> wavs = new HashMap<>();
    public int depthCopyFbo;
    private int depthCopyColorBuffer;
    private int depthCopyDepthBuffer;

    public static WaveRenderer getInstance() {
        return INSTANCE;
    }


    public void addWave(Vec3d pos, float range, float speed) {
        for (int i = 0; i < 300; i++) {
            if (!wavs.containsKey(i) || wavs.get(i) == null) {
                Wave wave = new Wave(pos, range, speed);
                wave.startWave();
                WaveShader.getInstance().setCenter(i, pos);
                WaveShader.getInstance().setMaxRadiuss(i, range);
                wavs.put(i, wave);
                return;
            }
        }
    }

    public void onRender(RenderWorldLastEvent e) {
        if (depthCopyFbo == 0) {
            createDepthCopyFramebuffer();
        }
        render(e.getMatrixStack().getLast().getMatrix(), e.getProjectionMatrix());
    }


    private void render(Matrix4f viewMatrix, Matrix4f projectionMatrix) {

        List<Integer> dl = new ArrayList<>();
        wavs.forEach((n, m) -> {
            if (!m.isOn())
                dl.add(n);
        });

        dl.forEach(n -> {
            WaveShader.getInstance().removeCenter(n);
            WaveShader.getInstance().removeMaxRadiuss(n);
            wavs.remove(n);
        });

        WaveShader.getInstance().setArrayCenter();
        WaveShader.getInstance().setArrayMaxRadiuss();

        Framebuffer framebuffer = mc.getFramebuffer();

        updateDepthTexture(framebuffer);


        Matrix4f invertedViewMatrix = new Matrix4f(viewMatrix);
        invertedViewMatrix.invert();
        WaveShader.getInstance().setInverseViewMatrix(invertedViewMatrix);

        Matrix4f invertedProjectionMatrix = new Matrix4f(projectionMatrix);
        invertedProjectionMatrix.invert();
        WaveShader.getInstance().setInverseProjectionMatrix(invertedProjectionMatrix);

        Vec3d position = mc.gameRenderer.getActiveRenderInfo().getProjectedView();
        WaveShader.getInstance().setPosition(position);

        wavs.forEach((m, n) -> WaveShader.getInstance().setRadiuss(m, n.getRadius()));

        WaveShader.getInstance().setArrayRadiuss();

        RESET_BLEND_STATE.apply();

        WaveShader.getInstance().bind();

        blit(framebuffer);

        WaveShader.getInstance().unbind();
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

    private void updateDepthTexture(final Framebuffer framebuffer) {
        GlStateManager.bindFramebuffer(GL30.GL_READ_FRAMEBUFFER, framebuffer.framebufferObject);
        GlStateManager.bindFramebuffer(GL30.GL_DRAW_FRAMEBUFFER, depthCopyFbo);
        GL30.glBlitFramebuffer(0, 0, framebuffer.framebufferTextureWidth, framebuffer.framebufferTextureHeight, 0, 0, framebuffer.framebufferTextureWidth, framebuffer.framebufferTextureHeight, GL30.GL_DEPTH_BUFFER_BIT, GL30.GL_NEAREST);
    }

    public void deleteDepthCopyFramebuffer() {
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

        depthCopyColorBuffer = createTexture(framebuffer.framebufferTextureWidth, framebuffer.framebufferTextureHeight, GL11.GL_RGBA8, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE);

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
}