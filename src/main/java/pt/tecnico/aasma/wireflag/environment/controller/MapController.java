package pt.tecnico.aasma.wireflag.environment.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

import pt.tecnico.aasma.wireflag.agent.Agent;
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
    @Deprecated
	private Perception getTilePerception(int teamId, int id, MapPosition pos) {
		Landscape land = getLandscape(pos);

		Perception perception = new Perception(pos, land.getRating());
		perception.setFlag(land.hasFlag());
		perception.setEnemy(land.hasAgent() && land.getAgent().isEnemy(teamId));
		perception.setEndPoint(land.hasEndPoint());
		perception.setAnimal(land.hasAnimal());
		perception.setFire(land.hasFire());
		perception.setExtremeWeather(land.getWeather().isExtremeWeather());
		perception.setBlocked(isBlocked(pos));

		Agent agent = land.getAgent();
		if (agent != null) {
			perception.setAgentAttack(agent.getAgentAttack());
			perception.setTiredAgent(agent.hasFatigue());
			perception.setInjuredAgent(agent.hasLowLife());
		}

		return perception;
	}

	/*
	 * returns a list with a perception for each tile in the agent's visibility
	 */
    @Deprecated
	public List<Perception> getPerceptions(int teamId, MapPosition pos,
			int visibility) {
		List<Perception> list = new ArrayList<Perception>();

		int x = pos.getX();
		int y = pos.getY();
		int id = 0;

		for (int j = y + visibility; j >= y - visibility; j--) {
			for (int i = x - visibility; i <= x + visibility; i++) {

				if (j < getNVerticalTiles() && i < getNHorizontalTiles()
						&& j > 0 && i > 0) {
					list.add(getTilePerception(teamId, id++, new MapPosition(i,
							j)));
				}
			}
		}

		return list;
	}

	/************************
	 *** STATE PREDICATES ***
	 ************************/

	public boolean isBlocked(MapPosition p) {
		Landscape land = getLandscape(p);

		return getMovementSpeed(p) == 0 || land.hasAnimal() || land.hasAgent()
				|| land.hasEndPoint();
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
		int tileWidth = MapController.getMap().getTileWidth();
		int tileHeight = MapController.getMap().getTileHeight();

		/*g.setColor(new Color(Color.gray));
		for (int xAxis = 0; xAxis < grassMap.getWidth(); xAxis++) {
			g.drawLine(xAxis * tileWidth, 0, xAxis * tileWidth, MapController
					.getMap().getMapHeight());
			for (int yAxis = 0; yAxis < grassMap.getHeight(); yAxis++) {
				g.drawLine(0, yAxis * tileHeight, MapController.getMap()
						.getMapWidth(), yAxis * tileHeight);
			}
		}*/

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
