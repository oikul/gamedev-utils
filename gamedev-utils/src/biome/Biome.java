package biome;

import java.util.ArrayList;
import java.util.HashMap;

import blocks.Block;

public class Biome {

	private static HashMap<Integer, Biome> idMap = new HashMap<Integer, Biome>();

	// very cold biomes
	public static Biome polar_desert = new Biome("polar desert", 0, Block.snow);
	public static Biome ice_spikes = new Biome("ice spikes", 1, Block.snow);
	public static Biome frozen_lakes = new Biome("frozen lakes", 2, Block.snow);
	public static Biome ice_sheet = new Biome("ice sheet", 3, Block.ice);
	public static Biome ice_bergs = new Biome("ice bergs", 4, Block.water_ocean);

	// cold biomes
	public static Biome tundra = new Biome("tundra", 5, Block.grass_tundra);
	public static Biome mountain = new Biome("mountain", 6, Block.grass_mountains);
	public static Biome taiga = new Biome("taiga", 7, Block.grass_snowy);
	public static Biome mountain_forest = new Biome("mountain forest", 8, Block.grass_mountains);
	public static Biome ocean = new Biome("ocean", 9, Block.water_ocean);

	// medium biomes
	public static Biome steppe = new Biome("steppe", 10, Block.grass_steppe);
	public static Biome plains = new Biome("plains", 11, Block.grass_plains);
	public static Biome forest = new Biome("forest", 12, Block.grass_forest);
	public static Biome lakes = new Biome("lakes", 13, Block.grass_plains);
	public static Biome islands = new Biome("islands", 14, Block.water_ocean);

	// hot biomes
	public static Biome desert_plains = new Biome("desert plains", 15, Block.sand);
	public static Biome canyon = new Biome("canyon", 16, Block.clay);
	public static Biome savannah = new Biome("savannah", 17, Block.grass_savannah);
	public static Biome jungle = new Biome("jungle", 18, Block.grass_jungle);
	public static Biome rainforest = new Biome("rainforest", 19, Block.grass_rainforest);

	// very hot biomes
	public static Biome lava_ocean = new Biome("lava ocean", 20, Block.lava);
	public static Biome lava_islands = new Biome("lava islands", 21, Block.lava);
	public static Biome lava_lakes = new Biome("lava lakes", 22, Block.stone_volcanic);
	public static Biome igneous_desert = new Biome("igneous desert", 23, Block.stone_volcanic);
	public static Biome volcanic_mountains = new Biome("volcanic_mountains", 24, Block.stone_volcanic);

	private ArrayList<BiomePart> biomeParts = new ArrayList<BiomePart>(), decoParts = new ArrayList<BiomePart>();
	private String name;
	private int id;
	private float temperature, precipitation;

	public Biome(String name, int id, Block base) {
		setName(name);
		setId(id);
		idMap.put(id, this);
		setTemperature(temperature);
		setPrecipitation(precipitation);
		biomeParts.add(new BiomePart(base, 0, 1, 1));
	}

	public void addBiomePart(BiomePart part) {
		biomeParts.add(part);
	}

	public ArrayList<BiomePart> getBiomeParts() {
		return biomeParts;
	}

	public void addDecoPart(BiomePart part) {
		decoParts.add(part);
	}

	public ArrayList<BiomePart> getDecoParts() {
		return decoParts;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
		idMap.put(id, this);
	}

	public float getTemperature() {
		return temperature;
	}

	public void setTemperature(float temperature) {
		this.temperature = temperature;
	}

	public float getPrecipitation() {
		return precipitation;
	}

	public void setPrecipitation(float precipitation) {
		this.precipitation = precipitation;
	}

	public static Biome getBiome(int id) {
		return idMap.get(id);
	}

