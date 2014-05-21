package pt.tecnico.aasma.wireflag.agent.architecture.deliberative.plan;

import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.Beliefs;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.action.StopAction;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.action.sequence.ActionSequence;
import pt.tecnico.aasma.wireflag.util.position.MapPosition;

public class WaitPlan extends Plan {

	public WaitPlan(Beliefs beliefs) {
		super(beliefs);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createNewAction(MapPosition pos, ActionSequence actionSeq) {
		ActionSequence seq;

		if (actionSeq == null) {
			seq = new ActionSequence(beliefs);
		} else {
			seq = new ActionSequence(beliefs, actionSeq);
		}

		seq.addAction(new StopAction(beliefs, pos));
		seq.setFinished(true);
		actSequences.addLast(seq);
	}
}
