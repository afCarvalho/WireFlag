package pt.tecnico.aasma.wireflag.agent.architecture.deliberative.plan;

import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.Beliefs;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.action.Action;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.action.HuntAction;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.action.MoveAction;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.action.sequence.ActionSequence;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.action.sequence.MoveActionSequence;
import pt.tecnico.aasma.wireflag.util.position.MapPosition;

public class HuntPlan extends Plan {

	public HuntPlan(Beliefs beliefs) {
		super(beliefs);
	}

	@Override
	public void createNewAction(MapPosition pos, ActionSequence actionSeq) {

		ActionSequence seq;

		if (actionSeq == null) {
			seq = new MoveActionSequence(beliefs);
		} else {
			seq = new MoveActionSequence(beliefs, actionSeq);
		}

		actSequences.add(seq);
		if (beliefs.getAnimalState().getPosition().getDistanceFrom(pos) == 1) {

			seq.addAction(new MoveAction(pos));

			int xInc = beliefs.getAnimalState().getPosition().getX()
					- pos.getX();
			int yInc = beliefs.getAnimalState().getPosition().getY()
					- pos.getY();

			seq.addAction(new HuntAction(pos, xInc, yInc));
			seq.setFinished(true);
		} else {
			seq.addAction(new MoveAction(pos));
		}
	}
}
