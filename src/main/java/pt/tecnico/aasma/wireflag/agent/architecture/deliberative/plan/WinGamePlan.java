package pt.tecnico.aasma.wireflag.agent.architecture.deliberative.plan;

import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.Beliefs;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.action.Action;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.action.CatchFlagAction;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.action.DropFlagAction;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.action.HuntAction;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.action.MoveAction;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.action.sequence.ActionSequence;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.action.sequence.MoveActionSequence;
import pt.tecnico.aasma.wireflag.util.position.MapPosition;

public class WinGamePlan extends Plan {

	public WinGamePlan(Beliefs beliefs) {
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

		if (beliefs.getTeamBase().getDistanceFrom(pos) == 1) {
			seq.addAction(new MoveAction(pos));
			seq.addAction(new DropFlagAction(pos));
			seq.setFinished(true);
			actSequences.add(seq);
		} else if (seq.getActions().size() == 0
				|| beliefs.getTeamBase().getDistanceFrom(pos) < beliefs
						.getTeamBase().getDistanceFrom(seq.getTailPos())) {

			seq.addAction(new MoveAction(pos));
			actSequences.add(seq);

			if (seq.getActions().size() > 2) {
				seq.setFinished(true);
			}
		}
	}
}
