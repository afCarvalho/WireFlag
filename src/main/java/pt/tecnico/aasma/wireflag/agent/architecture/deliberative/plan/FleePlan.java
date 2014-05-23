package pt.tecnico.aasma.wireflag.agent.architecture.deliberative.plan;

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

		int enemyAgentDiffX = Math.abs(beliefs.getEnemyState().getPosition()
				.getX()
				- beliefs.getAgentPos().getX());
		int enemyActualDiffX = Math.abs(beliefs.getEnemyState().getPosition()
				.getX()
				- pos.getX());
		int enemyAgentDiffY = Math.abs(beliefs.getEnemyState().getPosition()
				.getY()
				- beliefs.getAgentPos().getY());
		int enemyActualDiffY = Math.abs(beliefs.getEnemyState().getPosition()
				.getY()
				- pos.getY());

		if (enemyActualDiffX > 2 * enemyAgentDiffX
				|| enemyActualDiffY > 2 * enemyAgentDiffY) {
			seq.addAction(new MoveAction(pos));
			actSequences.add(seq);
			seq.setFinished(true);
		} else {
			seq.addAction(new MoveAction(pos));
			actSequences.add(seq);
		}
	}
}
