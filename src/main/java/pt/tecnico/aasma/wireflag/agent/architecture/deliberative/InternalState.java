package pt.tecnico.aasma.wireflag.agent.architecture.deliberative;

import java.util.List;

import pt.tecnico.aasma.wireflag.agent.Agent;
import pt.tecnico.aasma.wireflag.environment.controller.MapController;
import pt.tecnico.aasma.wireflag.environment.perception.Perception;
import pt.tecnico.aasma.wireflag.util.MapPosition;

public class InternalState {

	private MapPosition endPos;
	private MapPosition flagPos;
	private boolean teamHasFlag;
	private WorldState[][] world;
	private int horizontalSize;
	private int verticalSize;
	private KmCounter kmCounter;

	public InternalState() {
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

	public MapPosition getFlagPos() {
		return flagPos;
	}

	public WorldState[][] getWorld() {
		return world;
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

	public void setPerceptions(List<Perception> perceptions) {

		for (int i = 0; i < horizontalSize; i++) {
			for (int j = 0; j < verticalSize; j++) {
				world[i][j].updateState();
			}
		}

		for (Perception p : perceptions) {
			if (p.hasFlag()) {
				flagPos = p.getPosition();
			} else if (p.hasEndPoint()) {
				endPos = p.getPosition();
			}
			world[p.getPosition().getX()][p.getPosition().getY()]
					.setPerception(p);
		}
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

	public int hasEnemyClose(Agent a) {
		int result = 0;

		for (int i = 0; i < horizontalSize; i++) {
			for (int j = 0; j < verticalSize; j++) {
				if (world[i][j].hasEnemy()
						&& !(world[i][j].getAgentAttack() > a.getAgentAttack())
						|| (world[i][j].hasInjuredAgent() && !a.hasLowLife())) {
					result++;
				}
			}
		}
		return result;
	}

	public int hasWeakTeamMember() {
		int result = 0;

		for (int i = 0; i < horizontalSize; i++) {
			for (int j = 0; j < verticalSize; j++) {
				if (!world[i][j].hasEnemy() && world[i][j].hasInjuredAgent()) {
					result++;
				}
			}
		}
		return result;
	}

	public int hasTiredTeamMember() {
		int result = 0;

		for (int i = 0; i < horizontalSize; i++) {
			for (int j = 0; j < verticalSize; j++) {
				if (world[i][j].hasEnemy() && world[i][j].hasTiredAgent()) {
					result++;
				}
			}
		}
		return result;
	}

	public void countKm(int actualFatigue) {
		kmCounter.update(actualFatigue);
	}

	public void resetKm(int actualFatigue) {
		kmCounter.reset(actualFatigue);
	}

	public boolean shouldStop(int actualFatigue) {
		return kmCounter.isBurningOut(actualFatigue);
	}
}
