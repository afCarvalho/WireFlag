package pt.tecnico.aasma.wireflag.environment.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

import pt.tecnico.aasma.wireflag.environment.landscape.Landscape;
import pt.tecnico.aasma.wireflag.environment.landscape.LandscapeType;
import pt.tecnico.aasma.wireflag.environment.perception.Perception;
import pt.tecnico.aasma.wireflag.util.MapPosition;
import pt.tecnico.aasma.wireflag.util.WorldPosition;

public class MapController implements IController {

	private static final MapController INSTANCE = new MapController();
	private TiledMap grassMap;
	private Landscape[][] tileMatrix;

	private MapController() {
	}

	/***************
	 *** GETTERS ***
	 ***************/

	public static MapController getMap() {
		return INSTANCE;
	}

	public Landscape getLandscape(MapPosition p) {
		return tileMatrix[p.getX()][p.getY()];
	}

	public Landscape getLandscape(WorldPosition p) {
		return getLandscape(p.getMapPosition());
	}

	public float getMovementSpeed(MapPosition p) {
		return getLandscape(p).getMovementSpeed();
	}

	/* return number of horizontal tiles */
	public int getNHorizontalTiles() {
		return grassMap.getWidth();
	}

	/* return number of vertical tiles */
	public int getNVerticalTiles() {
		return grassMap.getHeight();
	}

	/* return total height of the map */
	public int getMapHeight() {
		return grassMap.getHeight() * grassMap.getTileHeight();
	}

	/* return total width of the map */
	public int getMapWidth() {
		return grassMap.getWidth() * grassMap.getTileWidth();
	}

	public int getTileWidth() {
		return grassMap.getTileWidth();
	}

	public int getTileHeight() {
		return grassMap.getTileHeight();
	}

	public MapPosition getRandomPosition() {
		Random r = new Random();

		int x = r.nextInt(getNHorizontalTiles());
		int y = r.nextInt(getNVerticalTiles());
		return new MapPosition(x, y);
	}

	private Landscape getLandscapeType(int x, int y) throws SlickException {
		int tileID = grassMap.getTileId(x, y, 0);
		String value = grassMap.getTileProperty(tileID, "terrain", "plain");
		return LandscapeType.getTileLandscape(value, new MapPosition(x, y));
	}

	/* for each tile is created a perception */
	private Perception getTilePerception(int teamId, int id, MapPosition pos) {
		Landscape land = getLandscape(pos);

		Perception perception = new Perception(pos, id);
		perception.setFlag(land.hasFlag());
		perception.setEnemy(land.hasAgent() && land.getAgent().isEnemy(teamId));
		perception.setEndPoint(land.hasEndPoint());
		perception.setAnimal(land.hasAnimal());
		perception.setNight(TimeController.getTime().isNight());
		perception.setFire(land.hasFire());
		perception.setExtremeWeather(land.getWeather().isExtremeWeather());
		perception.setAgentAttack(land.getAgent().getAgentAttack());
		perception.setTiredAgent(land.getAgent().hasFatigue());
		perception.setInjuredAgent(land.getAgent().hasLowLife());
		return perception;
	}

	/*
	 * returns a list with a perception for each tile in the agent's visibility
	 */
	public List<Perception> getPerceptions(int teamId, MapPosition pos,
			int visibility) {
		List<Perception> list = new ArrayList<Perception>();

		int x = pos.getX();
		int y = pos.getY();
		int id = 0;

		for (int j = y + visibility; j >= y - visibility && j > 0
				&& j < getNVerticalTiles(); j--) {

			for (int i = x - visibility; i <= x + visibility && i > 0
					&& i < getNHorizontalTiles(); i++) {

				list.add(getTilePerception(teamId, id++, new MapPosition(i, j)));
			}
		}

		return list;
	}

	/************************
	 *** STATE PREDICATES ***
	 ************************/

	public boolean isBlocked(MapPosition p) {
		Landscape land = getLandscape(p);

		// Debug begin
		System.out.println("getMovementSpeed(p) == 0: " + getMovementSpeed(p));
		System.out.println("getMovementSpeed(p) == 0: " + getMovementSpeed(p));
		System.out.println("land.hasAgent(): " + land.hasAgent());
		// Debug end

		return getMovementSpeed(p) == 0 || land.hasAnimal() || land.hasAgent();
	}

	/*********************
	 *** GAME RELATED ****
	 *********************/

	@Override
	public void init() throws SlickException {

		grassMap = new TiledMap(System.getProperty("map") + "grassmap.tmx");
		tileMatrix = new Landscape[grassMap.getWidth()][grassMap.getHeight()];

		for (int xAxis = 0; xAxis < grassMap.getWidth(); xAxis++) {
			for (int yAxis = 0; yAxis < grassMap.getHeight(); yAxis++) {
				tileMatrix[xAxis][yAxis] = getLandscapeType(xAxis, yAxis);
			}
		}
	}

	@Override
	public void render(Graphics g) {
		grassMap.render(0, 0);

		for (int xAxis = 0; xAxis < grassMap.getWidth(); xAxis++) {
			for (int yAxis = 0; yAxis < grassMap.getHeight(); yAxis++) {
				tileMatrix[xAxis][yAxis].render(g);
			}
		}
	}

	public void update(int delta) throws SlickException {
		for (int xAxis = 0; xAxis < grassMap.getWidth(); xAxis++) {
			for (int yAxis = 0; yAxis < grassMap.getHeight(); yAxis++) {
				tileMatrix[xAxis][yAxis].update(delta);
			}
		}
	}

}
