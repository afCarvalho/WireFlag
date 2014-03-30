package gettheflag;

import java.util.Random;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class WireFlagGame extends BasicGame {

	private static final int MAP = 0;
	private static final int FLAG = 1;
	private static final int SPRITE = 2;
	private static final int CLIMATE = 3;
	private static final int TIME = 4;

	private GameElement[] elements;

	public WireFlagGame() {
		super("Wired-Flag");
	}

	public static void main(String[] arguments) {
		try {
			AppGameContainer app = new AppGameContainer(new WireFlagGame());
			app.setDisplayMode(1400, 800, false);
			app.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void init(GameContainer container) throws SlickException {

		Random random = new Random();
		elements = new GameElement[] { new Map(), new Flag(), new Sprite(this),
				new Climate(this), new Time(this) };

		for (GameElement e : elements) {
			e.init();
		}

		int xCoord = random.nextInt(getMapWidth());
		int yCoord = random.nextInt(getMapHeight());

		while (isBlocked(xCoord, yCoord)) {
			xCoord = random.nextInt(getMapWidth());
			yCoord = random.nextInt(getMapHeight());

		}

		getFlag().setCoords(xCoord, yCoord);

	}

	@Override
	public void update(GameContainer container, int delta)
			throws SlickException {

		getMap().update();
		getFlag().update();
		getSprite().update(container, delta);
		getClimate().update();
		getTime().update();

	}

	public void render(GameContainer container, Graphics g)
			throws SlickException {

		for (GameElement e : elements) {
			e.render(g);
		}

	}

	private Map getMap() {
		return (Map) elements[MAP];
	}

	private Climate getClimate() {
		return (Climate) elements[CLIMATE];
	}

	private Flag getFlag() {
		return (Flag) elements[FLAG];
	}

	private Time getTime() {
		return (Time) elements[TIME];
	}

	private Sprite getSprite() {
		return (Sprite) elements[SPRITE];
	}

	public int getMapHeight() {
		return getMap().getHeight() * getMap().getTileHeight();
	}

	public int getMapWidth() {
		return getMap().getWidth() * getMap().getTileWidth();
	}

	public boolean isBlocked(float xCoord, float yCoord) {
		return getMap().getTileType(xCoord, yCoord) == 0;
	}

	public float getTile(float xCoord, float yCoord) {
		return getMap().getTileType(xCoord, yCoord);
	}

	public int getNTiles() {
		return getMap().getNTiles();
	}

}