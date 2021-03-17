package net.kunmc.lab.dtf.items;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

public class DevilsTuneForkItem extends Item {

    public DevilsTuneForkItem(Properties properties) {
        super(properties);
    }

    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        if (!worldIn.isRemote) {
            //     Chunk ch = (Chunk) playerIn.world.getChunk(playerIn.getPosition());
            //    PacketHandler.INSTANCE.send(PacketDistributor.TRACKING_CHUNK.with(() -> ch), new WaveMessage(playerIn.getPositionVector(), 50, 1));
            worldIn.playSound(null, playerIn.getPosX(), playerIn.getPosY(), playerIn.getPosZ(), SoundEvents.BLOCK_BELL_USE, SoundCategory.NEUTRAL, 3, 0.1F / (random.nextFloat() * 0.4F + 0.8F));
            playerIn.getCooldownTracker().setCooldown(this, 20 * 4);
        }
        return ActionResult.resultSuccess(itemstack);
    }
}
