package pt.tecnico.aasma.wireflag.agent;

import java.util.ArrayList;
import java.util.List;

import pt.tecnico.aasma.wireflag.environment.perception.Perception;
import pt.tecnico.aasma.wireflag.util.MapPosition;

public class InternalState {

	private MapPosition endPos;
	private MapPosition flagPos;
	private List<Perception> perceptions;
	private boolean teamHasFlag;

	public InternalState() {
		perceptions = new ArrayList<Perception>();
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

	public List<Perception> getPerceptions() {
		return perceptions;
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
		this.perceptions = perceptions;

		for (Perception p : perceptions) {
			if (p.hasFlag()) {
				flagPos = p.getPosition();
			} else if (p.hasEndPoint()) {
				endPos = p.getPosition();
			}
		}
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
		for (Perception p : perceptions) {
			if (p.hasEnemy() && !(p.getAgentAttack() > a.getAgentAttack())
					|| (p.hasInjuredAgent() && !a.hasLowLife())) {
				result++;
			}
		}
		return result;
	}

	public int hasWeakTeamMember() {
		int result = 0;

		for (Perception p : perceptions) {
			if (!p.hasEnemy() && p.hasInjuredAgent()) {
				result++;
			}
		}
		return result;
	}

	public int hasTiredTeamMember() {
		int result = 0;

		for (Perception p : perceptions) {
			if (p.hasEnemy() && p.hasTiredAgent()) {
				result++;
			}
		}
		return result;
	}
}
