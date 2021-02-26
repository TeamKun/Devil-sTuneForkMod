package net.kunmc.lab.dtf.items;

import net.kunmc.lab.dtf.DevilsTuneFork;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

import java.util.ArrayList;
import java.util.List;

public class DTFItems {
    public static List<Item> MOD_ITEMS = new ArrayList<>();
    public static final Item DEVILSTUNEFORK = register("devilstunefork", new DevilsTuneForkItem(new Item.Properties().group(ItemGroup.TOOLS).maxStackSize(1)));

    private static Item register(String name, Item item) {
        MOD_ITEMS.add(item.setRegistryName(DevilsTuneFork.MODID, name));
        return item;
    }
}
