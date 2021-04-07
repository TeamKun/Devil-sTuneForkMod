package net.kunmc.lab.dtf.util;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;

public class PlayerUtils {
    public static void grantAdvancement(ResourceLocation rl, ServerPlayerEntity spl) {

        Advancement advancement = spl.getServer().getAdvancementManager().getAdvancement(rl);
        AdvancementProgress advancementprogress = spl.getAdvancements().getProgress(advancement);

        if (advancementprogress.isDone())
            return;

        for (String s : advancementprogress.getRemaningCriteria()) {
            spl.getAdvancements().grantCriterion(advancement, s);
        }
    }
}
