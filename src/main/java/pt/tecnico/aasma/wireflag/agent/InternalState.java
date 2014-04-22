package pt.tecnico.aasma.wireflag.agent;

import java.util.ArrayList;
import java.util.List;

import pt.tecnico.aasma.wireflag.environment.controller.MapController;
import pt.tecnico.aasma.wireflag.environment.landscape.Landscape;
import pt.tecnico.aasma.wireflag.environment.perception.Perception;
import pt.tecnico.aasma.wireflag.util.MapPosition;

public class InternalState {

	private MapPosition endPos;
	private MapPosition flagPos;
	private boolean teamHasFlag;
	private Perception[][] world;
	private int horizontalSize;
	private int verticalSize;

	public static final int EXPLORED = -1;
	public static final int NEWLY_DISCOVERED = 1;
	public static final int UNKNOWN = 0;

	public InternalState() {
		horizontalSize = MapController.getMap().getNHorizontalTiles();
		verticalSize = MapController.getMap().getNVerticalTiles();
		world = new Perception[horizontalSize][verticalSize];

		for (int i = 0; i < horizontalSize; i++) {
			for (int j = 0; j < verticalSize; j++) {
				world[i][j] = new Perception(new MapPosition(i, j), 0, 0);
				world[i][j].setBlocked(true);
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

	public Perception[][] getWorld() {
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
				world[i][j].setExtremeWeather(false);
				world[i][j].setFire(false);
				world[i][j].setAnimal(false);
				world[i][j].setTiredAgent(false);
				world[i][j].setInjuredAgent(false);
				world[i][j].setAgentAttack(0);
				world[i][j].setEnemy(false);
				if (world[i][j].getId() == NEWLY_DISCOVERED) {
					world[i][j].setId(EXPLORED);
				}
			}
		}

		for (Perception p : perceptions) {
			if (p.hasFlag()) {
				flagPos = p.getPosition();
			} else if (p.hasEndPoint()) {
				endPos = p.getPosition();
			}

			if (world[p.getPosition().getX()][p.getPosition().getY()].getId() == UNKNOWN) {
				p.setId(NEWLY_DISCOVERED);
			} else {
				p.setId(EXPLORED);
			}

			world[p.getPosition().getX()][p.getPosition().getY()] = p;
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
}
