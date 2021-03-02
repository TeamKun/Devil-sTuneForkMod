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

    private ShaderDefault centerUniformX;
    private ShaderDefault centerUniformY;
    private ShaderDefault centerUniformZ;
    private ShaderDefault radiussUniform;
    private ShaderDefault maxRadiussUniform;

    private float[] wavesX = new float[300];
    private float[] wavesY = new float[300];
    private float[] wavesZ = new float[300];
    private float[] radiuss = new float[300];
    private float[] maxRadiuss = new float[300];

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


        centerUniformX = shaderInstance.getShaderUniform("centerX");
        centerUniformY = shaderInstance.getShaderUniform("centerY");
        centerUniformZ = shaderInstance.getShaderUniform("centerZ");
        radiussUniform = shaderInstance.getShaderUniform("radiuss");
        maxRadiussUniform = shaderInstance.getShaderUniform("maxRadiuss");
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

    public void setCenter(int num, Vec3d value) {

        if (wavesX.length < num)
            return;

        wavesX[num] = (float) value.getX();
        wavesY[num] = (float) value.getY();
        wavesZ[num] = (float) value.getZ();
    }

    public void removeCenter(int num) {
        if (wavesX.length < num)
            return;
        wavesX[num] = 0;
        wavesY[num] = 0;
        wavesZ[num] = 0;
    }

    public void setArrayCenter() {
        centerUniformX.set(wavesX);
        centerUniformY.set(wavesY);
        centerUniformZ.set(wavesZ);
    }

    public void setMaxRadiuss(int num, float value) {
        if (maxRadiuss.length < num)
            return;
        maxRadiuss[num] = value;
    }
    public void setArrayMaxRadiuss() {
        maxRadiussUniform.set(maxRadiuss);
    }
    public void setRadiuss(int num, float value) {
        if (radiuss.length < num)
            return;
        radiuss[num] = value;
    }

    public void setArrayRadiuss() {
        radiussUniform.set(radiuss);
    }

    public void setDepthBuffer(final int buffer) {
        shaderInstance.func_216537_a("depthTex", buffer);
    }
}
