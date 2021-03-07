package net.kunmc.lab.dtf.client.handler;

import net.kunmc.lab.dtf.DevilsTuneFork;
import net.kunmc.lab.dtf.client.renderer.WaveRenderer;
import net.kunmc.lab.dtf.client.renderer.WhiteLineRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class RenderHandler {
    private static final Minecraft mc = Minecraft.getInstance();
    private static final ResourceLocation blackshader = new ResourceLocation(DevilsTuneFork.MODID, "shaders/post/test1.json");

    @SubscribeEvent
    public static void onWorldRender(RenderWorldLastEvent e) {
        WhiteLineRenderer.getInstance().onRender(e);
        WaveRenderer.getInstance().onRender(e);
        //  if (mc.gameRenderer.getShaderGroup() == null || !mc.gameRenderer.getShaderGroup().getShaderGroupName().equals(blackshader.toString())) {
        //      mc.gameRenderer.loadShader(blackshader);
        // }
    }

}
