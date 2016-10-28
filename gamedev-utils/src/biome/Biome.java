package biome;

import java.util.ArrayList;
import java.util.HashMap;

import blocks.Block;

public class Biome {

	private static HashMap<Integer, Biome> idMap = new HashMap<Integer, Biome>();
	
	public static final Biome plains = new Biome("plains", 0, 0.5f, 0.5f, Block.grass_plains);
	public static final Biome forest = new Biome("forest", 1, 0.4f, 0.6f, Block.grass_forest);
	public static final Biome rivers = new Biome("rivers", 2, 0.5f,0.4f, Block.grass_plains);

	private ArrayList<BiomePart> biomeParts = new ArrayList<BiomePart>(), decoParts = new ArrayList<BiomePart>();
	private String name;
	private int id;
	private float temperature, precipitation;

	public Biome(String name, int id, float temperature, float precipitation, Block base) {
		setName(name);
		setId(id);
		setTemperature(temperature);
		setPrecipitation(precipitation);
		biomeParts.add(new BiomePart(base, 0, 1, 0));
	}
	
	public void addBiomePart(BiomePart part){
		biomeParts.add(part);
	}

	public ArrayList<BiomePart> getBiomeParts() {
		return biomeParts;
	}
	
	public void addDecoPart(BiomePart part){
		decoParts.add(part);
	}
	
	public ArrayList<BiomePart> getDecoParts(){
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
	
	public static Biome getBiome(int id){
		return idMap.get(id);
	}
	
	public static void createDefaultBiomes(){
		plains.addDecoPart(new BiomePart(Block.flowers, 0.3f, 0.5f, 0.8f));
		plains.addDecoPart(new BiomePart(Block.flower, 0.8f, 1f, 0.8f));
		
		forest.addBiomePart(new BiomePart(Block.water_river, 0.9f, 1f, 0));
		forest.addDecoPart(new BiomePart(Block.tree_birch_1, 0f, 0.4f, 0.85f));
		forest.addDecoPart(new BiomePart(Block.tree_oak_1, 0.45f, 0.85f, 0.85f));
		
		rivers.addBiomePart(new BiomePart(Block.water_river, 0.4f, 0.6f, 0));
		rivers.addBiomePart(new BiomePart(Block.sand_beach, 0.38f, 0.4f, 0));
		rivers.addBiomePart(new BiomePart(Block.sand_beach, 0.6f, 0.62f, 0));
		rivers.addDecoPart(new BiomePart(Block.tree_oak_1, 0, 0.36f, 0.95f));
	}

}
