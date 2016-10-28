package biome;

import blocks.Block;

public class BiomePart {

	private Block block;
	private float start, end, chance;

	public BiomePart(Block block, float start, float end, float chance) {
		setBlock(block);
		setStart(start);
		setEnd(end);
		setChance(chance);
	}

	public Block getBlock() {
		return block;
	}

	public void setBlock(Block block) {
		this.block = block;
	}

	public float getStart() {
		return start;
	}

	public void setStart(float start) {
		this.start = start;
	}

	public float getEnd() {
		return end;
	}

	public void setEnd(float end) {
		this.end = end;
	}

	public float getChance() {
		return chance;
	}

	public void setChance(float chance) {
		this.chance = chance;
	}

}
