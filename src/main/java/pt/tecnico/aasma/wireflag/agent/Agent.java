package pt.tecnico.aasma.wireflag.agent;

import java.util.Random;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;

import pt.tecnico.aasma.wireflag.GameElement;
import pt.tecnico.aasma.wireflag.agent.architecture.Architecture;
import pt.tecnico.aasma.wireflag.environment.controller.MapController;
import pt.tecnico.aasma.wireflag.util.WorldPosition;
import pt.tecnico.aasma.wireflag.util.MapPosition;

public class Agent implements GameElement {

	/* speed */
	protected final static float LOWSPD = 0.01f;
	protected final static float NORMALSPD = 0.05f;
	protected final static float HIGHSPD = 0.1f;

	/* attack */
	protected final static int LOWATCK = 10;
	protected final static int NORMALATCK = 20;
	protected final static int HIGHTATCK = 30;

	/* life 0-100 */
	protected final static int VLOW_LIFE = 10;
	protected final static int LOW_LIFE = 20;
	protected final static int FULL_LIFE = 100;

	/* fatigue 0-100 */
	protected final static int HIGH_FATIGUE = 80;
	protected final static int LOW_FATIGUE = 0;

	private Animation up;
	private Animation down;
	private Animation right;
	private Animation left;
	private Animation sprite;
	private int play;

	private int teamId;
	private WorldPosition agentPos;
	private Random random;
	private float agentSpeed;
	private int agentAttack;
	private int fatigue;
	private int life;

	private Architecture arquitecture;

	public Agent(float agentSpeed, int agentAttack, int teamId,
			Architecture arquitecture) {
		random = new Random();
		this.life = FULL_LIFE;
		this.fatigue = LOW_FATIGUE;
		this.agentSpeed = agentSpeed;
		this.agentAttack = agentAttack;
		this.teamId = teamId;
		this.arquitecture = arquitecture;
	}

	/* returns the agent's team id */
	public int getTeamId() {
		return teamId;
	}

	/* decrement value in agent's life */
	public void decrementLife(int value) {
		life -= value;
	}

	public boolean hasLowLife() {
		return life <= LOW_LIFE;
	}

	public boolean hasVeryLowLife() {
		return life <= VLOW_LIFE;
	}

	public boolean hasFatigue() {
		return fatigue >= HIGH_FATIGUE;
	}

	public boolean isEnemy(int teamId) {
		return this.teamId != teamId;
	}

	/* returns true if the agent is alive */
	public boolean isAlive() {
		return life > 0;
	}

	public void randomMovement(int delta) {

		/* to avoid the agent get out of the matrix */
		delta = Math.min(delta, 20);

		MapPosition oldPos = agentPos.getMapPosition();

		if (random.nextInt(10000) > 9990)
			play = random.nextInt(4);

		if (play == 0) {
			sprite = up;
			MapPosition nextPos = new WorldPosition(agentPos.getX(),
					agentPos.getY() - 2 * delta).getMapPosition();

			if (!MapController.getMap().isBlocked(nextPos)) {
				moveUp(delta, nextPos, oldPos);
			} else {
				play = random.nextInt(4);
			}
		} else if (play == 1) {
			sprite = down;
			MapPosition nextPos = new WorldPosition(agentPos.getX(),
					agentPos.getY() + 2 * delta).getMapPosition();

			if (!MapController.getMap().isBlocked(nextPos)) {
				moveDown(delta, nextPos, oldPos);
			} else {
				play = random.nextInt(4);
			}
		} else if (play == 2) {
			sprite = left;
			MapPosition nextPos = new WorldPosition(
					agentPos.getX() - 2 * delta, agentPos.getY())
					.getMapPosition();

			if (!MapController.getMap().isBlocked(nextPos)) {
				moveLeft(delta, nextPos, oldPos);
			} else {
				play = random.nextInt(4);
			}

		} else if (play == 3) {
			sprite = right;
			MapPosition nextPos = new WorldPosition(
					agentPos.getX() + 2 * delta, agentPos.getY())
					.getMapPosition();

			if (!MapController.getMap().isBlocked(nextPos)) {
				moveRight(delta, nextPos, oldPos);
			} else {
				play = random.nextInt(4);
			}
		}
	}

