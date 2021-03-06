package io.github.phantamanta44.wtflux.block;

import io.github.phantamanta44.wtflux.item.block.ItemBlockOre;
import io.github.phantamanta44.wtflux.lib.LibCore;
import io.github.phantamanta44.wtflux.lib.LibLang;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import cpw.mods.fml.common.registry.GameRegistry;

public class BlockOre extends BlockModSubs {

    public static final int ZINC = 0, URAN = 1;

    public BlockOre() {
        super(Material.rock, 2);
        setHardness(1.2F);
        setResistance(4.8F);
        setBlockName(LibLang.ORE_NAME);
        setHarvestLevel(LibCore.TOOL_PICK, 2);
    }

    @Override
    public Block setBlockName(String name) {
        GameRegistry.registerBlock(this, ItemBlockOre.class, name);
        return super.setBlockName(name);
    }

}