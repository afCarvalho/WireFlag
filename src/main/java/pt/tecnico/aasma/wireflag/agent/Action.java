package pt.tecnico.aasma.wireflag.agent;

import java.util.List;

import pt.tecnico.aasma.wireflag.environment.perception.Perception;
import pt.tecnico.aasma.wireflag.util.MapPosition;

public class Action {

	private Perception perception;
	private Action ancestor;
	private int intention;
	private int action;

	public static int MOVE_ACTION = 0;
	public static int STOP_ACTION = 1;
	public static int HABILITY_ACTION = 2;

	public Action(Action action, Perception p, int intention) {
		this.ancestor = action;
		this.perception = p;
		this.intention = intention;
	}

	public MapPosition getPos() {
		return perception.getPosition();
	}

	public Perception getPerception() {
		return perception;
	}

	public double getValue(InternalState state, Agent a) {

		double landUtility = getClimateUtility() + getLandscapeUtility()
				+ getFireUtility() + getFlagUtility() + getEndUtility(state)
				+ getIllUtility(a) + getAnimalUtility(a);

		double stopUtility = getStopUtility(a) + getIllUtility(a);
		double habilityUtility = getHabilityUtility(state, a);

		if (intention == Intention.END_GAME) {
			landUtility += getEndUtility(state);
		}

		if (intention == Intention.GET_FLAG) {
			landUtility += getFlagUtility();
		}

		if (intention == Intention.SURVIVE) {
			stopUtility = 2 * stopUtility;
			landUtility += getAnimalUtility(a) + getClimateUtility()
					+ getFireUtility() + getIllUtility(a);
		}

		if (intention == Intention.TEAM_SURVIVE) {
			stopUtility = 2 * stopUtility;
			habilityUtility = 2 * habilityUtility;
		}

		if (intention == Intention.MOVE) {
			landUtility = 2 * landUtility;
		}

		if (landUtility > stopUtility && landUtility > habilityUtility) {
			action = MOVE_ACTION;
		}

		if (stopUtility > landUtility && stopUtility > habilityUtility) {
			action = STOP_ACTION;
		}

		if (habilityUtility > landUtility && habilityUtility > stopUtility) {
			action = HABILITY_ACTION;
		}

		return landUtility + stopUtility + habilityUtility;
	}

	public int getAction() {
		return action;
	}

	public void getActionsList(List<Action> actions) {

		if (actions.size() == 0) {
			actions.add(this);
		}
		actions.add(ancestor);
	}

	/*************************
	 *** LANDSCAPE FACTORS ***
	 *************************/

	private double getClimateUtility() {
		if (perception.hasExtremeWeather()) {
			return -20;
		} else {
			return 0;
		}
	}

	private double getLandscapeUtility() {
		return 10 * perception.getLandRating();
	}

	private double getFireUtility() {
		if (perception.hasFire()) {
			return -30;
		} else {
			return 0;
		}
	}

	private double getFlagUtility() {
		if (perception.hasFlag()) {
			return 50;
		} else {
			return 0;
		}
	}

	private double getEndUtility(InternalState state) {
		if (state.teamHasFlag()) {
			return 50;
		} else {
			return 0;
		}
	}

	private double getIllUtility(Agent a) {
		if (a.isIll()) {
			return 20;
		}
		return 0;
	}

	private double getAnimalUtility(Agent a) {
		return (100 - a.getLife()) / 3;
	}

	/***********************
	 *** SPECIAL FACTORS ***
	 ***********************/

	private double getStopUtility(Agent a) {
		if (a.hasLowLife()) {
			return 34;
		} else {
			return 0;
		}
	}

	private double getHabilityUtility(InternalState state, Agent a) {
		return a.habilityRate(state.hasWeakTeamMember(),
				state.hasTiredTeamMember(), state.hasEnemyClose(a),
				state.hasFlagPos() && !state.teamHasFlag());
	}

}
