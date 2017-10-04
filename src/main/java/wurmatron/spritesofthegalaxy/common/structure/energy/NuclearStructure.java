package wurmatron.spritesofthegalaxy.common.structure.energy;

import wurmatron.spritesofthegalaxy.api.mutiblock.*;
import wurmatron.spritesofthegalaxy.api.research.IResearch;
import wurmatron.spritesofthegalaxy.common.research.ResearchHelper;
import wurmatron.spritesofthegalaxy.common.tileentity.TileHabitatCore;

import java.util.HashMap;

public class NuclearStructure implements IStructure, IProduction, IEnergy {

	@Override
	public String getName () {
		return "nuclear";
	}

	@Override
	public String getDisplayName () {
		return "Nuclear";
	}

	@Override
	public StructureType getStructureType () {
		return StructureType.ENERGY;
	}

	@Override
	public HashMap <IResearch, Integer> getRequiredResearch () {
		HashMap <IResearch, Integer> req = new HashMap <> ();
		req.put (ResearchHelper.nuclear,1);
		return req;
	}

	@Override
	public int getCost (int researchLevel,int structureTier) {
		return structureTier * 10000;
	}

	@Override
	public void addProduction (TileHabitatCore core,int structureTier) {
		core.addEnergy (structureTier * 10);
	}

	@Override
	public void removeProduction (TileHabitatCore core,int structureTier) {
		core.consumeEnergy (structureTier * 10);
	}

	@Override
	public int getAmountPerTier (TileHabitatCore core,int tier) {
		return tier * 10;
	}

	@Override
	public EnumProductionType getType () {
		return EnumProductionType.VALUE;
	}

	@Override
	public int getEnergyUsage (int tier) {
		return 0;
	}
}
