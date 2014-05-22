package pt.tecnico.aasma.wireflag.agent.architecture.deliberative.plan;

import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.Beliefs;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.action.AbilityAction;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.action.MoveAction;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.action.sequence.ActionSequence;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.action.sequence.MoveActionSequence;
import pt.tecnico.aasma.wireflag.util.position.MapPosition;

public class AbilityPlan extends Plan {

	public AbilityPlan(Beliefs beliefs) {
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

		if (beliefs.getWorldState(pos.getX(), pos.getY()).isAbilityUseful()) {
			seq.addAction(new AbilityAction(pos));
			seq.setFinished(true);
		} 

		actSequences.add(seq);
	}
}
