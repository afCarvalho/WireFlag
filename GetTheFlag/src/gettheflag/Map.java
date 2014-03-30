package gettheflag;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

public class Map implements GameElement {

	private TiledMap grassMap;
	private float[][] blocked;
	private int nTiles;

	public Map() {
	}

	@Override
	public void init() throws SlickException {

		nTiles = 34;

		grassMap = new TiledMap("data/grassmap.tmx");

		// build a collision map based on tile properties in the TileD map
		blocked = new float[grassMap.getWidth()][grassMap.getHeight()];

		for (int xAxis = 0; xAxis < grassMap.getWidth(); xAxis++) {
			for (int yAxis = 0; yAxis < grassMap.getHeight(); yAxis++) {
				int tileID = grassMap.getTileId(xAxis, yAxis, 0);
				String value = grassMap.getTileProperty(tileID, "terrain",
						"grass");

				if (value.equals("desert"))
					blocked[xAxis][yAxis] = 0.5f;
				else if (value.equals("trees"))
					blocked[xAxis][yAxis] = 0.7f;
				else if (value.equals("hole"))
					blocked[xAxis][yAxis] = 0;
				else if (value.equals("mountain"))
					blocked[xAxis][yAxis] = 0.1f;
				else if (value.equals("water"))
					blocked[xAxis][yAxis] = 0f;
				else if (value.equals("limit"))
					blocked[xAxis][yAxis] = 0f;
				else
					blocked[xAxis][yAxis] = 1f;

			}
		}

	}

	@Override
	public void render(Graphics g) {
		grassMap.render(0, 0);
	}

	public void update() {

	}

	public float getTileType(float x, float y) {

		// System.out.println((int) x / SIZE);
		// System.out.println(Math.round( x / SIZE));
		// int xBlockRound = Math.round(x / nTiles);
		// int yBlockRound = Math.round(y / nTiles);
		int xBlock = (int) x / nTiles;
		int yBlock = (int) y / nTiles;
		// if(blocked[xBlock][yBlock] == 0 || blocked[xBlockRound][yBlockRound]
		// )

		return blocked[xBlock][yBlock];
	}

	public int getHeight() {
		return grassMap.getHeight();
	}

	public int getWidth() {
		return grassMap.getWidth();
	}

	public int getTileHeight() {
		return grassMap.getTileHeight();
	}

	public int getTileWidth() {
		return grassMap.getTileWidth();
	}

	public int getNTiles() {
		return nTiles;
	}

}
