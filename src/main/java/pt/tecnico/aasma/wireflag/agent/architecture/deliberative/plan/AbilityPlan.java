package pt.tecnico.aasma.wireflag.agent.architecture.deliberative.plan;

import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.Beliefs;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.action.AbilityAction;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.action.sequence.ActionSequence;
import pt.tecnico.aasma.wireflag.environment.controller.MapController;
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

		int visibility = beliefs.getAgentVisibilityRange();
		int x = beliefs.getAgentPos().getX();
		int y = beliefs.getAgentPos().getY();

		for (int j = y + visibility; j >= y - visibility; j--) {
			for (int i = x - visibility; i <= x + visibility; i++) {
				if (j < MapController.getMap().getNVerticalTiles()
						&& i < MapController.getMap().getNHorizontalTiles()
						&& j > 0 && i > 0) {
					seq.addAction(new AbilityAction(new MapPosition(i, j)));
				}

			}
		}

		seq.setFinished(true);
		actSequences.add(seq);
	}
}
