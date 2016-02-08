package utils;

public class State {
	
	public static STATE state;

	public static enum STATE{
		MAIN_MENU, NEW_GAME_MENU, LOAD_GAME_MENU,
		CONNECT_MENU, GALACTIC, SOLAR,
		PLANETRY, SURFACE_PLANETRY, SURFACE_MOON,
		SURFACE_ASTEROID, SPACE_STATION, SHIP,
		DUNGEON, SPACE_BATTLE
	}
	
}
