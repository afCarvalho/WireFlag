package pt.tecnico.aasma.wireflag.agent;

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
import pt.tecnico.aasma.wireflag.environment.Map;

public class Agent implements GameElement {

	private Animation up;
	private Animation down;
	private Animation right;
	private Animation left;
	private Animation sprite;
	private int hp;
	private int play;
	private float x;
	private float y;
	private int timeElapsed;
	private Random random;

	public Agent() {
		random = new Random();
	}

	public void setTimeElapsed(int timeElapsed) {
		this.timeElapsed = timeElapsed;
	}

	public void update() {

		if (random.nextInt(10000) > 9990)
			play = random.nextInt(4);

		if (play == 0) {
			sprite = up;
			if (!Map.getMap().isBlocked(x, y - timeElapsed * 0.05f)) {
				moveUp(timeElapsed);
			} else {
				play = random.nextInt(4);
			}
		} else if (play == 1) {
			sprite = down;
			if (!Map.getMap().isBlocked(x,
					y + Map.getMap().getNTiles() + timeElapsed * 0.05f)) {
				moveDown(timeElapsed);
			} else {
				play = random.nextInt(4);
			}
		} else if (play == 2) {
			sprite = left;
			if (!Map.getMap().isBlocked(x - timeElapsed * 0.05f, y)) {
				moveLeft(timeElapsed);
			} else {
				play = random.nextInt(4);
			}

		} else if (play == 3) {
			sprite = right;
			if (!Map.getMap().isBlocked(
					x + Map.getMap().getNTiles() + timeElapsed * 0.05f, y)) {
				moveRight(timeElapsed);
			} else {
				play = random.nextInt(4);
			}
		}

		if (hp < 200) {
			hp += 500;
			play = random.nextInt(4);
			return;
		}

	}

	public void moveDown(int delta ) {
		sprite.update(delta);
		y -= delta * 0.05f * Map.getMap().getTileValue(x, y - delta * 0.05f);
		hp -= 1;
	}

	public void moveUp(int delta) {
		sprite.update(delta);
		y += delta
				* 0.05f
				* Map.getMap().getTileValue(x,
						y + Map.getMap().getNTiles() + delta * 0.05f);
		hp -= 1;
	}

	public void moveRight(int delta) {
		sprite.update(delta);
		x += delta
				* 0.05f
				* Map.getMap().getTileValue(
						x + Map.getMap().getNTiles() + delta * 0.05f, y);
		hp -= 1;
	}

	public void moveLeft(int delta) {
		sprite.update(delta);
		x -= delta * 0.05f * Map.getMap().getTileValue(x - delta * 0.05f, y);
		hp -= 1;
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
