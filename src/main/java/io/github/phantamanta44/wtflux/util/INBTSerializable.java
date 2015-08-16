package io.github.phantamanta44.wtflux.util;

import net.minecraft.nbt.NBTTagCompound;

public interface INBTSerializable {

	public void writeToNBT(NBTTagCompound tag);
	
	public void readFromNBT(NBTTagCompound tag);
	
}
