package pt.tecnico.aasma.wireflag.environment.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

import pt.tecnico.aasma.wireflag.environment.Perception;
import pt.tecnico.aasma.wireflag.environment.landscape.Landscape;
import pt.tecnico.aasma.wireflag.environment.landscape.LandscapeType;
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

	public int getNHorizontalTiles() {
		return grassMap.getWidth();
	}

	public int getNVerticalTiles() {
		return grassMap.getHeight();
	}

	public int getMapHeight() {
		return grassMap.getHeight() * grassMap.getTileHeight();
	}

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
	public Perception getTilePerception(int teamId, MapPosition pos) {
		Landscape land = getLandscape(pos);

		Perception perception = new Perception(pos);
		perception.setFlag(land.hasFlag());
		perception.setEnemy(land.hasAgent() && land.getAgent().isEnemy(teamId));
		perception.setEndPoint(land.hasEndPoint());
		perception.setAnimal(land.hasAnimal());
		perception.setNight(TimeController.getTime().isNight());
		perception.setFire(land.hasFire());
		perception.setExtremeWeather(land.getWeather().isExtremeWeather());

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

		for (int j = y + visibility; j >= y - visibility && j > 0
				&& j < getNVerticalTiles(); j--) {

			for (int i = x - visibility; i <= x + visibility && i > 0
					&& i < getNHorizontalTiles(); i++) {

				list.add(getTilePerception(teamId, new MapPosition(i, j)));
			}
		}

		return list;
	}

	/************************
	 *** STATE PREDICATES ***
	 ************************/

	public boolean isBlocked(MapPosition p) {
		Landscape land = getLandscape(p);
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
