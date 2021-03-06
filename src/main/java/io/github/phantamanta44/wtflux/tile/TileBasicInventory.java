package io.github.phantamanta44.wtflux.tile;

import io.github.phantamanta44.wtflux.lib.LibNBT;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;

public abstract class TileBasicInventory extends TileMod implements IInventory {

    protected ItemStack[] slots;

    public TileBasicInventory(int size) {
        super();
        slots = new ItemStack[size];
    }

    @Override
    public int getSizeInventory() {
        return slots.length;
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return slots[slot];
    }

    @Override
    public ItemStack decrStackSize(int slot, int amt) {
        if (slots[slot] != null) {
            if (slots[slot].stackSize <= amt) {
                ItemStack stack = slots[slot];
                slots[slot] = null;
                markDirty();
                return stack;
            }
            ItemStack stack = slots[slot].splitStack(amt);
            markDirty();
            return stack;
        }
        return null;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slot) {
        if (slots[slot] != null) {
            ItemStack stack = getStackInSlot(slot);
            slots[slot] = null;
            return stack;
        }
        return null;
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack stack) {
        slots[slot] = stack;
        markDirty();
    }

    @Override
    public String getInventoryName() {
        return getClass().getTypeName();
    }

    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return worldObj.getTileEntity(xCoord, yCoord, zCoord) == this
                && player.getDistanceSq(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D) < 64D;
    }

    @Override
    public void openInventory() {
        // NO-OP
    }

    @Override
    public void closeInventory() {
        // NO-OP
    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack stack) {
        return true;
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        if (tag.hasKey(LibNBT.ITEMS)) {
            NBTTagList tagList = tag.getTagList(LibNBT.ITEMS, 10);
            slots = new ItemStack[slots.length];
            for (int i = 0; i < tagList.tagCount(); i++) {
                NBTTagCompound itemTag = tagList.getCompoundTagAt(i);
                int slot = itemTag.getInteger(LibNBT.SLOT);
                slots[slot] = ItemStack.loadItemStackFromNBT(itemTag);
            }
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        NBTTagList tagList = new NBTTagList();
        for (int i = 0; i < slots.length; i++) {
            if (slots[i] != null) {
                NBTTagCompound itemTag = new NBTTagCompound();
                slots[i].writeToNBT(itemTag);
                itemTag.setInteger(LibNBT.SLOT, i);
                tagList.appendTag(itemTag);
            }
        }
        tag.setTag(LibNBT.ITEMS, tagList);
    }

    @Override
    public Packet getDescriptionPacket() {
        S35PacketUpdateTileEntity packet = (S35PacketUpdateTileEntity)super.getDescriptionPacket();
        packet.func_148857_g().removeTag(LibNBT.ITEMS);
        return packet;
    }

}
