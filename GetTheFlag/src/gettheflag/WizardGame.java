/*  
 * To change this template, choose Tools | Templates  
 * and open the template in the editor.  
 */
package gettheflag;

import java.util.LinkedList;
import java.util.Random;

import org.newdawn.slick.Animation;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.tiled.TiledMap;

/**
 * 
 * @author panos
 */
public class WizardGame extends BasicGame {
	private TiledMap grassMap;
	private TiledMap b;
	private Animation sprite, up, down, left, right, water, bread, rainWeather;
	private float x = 34f, y = 34f;
	private int hp = 10000;
	private LinkedList<CoordinatesPair> breadCrumbs = new LinkedList<CoordinatesPair>();
	private String climate = "";

	/**
	 * The collision map indicating which tiles block movement - generated based
	 * on tile properties
	 */
	private double[][] blocked;
	private static final int SIZE = 34;
	boolean active = false;
	private int aa = 0;
	private int bb = 0;
	Random random = new Random();
	int play = random.nextInt(4);
	int hora = 7;
	int minutos = 0;
	double segundos = 0;

	public WizardGame() {
		super("Wizard game");
	}

	public static void main(String[] arguments) {
		try {
			AppGameContainer app = new AppGameContainer(new WizardGame());
			app.setDisplayMode(1400, 800, false);
			app.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void init(GameContainer container) throws SlickException {
		Image[] movementUp = { new Image("data/wmg1_bk1.png"),
				new Image("data/wmg1_bk2.png") };
		Image[] movementDown = { new Image("data/wmg1_fr1.png"),
				new Image("data/wmg1_fr2.png") };
		Image[] movementLeft = { new Image("data/wmg1_lf1.png"),
				new Image("data/wmg1_lf2.png") };
		Image[] movementRight = { new Image("data/wmg1_rt1.png"),
				new Image("data/wmg1_rt2.png") };
		Image[] waterImage = { new Image("data/SmallFlag.png") };
		int[] duration = { 300, 300 };
		grassMap = new TiledMap("data/grassmap.tmx");
		Image[] breadCrumbs = { new Image("data/small_bread_crumbs.png") };
		Image[] rain = { new Image("data/rain.png") };

		/*
		 * false variable means do not auto update the animation. By setting it
		 * to false animation will update only when the user presses a key.
		 */
		up = new Animation(movementUp, duration, false);
		down = new Animation(movementDown, duration, false);
		left = new Animation(movementLeft, duration, false);
		right = new Animation(movementRight, duration, false);
		water = new Animation(waterImage, new int[] { 300 }, false);
		bread = new Animation(breadCrumbs, new int[] { 300 }, false);
		rainWeather = new Animation(rain, new int[] { 300 }, false);

		// Original orientation of the sprite. It will look right.
		sprite = right;

		// build a collision map based on tile properties in the TileD map
		blocked = new double[grassMap.getWidth()][grassMap.getHeight()];

		for (int xAxis = 0; xAxis < grassMap.getWidth(); xAxis++) {
			for (int yAxis = 0; yAxis < grassMap.getHeight(); yAxis++) {
				int tileID = grassMap.getTileId(xAxis, yAxis, 0);
				String value = grassMap.getTileProperty(tileID, "terrain",
						"grass");

				// System.out.println(
				// grassMap.getTileProperty(tileID, "terrain","desert") + " " +
				// grassMap.getTileProperty(tileID, "terrain", "trees") + " " +
				// grassMap.getTileProperty(tileID, "terrain", "hole") + " " +
				// grassMap.getTileProperty(tileID, "terrain", "water") + " " +
				// grassMap.getTileProperty(tileID, "terrain","mountain"));

				if (value.equals("desert"))
					blocked[xAxis][yAxis] = 0.5;
				else if (value.equals("trees"))
					blocked[xAxis][yAxis] = 0.7;
				else if (value.equals("hole"))
					blocked[xAxis][yAxis] = 0;
				else if (value.equals("mountain"))
					blocked[xAxis][yAxis] = 0.1;
				else if (value.equals("water"))
					blocked[xAxis][yAxis] = 0f;
				else if(value.equals("limit"))
					blocked[xAxis][yAxis] = 0f;
				else
					blocked[xAxis][yAxis] = 1f;

			}
		}
	}

	@Override
	public void update(GameContainer container, int delta)
			throws SlickException {

		updateHora();

		// System.out.println(x);
		// System.out.println(y);
		// System.out.println(play);

		if (hp < 200) {

			hp += 500;
			// Thread.sleep(1000);
			play = random.nextInt(4);

			return;
		}

		Input input = container.getInput();
		if (input.isKeyDown(Input.KEY_UP)/* play==0 */) {
			sprite = up;
			if (isBlocked(x, y - delta * 0.05f) > 0) {
				sprite.update(delta);
				// The lower the delta the slowest the sprite will animate.
				breadCrumbs.add(new CoordinatesPair((int) x, (int) y));
				y -= delta * 0.05f * isBlocked(x, y - delta * 0.05f);
				hp -= 1;

			} else {
				play = random.nextInt(4);
			}
		} else if (input.isKeyDown(Input.KEY_DOWN)/* play==1 */) {
			sprite = down;
			if (isBlocked(x, y + SIZE + delta * 0.05f) > 0) {
				sprite.update(delta);
				breadCrumbs.add(new CoordinatesPair((int) x, (int) y));
				y += delta * 0.05f * isBlocked(x, y + SIZE + delta * 0.05f);
				hp -= 1;

			} else {
				play = random.nextInt(4);
			}
		} else if (input.isKeyDown(Input.KEY_LEFT)/* play==2 */) {
			sprite = left;
			if (isBlocked(x - delta * 0.05f, y) > 0) {
				sprite.update(delta);
				breadCrumbs.add(new CoordinatesPair((int) x, (int) y));
				x -= delta * 0.05f * isBlocked(x - delta * 0.05f, y);
				hp -= 1;

			} else {
				play = random.nextInt(4);
			}

		} else if (input.isKeyDown(Input.KEY_RIGHT)/* play==3 */) {
			sprite = right;
			if (isBlocked(x + SIZE + delta * 0.05f, y) > 0) {
				sprite.update(delta);
				breadCrumbs.add(new CoordinatesPair((int) x, (int) y));
				x += delta * 0.05f * isBlocked(x + SIZE + delta * 0.05f, y);
				hp -= 1;

			} else {
				play = random.nextInt(4);
			}
		}
	}

	public void render(GameContainer container, Graphics g)
			throws SlickException {
		grassMap.render(0, 0);
		sprite.draw((int) x, (int) y);

		if (!active) {
			Random random = new Random();
			
			aa = random
					.nextInt(grassMap.getHeight() * grassMap.getTileHeight()+600);
			bb = random.nextInt(grassMap.getWidth() * grassMap.getTileWidth()-700);
			
			
			
			while(blocked[(int)aa/SIZE][(int)bb/SIZE]==0){
				aa = random
						.nextInt(grassMap.getHeight() * grassMap.getTileHeight()+600);
				bb = random.nextInt(grassMap.getWidth() * grassMap.getTileWidth()-700);
			}

			//water.draw(aa * 1.0f, bb * 1.0f);

			active = true;

		}

		water.draw(aa * 1.0f, bb * 1.0f);

		if (climate.equals("rain")) {
			for (int i = 0; i < grassMap.getHeight() * grassMap.getTileHeight(); i++)
				for (int j = 0; j < grassMap.getWidth()
						* grassMap.getTileWidth(); j++) {
					if (i % 50 == 0 && j % 50 == 0)
						rainWeather.draw(j * 1.0f, i * 1.0f);
				}

		}

		/*
		 * for (CoordinatesPair c : breadCrumbs) { bread.draw(c.getX(),
		 * c.getY());
		 * 
		 * }
		 */
		// g.drawString("OLA", 10f, 10f);
		if (hora > 17 || hora < 6) {
			g.setColor(new Color(0f, 0f, 0f, 0.6f));
			Rectangle r = new Rectangle(0, 0, grassMap.getWidth()
					* grassMap.getTileWidth(), grassMap.getHeight()
					* grassMap.getTileHeight());
			g.draw(r);
			g.fill(r);
		}

		// Circle circle1 = new Circle(40.5f,40.5f,40.5f);
		// g.setColor(Color.black);
		// g.draw(circle1);
		// g.fill(circle1);
		// g.setDrawMode(Graphics.MODE_NORMAL);

		g.setColor(new Color(1f, 1f, 1f, 0.4f));
		Circle circle = new Circle(x + 15, y + 15, 40.5f);

		g.drawString("Hora: " + hora + "h : " + minutos + " min ", 20f, 400f);
		g.draw(circle);
		// g.setColor(Color.transparent);
		g.fill(circle);
		// g.pushTransform();
		// g.drawOval(200f, 200f, 200f, 200f);
		// g.drawArc(100f, 100f, 100f, 100f, 100f, 100f);

		// g.popTransform();

	}

	private double isBlocked(float x, float y) {

		// System.out.println((int) x / SIZE);
		// System.out.println(Math.round( x / SIZE));
		int xBlockRound = Math.round(x / SIZE);
		int yBlockRound = Math.round(y / SIZE);
		int xBlock = (int) x / SIZE;
		int yBlock = (int) y / SIZE;
		// if(blocked[xBlock][yBlock] == 0 || blocked[xBlockRound][yBlockRound]
		// )

		return blocked[xBlock][yBlock];
	}

	public void updateHora() {

		segundos += 1;

		if (segundos == 1000) {
			segundos = 0;
			minutos += 10;

			if (random.nextInt(100) > 90) {
				if (climate.equals("rain"))
					climate = "sun";
				else
					climate = "rain";

			}

		}

		if (minutos == 60) {
			minutos = 0;
			hora += 1;
		}

		if (hora == 24)
			hora = 0;
	}
}