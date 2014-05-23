package pt.tecnico.aasma.wireflag.agent.architecture.deliberative.plan;

import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.Beliefs;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.action.BattleAction;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.action.MoveAction;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.action.sequence.ActionSequence;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.action.sequence.MoveActionSequence;
import pt.tecnico.aasma.wireflag.util.position.MapPosition;

public class BattlePlan extends Plan {

	public BattlePlan(Beliefs beliefs) {
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

		actSequences.add(seq);

		if (beliefs.getEnemyState() != null
				&& beliefs.getEnemyState().getPosition().getDistanceFrom(pos) == 1) {
			seq.addAction(new MoveAction(pos));

			int xInc = beliefs.getEnemyState().getPosition().getX()
					- pos.getX();
			int yInc = beliefs.getEnemyState().getPosition().getY()
					- pos.getY();

			seq.addAction(new BattleAction(pos, xInc, yInc));
			seq.setFinished(true);
		} else {
			seq.addAction(new MoveAction(pos));
		}
	}
}
