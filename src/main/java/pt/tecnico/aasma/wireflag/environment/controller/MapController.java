package pt.tecnico.aasma.wireflag.environment.controller;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

import pt.tecnico.aasma.wireflag.GameElement;
import pt.tecnico.aasma.wireflag.environment.landscape.Landscape;
import pt.tecnico.aasma.wireflag.environment.landscape.factory.DesertFactory;
import pt.tecnico.aasma.wireflag.environment.landscape.factory.ForestFactory;
import pt.tecnico.aasma.wireflag.environment.landscape.factory.LandscapeFactory;
import pt.tecnico.aasma.wireflag.environment.landscape.factory.LimitFactory;
import pt.tecnico.aasma.wireflag.environment.landscape.factory.MountainFactory;
import pt.tecnico.aasma.wireflag.environment.landscape.factory.PlainFactory;
import pt.tecnico.aasma.wireflag.environment.landscape.factory.WaterFactory;
import pt.tecnico.aasma.wireflag.exception.LandscapeNotFoundException;

public class MapController implements GameElement {

	public enum LandscapeType {
		DESERT("desert", new DesertFactory()), FOREST("trees",
				new ForestFactory()), HOLE("hole", new LimitFactory()), MOUNTAIN(
				"mountain", new MountainFactory()), WATER("water",
				new WaterFactory()), LIMIT("limit", new LimitFactory()), PLAIN(
				"grass", new PlainFactory());

		private String name;
		private LandscapeFactory factory;

		LandscapeType(String name, LandscapeFactory factory) {
			this.name = name;
			this.factory = factory;
		}

		public static Landscape getTileLandscape(String landscapeName)
				throws LandscapeNotFoundException {

			for (LandscapeType land : LandscapeType.values()) {
				if (land.name.equals(landscapeName))
					return land.getLandscape();
			}
			throw new LandscapeNotFoundException(landscapeName);
		}

		public Landscape getLandscape() {
			return factory.createLandscape();
		}
	}

	private static final MapController INSTANCE = new MapController();
	private TiledMap grassMap;
	private static Landscape[][] blocked;
	private final static int NTILES = 34;

	private MapController() {
	}

	@Override
	public void init() throws SlickException {

		grassMap = new TiledMap("data/grassmap.tmx");
		blocked = new Landscape[grassMap.getWidth()][grassMap.getHeight()];

		for (int xAxis = 0; xAxis < grassMap.getWidth(); xAxis++) {
			for (int yAxis = 0; yAxis < grassMap.getHeight(); yAxis++) {
				blocked[xAxis][yAxis] = getLandscapeType(xAxis, yAxis);
			}
		}
	}

	@Override
	public void render(Graphics g) {
		grassMap.render(0, 0);
	}

	public void update(int delta) {
	}

	public float getTileValue(float x, float y) {

		int xBlock = (int) x / NTILES;
		int yBlock = (int) y / NTILES;

		return blocked[xBlock][yBlock].getMovementSpeed();
	}

	public int getMapHeight() {
		return grassMap.getHeight() * grassMap.getTileHeight();
	}

	public int getMapWidth() {
		return grassMap.getWidth() * grassMap.getTileWidth();
	}

	public boolean isBlocked(float xCoord, float yCoord) {
		return getTileValue(xCoord, yCoord) == 0;
	}

	public int getNTiles() {
		return NTILES;
	}

	public static MapController getMap() {
		return INSTANCE;
	}

	private Landscape getLandscapeType(int xCoord, int yCoord) {
		try {
			int tileID = grassMap.getTileId(xCoord, yCoord, 0);
			String value = grassMap.getTileProperty(tileID, "terrain", "grass");
			return LandscapeType.getTileLandscape(value);
		} catch (LandscapeNotFoundException e) {
			System.out.println(e.toString());
			System.exit(0);
		}
		return null;
	}
}
