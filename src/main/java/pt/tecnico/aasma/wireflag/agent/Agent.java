package pt.tecnico.aasma.wireflag.agent;

import java.util.Random;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Circle;

import pt.tecnico.aasma.wireflag.IGameElement;
import pt.tecnico.aasma.wireflag.agent.architecture.Architecture;
import pt.tecnico.aasma.wireflag.environment.controller.MapController;
import pt.tecnico.aasma.wireflag.environment.controller.TimeController;
import pt.tecnico.aasma.wireflag.util.AnimationLoader;
import pt.tecnico.aasma.wireflag.util.MapPosition;
import pt.tecnico.aasma.wireflag.util.WorldPosition;

public abstract class Agent implements IGameElement {

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

	protected Animation up;
	protected Animation down;
	protected Animation right;
	protected Animation left;
	protected Animation sprite;
	protected Animation ill;
	private int play;

	private int teamId;
	private WorldPosition agentPos;
	private Random random;
	private float agentSpeed;
	private int agentAttack;
	private int fatigue;
	private int life;
	private boolean isIll;

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

		ill = AnimationLoader.getLoader().getIll();
		this.isIll = false;
		play = 0;
		agentPos = new WorldPosition(550f, 600f);

	}

	/* returns the agent's team id */
	public int getTeamId() {
		return teamId;
	}

	public WorldPosition getPos() {
		return agentPos;
	}

	/* decrement value in agent's life */
	public void decreaseLife(int value) {
		life -= value;
		fatigue = Math.max((100 - life), fatigue);
	}

	public void decreaseFatigue(int value) {
		fatigue = Math.max((100 - life), fatigue - value);
	}

	public void increaseFatigue(int value) {
		fatigue = Math.min(fatigue + value, 100);
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

	public boolean isIll() {
		return isIll;
	}

	public void setIll(boolean isIll) {
		this.isIll = isIll;
	}

	public float getAgentSpeed(MapPosition pos) {
		return agentSpeed * (100 - fatigue) * 1.0f / 100
				* MapController.getMap().getMovementSpeed(pos);
	}

	public void move(int delta) {
		MapPosition oldPos = agentPos.getMapPosition();

		/* to avoid the agent get out of the matrix */
		delta = Math.min(delta, 20);

		if (play == 0) {
			sprite = up;
			MapPosition nextPos = new WorldPosition(agentPos.getX(),
					agentPos.getY() - 2 * delta).getMapPosition();

			if (!MapController.getMap().isBlocked(nextPos)) {
				moveUp(delta, nextPos, oldPos);
			}
		} else if (play == 1) {
			sprite = down;
			MapPosition nextPos = new WorldPosition(agentPos.getX(),
					agentPos.getY() + 2 * delta).getMapPosition();

			if (!MapController.getMap().isBlocked(nextPos)) {
				moveDown(delta, nextPos, oldPos);
			}
		} else if (play == 2) {
			sprite = left;
			MapPosition nextPos = new WorldPosition(
					agentPos.getX() - 2 * delta, agentPos.getY())
					.getMapPosition();

			if (!MapController.getMap().isBlocked(nextPos)) {
				moveLeft(delta, nextPos, oldPos);
			}
		} else if (play == 3) {
			sprite = right;
			MapPosition nextPos = new WorldPosition(
					agentPos.getX() + 2 * delta, agentPos.getY())
					.getMapPosition();

			if (!MapController.getMap().isBlocked(nextPos)) {
				moveRight(delta, nextPos, oldPos);
			}
		}
	}

	/* agent move to a different direction */
	public void moveDifferentDirection(int delta) {

		/* to avoid the agent get out of the matrix */
		delta = Math.min(delta, 20);

		int oldPlay = play;
		while (oldPlay == play) {
			play = random.nextInt(4);
		}

		move(delta);
	}

	/*
	 * agent moves to the same direction, but change it from time to time to
	 * don't walk just in a straight line
	 */
	public void moveSameDirection(int delta) {

		/* to avoid the agent get out of the matrix */
		delta = Math.min(delta, 20);

		if (random.nextInt(1000) > 990) {
			play = random.nextInt(10);
		}

		move(delta);
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

	public void moveDown(int delta, MapPosition newPos, MapPosition oldPos) {
		agentPos.setY(agentPos.getY() + delta * getAgentSpeed(newPos));
		changePosition(delta, oldPos, newPos);
	}

	public void moveUp(int delta, MapPosition newPos, MapPosition oldPos) {
		agentPos.setY(agentPos.getY() - delta * getAgentSpeed(newPos));
		changePosition(delta, oldPos, newPos);
	}

	public void moveRight(int delta, MapPosition newPos, MapPosition oldPos) {
		agentPos.setX(agentPos.getX() + delta * getAgentSpeed(newPos));
		changePosition(delta, oldPos, newPos);
	}

	public void moveLeft(int delta, MapPosition newPos, MapPosition oldPos) {
		agentPos.setX(agentPos.getX() - delta * getAgentSpeed(newPos));
		changePosition(delta, oldPos, newPos);
	}

	public void changePosition(int delta, MapPosition oldPos, MapPosition newPos) {
		sprite.update(delta);
		MapController.getMap().getLandscape(oldPos).setAgent(null);
		MapController.getMap().getLandscape(agentPos).setAgent(this);

		if (!oldPos.isSamePosition(agentPos.getMapPosition())) {
			MapController.getMap().getLandscape(agentPos).takePenalty();
		}
	}

	public void attack(Agent agent) {
		agent.decreaseLife(agentAttack);
	}

	public void update(int delta) {
		arquitecture.makeAction(this, delta);
	}

	public int getVisibilityRange() {
		int visibility = 2;
		visibility += MapController.getMap().getLandscape(agentPos)
				.getVisibility();

		if (TimeController.getTime().isNight())
			visibility--;

		return visibility;
	}

	@Override
	public void render(Graphics g) {
		sprite.draw(agentPos.getX(), agentPos.getY());

		g.setColor(new Color(1f, 1f, 1f, 1f));
		g.drawString("hp:" + life, agentPos.getX() + 3, agentPos.getY() - 20);

		g.setColor(new Color(1f, 1f, 1f, 1f));
		g.drawString("fp:" + fatigue + "", agentPos.getX() + 3,
				agentPos.getY() + 30);

		g.setColor(new Color(1f, life * 1.0f / 100,
				((100 - fatigue) * 1.0f) / 100, 0.4f));
		Circle circle = new Circle(agentPos.getX() + 15, agentPos.getY() + 15,
				getVisibilityRange() * MapController.getMap().getTileWidth());

		g.draw(circle);
		g.fill(circle);

		if (isIll()) {
			ill.draw(agentPos.getX(), agentPos.getY());
		}
	}
}
