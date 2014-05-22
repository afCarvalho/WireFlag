package pt.tecnico.aasma.wireflag.agent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Circle;

import pt.tecnico.aasma.wireflag.IGameElement;
import pt.tecnico.aasma.wireflag.agent.architecture.Architecture;
import pt.tecnico.aasma.wireflag.agent.communication.Message;
import pt.tecnico.aasma.wireflag.environment.controller.MapController;
import pt.tecnico.aasma.wireflag.environment.controller.TimeController;
import pt.tecnico.aasma.wireflag.environment.landscape.Landscape;
import pt.tecnico.aasma.wireflag.environment.object.Animal;
import pt.tecnico.aasma.wireflag.environment.object.Flag;
import pt.tecnico.aasma.wireflag.environment.perception.Perception;
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
	protected final static int LIFE_RECOVER = 10;

	/* fatigue 0-100 */
	protected final static int HIGH_FATIGUE = 80;
	protected final static int LOW_FATIGUE = 0;
	protected final static int FATIGUE_RECOVER = 5;

	/* direction */
	public final static int UP = 0;
	public final static int DOWN = 1;
	public final static int RIGHT = 2;
	public final static int LEFT = 3;

	protected Animation up;
	protected Animation down;
	protected Animation right;
	protected Animation left;
	protected Animation sprite;
	protected Animation ill;
	protected Animation ballon;

	private int teamId;
	private int agentId;

	private WorldPosition position;
	private Random random;
	private int direction;
	private float agentSpeed;
	private int agentAttack;
	private int fatigue;
	private int life;
	private boolean isIll;
	private Flag flag;
	private Architecture architecture;
	private AgentThread agentThread;
	private BlockingQueue<Message> mailbox;

	public Agent(float agentSpeed, int agentAttack, int teamId, int agentId,
			Architecture arquitecture) {
		random = new Random();
		this.life = FULL_LIFE;
		this.fatigue = LOW_FATIGUE;
		this.agentSpeed = agentSpeed;
		this.agentAttack = agentAttack;
		this.teamId = teamId;
		this.agentId = agentId;
		this.architecture = arquitecture;
		this.agentThread = new AgentThread(this);
		this.mailbox = new LinkedBlockingQueue<Message>();

		ballon = AnimationLoader.getLoader().getUpArrow();
		ill = AnimationLoader.getLoader().getIll();
		direction = UP;
	}

	/***************
	 *** GETTERS ***
	 ***************/

	public int getTeamId() {
		return teamId;
	}

	public WorldPosition getPos() {
		return position;
	}

	public int getAgentId() {
		return agentId;
	}

	public int getDirection() {
		return direction;
	}

	public int getAgentAttack() {
		return agentAttack;
	}

	public int getLife() {
		return life;
	}

	public int getFatigue() {
		return fatigue;
	}

	public float getAgentSpeed(MapPosition pos) {
		return agentSpeed * (100 - fatigue) * 1.0f / 100
				* MapController.getMap().getMovementSpeed(pos);
	}

	public int getVisibilityRange() {
		int visibility = 2;
		visibility += MapController.getMap().getLandscape(position)
				.getVisibility();

		if (TimeController.getTime().isNight())
			visibility--;

		return visibility;
	}

	public AgentThread getAgentThread() {
		return agentThread;
	}

	/* for each tile is created a perception */
	private Perception getTilePerception(MapPosition pos) {
		Landscape land = MapController.getMap().getLandscape(pos);

		Perception perception = new Perception(pos, land.getRating());
		perception.setFlag(land.hasFlag());
		perception.setEnemy(land.hasAgent() && land.getAgent().isEnemy(teamId));
		perception.setEndPoint(land.hasEndPoint());
		perception.setAnimal(land.hasAnimal());
		perception.setFire(land.hasFire());
		perception.setExtremeWeather(land.getWeather().isExtremeWeather());
		perception.setBlocked(isBlocked(pos));
		if (land.hasAgent()) {
			perception.setAgentAttack(land.getAgent().getAgentAttack());
			perception.setTiredAgent(land.getAgent().hasFatigue());
			perception.setInjuredAgent(land.getAgent().hasLowLife());
		}

		return perception;
	}

	/*
	 * returns a list with a perception for each tile in the agent's visibility
	 */
	public List<Perception> getPerceptions() {
		List<Perception> list = new ArrayList<Perception>();
		int visibility = getVisibilityRange();
		int x = position.getMapPosition().getX();
		int y = position.getMapPosition().getY();
		int id = 0;

		for (int j = y + visibility; j >= y - visibility; j--) {
			for (int i = x - visibility; i <= x + visibility; i++) {

				if (j < MapController.getMap().getNVerticalTiles()
						&& i < MapController.getMap().getNHorizontalTiles()
						&& j > 0 && i > 0) {
					list.add(getTilePerception(new MapPosition(i, j)));
				}
			}
		}

		return list;
	}

	public List<Agent> getNearTeammates() {
		List<Perception> perceptions = getPerceptions();
		List<Agent> mates = new ArrayList<Agent>();

		for (Perception perception : perceptions) {
			Agent agent = perception.getAgent();
			if (agent != null && agent.getTeamId() == teamId) {
				mates.add(agent);
			}
		}
		return mates;
	}
	
	public BlockingQueue<Message> getMailbox() {
		return this.mailbox;
	}

	/***************
	 *** SETTERS ***
	 ***************/

	public void setTeamId(int id) {
		this.teamId = id;
	}

	public void setPos(WorldPosition pos) {
		position = pos;
		sprite = down;
	}

	public void setIll(boolean isIll) {
		this.isIll = isIll;
	}

	/***********************
	 *** STATE MODIFIERS ***
	 ***********************/

	public void init() {
		agentThread.init();
	}

	public void stop() {
		ballon = AnimationLoader.getLoader().getStop();

		/*
		 * try { Thread.sleep(250); } catch (InterruptedException e) {
		 * e.printStackTrace(); }
		 */

		if (isIll && life > 85) {
			setIll(false);
		}

		modifyLife(1);
		fatigue = Math.min(100, fatigue - FATIGUE_RECOVER);
		fatigue = Math.max(fatigue, 0);
	}

	public void catchFlag() {
		if (MapController.getMap().getLandscape(position).hasFlag()) {
			flag = MapController.getMap().getLandscape(position).removeFlag();
		}
	}

	public void dropFlag() {
		if (hasFlag()) {
			MapController.getMap().getLandscape(position).setFlag(flag);
			flag.setFlagPos(position);
			flag = null;
		}
	}

	public void moveFlag() {
		if (hasFlag()) {
			flag.setFlagPos(new WorldPosition(position.getX() + 10, position
					.getY()));
		}
	}

	public void attack(Agent agent) {
		ballon = AnimationLoader.getLoader().getAttack();

		if (agent == null) {
			return;
		}

		/*
		 * try { Thread.sleep(500); } catch (InterruptedException e) {
		 * e.printStackTrace(); }
		 */

		int hitRate = random.nextInt(100);

		if (hitRate > 95) {
			hitRate = agentAttack * 2;
		} else if (hitRate < 15) {
			hitRate = 0;
		} else {
			hitRate = agentAttack;
		}

		agent.modifyLife(-hitRate);
	}

	public synchronized void modifyLife(int value) {
		life = Math.max(0, life + value);
		life = Math.min(life, 100);
		this.notify();
	}

	public synchronized void modifyFatigue(int value) {
		fatigue = Math.max(0, fatigue + value);
		fatigue = Math.min(fatigue, 100);
		this.notify();
	}

	public abstract int habilityRate(int nInjured, int nTired, int nEnemy,
			boolean flag);

	public void hunt(Animal prey) {
		ballon = AnimationLoader.getLoader().getBow();

		/*
		 * try { Thread.sleep(250); } catch (InterruptedException e) {
		 * e.printStackTrace(); }
		 */
		modifyLife(prey.kill());
	}

	/* this agent use its ability at MapPosition pos */
	public abstract void useAbility(MapPosition pos);

	/**
	 * ********************* *** STATE PREDICATES *** **********************
	 */

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

	public boolean isAlive() {
		return life > 0;
	}

	public boolean isIll() {
		return isIll;
	}

	public boolean hasFlag() {
		return flag != null;
	}

	/*
	 * returns false if the position p is free or if the agent in that position
	 * is this agent
	 */
	public boolean isBlocked(MapPosition p) {
		Landscape land = MapController.getMap().getLandscape(p);
		boolean posBlocked = MapController.getMap().isBlocked(p);

		return posBlocked
				&& (!land.hasAgent() || land.hasAgent()
						&& land.getAgent().getAgentId() != getAgentId() || land
						.getAgent().getTeamId() != getTeamId());
	}

	/*
	 * returns true if its useful that this agent uses its ability at
	 * MapPosition pos
	 */
	public abstract boolean isAbilityUseful(MapPosition pos);

	/**
	 * ********************* *** MOVEMENT RELATED *** **********************
	 */

	private void move(int delta) {

		/* to avoid the agent get out of the matrix */
		delta = Math.min(delta, 20);
		MapPosition oldPos = position.getMapPosition();
		if (direction == UP) {
			moveUp(delta, oldPos);
		} else if (direction == DOWN) {
			moveDown(delta, oldPos);
		} else if (direction == LEFT) {
			moveLeft(delta, oldPos);
		} else if (direction == RIGHT) {
			moveRight(delta, oldPos);
		}
	}

	/* agent move to a different direction */
	public void moveDifferentDirection(int delta) {
		int oldDirection = direction;
		while (direction == oldDirection) {
			direction = random.nextInt(4);
		}
		move(delta);
	}

	/*
	 * agent moves to the same direction, but change it from time to time to
	 * don't walk just in a straight line
	 */
	public void moveSameDirection(int delta) {
		if (random.nextInt(10000) > 9990) {
			direction = random.nextInt(4);
		}

		move(delta);
	}

	/*
	 * Agent approaches the tile on its left. Returns true if the agent can move
	 * in the desired direction
	 */
	private boolean approachLeft(int delta, MapPosition oldPos) {

		if (direction == UP && moveLeft(delta, oldPos)) {
			direction = LEFT;
			return true;
		} else if (direction == LEFT && moveDown(delta, oldPos)) {
			direction = DOWN;
			return true;
		} else if (direction == DOWN && moveRight(delta, oldPos)) {
			direction = RIGHT;
			return true;
		} else if (direction == RIGHT && moveUp(delta, oldPos)) {
			direction = UP;
			return true;
		}
		return false;
	}

	/*
	 * Agent approaches the tile on its right. Returns true if the agent can
	 * move in the desired direction
	 */
	private boolean approachRight(int delta, MapPosition oldPos) {

		if (direction == UP && moveRight(delta, oldPos)) {
			direction = RIGHT;
			return true;
		} else if (direction == LEFT && moveUp(delta, oldPos)) {
			direction = UP;
			return true;
		} else if (direction == DOWN && moveLeft(delta, oldPos)) {
			direction = LEFT;
			return true;
		} else if (direction == RIGHT && moveDown(delta, oldPos)) {
			direction = DOWN;
			return true;
		}
		return false;
	}

	/*
	 * Agent approaches the tile ahead it. Returns true if the agent can move in
	 * the desired direction
	 */
	private boolean approachAhead(int delta, MapPosition oldPos) {

		if (direction == UP && moveUp(delta, oldPos)) {
			return true;
		} else if (direction == LEFT && moveLeft(delta, oldPos)) {
			return true;
		} else if (direction == DOWN && moveDown(delta, oldPos)) {
			return true;
		} else if (direction == RIGHT && moveRight(delta, oldPos)) {
			return true;
		}
		return false;
	}

	/*
	 * Agent approaches the tile behind it. Returns true if the agent can move
	 * in the desired direction
	 */
	private boolean approachBehind(int delta, MapPosition oldPos) {

		if (direction == UP && moveDown(delta, oldPos)) {
			direction = DOWN;
			return true;
		} else if (direction == LEFT && moveRight(delta, oldPos)) {
			direction = RIGHT;
			return true;
		} else if (direction == DOWN && moveUp(delta, oldPos)) {
			direction = UP;
			return true;
		} else if (direction == RIGHT && moveLeft(delta, oldPos)) {
			direction = LEFT;
			return true;
		}
		return false;
	}

	/* agent approaches tile identified by mapPos */
	public void approachTile(int delta, MapPosition mapPos) {

		/* to avoid the agent get out of the matrix */
		delta = Math.min(delta, 20);
		MapPosition oldPos = position.getMapPosition();

		/* if position is left to the agent's position, moves to the left */
		if (oldPos.isLeft(mapPos, direction) && approachLeft(delta, oldPos)) {
			return;
		}
		/* if position is ahead to the agent's position, moves up */
		if (oldPos.isAhead(mapPos, direction) && approachAhead(delta, oldPos)) {
			return;
		}
		/* if position is right to the agent's position, moves to the right */
		if (oldPos.isRight(mapPos, direction) && approachRight(delta, oldPos)) {
			return;
		}
		/* if position is behind to the agent's position, moves up */

		if (oldPos.isBehind(mapPos, direction) && approachBehind(delta, oldPos)) {
			return;
		}
	}

	/*
	 * if the agent can move down it moves and returns true. Returns false
	 * otherwise.
	 */
	public boolean moveDown(int delta, MapPosition oldPos) {
		sprite = down;
		ballon = AnimationLoader.getLoader().getDownArrow();

		WorldPosition newPos = new WorldPosition(position.getX(),
				position.getY() + delta * getAgentSpeed(oldPos));

		return changePosition(delta, oldPos, newPos);
	}

	/*
	 * if the agent can move up it moves and returns true. Returns false
	 * otherwise.
	 */
	public boolean moveUp(int delta, MapPosition oldPos) {
		sprite = up;
		ballon = AnimationLoader.getLoader().getUpArrow();

		WorldPosition newPos = new WorldPosition(position.getX(),
				position.getY() - delta * getAgentSpeed(oldPos));

		return changePosition(delta, oldPos, newPos);
	}

	/*
	 * if the agent can move to the right it moves and returns true. Returns
	 * false otherwise.
	 */
	public boolean moveRight(int delta, MapPosition oldPos) {
		sprite = right;
		ballon = AnimationLoader.getLoader().getRightArrow();

		WorldPosition newPos = new WorldPosition(position.getX() + delta
				* getAgentSpeed(oldPos), position.getY());

		return changePosition(delta, oldPos, newPos);
	}

	/*
	 * if the agent can move to the left it moves and returns true. Returns
	 * false otherwise.
	 */
	public boolean moveLeft(int delta, MapPosition oldPos) {
		sprite = left;
		ballon = AnimationLoader.getLoader().getLeftArrow();

		WorldPosition newPos = new WorldPosition(position.getX() - delta
				* getAgentSpeed(oldPos), position.getY());

		return changePosition(delta, oldPos, newPos);
	}

	public synchronized boolean changePosition(int delta, MapPosition oldPos,
			WorldPosition newPos) {

		if (!isBlocked(newPos.getMapPosition())) {
			sprite.update(delta);
			ballon.update(delta);

			position = newPos;

			MapController.getMap().getLandscape(oldPos).setAgent(null);
			MapController.getMap().getLandscape(position).setAgent(this);

			if (!oldPos.isSamePosition(position.getMapPosition())) {
				MapController.getMap().getLandscape(position).takePenalty();
			}

			return true;
		} else {
			return false;
		}
	}

	/*********************
	 *** GAME RELATED ****
	 *********************/

	public void update(int delta) {
		architecture.makeAction(this, delta);
		architecture.processMessage(mailbox.poll());
		moveFlag();
	}

	@Override
	public void render(Graphics g) {

		g.setColor(new Color(1f, life * 1.0f / 100,
				((100 - fatigue) * 1.0f) / 100, 0.4f));
		Circle circle = new Circle(position.getX() + 15, position.getY() + 15,
				getVisibilityRange() * MapController.getMap().getTileWidth()
						* 1.5f);
		g.draw(circle);
		g.fill(circle);

		sprite.draw(position.getX(), position.getY());
		ballon.draw(position.getX() - 10, position.getY());

		g.setColor(new Color(1f, 1f, 1f, 1f));
		g.drawString("hp:" + life, position.getX() + 3, position.getY() - 20);
		g.drawString("fp:" + getFatigue() + "", position.getX() + 3,
				position.getY() + 30);
		g.drawString("T" + getTeamId() + "A" + getAgentId(),
				position.getX() - 40, position.getY() + 30);

		if (isIll()) {
			ill.draw(position.getX(), position.getY());
		}
	}
}
