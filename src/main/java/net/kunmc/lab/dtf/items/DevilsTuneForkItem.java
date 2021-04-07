package net.kunmc.lab.dtf.items;

import net.kunmc.lab.dtf.DTFSoundEvents;
import net.kunmc.lab.dtf.packet.PacketHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;


public class DevilsTuneForkItem extends Item {

    public DevilsTuneForkItem(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        if (!worldIn.isRemote) {
            PacketHandler.sendWave(worldIn, playerIn.getPositionVector(), 50, 1.5f, true);
            worldIn.playSound(null, playerIn.getPosX(), playerIn.getPosY(), playerIn.getPosZ(), DTFSoundEvents.USEDSOUND, SoundCategory.NEUTRAL, 3, 1);
            playerIn.getCooldownTracker().setCooldown(this, 20 * 3);
        } /*else {
            if (playerIn.isCrouching()) {
                playerIn.sendStatusMessage(new StringTextComponent("LWJGL: " + Version.getVersion()), false);
                playerIn.sendStatusMessage(new StringTextComponent("OpenGL: " + GL11.glGetString(GL11.GL_VERSION)), false);
            }
        }*/
        return ActionResult.resultSuccess(itemstack);
    }
}
