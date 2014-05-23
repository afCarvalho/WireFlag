package pt.tecnico.aasma.wireflag.agent.architecture.deliberative.action.sequence;

import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.Beliefs;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.WorldState;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.action.Action;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.action.MoveAction;
import pt.tecnico.aasma.wireflag.util.position.MapPosition;

public class MoveActionSequence extends ActionSequence {

	private double dangerValue = 1;

	public MoveActionSequence(Beliefs beliefs) {
		super(beliefs);
	}

	public MoveActionSequence(Beliefs beliefs, ActionSequence actionSeq) {
		super(beliefs, actionSeq);
		dangerValue = Math.min(dangerValue,
				((MoveActionSequence) actionSeq).dangerValue);
	}

	@Override
	public void addAction(Action a) {
		dangerValue = Math.min(dangerValue, getDangerUtility(a.getPos()));
		value += getLandUtility(a.getPos());
		// System.err.println("VALUE " + value + " SIZE " + actions.size());
		actions.addLast(a);
	}

	@Override
	public double getSequenceValue() {
		double distance = beliefs.getAgentPos().getDistanceFrom(getTailPos()) + 1;
		/*
		 * if (distance > 1) { System.err.println("VALUE " + value + " DANGEr "
		 * + dangerValue + " N ACTIONS " + actions.size() + " DISTANCE " +
		 * distance + " RESULT " + (value * dangerValue) / (actions.size() +
		 * distance));
		 * 
		 * }
		 */

		return (value * dangerValue) / (actions.size() * distance);
	}

	private double getLandUtility(MapPosition pos) {
		return beliefs.getWorldState(pos.getX(), pos.getY()).getLandRating();
	}

	private double getDangerUtility(MapPosition pos) {
		if (beliefs.blockedWay(pos.getX(), pos.getY())) {
			return -1;
		} else {
			return 1;
		}
	}
}
