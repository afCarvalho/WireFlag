package pt.tecnico.aasma.wireflag.agent.architecture.deliberative.plan;

import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.Beliefs;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.action.StopAction;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.action.sequence.ActionSequence;
import pt.tecnico.aasma.wireflag.util.position.MapPosition;

public class RestPlan extends Plan {

	public RestPlan(Beliefs beliefs) {
		super(beliefs);
	}

	@Override
	public void createNewAction(MapPosition pos, ActionSequence actionSeq) {

		ActionSequence seq;

		if (actionSeq == null) {
			seq = new ActionSequence(beliefs);
		} else {
			seq = new ActionSequence(beliefs, actionSeq);
		}

		while (5 * seq.getSequenceValue() < beliefs.getFatigue()) {
			seq.addAction(new StopAction(beliefs, pos));
		}
		seq.setFinished(true);
		actSequences.addLast(seq);
	}
}
