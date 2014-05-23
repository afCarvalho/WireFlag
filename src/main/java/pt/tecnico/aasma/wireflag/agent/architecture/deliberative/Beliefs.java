package pt.tecnico.aasma.wireflag.agent.architecture.deliberative;

import pt.tecnico.aasma.wireflag.agent.Agent;
import pt.tecnico.aasma.wireflag.environment.controller.AgentController;
import pt.tecnico.aasma.wireflag.environment.controller.MapController;
import pt.tecnico.aasma.wireflag.environment.perception.Perception;
import pt.tecnico.aasma.wireflag.util.position.MapPosition;

public class Beliefs {

	private MapPosition flagPos;
	private boolean teamHasFlag;
	private WorldState[][] world;
	private int horizontalSize;
	private int verticalSize;
	private KmCounter kmCounter;
	private double worldExploredPercentage;
	private WorldState animalState;
	private WorldState enemyState;
	private Agent agent;
	private boolean isAbilityUseful;
	private boolean reconsider;
	private int positionAvailable;
	private MapPosition teamBasePosition;

	// private MapPosition enemyPos;

	public Beliefs() {
		horizontalSize = MapController.getMap().getNHorizontalTiles();
		verticalSize = MapController.getMap().getNVerticalTiles();
		world = new WorldState[horizontalSize][verticalSize];
		kmCounter = new KmCounter();

		for (int i = 0; i < horizontalSize; i++) {
			for (int j = 0; j < verticalSize; j++) {
				world[i][j] = new WorldState(i, j);
				if (i == horizontalSize - 1 || j == verticalSize - 1 || i == 0
						|| j == 0) {
					world[i][j].setAsExplored();
				}
			}
		}
	}

	/***************
	 *** GETTERS ***
	 ***************/
	public MapPosition getTeamBase() {
		return teamBasePosition;
	}

	public MapPosition getAgentPos() {
		return agent.getPos().getMapPosition();
	}

	public int getAgentVisibilityRange() {
		return agent.getVisibilityRange();
	}

	public boolean isAgentAbilityUseful() {
		return isAbilityUseful;
	}

	public MapPosition getFlagPos() {
		return flagPos;
	}

	public int getLife() {
		return agent.getLife();
	}

	public int getFatigue() {
		return agent.getFatigue();
	}

	public int getDirection() {
		return agent.getDirection();
	}

	public WorldState getAnimalState() {
		return animalState;
	}

	public WorldState getEnemyState() {
		return enemyState;
	}

	/***************
	 *** SETTERS ***
	 ***************/

	public void setTeamBasePos(MapPosition pos) {
		this.teamBasePosition = pos;
	}

	public void setFlagPos(MapPosition flagPos) {
		this.flagPos = flagPos;
	}

	public void setAgent(Agent a) {
		this.agent = a;
	}

	public void updateBeliefs() {
		reconsider = false;
		isAbilityUseful = false;
		enemyState = null;
		double exploredPercentage = 0;
		positionAvailable = -1;
		// String message="";

		for (Perception p : agent.getPerceptions()) {
			if (p.hasFlag()) {
				flagPos = p.getPosition();
			}
			reconsider = world[p.getPosition().getX()][p.getPosition().getY()]
					.setState(p) || reconsider;

			isAbilityUseful = isAbilityUseful
					|| world[p.getPosition().getX()][p.getPosition().getY()]
							.isAbilityUseful();

			if (p.hasEnemy()) {
				enemyState = getWorldState(p.getPosition().getX(), p
						.getPosition().getY());
			}
		}

		if (flagPos != null
				&& !getWorldState(flagPos.getX(), flagPos.getY()).hasFlag()) {
			flagPos = null;
		}

		for (int i = 0; i < horizontalSize; i++) {
			for (int j = 0; j < verticalSize; j++) {
				world[i][j].updateState();
				positionAvailable = Math.max(world[i][j].getCondition(),
						positionAvailable);
				exploredPercentage += Math.abs(world[i][j].getCondition());

				if (world[i][j].hasAnimal()
						&& (getAnimalState() == null || !getAnimalState()
								.hasAnimal())) {
					animalState = world[i][j];
				} else if (world[i][j].hasAnimal()
						&& world[i][j].getPosition().getDistanceFrom(
								getAgentPos()) < animalState.getPosition()
								.getDistanceFrom(getAgentPos())) {
					animalState = world[i][j];
				}

			}
		}

		worldExploredPercentage = (exploredPercentage / horizontalSize * verticalSize) / 100;
	}

	public double getWorldExploredPercentage() {
		return worldExploredPercentage;
	}

	public int getHorizontalSize() {
		return horizontalSize;
	}

	public void setHorizontalSize(int horizontalSize) {
		this.horizontalSize = horizontalSize;
	}

	public int getVerticalSize() {
		return verticalSize;
	}

	public void setVerticalSize(int verticalSize) {
		this.verticalSize = verticalSize;
	}

	public void setTeamHasFlag(boolean teamHasFlag) {
		this.teamHasFlag = teamHasFlag;
	}

	public WorldState getWorldState(int x, int y) {
		return world[x][y];
	}

	/************************
	 *** STATE PREDICATES ***
	 ************************/

	public boolean hasFlagPos() {
		return flagPos != null;
	}

	public boolean teamHasFlag() {
		return teamHasFlag;
	}

	public boolean shouldStop() {
		return kmCounter.isBurningOut(getFatigue());
	}

	public boolean isIll() {
		return agent.isIll();
	}

	public boolean carriesFlag() {
		return agent.hasFlag();
	}

	public boolean blockedWay(int x, int y) {
		WorldState world = getWorldState(x, y);
		return world.hasExtremeWeather() || world.hasAnimal()
				|| world.hasFire() || world.isBlocked() || world.hasAgent()
				&& !world.getPosition().isSamePosition(getAgentPos());
	}

	public boolean reconsider() {
		return reconsider;
	}

	public boolean hasNewPosition() {
		return positionAvailable == WorldState.NEWLY_DISCOVERED;
	}

	public boolean hasUnknownPosition() {
		return positionAvailable == WorldState.UNKNOWN;
	}

	/************************
	 *** STATE MODIFIERS ***
	 ************************/

	public void countKm() {
		kmCounter.update(getFatigue());
	}

	public void resetKm() {
		kmCounter.reset(getFatigue());
	}

	public void setBasePos(MapPosition teamBasePos) {
		this.teamBasePosition = teamBasePos;
	}
}
