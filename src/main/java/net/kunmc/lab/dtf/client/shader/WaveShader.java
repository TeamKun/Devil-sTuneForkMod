package net.kunmc.lab.dtf.client.shader;

import net.kunmc.lab.dtf.DevilsTuneFork;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Matrix4f;
import net.minecraft.client.shader.ShaderDefault;
import net.minecraft.client.shader.ShaderInstance;
import net.minecraft.resources.IReloadableResourceManager;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.resource.ISelectiveResourceReloadListener;
import net.minecraftforge.resource.VanillaResourceType;

import java.io.IOException;

public class WaveShader {
    private static final long START_TIME = System.currentTimeMillis();
    private static final WaveShader INSTANCE = new WaveShader();
    protected ShaderInstance shaderInstance;

    private ShaderDefault timeUniform;
    private ShaderDefault inverseViewMatrixUniform;
    private ShaderDefault inverseProjectionMatrixUniform;
    private ShaderDefault positionUniform;
    private ShaderDefault centerUniform;
    private ShaderDefault radiusUniform;

    public static WaveShader getInstance() {
        return INSTANCE;
    }

    public void init() {
        IResourceManager resourceManager = Minecraft.getInstance().getResourceManager();
        Minecraft.getInstance().deferTask(() -> reloadShaders(resourceManager));
        if (resourceManager instanceof IReloadableResourceManager) {
            ((IReloadableResourceManager) resourceManager).addReloadListener((ISelectiveResourceReloadListener) (manager, predicate) -> {
                if (predicate.test(VanillaResourceType.SHADERS)) {
                    reloadShaders(manager);
                }
            });
        }
    }

    public void bind() {
        if (shaderInstance != null) {
            timeUniform.set((System.currentTimeMillis() - START_TIME) / 1000.0f);
            shaderInstance.func_216535_f();
        }
    }

    public void unbind() {
        if (shaderInstance != null) {
            shaderInstance.func_216544_e();
        }
    }

    private void reloadShaders(final IResourceManager manager) {
        if (shaderInstance != null) {
            shaderInstance.close();
            shaderInstance = null;
        }

        try {
            shaderInstance = new ShaderInstance(manager, DevilsTuneFork.MODID + ":wave_effect");
            handleShaderLoad();
        } catch (final IOException ex) {
            ex.printStackTrace();
        }
    }

    public void handleShaderLoad() {
        timeUniform = shaderInstance.getShaderUniform("time");

        inverseViewMatrixUniform = shaderInstance.getShaderUniform("invViewMat");
        inverseProjectionMatrixUniform = shaderInstance.getShaderUniform("invProjMat");
        positionUniform = shaderInstance.getShaderUniform("pos");
        centerUniform = shaderInstance.getShaderUniform("center");
        radiusUniform = shaderInstance.getShaderUniform("radius");
    }


    public void setInverseViewMatrix(final Matrix4f value) {
        inverseViewMatrixUniform.set(value);
    }

    public void setInverseProjectionMatrix(final Matrix4f value) {
        inverseProjectionMatrixUniform.set(value);
    }

    public void setPosition(final Vec3d value) {
        positionUniform.set((float) value.getX(), (float) value.getY(), (float) value.getZ());
    }

    public void setCenter(final Vec3d value) {
        centerUniform.set((float) value.getX(), (float) value.getY(), (float) value.getZ());
    }

    public void setRadius(final float value) {
        radiusUniform.set(value);
    }

    public void setDepthBuffer(final int buffer) {
        shaderInstance.func_216537_a("depthTex", buffer);
    }
}
