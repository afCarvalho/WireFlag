package pt.tecnico.aasma.wireflag.agent.architecture.deliberative.plan;

import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.Beliefs;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.action.Action;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.action.StopAction;
import pt.tecnico.aasma.wireflag.util.position.MapPosition;

public class RestPlan extends Plan {

	public RestPlan(Beliefs beliefs) {
		super(beliefs);
	}

	@Override
	public void createNewAction(MapPosition pos, Action previousAction) {
		if (previousAction.getValue() != 0
				&& 5 * previousAction.getValue() < beliefs.getFatigue() + 1) {

			usedPerception[previousAction.getPos().getX()][previousAction
					.getPos().getY()] = false;
			actions.addLast(new StopAction(beliefs, previousAction.getPos(),
					previousAction));

		} else {
			previousAction.setFinished(true);
		}
	}
}
