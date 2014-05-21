package pt.tecnico.aasma.wireflag.agent.architecture.deliberative.plan;

import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.Beliefs;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.action.MoveAction;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.action.sequence.ActionSequence;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.action.sequence.MoveActionSequence;
import pt.tecnico.aasma.wireflag.util.position.MapPosition;

public class SurviveFirePlan extends Plan {

	public SurviveFirePlan(Beliefs beliefs) {
		super(beliefs);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createNewAction(MapPosition pos, ActionSequence actionSeq) {
		MoveActionSequence seq;

		if (actionSeq == null) {
			seq = new MoveActionSequence(beliefs);
		} else {
			seq = new MoveActionSequence(beliefs, actionSeq);
		}

		seq.addAction(new MoveAction(pos));
		actSequences.add(seq);

		if (!beliefs.getWorldState(pos.getX(), pos.getY()).hasFire()) {
			seq.setFinished(true);
		}
	}
}
