package pt.tecnico.aasma.wireflag.agent.architecture.deliberative.plan;

import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.Beliefs;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.action.Action;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.action.CatchFlagAction;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.action.DropFlagAction;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.action.sequence.ActionSequence;
import pt.tecnico.aasma.wireflag.util.position.MapPosition;

public class GetFlagPlan extends Plan {

	int moveStrategy;

	public GetFlagPlan(Beliefs beliefs) {
		super(beliefs);
	}

	@Override
	public void createNewAction(MapPosition pos, ActionSequence actionSeq) {
		if (beliefs.getWorldState(pos.getX(), pos.getY()).hasFlag()) {
			ActionSequence seq = new ActionSequence(beliefs, actionSeq);
			seq.addAction(new CatchFlagAction(pos));
			seq.setFinished(true);
			actSequences.add(seq);
		} else {
			ExplorePlan.createExploreAction(actSequences, beliefs, pos,
					actionSeq);
		}
	}
}
