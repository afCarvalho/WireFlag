package pt.tecnico.aasma.wireflag.agent.architecture.deliberative.plan;

import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.Beliefs;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.action.Action;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.action.DropFlagAction;
import pt.tecnico.aasma.wireflag.util.position.MapPosition;

public class WinGamePlan extends Plan {

	public WinGamePlan(Beliefs beliefs) {
		super(beliefs);
	}

	@Override
	public void createNewAction(MapPosition pos, Action previousAction) {
		if (beliefs.getWorldState(pos.getX(), pos.getY()).hasEndPoint()) {
			actions.addLast(new DropFlagAction(beliefs, pos, previousAction));
			actions.getLast().setFinished(true);
		} else {
			ExplorePlan.createExploreAction(actions, beliefs, pos,
					previousAction);	
		}
	}
}
