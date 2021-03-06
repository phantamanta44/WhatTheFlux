package io.github.phantamanta44.wtflux.inventory;

import com.google.common.collect.Maps;
import io.github.phantamanta44.wtflux.tile.TileGenerator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

import java.util.Map;

public abstract class ContainerGenerator extends ContainerMod<TileGenerator> {

    private static final Map<Class<? extends TileEntity>, Class<? extends ContainerGenerator>> contMap = Maps.newHashMap();

    static {
        contMap.put(TileGenerator.Furnace.class, Furnace.class);
        contMap.put(TileGenerator.Heat.class, Heat.class);
        contMap.put(TileGenerator.Wind.class, Wind.class);
        contMap.put(TileGenerator.Water.class, Water.class);
        contMap.put(TileGenerator.Nuke.class, Nuke.class);
        contMap.put(TileGenerator.Solar.class, Solar.class);
    }

    public static ContainerGenerator newInstance(InventoryPlayer ipl, TileGenerator te) {
        try {
            Class<? extends ContainerGenerator> clazz = contMap.get(te.getClass());
            return clazz.getDeclaredConstructor(InventoryPlayer.class, te.getClass()).newInstance(ipl, te);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public ContainerGenerator(InventoryPlayer inv, TileGenerator te) {
        tile = te;
        addPlayerInventory(inv);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int index) {
        Slot slot = (Slot)inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack stack = slot.getStack();
            ItemStack orig = stack.copy();
            if (index >= 36) {
                if (!this.mergeItemStack(stack, 0, 36, true))
                    return null;
            } else {
                for (int i = 36; i < 36 + tile.getSizeInventory(); i++) {
                    if (((Slot)inventorySlots.get(i)).isItemValid(stack)) {
                        if (!this.mergeItemStack(stack, i, i + 1, false))
                            return null;
                        if (stack.stackSize != orig.stackSize)
                            break;
                    }
                }
            }
            if (stack.stackSize <= 0)
                slot.putStack(null);
            else
                slot.onSlotChanged();
            if (stack.stackSize != orig.stackSize)
                return orig;
        }
        return null;
    }

    public static class Furnace extends ContainerGenerator {

        public Furnace(InventoryPlayer inv, TileGenerator.Furnace te) {
            super(inv, te);
            addSlotToContainer(new SlotMod(te, 0, 80, 43));
        }

    }

    public static class Heat extends ContainerGenerator {

        public Heat(InventoryPlayer inv, TileGenerator.Heat te) {
            super(inv, te);
            addSlotToContainer(new SlotMod(te, 0, 102, 28));
            addSlotToContainer(new SlotMod(te, 1, 102, 50));
        }

    }

    public static class Wind extends ContainerGenerator {

        public Wind(InventoryPlayer inv, TileGenerator.Wind te) {
            super(inv, te);
        }

    }

    public static class Water extends ContainerGenerator {

        public Water(InventoryPlayer inv, TileGenerator.Water te) {
            super(inv, te);
            addSlotToContainer(new SlotMod(te, 0, 80, 28));
            addSlotToContainer(new SlotMod(te, 1, 80, 50));
        }

    }

    public static class Nuke extends ContainerGenerator {

        public Nuke(InventoryPlayer inv, TileGenerator.Nuke te) {
            super(inv, te);
            addSlotToContainer(new SlotMod(te, 0, 74, 56));
            addSlotToContainer(new SlotMod(te, 1, 30, 12));
            addSlotToContainer(new SlotMod(te, 2, 30, 34));
            addSlotToContainer(new SlotMod(te, 3, 30, 56));
            addSlotToContainer(new SlotMod(te, 4, 52, 56));
            addSlotToContainer(new SlotMod(te, 5, 96, 56));
        }

    }

    public static class Solar extends ContainerGenerator {

        public Solar(InventoryPlayer inv, TileGenerator.Solar te) {
            super(inv, te);
            addSlotToContainer(new SlotMod(te, 0, 102, 28));
            addSlotToContainer(new SlotMod(te, 1, 102, 50));
        }

    }

}
