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
import pt.tecnico.aasma.wireflag.environment.landscape.Landscape;
import pt.tecnico.aasma.wireflag.environment.object.Flag;
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

	private WorldPosition agentPos;
	private Random random;
	private int direction;
	private float agentSpeed;
	private int agentAttack;
	private int fatigue;
	private int life;
	private boolean isIll;
	private Flag flag;

	private Architecture architecture;

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
		return agentPos;
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
		visibility += MapController.getMap().getLandscape(agentPos)
				.getVisibility();

		if (TimeController.getTime().isNight())
			visibility--;

		return visibility;
	}

	public Architecture getArch() {
		return architecture;
	}

	/***************
	 *** SETTERS ***
	 ***************/

	public void setTeamId(int id) {
		this.teamId = id;
	}

	public void setPos(WorldPosition pos) {
		agentPos = pos;
		sprite = down;
	}

	public void setIll(boolean isIll) {
		this.isIll = isIll;
	}

	/***********************
	 *** STATE MODIFIERS ***
	 ***********************/

	public void stop() {
		ballon = AnimationLoader.getLoader().getStop();

		if (isIll && life > 85) {
			setIll(false);
		}

		increaseLife(1);
		fatigue = Math.min(100, fatigue - FATIGUE_RECOVER);
		fatigue = Math.max(fatigue, 0);
	}

	public void catchFlag() {
		if (MapController.getMap().getLandscape(agentPos).hasFlag()) {
			flag = MapController.getMap().getLandscape(agentPos).removeFlag();
		}
	}

	public void dropFlag() {
		if (hasFlag()) {
			MapController.getMap().getLandscape(agentPos).setFlag(flag);
			flag = null;
		}
	}

	public void attack(Agent agent) {
		ballon = AnimationLoader.getLoader().getAttack();
		int hitRate = random.nextInt(100);

		if (hitRate > 95) {
			hitRate = agentAttack * 2;
		} else if (hitRate < 15) {
			hitRate = 0;
		} else {
			hitRate = agentAttack;
		}

		agent.decreaseLife(hitRate);
	}

	public void decreaseLife(int value) {
		life = Math.max(0, life - value);
	}

	public void increaseLife(int value) {
		life = Math.min(life + value, 100);
	}

	public void decreaseFatigue(int value) {
		fatigue = Math.max(0, fatigue - value);
	}

	public void increaseFatigue(int value) {
		fatigue = Math.min(fatigue + value, 100);
	}

	public abstract int habilityRate(int nInjured, int nTired, int nEnemy,
			boolean flag);

	/* this agent use its ability at MapPosition pos */
	public abstract void useAbility(MapPosition pos);

	/************************
	 *** STATE PREDICATES ***
	 ************************/

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

	/************************
	 *** MOVEMENT RELATED ***
	 ************************/

	private void move(int delta) {

		/* to avoid the agent get out of the matrix */
		delta = Math.min(delta, 20);
		MapPosition oldPos = agentPos.getMapPosition();
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
		ballon = AnimationLoader.getLoader().getApproach();

		/* to avoid the agent get out of the matrix */
		delta = Math.min(delta, 20);
		MapPosition oldPos = agentPos.getMapPosition();

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

		WorldPosition newPos = new WorldPosition(agentPos.getX(),
				agentPos.getY() + delta * getAgentSpeed(oldPos));

		if (!isBlocked(newPos.getMapPosition())) {
			changePosition(delta, oldPos, newPos);
			return true;
		}
		return false;
	}

	/*
	 * if the agent can move up it moves and returns true. Returns false
	 * otherwise.
	 */
	public boolean moveUp(int delta, MapPosition oldPos) {
		sprite = up;
		ballon = AnimationLoader.getLoader().getUpArrow();

		WorldPosition newPos = new WorldPosition(agentPos.getX(),
				agentPos.getY() - delta * getAgentSpeed(oldPos));

		if (!isBlocked(newPos.getMapPosition())) {
			changePosition(delta, oldPos, newPos);
			return true;
		}
		return false;
	}

	/*
	 * if the agent can move to the right it moves and returns true. Returns
	 * false otherwise.
	 */
	public boolean moveRight(int delta, MapPosition oldPos) {
		sprite = right;
		ballon = AnimationLoader.getLoader().getRightArrow();

		WorldPosition newPos = new WorldPosition(agentPos.getX() + delta
				* getAgentSpeed(oldPos), agentPos.getY());

		if (!isBlocked(newPos.getMapPosition())) {
			changePosition(delta, oldPos, newPos);
			return true;
		}
		return false;
	}

	/*
	 * if the agent can move to the left it moves and returns true. Returns
	 * false otherwise.
	 */
	public boolean moveLeft(int delta, MapPosition oldPos) {
		sprite = left;
		ballon = AnimationLoader.getLoader().getLeftArrow();

		WorldPosition newPos = new WorldPosition(agentPos.getX() - delta
				* getAgentSpeed(oldPos), agentPos.getY());

		if (!isBlocked(newPos.getMapPosition())) {
			changePosition(delta, oldPos, newPos);
			return true;
		}
		return false;
	}

	public void changePosition(int delta, MapPosition oldPos,
			WorldPosition newPos) {
		sprite.update(delta);
		ballon.update(delta);

		agentPos = newPos;

		MapController.getMap().getLandscape(oldPos).setAgent(null);
		MapController.getMap().getLandscape(agentPos).setAgent(this);

		if (!oldPos.isSamePosition(agentPos.getMapPosition())) {
			MapController.getMap().getLandscape(agentPos).takePenalty();
		}
	}

	/*********************
	 *** GAME RELATED ****
	 *********************/

	public void update(int delta) {
		architecture.makeAction(this, delta);
	}

	@Override
	public void render(Graphics g) {
		sprite.draw(agentPos.getX(), agentPos.getY());
		ballon.draw(agentPos.getX() - 10, agentPos.getY());

		g.setColor(new Color(1f, 1f, 1f, 1f));
		g.drawString("hp:" + life, agentPos.getX() + 3, agentPos.getY() - 20);
		g.drawString("fp:" + getFatigue() + "", agentPos.getX() + 3,
				agentPos.getY() + 30);
		g.drawString("T" + getTeamId() + "A" + getAgentId(),
				agentPos.getX() - 40, agentPos.getY() + 30);

		if (hasFlag()) {
			AnimationLoader.getLoader().getFlag()
					.draw(agentPos.getX() + 10, agentPos.getY());
		}

		g.setColor(new Color(1f, life * 1.0f / 100,
				((100 - fatigue) * 1.0f) / 100, 0.4f));
		Circle circle = new Circle(agentPos.getX() + 15, agentPos.getY() + 15,
				getVisibilityRange() * MapController.getMap().getTileWidth()
						* 1.5f);

		g.draw(circle);
		g.fill(circle);

		if (isIll()) {
			ill.draw(agentPos.getX(), agentPos.getY());
		}
	}
}