	public static void createDefaultBiomes() {
		//very cold
		polar_desert.addBiomePart(new BiomePart(Block.ice, 0.4f, 0.5f, 1f));
		
		ice_spikes.addBiomePart(new BiomePart(Block.ice, 0f, 0.4f, 1f));
		ice_spikes.addDecoPart(new BiomePart(Block.ice_spikes, 0.3f, 0.7f, 0.01f));
		
		frozen_lakes.addBiomePart(new BiomePart(Block.ice, 0.7f, 1f, 1f));
		
		ice_sheet.addBiomePart(new BiomePart(Block.water_river, 0.35f, 0.5f, 1f));
		
		ice_bergs.addBiomePart(new BiomePart(Block.ice, 0.6f, 1f, 1f));
		
		//cold
		tundra.addBiomePart(new BiomePart(Block.water_murky, 0.7f, 1f, 1f));
		
		mountain.addBiomePart(new BiomePart(Block.water_ocean, 0f, 0.4f, 1f));
		mountain.addBiomePart(new BiomePart(Block.sand, 0.4f, 0.45f, 1f));
		mountain.addBiomePart(new BiomePart(Block.stone_solid, 0.7f, 1f, 1f));
		mountain.addBiomePart(new BiomePart(Block.stone_snowy, 0.8f, 1f, 1f));
		mountain.addBiomePart(new BiomePart(Block.snow, 0.9f, 1f, 1f));
		mountain.addDecoPart(new BiomePart(Block.rocks, 0.65f, 0.75f, 0.1f));
		mountain.addDecoPart(new BiomePart(Block.tree_pine, 0.45f, 0.7f, 0.1f));
		
		taiga.addBiomePart(new BiomePart(Block.water_river, 0.4f, 0.5f, 1f));
		taiga.addDecoPart(new BiomePart(Block.tree_pine, 0f, 0.4f, 0.1f));
		taiga.addDecoPart(new BiomePart(Block.tree_pine, 0.5f, 1f, 0.1f));
		
		mountain_forest.addBiomePart(new BiomePart(Block.water_ocean, 0f, 0.4f, 1f));
		mountain_forest.addBiomePart(new BiomePart(Block.sand, 0.4f, 0.45f, 1f));
		mountain_forest.addBiomePart(new BiomePart(Block.stone_solid, 0.7f, 1f, 1f));
		mountain_forest.addBiomePart(new BiomePart(Block.stone_snowy, 0.8f, 1f, 1f));
		mountain_forest.addBiomePart(new BiomePart(Block.snow, 0.9f, 1f, 1f));
		mountain_forest.addDecoPart(new BiomePart(Block.rocks, 0.65f, 0.75f, 0.1f));
		mountain_forest.addDecoPart(new BiomePart(Block.tree_pine, 0.45f, 0.7f, 0.2f));
		mountain_forest.addDecoPart(new BiomePart(Block.tree_spruce, 0.45f, 0.7f, 0.2f));
		
		ocean.addBiomePart(new BiomePart(Block.rock, 0f, 0.1f, 1f));
		ocean.addDecoPart(new BiomePart(Block.rocks, 0.05f, 0.25f, 0.1f));
		ocean.addDecoPart(new BiomePart(Block.rocks, 0.5f, 1f, 0.05f));
		
		//medium
		steppe.addDecoPart(new BiomePart(Block.tree_shrub, 0f, 1f, 0.05f));
		steppe.addDecoPart(new BiomePart(Block.tree_shrub_1, 0.5f, 1f, 0.1f));
		
		plains.addDecoPart(new BiomePart(Block.flowers, 0.3f, 0.5f, 0.2f));
		plains.addDecoPart(new BiomePart(Block.flower, 0.8f, 1f, 0.2f));
		plains.addDecoPart(new BiomePart(Block.tree_oak, 0f, 0.4f, 0.001f));
		plains.addDecoPart(new BiomePart(Block.tree_shrub, 0f, 1f, 0.03f));

		forest.addBiomePart(new BiomePart(Block.water_river, 0.9f, 1f, 1f));
		forest.addBiomePart(new BiomePart(Block.sand, 0.83f, 0.9f, 1f));
		forest.addDecoPart(new BiomePart(Block.tree_birch, 0f, 0.8f, 0.2f));
		forest.addDecoPart(new BiomePart(Block.tree_oak_1, 0f, 0.8f, 0.2f));
		forest.addDecoPart(new BiomePart(Block.tree_pine, 0f, 0.8f, 0.1f));
		
		lakes.addBiomePart(new BiomePart(Block.water_river, 0.7f, 1f, 1f));
		lakes.addDecoPart(new BiomePart(Block.tree_shrub_1, 0f, 0.7f, 0.01f));
		
		islands.addBiomePart(new BiomePart(Block.sand, 0.8f, 0.9f, 1f));
		islands.addBiomePart(new BiomePart(Block.grass_jungle, 0.9f, 1f, 1f));
		islands.addDecoPart(new BiomePart(Block.tree_palm, 0.9f, 1f, 0.03f));
		islands.addDecoPart(new BiomePart(Block.rocks, 0f, 0.3f, 0.05f));

		//hot
		
		//very hot
	}

}
