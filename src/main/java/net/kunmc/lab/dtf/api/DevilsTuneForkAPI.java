package net.kunmc.lab.dtf.api;

import net.kunmc.lab.dtf.DevilsTuneFork;
import net.kunmc.lab.dtf.client.renderer.WaveRenderer;
import net.kunmc.lab.dtf.client.renderer.WhiteLineRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;

@OnlyIn(Dist.CLIENT)
public class DevilsTuneForkAPI {
    private static final DevilsTuneForkAPI INSTANCE = new DevilsTuneForkAPI();
    private static final ResourceLocation BLACK_WHITE_SHADER = new ResourceLocation(DevilsTuneFork.MODID, "shaders/post/blackwhite.json");

    public static DevilsTuneForkAPI getInstance() {
        return INSTANCE;
    }

    public ResourceLocation getBlackWhiteShader() {
        return BLACK_WHITE_SHADER;
    }

    public boolean isEnable() {
        DevilsTuneForkEvent de = new DevilsTuneForkEvent(this);

        if (MinecraftForge.EVENT_BUS.post(de))
            return false;

        return de.isEnable();
    }

    public void resized() {
        if (isEnable()) {
            WaveRenderer waveRenderer = WaveRenderer.getInstance();
            WhiteLineRenderer whiteLineRenderer = WhiteLineRenderer.getInstance();
            if (waveRenderer.depthCopyFbo != 0) {
                waveRenderer.deleteDepthCopyFramebuffer();
            }
            if (whiteLineRenderer.depthCopyFbo != 0) {
                whiteLineRenderer.deleteDepthCopyFramebuffer();
            }
        }
    }

}
