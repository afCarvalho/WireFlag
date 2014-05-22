package pt.tecnico.aasma.wireflag.agent.architecture.deliberative.plan;

import pt.tecnico.aasma.wireflag.agent.Agent;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.Beliefs;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.action.MoveAction;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.action.sequence.ActionSequence;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.action.sequence.MoveActionSequence;
import pt.tecnico.aasma.wireflag.util.position.MapPosition;

public class FleePlan extends Plan {

	public FleePlan(Beliefs beliefs) {
		super(beliefs);
	}

	@Override
	public void createNewAction(MapPosition pos, ActionSequence actionSeq) {

		MoveActionSequence seq;

		if (actionSeq == null) {
			seq = new MoveActionSequence(beliefs);
		} else {
			seq = new MoveActionSequence(beliefs, actionSeq);
		}

		if (beliefs.getLife() < Agent.LOW_LIFE
				|| Math.abs(beliefs.getEnemyState().getPosition().getX()
						- pos.getX()) > beliefs.getAgentVisibilityRange()
				|| Math.abs(beliefs.getEnemyState().getPosition().getY()
						- pos.getY()) > beliefs.getAgentVisibilityRange()) {
			seq.addAction(new MoveAction(pos));
			actSequences.add(seq);
		} else {
			seq.setFinished(true);
		}
	}
}