	/* agent approaches tile identified by mapPos */
	public void approachTile(int delta, MapPosition mapPos) {

		/* to avoid the agent get out of the matrix */
		delta = Math.min(delta, 20);

		MapPosition oldPos = agentPos.getMapPosition();

		/* if the agent is left to the position, moves to the right */
		if (oldPos.isLeft(mapPos)) {
			sprite = right;
			MapPosition nextPos = new WorldPosition(
					agentPos.getX() + 2 * delta, agentPos.getY())
					.getMapPosition();

			if (!MapController.getMap().isBlocked(nextPos)) {
				moveRight(delta, nextPos, oldPos);
				return;
			}
		}

		/* if the agent is right to the position, moves to the left */
		if (oldPos.isRight(mapPos)) {
			sprite = left;
			MapPosition nextPos = new WorldPosition(
					agentPos.getX() - 2 * delta, agentPos.getY())
					.getMapPosition();

			if (!MapController.getMap().isBlocked(nextPos)) {
				moveLeft(delta, nextPos, oldPos);
				return;
			}
		}

		/* if the agent is ahead to the position, moves down */
		if (oldPos.isAhead(mapPos)) {
			sprite = down;
			MapPosition nextPos = new WorldPosition(agentPos.getX(),
					agentPos.getY() + 2 * delta).getMapPosition();

			if (!MapController.getMap().isBlocked(nextPos)) {
				moveDown(delta, nextPos, oldPos);
				return;
			}
		}

		/* if the agent is behind to the position, moves up */
		if (oldPos.isBehind(mapPos)) {
			sprite = up;
			MapPosition nextPos = new WorldPosition(agentPos.getX(),
					agentPos.getY() - 2 * delta).getMapPosition();

			if (!MapController.getMap().isBlocked(nextPos)) {
				moveUp(delta, nextPos, oldPos);
			}
		}
	}

	public WorldPosition getPos() {
		return agentPos;
	}

	public void moveDown(int delta, MapPosition newPos, MapPosition oldPos) {
		MapController.getMap().getLandscape(oldPos).setAgent(null);
		sprite.update(delta);

		agentPos.setY(agentPos.getY() + delta * agentSpeed
				* MapController.getMap().getMovementSpeed(newPos));

		MapController.getMap().getLandscape(agentPos).setAgent(this);
	}

	public void moveUp(int delta, MapPosition newPos, MapPosition oldPos) {
		MapController.getMap().getLandscape(oldPos).setAgent(null);
		sprite.update(delta);

		agentPos.setY(agentPos.getY() - delta * agentSpeed
				* MapController.getMap().getMovementSpeed(newPos));

		MapController.getMap().getLandscape(agentPos).setAgent(this);
	}

	public void moveRight(int delta, MapPosition newPos, MapPosition oldPos) {
		MapController.getMap().getLandscape(oldPos).setAgent(null);
		sprite.update(delta);

		agentPos.setX(agentPos.getX() + delta * agentSpeed
				* MapController.getMap().getMovementSpeed(newPos));

		MapController.getMap().getLandscape(agentPos).setAgent(this);
	}

	public void moveLeft(int delta, MapPosition newPos, MapPosition oldPos) {
		MapController.getMap().getLandscape(oldPos).setAgent(null);
		sprite.update(delta);

		agentPos.setX(agentPos.getX() - delta * agentSpeed
				* MapController.getMap().getMovementSpeed(newPos));

		MapController.getMap().getLandscape(agentPos).setAgent(this);
	}

	public void attack(Agent agent) {
		agent.decrementLife(agentAttack);
	}

	public void init() throws SlickException {

		Image[] movementUp = { new Image(System.getProperty("agent")
				+ "grey-back.png") };
		Image[] movementDown = { new Image(System.getProperty("agent")
				+ "grey-front.png") };
		Image[] movementLeft = { new Image(System.getProperty("agent")
				+ "grey-left.png") };
		Image[] movementRight = { new Image(System.getProperty("agent")
				+ "grey-right.png") };
		int[] duration = { 300 };

		up = new Animation(movementUp, duration, false);
		down = new Animation(movementDown, duration, false);
		left = new Animation(movementLeft, duration, false);
		right = new Animation(movementRight, duration, false);

		sprite = right;

		play = 0;
		agentPos = new WorldPosition(550f, 600f);
	}

	public void update(int delta) {
		arquitecture.makeAction(this, delta);
	}

	@Override
	public void render(Graphics g) {
		sprite.draw(agentPos.getX(), agentPos.getY());

		g.setColor(new Color(1f, 1f, 1f, 0.4f));
		Circle circle = new Circle(agentPos.getX() + 15, agentPos.getY() + 15,
				40.5f);

		g.draw(circle);
		// g.setColor(Color.transparent);
		g.fill(circle);
	}
}
