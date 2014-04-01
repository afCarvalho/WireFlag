package pt.tecnico.aasma.wireflag.agents;

import java.util.Random;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;

import pt.tecnico.aasma.wireflag.GameElement;
import pt.tecnico.aasma.wireflag.WireFlagGame;

public class Agent implements GameElement {

	private Animation up;
	private Animation down;
	private Animation right;
	private Animation left;
	private Animation sprite;
	private int hp;
	private int play;
	private Random random;
	private float x;
	private float y;
	private WireFlagGame game;

	public Agent(WireFlagGame game) {

		random = new Random();
		this.game = game;

	}

	public void update(GameContainer container, int delta) {

		if (random.nextInt(10000) > 9990)
			play = random.nextInt(4);

		Input input = container.getInput();
		if (/* input.isKeyDown(Input.KEY_UP) */play == 0) {
			sprite = up;
			if (!game.isBlocked(x, y - delta * 0.05f)) {
				sprite.update(delta);
				// The lower the delta the slowest the sprite will animate.
				y -= delta * 0.05f * game.getTile(x, y - delta * 0.05f);
				hp -= 1;

			} else {
				play = random.nextInt(4);
			}
		} else if (/* input.isKeyDown(Input.KEY_DOWN) */play == 1) {
			sprite = down;
			if (game.getTile(x, y + game.getNTiles() + delta * 0.05f) > 0) {
				sprite.update(delta);
				y += delta * 0.05f
						* game.getTile(x, y + game.getNTiles() + delta * 0.05f);
				hp -= 1;

			} else {
				play = random.nextInt(4);
			}
		} else if (/* input.isKeyDown(Input.KEY_LEFT) */play == 2) {
			sprite = left;
			if (game.getTile(x - delta * 0.05f, y) > 0) {
				sprite.update(delta);
				x -= delta * 0.05f * game.getTile(x - delta * 0.05f, y);
				hp -= 1;

			} else {
				play = random.nextInt(4);
			}

		} else if (/* input.isKeyDown(Input.KEY_RIGHT) */play == 3) {
			sprite = right;
			if (game.getTile(x + game.getNTiles() + delta * 0.05f, y) > 0) {
				sprite.update(delta);
				x += delta * 0.05f
						* game.getTile(x + game.getNTiles() + delta * 0.05f, y);
				hp -= 1;

			} else {
				play = random.nextInt(4);
			}
		}

		if (hp < 200) {

			hp += 500;
			// Thread.sleep(1000);
			play = random.nextInt(4);

			return;
		}

	}

	public void init() throws SlickException {

		Image[] movementUp = { new Image("data/grey-back.png") };
		Image[] movementDown = { new Image("data/grey-front.png") };
		Image[] movementLeft = { new Image("data/grey-left.png") };
		Image[] movementRight = { new Image("data/grey-right.png") };
		int[] duration = { 300 };

		up = new Animation(movementUp, duration, false);
		down = new Animation(movementDown, duration, false);
		left = new Animation(movementLeft, duration, false);
		right = new Animation(movementRight, duration, false);

		sprite = right;

		play = 0;
		hp = 10000;
		x = 34f;
		y = 34f;

	}

	@Override
	public void render(Graphics g) {

		sprite.draw((int) x, (int) y);

		g.setColor(new Color(1f, 1f, 1f, 0.4f));
		Circle circle = new Circle(x + 15, y + 15, 40.5f);

		g.draw(circle);
		// g.setColor(Color.transparent);
		g.fill(circle);

	}

}
