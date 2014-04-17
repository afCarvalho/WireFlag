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
import pt.tecnico.aasma.wireflag.agent.team.Team;
import pt.tecnico.aasma.wireflag.environment.controller.MapController;
import pt.tecnico.aasma.wireflag.util.MapPosition;
import pt.tecnico.aasma.wireflag.util.WorldPosition;

public class Agent implements GameElement {

	/* speed */
	protected final static float LOWSPD = 1.0f;
	protected final static float NORMALSPD = 0.005f;
	protected final static float HIGHSPD = 0.1f;

	/* attack */
	protected final static float LOWATCK = 1.0f;
	protected final static float NORMALATCK = 2.0f;
	protected final static float HIGHTATCK = 3.0f;

	/* life 0-100 */
	protected final static int VLOW_LIFE = 10;
	protected final static int LOW_LIFE = 20;
	protected final static int FULL_LIFE = 100;

	/* fatigue 0-100 */
	protected final static int HIGH_FATIGUE = 80;
	protected final static int LOW_FATIGUE = 0;

	/** The agent's team. */
	private Team team;

	/** The agent's identifier. */
	private int identifier;

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
	private float agentAttack;
	private int fatigue;
	private int life;

	private Architecture architecture;

	public Agent(int identifier, float agentSpeed, float agentAttack,
			Architecture architecture) {
		this.identifier = identifier;
		random = new Random();
		this.life = FULL_LIFE;
		this.fatigue = LOW_FATIGUE;
		this.agentSpeed = agentSpeed;
		this.agentAttack = agentAttack;
		this.architecture = architecture;
	}

	/**
	 * Instantiates a new agent with team.
	 * 
	 * @param identifier
	 *            the agent identifier
	 * @param team
	 *            the team of the agent
	 * @param speed
	 *            the agent speed
	 * @param attack
	 *            the agent attack strength
	 * @param architecture
	 *            the agent architectural style
	 */
	public Agent(int identifier, Team team, float speed, float attack,
			Architecture architecture) {
		this(identifier, speed, attack, architecture);
		this.team = team;
	}

	/**
	 * Obtains the agent identifier.
	 * 
	 * @return the identifier
	 */
	public final int getIdentifier() {
		return identifier;
	}

	/**
	 * Obtains the agent's team.
	 * 
	 * @return the identifier
	 */
	public final Team getTeam() {
		return team;
	}

	/**
	 * Changes the agent's team.
	 * 
	 * @param team
	 *            the new team
	 */
	public final void setTeam(Team team) {
		team.removeMember(this);
		this.team = team;
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

	public void randomMovement(int delta) {
		MapPosition oldPos = MapController.getMap().getMapPosition(
				agentPos.getX(), agentPos.getY());

		if (random.nextInt(10000) > 9990)
			play = random.nextInt(4);

		if (play == 0) {
			sprite = up;
			MapPosition nextPos = MapController.getMap().getMapPosition(
					agentPos.getX(), agentPos.getY() - delta);

			if (!MapController.getMap().isBlocked(nextPos)) {
				moveUp(delta, nextPos, oldPos);
			} else {
				play = random.nextInt(4);
			}
		} else if (play == 1) {
			sprite = down;
			MapPosition nextPos = MapController.getMap().getMapPosition(
					agentPos.getX(), agentPos.getY() + delta);
			if (!MapController.getMap().isBlocked(nextPos)) {
				moveDown(delta, nextPos, oldPos);
			} else {
				play = random.nextInt(4);
			}
		} else if (play == 2) {
			sprite = left;
			MapPosition nextPos = MapController.getMap().getMapPosition(
					agentPos.getX() - delta, agentPos.getY());

			if (!MapController.getMap().isBlocked(nextPos)) {
				moveLeft(delta, nextPos, oldPos);
			} else {
				play = random.nextInt(4);
			}

		} else if (play == 3) {
			sprite = right;
			MapPosition nextPos = MapController.getMap().getMapPosition(
					agentPos.getX() + delta, agentPos.getY());

			if (!MapController.getMap().isBlocked(nextPos)) {
				moveRight(delta, nextPos, oldPos);
			} else {
				play = random.nextInt(4);
			}
		}
	}

	public WorldPosition getPos() {
		return agentPos;
	}

	public void update(int delta) {
		architecture.makeAction(this, delta);
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

	public void init() throws SlickException {
		Image[] movementUp = { new Image(System.getProperty("data")
				+ "grey-back.png") };
		Image[] movementDown = { new Image(System.getProperty("data")
				+ "grey-front.png") };
		Image[] movementLeft = { new Image(System.getProperty("data")
				+ "grey-left.png") };
		Image[] movementRight = { new Image(System.getProperty("data")
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
