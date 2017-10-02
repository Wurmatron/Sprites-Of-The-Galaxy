package wurmatron.spritesofthegalaxy.common.network.server;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import wurmatron.spritesofthegalaxy.api.SpritesOfTheGalaxyAPI;
import wurmatron.spritesofthegalaxy.api.mutiblock.IStructure;
import wurmatron.spritesofthegalaxy.common.network.CustomMessage;
import wurmatron.spritesofthegalaxy.common.reference.NBT;
import wurmatron.spritesofthegalaxy.common.tileentity.TileHabitatCore;
import wurmatron.spritesofthegalaxy.common.utils.LogHandler;
import wurmatron.spritesofthegalaxy.common.utils.MutiBlockHelper;

import java.io.IOException;
import java.util.HashMap;

public class DestroyStructureMessage extends CustomMessage.CustomtServerMessage <DestroyStructureMessage> {

	private NBTTagCompound data;

	public DestroyStructureMessage () {
	}

	public DestroyStructureMessage (IStructure research,int destroyAmount,BlockPos coreLoc) {
		data = new NBTTagCompound ();
		data.setString (NBT.STRUCTURES,research.getName ());
		data.setInteger (NBT.LEVEL,destroyAmount);
		data.setIntArray (NBT.POSITION,new int[] {coreLoc.getX (),coreLoc.getY (),coreLoc.getZ ()});
	}

	@Override
	protected void read (PacketBuffer buff) throws IOException {
		data = buff.readCompoundTag ();
	}

	@Override
	protected void write (PacketBuffer buff) throws IOException {
		buff.writeCompoundTag (data);
	}

	@Override
	public void process (EntityPlayer player,Side side) {
		int[] coreLoc = data.getIntArray (NBT.POSITION);
		IStructure structure = SpritesOfTheGalaxyAPI.getStructureFromName (data.getString (NBT.STRUCTURES));
		int tier = data.getInteger (NBT.LEVEL);
		BlockPos coreLocation = new BlockPos (coreLoc[0],coreLoc[1],coreLoc[2]);
		if (player.world.getTileEntity (coreLocation) != null && player.world.getTileEntity (coreLocation) instanceof TileHabitatCore) {
			TileHabitatCore core = (TileHabitatCore) player.world.getTileEntity (coreLocation);
			if (core != null) {
				int currentLevel = getStructureLevel (core,structure) - tier;
				core.addMinerals (MutiBlockHelper.calcMineralsForStructure (structure,tier,getStructureLevel (core,structure),0));
				core.removeStructure (structure);
				core.addStructure (structure,currentLevel);
			}
		}
	}

	private int getStructureLevel (TileHabitatCore tile,IStructure structure) {
		if (structure != null && tile != null) {
			HashMap <IStructure, Integer> currentStructure = tile.getStructures ();
			if (currentStructure != null && currentStructure.size () > 0 && currentStructure.containsKey (structure))
				return currentStructure.get (structure);
		}
		return 0;
	}
}
