package pt.tecnico.aasma.wireflag.agent.architecture.deliberative.plan;

import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.Beliefs;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.action.Action;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.action.CatchFlagAction;
import pt.tecnico.aasma.wireflag.util.position.MapPosition;

public class GetFlagPlan extends Plan {

	public GetFlagPlan(Beliefs beliefs) {
		super(beliefs);
	}

	@Override
	public void createNewAction(MapPosition pos, Action previousAction) {
		if (beliefs.getWorldState(pos.getX(), pos.getY()).hasFlag()) {
			actions.addLast(new CatchFlagAction(beliefs, pos, previousAction));
			actions.getLast().setFinished(true);
		} else {
			ExplorePlan.createExploreAction(actions, beliefs, pos,
					previousAction);
		}
	}
}
