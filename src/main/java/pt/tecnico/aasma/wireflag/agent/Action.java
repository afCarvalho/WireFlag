package pt.tecnico.aasma.wireflag.agent;

import java.util.ArrayList;
import java.util.LinkedList;
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
	public static int GET_FLAG = 3;
	public static int GET_ANIMAL = 4;
	public static int GET_END = 5;
	public static int ATTACK = 6;

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

	public double getValue(InternalState state, Agent a, MapPosition initialPos) {

		double moveUtility = getClimateUtility() + getLandscapeUtility()
				+ getFireUtility() + getIllUtility(a);
		double flagUtility = getFlagUtility();
		double animalUtility = getAnimalUtility(a);
		double endUtility = getEndUtility(state);
		double stopUtility = getStopUtility(a) + getIllUtility(a);
		double habilityUtility = getHabilityUtility(state, a);
		double enemyUtility = getEnemyUtility(a, state);

		if (intention == Intention.END_GAME) {
			endUtility += getEndUtility(state);
		}

		if (intention == Intention.GET_FLAG) {
			flagUtility += getFlagUtility();
		}

		if (intention == Intention.SURVIVE) {
			stopUtility += stopUtility;
			animalUtility += getAnimalUtility(a);
		}

		if (intention == Intention.TEAM_SURVIVE) {
			stopUtility += stopUtility;
			habilityUtility += habilityUtility;
		}

		if (intention == Intention.MOVE) {
			moveUtility += moveUtility;
		}

		if (intention == Intention.ATTACK) {
			enemyUtility += enemyUtility;
		}

		/*
		 * if (perception.isBlocked()) { moveUtility = 0; }
		 */

		ArrayList<Double> utilities = new ArrayList<Double>();
		utilities.add(moveUtility);
		// utilities.add(flagUtility);
		// utilities.add(animalUtility);
		// utilities.add(endUtility);
		utilities.add(stopUtility);
		// utilities.add(habilityUtility);
		// utilities.add(enemyUtility);

		//System.out.println("Move utility " + moveUtility + " "
		//		+ perception.getId());
		// System.out.println("Flag utility " + flagUtility);
		// System.out.println("Animal utility " + flagUtility);
		// System.out.println("End utility " + flagUtility);
		//System.out.println("Stop utility " + stopUtility);
		//System.out.println("agent fatigue " + a.getFatigue());
		// System.out.println("Enemy utility " + flagUtility);

		double higherUtility = 0;
		double totalUtility = 0;

		for (Double value : utilities) {
			higherUtility = Math.max(higherUtility, value);
			totalUtility += value;
		}

		/*
		 * totalUtility = totalUtility (Math.abs(initialPos.getX() -
		 * perception.getPosition().getX()) + Math .abs(initialPos.getY() -
		 * perception.getPosition().getY())) + 1;
		 */

		if (ancestor != null) {
			totalUtility += ancestor.getValue(state, a, initialPos);
		}

		if (moveUtility == higherUtility) {
			System.out.println("MOVE ACTION");
			action = MOVE_ACTION;
		}

		if (higherUtility == flagUtility) {
			action = GET_FLAG;
		}

		if (higherUtility == animalUtility) {
			action = GET_ANIMAL;
		}

		if (higherUtility == endUtility) {
			action = GET_END;
		}

		if (stopUtility == higherUtility) {
			System.out.println("STOP ACTION");
			action = STOP_ACTION;
		}

		/*
		 * if (habilityUtility == higherUtility) { action = HABILITY_ACTION; }
		 */

		if (enemyUtility == higherUtility) {
			action = ATTACK;
		}

		if (stopUtility == higherUtility) {
			action = STOP_ACTION;
		}

		return totalUtility;
	}

	public int getAction() {
		return action;
	}

	public void getActionsList(LinkedList<Action> actions) {

		if (actions.size() == 0) {
			actions.addFirst(this);
		}

		if (ancestor != null) {
			actions.addFirst(ancestor);
			ancestor.getActionsList(actions);
		}
	}

	public int getNActions() {
		if (ancestor == null) {
			return 0;
		}

		return 1 + ancestor.getNActions();
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
		System.out.println(getNActions());
		return (6 * perception.getLandRating() + 4 * (1 + perception.getId()))
				/ (getNActions() + 1);
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

	private double getEnemyUtility(Agent a, InternalState state) {
		if (state.hasEnemyClose(a) > 0) {
			return (state.hasEnemyClose(a) + 1) * 10;
		} else {
			return 0;
		}
	}

	/***********************
	 *** SPECIAL FACTORS ***
	 ***********************/

	private double getStopUtility(Agent a) {
		if (a.hasLowLife() || a.hasFatigue()) {
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
