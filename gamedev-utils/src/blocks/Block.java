package blocks;

import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;

import handlers.ResourceHandler;

public class Block {

	// decoration
	public static final Block crates = new Block("decoration/crates.png", 0, true, 16, 16);
	public static final Block flower = new Block("decoration/flower.png", 0, false, 16, 16);
	public static final Block flowers = new Block("decoration/flowers.png", 0, false, 16, 16);

	// grass blocks
	public static final Block grass_forest = new Block("grass/grass_forest.png", 0, false, 16, 16);
	public static final Block grass_jungle = new Block("grass/grass_jungle.png", 0, false, 16, 16);
	public static final Block grass_mountains = new Block("grass/grass_mountains.png", 0, false, 16, 16);
	public static final Block grass_plains = new Block("grass/grass_plains.png", 0, false, 16, 16);
	public static final Block grass_rainforest = new Block("grass/grass_rainforest.png", 0, false, 16, 16);
	public static final Block grass_savannah = new Block("grass/grass_savannah.png", 0, false, 16, 16);
	public static final Block grass_snowy = new Block("grass/grass_snowy.png", 0, false, 16, 16);
	public static final Block grass_steppe = new Block("grass/grass_steppe.png", 0, false, 16, 16);
	public static final Block grass_tundra = new Block("grass/grass_tundra.png", 0, false, 16, 16);

	// ice
	public static final Block ice_spikes = new Block("ice/ice_spikes.png", 0, true, 16, 16);
	public static final Block ice = new Block("ice/ice.png", 0, false, 16, 16);
	public static final Block snow = new Block("ice/snow.png", 0, false, 16, 16);

	// sand blocks
	public static final Block sand_arid = new Block("sand/sand_arid.png", 0, false, 16, 16);
	public static final Block sand_beach = new Block("sand/sand_beach.png", 0, false, 16, 16);
	public static final Block sand_cracked = new Block("sand/sand_cracked.png", 0, false, 16, 16);
	public static final Block sand_dunes = new Block("sand/sand_dunes.png", 0, false, 16, 16);
	public static final Block sand = new Block("sand/sand.png", 0, false, 16, 16);

	// stone
	public static final Block clay = new Block("stone/clay.png", 0, false, 16, 16);
	public static final Block rock = new Block("stone/rock.png", 0, false, 16, 16);
	public static final Block rocks = new Block("stone/rocks.png", 0, false, 16, 16);
	public static final Block stone_mossy = new Block("stone/stone_mossy.png", 0, false, 16, 16);
	public static final Block stone_snowy = new Block("stone/stone_snowy.png", 0, false, 16, 16);
	public static final Block stone_solid = new Block("stone/stone_solid.png", 0, false, 16, 16);
	public static final Block stone_volcanic = new Block("stone/stone_volcanic.png", 0, false, 16, 16);
	public static final Block stone = new Block("stone/stone.png", 0, false, 16, 16);

	// tree blocks
	public static final Block tree_baobab = new Block("trees/tree_baobab.png", 0, true, 16, 16);
	public static final Block tree_birch_1 = new Block("trees/tree_birch_1.png", 0, true, 16, 16);
	public static final Block tree_birch = new Block("trees/tree_birch.png", 0, true, 16, 16);
	public static final Block tree_cactus = new Block("trees/tree_cactus.png", 0, true, 16, 16);
	public static final Block tree_oak_1 = new Block("trees/tree_oak_1.png", 0, true, 16, 16);
	public static final Block tree_oak = new Block("trees/tree_oak.png", 0, true, 16, 16);
	public static final Block tree_palm = new Block("trees/tree_palm.png", 0, true, 16, 16);
	public static final Block tree_pine = new Block("trees/tree_pine.png", 0, true, 16, 16);
	public static final Block tree_rubber = new Block("trees/tree_rubber.png", 0, true, 16, 16);
	public static final Block tree_sequoia = new Block("trees/tree_sequoia.png", 0, true, 16, 16);
	public static final Block tree_shrub_1 = new Block("trees/tree_shrub_1.png", 0, false, 16, 16);
	public static final Block tree_shrub = new Block("trees/tree_shrub.png", 0, false, 16, 16);
	public static final Block tree_spruce = new Block("trees/tree_spruce.png", 0, true, 16, 16);

	// liquids
	public static final Block lava = new Block("lava/lava.png", 500, true, 16, 16);
	public static final Block water_murky = new Block("water/water_murky.png", 500, true, 16, 16);
	public static final Block water_ocean = new Block("water/water_ocean.png", 500, true, 16, 16);
	public static final Block water_river = new Block("water/water_river.png", 500, true, 16, 16);

	// misc
	/*public static final Block iron = new Block("ship_interior/iron.png", 0, false, 16, 16);
	public static final Block engine_fire = new Block("ship_interior/engine_fire_sprites.png", 200, false, 64, 192);
	public static final Block engine = new Block("ship_interior/engine.png", 0, true, 16, 16);
	public static final Block ship_walls = new Block("ship_interior/ship_walls.png", 0, true, 16, 16);
	public static final Block glass = new Block("ship_interior/glass.png", 0, true, 16, 16);
	public static final Block ship_lights = new Block("ship_interior/ship_lights.png", 0, true, 16, 16);
	public static final Block control_desk = new Block("ship_interior/control_desk.png", 0, false, 16, 16);
	public static final Block wood = new Block("wood/wood.png", 0, false, 16, 16);
	public static final Block counter = new Block("wood/counter.png", 0, true, 16, 16);
	public static final Block smallship = new Block("spaceship/smallShipParked.png", 0, true, 16, 16);
	public static final Block bigship = new Block("spaceship/EnemyCommandShip2.png", 0, true, 16, 16);
	public static final Block smallshipf = new Block("spaceship/smallShipParkedFlip.png", 0, true, 16, 16);
	public static final Block bigshipf = new Block("spaceship/EnemyCommandShip2Flip.png", 0, true, 16, 16);
	public static final Block smallfighter = new Block("spaceship/smallFighterParked.png", 0, true, 16, 16);
	public static final Block smallfighterf = new Block("spaceship/smallFighterParkedFlip.png", 0, true, 16, 16);
	public static final Block entrance = new Block("entrance.png", 0, true, 16, 16);*/

	private Image[] textures;
	private long time, animationWaitTime;
	private int index, width, height;
	private boolean solid;
	private static ArrayList<Block> blocks = new ArrayList<Block>();

	public Block(String path, long animationWaitTime, boolean solid, int width, int height) {
		textures = ResourceHandler.getBlockSprites(path, width, height);
		this.animationWaitTime = animationWaitTime;
		time = System.currentTimeMillis();
		this.setSolid(solid);
		this.setWidth(width);
		this.setHeight(height);
		blocks.add(this);
	}
	
	public static void updateAll(){
		for(Block b : blocks){
			b.update();
		}
	}

	public void update() {
		long newTime = System.currentTimeMillis();
		if (newTime > time + animationWaitTime) {
			time = newTime;
			if (index < textures.length) {
				index++;
			} else {
				index = 0;
			}
		}
	}

	public void draw(Graphics2D g2d, int x, int y) {
		g2d.drawImage(textures[index], x, y, width, height, null);
	}

	public void draw(Graphics2D g2d, int x, int y, int multiplier) {
		g2d.drawImage(textures[index], x, y, width * multiplier, height * multiplier, null);
	}

	public boolean isSolid() {
		return solid;
	}

	public void setSolid(boolean solid) {
		this.solid = solid;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

}
