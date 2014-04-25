package pt.tecnico.aasma.wireflag.agent;

import java.util.ArrayList;
import java.util.LinkedList;

import pt.tecnico.aasma.wireflag.util.MapPosition;

public class Action {

	private WorldState perception;
	private Action ancestor;
	private int intention;
	private int action;
	private double val;

	public static int MOVE_ACTION = 0;
	public static int STOP_ACTION = 1;
	public static int HABILITY_ACTION = 2;
	public static int GET_FLAG = 3;
	public static int GET_ANIMAL = 4;
	public static int GET_END = 5;
	public static int ATTACK = 6;

	public static final int GO_FURTHER = 0;
	public static final int GO_CLOSE = 1;
	public static final int GO_AWAY = 2;

	public Action(Action action, WorldState p, int intention) {
		this.ancestor = action;
		this.perception = p;
		this.intention = intention;
	}

	public MapPosition getPos() {
		return perception.getPosition();
	}

	public WorldState getPerception() {
		return perception;
	}

	public double getNUnknownAdjacent(Agent a, InternalState state) {

		MapPosition pos = perception.getPosition();
		MapPosition aPos = a.getPos().getMapPosition();
		double result = 1;
		double distance = Math.abs(pos.getX() - aPos.getX())
				+ Math.abs(pos.getY() - aPos.getY()) + 1;

		// System.out.println("Perception " + pos.getX() + " " + pos.getY() +
		// " "
		// + distance);
		// System.out.println("Action " + aPos.getX() + " " + aPos.getY());

		WorldState p1 = state.getWorld()[pos.getX() + 1][pos.getY()];
		WorldState p2 = state.getWorld()[pos.getX() - 1][pos.getY()];
		WorldState p3 = state.getWorld()[pos.getX()][pos.getY() + 1];
		WorldState p4 = state.getWorld()[pos.getX()][pos.getY() - 1];

		/*
		 * for (int i = 0; i < state.getHorizontalSize(); i++) { for (int j = 0;
		 * j < state.getVerticalSize(); j++) { if
		 * (state.getWorld()[i][j].getPosition().isSamePosition(pos)) {
		 * System.out.print(" X "); } else if
		 * (state.getWorld()[i][j].getPosition().isSamePosition(
		 * p1.getPosition())) { System.out.print(" P1/" +
		 * state.getWorld()[i][j].getId()); } else if
		 * (state.getWorld()[i][j].getPosition().isSamePosition(
		 * p2.getPosition())) { System.out.print(" P2/" +
		 * state.getWorld()[i][j].getId()); } if
		 * (state.getWorld()[i][j].getPosition().isSamePosition(
		 * p3.getPosition())) { System.out.print(" P3/" +
		 * state.getWorld()[i][j].getId()); } if
		 * (state.getWorld()[i][j].getPosition().isSamePosition(
		 * p4.getPosition())) { System.out.print(" P4/" +
		 * state.getWorld()[i][j].getId()); } else { System.out .print(" " +
		 * state.getWorld()[i][j].getId() + " "); } }
		 * 
		 * System.out.println(""); }
		 */

		if (p1 != null && p1.isUnknown()) {
			// System.out.println("P1");
			result += 10 * result / distance;
		} else

		if (p2 != null && p2.isUnknown()) {
			// System.out.println("P2");
			result += 10 * result / distance;
		} else

		if (p3 != null && p3.isUnknown()) {
			// System.out.println("P3");
			result += 10 * result / distance;
		} else

		if (p4 != null && p4.isUnknown()) {
			// System.out.println("P4");
			result += 10 * result / distance;
		}

		// System.out.println("result " + result);

		return result;
	}

