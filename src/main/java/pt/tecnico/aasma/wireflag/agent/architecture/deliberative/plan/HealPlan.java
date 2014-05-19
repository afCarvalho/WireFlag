package pt.tecnico.aasma.wireflag.agent.architecture.deliberative.plan;

import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.Beliefs;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.action.Action;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.action.StopAction;
import pt.tecnico.aasma.wireflag.util.position.MapPosition;

public class HealPlan extends Plan {

	public HealPlan(Beliefs beliefs) {
		super(beliefs);
	}

	@Override
	public void createNewAction(MapPosition pos, Action previousAction) {

		if (previousAction.getValue() != 0
				&& previousAction.getValue() < 100 - beliefs.getLife()) {

			usedPerception[previousAction.getPos().getX()][previousAction
					.getPos().getY()] = false;
			actions.addLast(new StopAction(beliefs, previousAction.getPos(),
					previousAction));

		} else {
			previousAction.setFinished(true);
		}
	}
}
