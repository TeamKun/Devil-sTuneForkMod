package net.kunmc.lab.dtf.items;

import net.kunmc.lab.dtf.client.renderer.TestRenderer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class DevilsTuneForkItem extends Item {
    public DevilsTuneForkItem(Properties properties) {
        super(properties);
    }

    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        if (worldIn.isRemote) {
            TestRenderer.getInstance().ping(playerIn.getPositionVector());
        }
        return ActionResult.resultConsume(itemstack);
    }
}