	public double getValue(InternalState state, Agent a,
			MapPosition initialPos, int option) {

		double moveUtility;

		if (option == GO_FURTHER) {
			moveUtility = (((getMoveUtility(a))) / (getNActions()))
					+ ((getNUnknownAdjacent(a, state)));
		} else if (option == GO_AWAY) {
			moveUtility = getMoveUtility(a) * getNActions()
					+ getNUnknownAdjacent(a, state);
		} else {
			moveUtility = (((getMoveUtility(a)) + ((1 + perception
					.getCondition()) / (getNActions())))) / (getNActions());
		}

		moveUtility = moveUtility
				* Math.min(getClimateUtility(), getFireUtility())
				* getFlagUtility() * getBlockedUtility(a);
		double stopUtility = moveUtility;
		// System.out.println("Climate Utility " + getClimateUtility());
		// System.out.println("Actions " + getNActions() + 1 + " "
		// + getMoveUtility(a) + " " + moveUtility);

		// double flagUtility = getFlagUtility();
		// double animalUtility = getAnimalUtility(a);
		// double endUtility = getEndUtility(state);

		double stopFactor = getStopUtility(a, state);

		if (stopFactor != 0 && stopFactor < a.getFatigue() + 1) {
			// System.out.println("STOPPING IS POSSIBLE!" + stopFactor);
			stopUtility = stopUtility + stopFactor;
		}

		// +
		// getIllUtility(a);
		// double habilityUtility = getHabilityUtility(state, a);
		// double enemyUtility = getEnemyUtility(a, state);

		/*
		 * if (intention == Intention.END_GAME) { endUtility +=
		 * getEndUtility(state); }
		 */

		/*
		 * if (intention == Intention.GET_FLAG) { flagUtility +=
		 * getFlagUtility(); }
		 */

		/*
		 * if (intention == Intention.SURVIVE) { stopUtility += stopUtility;
		 * animalUtility += getAnimalUtility(a); }
		 */

		/*
		 * if (intention == Intention.TEAM_SURVIVE) { stopUtility +=
		 * stopUtility; habilityUtility += habilityUtility; }
		 */

		/*
		 * if (intention == Intention.MOVE) { moveUtility += moveUtility; }
		 */

		/*
		 * if (intention == Intention.ATTACK) { enemyUtility += enemyUtility; }
		 */

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

		// System.out.println("Move utility " + moveUtility + " "
		// + getMoveUtility(a));
		// System.out.println("Flag utility " + flagUtility);
		// System.out.println("Animal utility " + flagUtility);
		// System.out.println("End utility " + flagUtility);
		// System.out.println("Stop utility " + stopUtility);
		// System.out.println("agent fatigue " + a.getFatigue());
		// System.out.println("Enemy utility " + flagUtility);

		// System.out.println("Perception id " + perception.getId());

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

		// if (ancestor != null) {
		// totalUtility += ancestor.getValue(state, a, initialPos);
		// }

		if (moveUtility == higherUtility) {
			/*
			 * System.out.println("MOVE ACTION" + " move " + moveUtility +
			 * " stop " + stopUtility + " stop factor " + stopFactor +
			 * " fatigue " + a.getFatigue() + " NActions " + getNActions());
			 */
			action = MOVE_ACTION;
		} else

		// if (higherUtility == flagUtility) {
		// action = GET_FLAG;
		// } else
		/*
		 * if (higherUtility == animalUtility) { action = GET_ANIMAL; }
		 */
		// if (higherUtility == endUtility) {
		// action = GET_END;
		// } else

		if (stopUtility == higherUtility) {
			/*
			 * System.out.println("STOP ACTION" + " move " + moveUtility +
			 * " stop " + stopUtility + " stop factor " + stopFactor +
			 * " fatigue " + a.getFatigue() + " NActions " + getNActions());
			 */
			action = STOP_ACTION;
		}

		/*
		 * if (habilityUtility == higherUtility) { action = HABILITY_ACTION; }
		 */

		/*
		 * if (enemyUtility == higherUtility) { action = ATTACK; }
		 */

		if (ancestor == null) {
			totalUtility = 0;
		}

		// System.out.println("Total utility " + totalUtility + " n Actions "
		// + getNActions());
		val = totalUtility;

		return totalUtility;
	}

	public Action getAncestor() {
		return ancestor;
	}

	public void setAncestor(Action ancestor) {
		this.ancestor = ancestor;
	}

	public double getValue() {
		return val;
	}

	public int getAction() {
		return action;
	}

	public void getActionsList(LinkedList<Action> actions) {

		/*
		 * if (actions.size() == 0) { actions.addFirst(this); }
		 */
		actions.addFirst(this);

		if (ancestor != null) {
			ancestor.getActionsList(actions);
		}
	}

	public int getNActions() {
		if (ancestor == null) {
			return 1;
		}

		return 1 + ancestor.getNActions();
	}

	/*************************
	 *** LANDSCAPE FACTORS ***
	 *************************/

	public double getMoveUtility(Agent a) {
		double result = /* 10 * */getLandscapeUtility();

		if (ancestor == null) {
			return result;
		} else {
			return result + ancestor.getMoveUtility(a);
		}

	}

	private double getLandscapeUtility() {
		// System.out.println(getNActions());
		return perception.getLandRating();
	}

	private double getClimateUtility() {
		if (perception.hasExtremeWeather()) {
			return -1;
		} else if (ancestor != null) {
			return ancestor.getClimateUtility();
		} else {
			return 1;
		}
	}

	private double getFireUtility() {
		if (perception.hasFire()) {
			return -1;
		} else if (ancestor != null) {
			return ancestor.getFireUtility();
		} else {
			return 1;
		}
	}

	private double getBlockedUtility(Agent a) {
		if (perception.isBlocked()
				&& !a.getPos().getMapPosition()
						.isSamePosition(perception.getPosition())) {
			return 0;
		} else if (ancestor != null) {
			return ancestor.getBlockedUtility(a);
		} else {
			return 1;
		}
	}

	private double getFlagUtility() {
		if (perception.hasFlag()) {
			return 1;
		} else {
			return 1;
		}
	}

	private double getEndUtility(InternalState state) {
		if (state.hasEndPos()) {
			return 500;
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

	public double getStopUtility(Agent a, InternalState state) {
		if (state.shouldStop(a.getFatigue()) && ancestor != null) {
			return 5 + ancestor.getStopUtility(a, state);
		} else if (state.shouldStop(a.getFatigue()) && ancestor == null) {
			return 5;
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
