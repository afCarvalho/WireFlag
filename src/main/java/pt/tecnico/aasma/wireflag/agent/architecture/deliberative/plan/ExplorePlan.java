package pt.tecnico.aasma.wireflag.agent.architecture.deliberative.plan;

import java.util.LinkedList;

import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.Beliefs;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.WorldState;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.action.DropFlagAction;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.action.MoveAction;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.action.sequence.ActionSequence;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.action.sequence.MoveActionSequence;
import pt.tecnico.aasma.wireflag.util.position.MapPosition;

public class ExplorePlan extends Plan {

	public ExplorePlan(Beliefs beliefs) {
		super(beliefs);
	}

	@Override
	public void createNewAction(MapPosition pos, ActionSequence actionSeq) {
		createExploreAction(actSequences, beliefs, pos, actionSeq);
	}

	public static void createExploreAction(
			LinkedList<ActionSequence> actSequences, Beliefs beliefs,
			MapPosition pos, ActionSequence actionSeq) {

		MoveActionSequence seq;

		if (actionSeq == null) {
			seq = new MoveActionSequence(beliefs);
		} else {
			seq = new MoveActionSequence(beliefs, actionSeq);
		}

		seq.addAction(new MoveAction(pos));
		actSequences.add(seq);

		if (beliefs.hasNewPosition()) {
			if (beliefs.getWorldState(pos.getX(), pos.getY())
					.isNewlyDiscovered()) {
				seq.setFinished(true);
			}
		} else if (beliefs.hasUnknownPosition()) {
			if (getNUnknownAdjacent(beliefs, pos)) {
				seq.setFinished(true);
			}
		}
	}

	private static boolean getNUnknownAdjacent(Beliefs beliefs,
			MapPosition position) {

		MapPosition pos = beliefs.getWorldState(position.getX(),
				position.getY()).getPosition();

		WorldState p1 = beliefs.getWorldState(pos.getX() + 1, pos.getY());
		WorldState p2 = beliefs.getWorldState(pos.getX() - 1, pos.getY());
		WorldState p3 = beliefs.getWorldState(pos.getX(), pos.getY() + 1);
		WorldState p4 = beliefs.getWorldState(pos.getX(), pos.getY() - 1);

		if (p1 != null && p1.isUnknown()) {
			return true;
		} else if (p2 != null && p2.isUnknown()) {
			return true;
		} else if (p3 != null && p3.isUnknown()) {
			return true;
		} else if (p4 != null && p4.isUnknown()) {
			return true;
		}
		return false;
	}
}
