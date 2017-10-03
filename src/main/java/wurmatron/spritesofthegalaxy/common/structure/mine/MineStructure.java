package wurmatron.spritesofthegalaxy.common.structure.mine;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import wurmatron.spritesofthegalaxy.api.mutiblock.*;
import wurmatron.spritesofthegalaxy.api.research.IResearch;
import wurmatron.spritesofthegalaxy.common.tileentity.TileHabitatCore;
import wurmatron.spritesofthegalaxy.common.utils.LogHandler;

import java.util.HashMap;

public class MineStructure implements IStructure, IProduction, ITickStructure {

	@Override
	public String getName () {
		return "mine";
	}

	@Override
	public String getDisplayName () {
		return "Basic Mine";
	}

	@Override
	public StructureType getStructureType () {
		return StructureType.MINE;
	}

	@Override
	public HashMap <IResearch, Integer> getRequiredResearch () {
		return null;
	}

	@Override
	public int getCost (int researchLevel,int structureTier) {
		if (structureTier == 0)
			return 10;
		return structureTier * 10;
	}

	@Override
	public void tickStructure (TileHabitatCore core,int tier) {
		core.addMinerals (tier * 5);
	}

	@Override
	public void addProduction (TileHabitatCore core,int structureTier) {
	}

	@Override
	public void removeProduction (TileHabitatCore core,int structureTier) {
	}

	@Override
	public int getAmountPerTier (TileHabitatCore core,int tier) {
		return tier * 5;
	}

	@Override
	public EnumProductionType getType () {
		return EnumProductionType.VALUE;
	}

	@Override
	public int getEnergyUsage (int tier) {
		return tier;
	}
}
