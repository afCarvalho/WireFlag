package pt.tecnico.aasma.wireflag.agent.architecture.deliberative;

import pt.tecnico.aasma.wireflag.agent.Agent;
import pt.tecnico.aasma.wireflag.environment.controller.MapController;
import pt.tecnico.aasma.wireflag.environment.perception.Perception;
import pt.tecnico.aasma.wireflag.util.position.MapPosition;

public class Beliefs {

	private MapPosition endPos;
	private MapPosition flagPos;
	private boolean teamHasFlag;
	private WorldState[][] world;
	private int horizontalSize;
	private int verticalSize;
	private KmCounter kmCounter;
	private int worldExploredPercentage;
	private MapPosition animalPos;
	private Agent agent;
	private boolean reconsider;

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
	public MapPosition getEndPos() {
		return endPos;
	}

	public MapPosition getAgentPos() {
		return agent.getPos().getMapPosition();
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

	public MapPosition getAnimalPos() {
		return animalPos;
	}

	/***************
	 *** SETTERS ***
	 ***************/

	public void setEndPos(MapPosition endPos) {
		this.endPos = endPos;
	}

	public void setFlagPos(MapPosition flagPos) {
		this.flagPos = flagPos;
	}

	public void setAgent(Agent a) {
		this.agent = a;
	}

	public void updateBeliefs() {
		reconsider = false;

		for (Perception p : agent.getPerceptions()) {
			if (p.hasFlag()) {
				flagPos = p.getPosition();
			} else if (p.hasEndPoint()) {
				endPos = p.getPosition();
			}
			reconsider = world[p.getPosition().getX()][p.getPosition().getY()]
					.setState(p) || reconsider;
		}

		int exploredPercentage = 0;

		for (int i = 0; i < horizontalSize; i++) {
			for (int j = 0; j < verticalSize; j++) {
				world[i][j].updateState();
				exploredPercentage += Math.abs(world[i][j].getCondition());

				if (world[i][j].hasAnimal()
						&& world[i][j].getPosition().getDistanceFrom(
								getAgentPos()) < animalPos
								.getDistanceFrom(getAgentPos())) {
					animalPos = world[i][j].getPosition();
				}
			}
		}

		worldExploredPercentage = exploredPercentage / horizontalSize
				* verticalSize;
	}

	public int getWorldExploredPercentage() {
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

	public boolean hasEndPos() {
		return endPos != null;
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
				|| world.hasFire() || world.hasFlag() || world.isBlocked();
	}

	public boolean reconsider() {
		return reconsider;
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
}
