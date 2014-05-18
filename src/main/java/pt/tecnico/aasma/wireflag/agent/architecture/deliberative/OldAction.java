package pt.tecnico.aasma.wireflag.agent.architecture.deliberative;

import java.util.ArrayList;
import java.util.LinkedList;

import pt.tecnico.aasma.wireflag.agent.Agent;
import pt.tecnico.aasma.wireflag.util.position.MapPosition;

public class OldAction {

	private WorldState perception;
	private OldAction ancestor;
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

	public OldAction(OldAction action, WorldState p, int intention) {
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

	public double getNUnknownAdjacent(Agent a, Beliefs state) {

		MapPosition pos = perception.getPosition();
		MapPosition aPos = a.getPos().getMapPosition();
		double result = 1;
		double distance = Math.abs(pos.getX() - aPos.getX())
				+ Math.abs(pos.getY() - aPos.getY()) + 1;

		WorldState p1 = state.getWorldState(pos.getX() + 1, pos.getY());
		WorldState p2 = state.getWorldState(pos.getX() - 1, pos.getY());
		WorldState p3 = state.getWorldState(pos.getX(), pos.getY() + 1);
		WorldState p4 = state.getWorldState(pos.getX(), pos.getY() - 1);

		if (p1 != null && p1.isUnknown()) {
			result += 10 * result / distance;
		} else

		if (p2 != null && p2.isUnknown()) {
			result += 10 * result / distance;
		} else

		if (p3 != null && p3.isUnknown()) {
			result += 10 * result / distance;
		} else

		if (p4 != null && p4.isUnknown()) {
			result += 10 * result / distance;
		}

		return result;
	}

	public double getValue(Beliefs state, Agent a, MapPosition initialPos,
			int option) {

		double moveUtility;

		if (option == GO_FURTHER) {
			moveUtility = (((getMoveUtility(a))) / (getNActions()))
					+ ((getNUnknownAdjacent(a, state)));
		} else if (option == GO_AWAY) {
			moveUtility = getMoveUtility(a) * getNActions() + getNActions();
		} else {
			moveUtility = (((getMoveUtility(a)) + ((1 + perception
					.getCondition()) / (getNActions())))) / (getNActions());
		}

		if (!state.getWorldState(a.getPos().getMapPosition().getX(),
				a.getPos().getMapPosition().getY()).hasExtremeWeather()
				&& !state.getWorldState(a.getPos().getMapPosition().getX(),
						a.getPos().getMapPosition().getY()).hasFire()) {
			moveUtility = moveUtility
					* Math.min(Math.min(getClimateUtility(), getFireUtility()),
							getBlockedUtility(a));
		}
		//double stopUtility = moveUtility;
		//double flagUtility = getFlagUtility() * moveUtility;
		//double endUtility = getEndUtility(state, a) * moveUtility;
		/*double stopFactor = getStopUtility(a, state);
		double illFactor = getIllUtility(a);
		double lifeFactor = getLifeUtility(a);

		if (stopFactor != 0 && stopFactor < a.getFatigue() + 1) {
			stopUtility = stopUtility + stopFactor;
		} else if (illFactor != 0 && illFactor < 100 - a.getLife()) {
			stopUtility = stopUtility + illFactor;
		} else if (lifeFactor != 0 && lifeFactor < 100 - a.getLife()) {
			stopUtility = stopUtility + lifeFactor;
		}*/

		ArrayList<Double> utilities = new ArrayList<Double>();
		utilities.add(moveUtility);
		//utilities.add(flagUtility);
		//utilities.add(endUtility);
		//utilities.add(stopUtility);

		double higherUtility = 0;
		double lowerUtility = 0;
		double totalUtility = 0;

		for (Double value : utilities) {
			higherUtility = Math.max(higherUtility, value);
			lowerUtility = Math.min(lowerUtility, value);
			totalUtility += value;
		}

		/*if (moveUtility == higherUtility) {
			action = MOVE_ACTION;
		} else if (higherUtility == flagUtility) {
			action = GET_FLAG;
		} else if (higherUtility == endUtility) {
			action = GET_END;
		} else*/

		/*if (stopUtility == higherUtility) {
			action = STOP_ACTION;
		}*/

		if (ancestor == null) {
			totalUtility = Math.min(lowerUtility, 0);
		}

		val = totalUtility;

		return totalUtility;
	}

	public OldAction getAncestor() {
		return ancestor;
	}

	public void setAncestor(OldAction ancestor) {
		this.ancestor = ancestor;
	}

	public double getValue() {
		return val;
	}

	public int getAction() {
		return action;
	}

	/*public void getActionsList(LinkedList<OldAction> actions) {
		actions.addFirst(this);

		if (ancestor != null) {
			ancestor.getActionsList(actions);
		}
	}*/

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
		return perception.getLandRating();
	}

	public double getClimateUtility() {
		if (perception.hasExtremeWeather()) {
			return -1;
		} else if (ancestor != null) {
			return ancestor.getClimateUtility();
		} else {
			return 1;
		}
	}

	public double getFireUtility() {
		if (perception.hasFire()) {
			return -1;
		} else if (ancestor != null) {
			return ancestor.getFireUtility();
		} else {
			return 1;
		}
	}

	public double getBlockedUtility(Agent a) {
		if (perception.isBlocked()
				&& !a.getPos().getMapPosition()
						.isSamePosition(perception.getPosition())) {
			return -1;
		} else if (ancestor != null) {
			return ancestor.getBlockedUtility(a);
		} else {
			return 1;
		}
	}

	/*private double getFlagUtility() {
		if (perception.hasFlag()) {
			return 100;
		} else {
			return 1;
		}
	}*/

	/*private double getEndUtility(Beliefs state, Agent a) {
		if (state.hasEndPos()
				&& state.getEndPos().isAdjacentPosition(
						perception.getPosition(), a.getDirection())
				&& a.hasFlag()) {
			return 100;
		} else {
			return 1;
		}
	}*/

	/***********************
	 *** SPECIAL FACTORS ***
	 ***********************/
/*
	public double getStopUtility(Agent a, Beliefs state) {
		if (state.shouldStop() && ancestor != null) {
			return 5 + ancestor.getStopUtility(a, state);
		} else if (state.shouldStop() && ancestor == null) {
			return 5;
		} else {
			return 0;
		}
	}

	private double getIllUtility(Agent a) {
		if (a.isIll() && ancestor != null) {
			return 1 + ancestor.getIllUtility(a);
		} else if (a.isIll() && ancestor == null) {
			return 1;
		} else {
			return 0;
		}
	}

	private double getLifeUtility(Agent a) {
		if (a.hasLowLife() && ancestor != null) {
			return 1 + ancestor.getLifeUtility(a);
		} else if (a.hasLowLife() && ancestor == null) {
			return 1;
		} else {
			return 0;
		}
	}*/
}
