package pt.tecnico.aasma.wireflag.environment;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

import pt.tecnico.aasma.wireflag.GameElement;

public class Map implements GameElement {

	public enum Field {
		DESERT("desert", 0.5f), TREES("trees", 0.7f), HOLE("hole", 0), MOUNTAIN(
				"mountain", 0.1f), WATER("water", 0), LIMIT("limit", 0);

		private String name;
		private float value;

		Field(String name, float value) {
			this.name = name;
			this.value = value;
		}

		public static float getFieldValue(String fieldName) {
			for (Field field : Field.values()) {
				if (field.name.equals(fieldName))
					return field.value;
			}
			return 1f;
		}
	}

	private static final Map INSTANCE = new Map();
	private TiledMap grassMap;
	private static float[][] blocked;
	private final static int NTILES = 34;

	private Map() {
	}

	@Override
	public void init() throws SlickException {

		grassMap = new TiledMap("data/grassmap.tmx");
		blocked = new float[grassMap.getWidth()][grassMap.getHeight()];

		for (int xAxis = 0; xAxis < grassMap.getWidth(); xAxis++) {
			for (int yAxis = 0; yAxis < grassMap.getHeight(); yAxis++) {
				int tileID = grassMap.getTileId(xAxis, yAxis, 0);
				String value = grassMap.getTileProperty(tileID, "terrain",
						"grass");
				blocked[xAxis][yAxis] = Field.getFieldValue(value);
			}
		}
	}

	@Override
	public void render(Graphics g) {
		grassMap.render(0, 0);
	}

	public void update() {
	}

	public float getTileValue(float x, float y) {

		int xBlock = (int) x / NTILES;
		int yBlock = (int) y / NTILES;

		return blocked[xBlock][yBlock];
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

	public static Map getMap() {
		return INSTANCE;
	}

}
