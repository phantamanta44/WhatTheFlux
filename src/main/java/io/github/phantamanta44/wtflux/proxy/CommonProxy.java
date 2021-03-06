package io.github.phantamanta44.wtflux.proxy;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import dan200.computercraft.api.ComputerCraftAPI;
import io.github.phantamanta44.wtflux.WhatTheFlux;
import io.github.phantamanta44.wtflux.block.BlockOre;
import io.github.phantamanta44.wtflux.block.WtfBlocks;
import io.github.phantamanta44.wtflux.crafting.MasterRecipeManager;
import io.github.phantamanta44.wtflux.gui.GuiGenerator;
import io.github.phantamanta44.wtflux.gui.GuiSensor;
import io.github.phantamanta44.wtflux.handler.GuiHandler;
import io.github.phantamanta44.wtflux.inventory.ContainerDummy;
import io.github.phantamanta44.wtflux.inventory.ContainerGenerator;
import io.github.phantamanta44.wtflux.item.WtfItems;
import io.github.phantamanta44.wtflux.network.WtfNet;
import io.github.phantamanta44.wtflux.tile.TileGenerator;
import io.github.phantamanta44.wtflux.tile.TileSensor;
import io.github.phantamanta44.wtflux.util.BlockWithMeta;
import io.github.phantamanta44.wtflux.util.computercraft.CCPeripheralProvider;
import io.github.phantamanta44.wtflux.worldgen.OreGenSimple;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;

public class CommonProxy {

    public void onPreInit() {
        WtfItems.init();
        WtfBlocks.init();
        registerTileEntities();
        registerContainers();
        WtfNet.init();
    }

    public void onInit() {
        MasterRecipeManager.addRecipes();
        GameRegistry.registerWorldGenerator(new OreGenSimple(new BlockWithMeta(WtfBlocks.blockOre, BlockOre.ZINC), 0, new BlockWithMeta(Blocks.stone), 4, 72, 12, 8), 8);
        GameRegistry.registerWorldGenerator(new OreGenSimple(new BlockWithMeta(WtfBlocks.blockOre, BlockOre.URAN), 0, new BlockWithMeta(Blocks.stone), 4, 30, 6, 10), 5);
        NetworkRegistry.INSTANCE.registerGuiHandler(WhatTheFlux.instance, new GuiHandler());
        ComputerCraftAPI.registerPeripheralProvider(new CCPeripheralProvider());
    }

    public void onPostInit() {
        // NO-OP
    }

    protected void registerTileEntities() {
        addTEMapping(TileGenerator.Furnace.class);
        addTEMapping(TileGenerator.Heat.class);
        addTEMapping(TileGenerator.Wind.class);
        addTEMapping(TileGenerator.Water.class);
        addTEMapping(TileGenerator.Nuke.class);
        addTEMapping(TileGenerator.Solar.class);
        addTEMapping(TileSensor.Temperature.class);
        addTEMapping(TileSensor.Energy.class);
        addTEMapping(TileSensor.RPM.class);
    }

    protected void registerContainers() {
        registerContainer(TileGenerator.Furnace.class, GuiGenerator.Furnace.class, ContainerGenerator.Furnace.class);
        registerContainer(TileGenerator.Heat.class, GuiGenerator.Heat.class, ContainerGenerator.Heat.class);
        registerContainer(TileGenerator.Wind.class, GuiGenerator.Wind.class, ContainerGenerator.Wind.class);
        registerContainer(TileGenerator.Water.class, GuiGenerator.Water.class, ContainerGenerator.Water.class);
        registerContainer(TileGenerator.Nuke.class, GuiGenerator.Nuke.class, ContainerGenerator.Nuke.class);
        registerContainer(TileGenerator.Solar.class, GuiGenerator.Solar.class, ContainerGenerator.Solar.class);
        registerContainer(TileSensor.Temperature.class, GuiSensor.class, ContainerDummy.class);
        registerContainer(TileSensor.Energy.class, GuiSensor.class, ContainerDummy.class);
        registerContainer(TileSensor.RPM.class, GuiSensor.class, ContainerDummy.class);
    }

    protected void registerContainer(Class<? extends TileEntity> tile, Class<? extends GuiContainer> gui, Class<? extends Container> cont) {
        GuiHandler.guiMap.put(tile, gui);
        GuiHandler.containerMap.put(tile, cont);
    }

    protected void addTEMapping(Class c) {
        TileEntity.addMapping(c, c.getName());
    }

}
