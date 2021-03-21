package net.kunmc.lab.dtf;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

import java.util.ArrayList;
import java.util.List;

public class DTFSoundEvents {
    public static List<SoundEvent> MOD_SOUNDS = new ArrayList<SoundEvent>();
    public static final SoundEvent USEDSOUND = register("dtf.usedsound");
    public static final SoundEvent BOTU = register("dtf.botu");

    private static SoundEvent register(String name) {
        SoundEvent se = new SoundEvent(new ResourceLocation(DevilsTuneFork.MODID, name)).setRegistryName(name);
        MOD_SOUNDS.add(se);
        return se;
    }
}
