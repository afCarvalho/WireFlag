package pt.tecnico.aasma.wireflag.environment.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

import pt.tecnico.aasma.wireflag.GameElement;
import pt.tecnico.aasma.wireflag.agent.Agent;
import pt.tecnico.aasma.wireflag.agent.architecture.Reactive;
import pt.tecnico.aasma.wireflag.agent.type.Builder;
import pt.tecnico.aasma.wireflag.environment.Perception;
import pt.tecnico.aasma.wireflag.environment.landscape.Landscape;
import pt.tecnico.aasma.wireflag.environment.landscape.factory.DesertFactory;
import pt.tecnico.aasma.wireflag.environment.landscape.factory.ForestFactory;
import pt.tecnico.aasma.wireflag.environment.landscape.factory.LandscapeFactory;
import pt.tecnico.aasma.wireflag.environment.landscape.factory.LimitFactory;
import pt.tecnico.aasma.wireflag.environment.landscape.factory.MountainFactory;
import pt.tecnico.aasma.wireflag.environment.landscape.factory.PlainFactory;
import pt.tecnico.aasma.wireflag.environment.landscape.factory.WaterFactory;
import pt.tecnico.aasma.wireflag.environment.object.EndPoint;
import pt.tecnico.aasma.wireflag.environment.object.Flag;
import pt.tecnico.aasma.wireflag.exception.LandscapeNotFoundException;
import pt.tecnico.aasma.wireflag.util.MapPosition;
import pt.tecnico.aasma.wireflag.util.WorldPosition;

public class MapController implements GameElement {

	public enum LandscapeType {
		DESERT("desert", new DesertFactory()), FOREST("trees",
				new ForestFactory()), HOLE("hole", new LimitFactory()), MOUNTAIN(
				"mountain", new MountainFactory()), WATER("water",
				new WaterFactory()), LIMIT("limit", new LimitFactory()), PLAIN(
				"plain", new PlainFactory());

		private String name;
		private LandscapeFactory factory;

		LandscapeType(String name, LandscapeFactory factory) {
			this.name = name;
			this.factory = factory;
		}

		public Landscape createLandscape(MapPosition pos) {
			return factory.createLandscape(pos);
		}

		public static Landscape getTileLandscape(String landscapeName,
				MapPosition pos) throws SlickException {

			for (LandscapeType land : LandscapeType.values()) {
				if (land.name.equals(landscapeName))
					return land.createLandscape(pos);
			}
			throw new LandscapeNotFoundException(landscapeName);
		}
	}

	private static final MapController INSTANCE = new MapController();
	private TiledMap grassMap;
	private static Landscape[][] tileMatrix;

	private MapController() {
	}

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

	public Landscape getLandscape(MapPosition p) {
		return tileMatrix[p.getX()][p.getY()];
	}

	public Landscape getLandscape(WorldPosition p) {
		return getLandscape(p.getMapPosition());
	}

	public float getMovementSpeed(MapPosition p) {
		return getLandscape(p).getMovementSpeed();
	}

	public boolean isBlocked(MapPosition p) {
		return getMovementSpeed(p) == 0;
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

	public static MapController getMap() {
		return INSTANCE;
	}

	private Landscape getLandscapeType(int x, int y) throws SlickException {
		int tileID = grassMap.getTileId(x, y, 0);
		String value = grassMap.getTileProperty(tileID, "terrain", "plain");
		return LandscapeType.getTileLandscape(value, new MapPosition(x, y));
	}

	/* for each tile is created a perception */
	public Perception getTilePerception(int teamId, MapPosition pos) {
		Landscape landscape = getLandscape(pos);

		Perception perception = new Perception(pos);

		if (landscape.hasFlag()) {
			perception.setFlag(true);
		}

		if (landscape.hasAgent()) {
			if (landscape.getAgent().isEnemy(teamId)) {
				perception.setEnemy(true);
			}
		}

		if (landscape.hasEndPoint()) {
			perception.setEndPoint(true);
		}

		if (landscape.hasAnimal()) {
			perception.setAnimal(true);
		}

		if (TimeController.isNight()) {
			perception.setNight(true);
		}

		if (landscape.hasFire()) {
			perception.setFire(true);

		}

		if (landscape.getWeather().isExtremeWeather()) {
			perception.setExtremeWeather(true);
		}

		return perception;
	}

	public List<Perception> getPerceptions(int teamId, MapPosition pos) {
		List<Perception> list = new ArrayList<Perception>();

		// TODO apply this code to each tile on field of vision
		list.add(getTilePerception(teamId, pos));

		return list;
	}
}
