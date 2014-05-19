package pt.tecnico.aasma.wireflag.agent.architecture.deliberative.action.explore;

import pt.tecnico.aasma.wireflag.agent.Agent;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.Beliefs;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.WorldState;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.action.Action;
import pt.tecnico.aasma.wireflag.util.position.MapPosition;

public abstract class MoveAction extends Action {

	public MoveAction(Beliefs beliefs, MapPosition position,
			Action previouAction) {
		super(beliefs, position, previouAction);
	}

	@Override
	public boolean act(Agent agent, int delta) {

		agent.approachTile(delta, position);
		beliefs.countKm();

		return beliefs.getAgentPos().isSamePosition(position);
	}

	public abstract double getValue();

	public double getLandUtility() {
		double result = beliefs.getWorldState(position.getX(), position.getY())
				.getLandRating();

		/*
		 * if (previousAction == null) { return result; } else { return result +
		 * ((MoveAction) previousAction).getLandUtility(); }
		 */
		return result;
	}

	public double getDangerUtility() {
		WorldState wState = beliefs.getWorldState(position.getX(),
				position.getY());
		if (wState.hasFire() || wState.hasExtremeWeather()
				|| wState.isBlocked()) {
			return -1;
		} else if (previousAction != null) {
			return ((MoveAction) previousAction).getDangerUtility();
		} else {
			return 1;
		}
	}

	public double getLandValue() {
		//System.out.println("VALUE " + getValue() + " " + getNActions());
		if (previousAction != null) {
			//System.out.println("PREVIOUS..");
			return getValue() + ((MoveAction) previousAction).getLandValue();
		} else {
			return getValue();
		}
	}

	@Override
	public double getSequenceValue() {

		//System.out.println("S--------------------------");
		/*System.out.println("ACTIONS VALUE " + sequence + " DANGER "
				+ getDangerUtility() + " N ACTIONS " + getNActions()
				+ " RESULT " + sequence * getDangerUtility() / getNActions()
				+ " IS FINISHED " + isFinished);
		System.out.println("E--------------------------");*/
		return getLandValue() * getDangerUtility() / getNActions();
	}
}
