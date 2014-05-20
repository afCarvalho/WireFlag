package pt.tecnico.aasma.wireflag.agent.architecture.deliberative.plan;

import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.Beliefs;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.action.Action;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.action.HuntAction;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.action.sequence.ActionSequence;
import pt.tecnico.aasma.wireflag.util.position.MapPosition;

public class HuntPlan extends Plan {

	public HuntPlan(Beliefs beliefs) {
		super(beliefs);
	}

	@Override
	public void createNewAction(MapPosition pos, ActionSequence actionSeq) {
		if (beliefs.getWorldState(pos.getX(), pos.getY()).hasAnimal()) {
			ActionSequence seq = new ActionSequence(beliefs, actionSeq);
			seq.addAction(new HuntAction(pos));
			seq.setFinished(true);
			actSequences.add(seq);
		} else {
			ExplorePlan.createExploreAction(actSequences, beliefs, pos,
					actionSeq);
		}
	}
}
