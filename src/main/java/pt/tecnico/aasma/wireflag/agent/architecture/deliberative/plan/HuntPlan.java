package pt.tecnico.aasma.wireflag.agent.architecture.deliberative.plan;

import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.Beliefs;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.action.Action;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.action.HuntAction;
import pt.tecnico.aasma.wireflag.util.position.MapPosition;

public class HuntPlan extends Plan {

	public HuntPlan(Beliefs beliefs) {
		super(beliefs);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createNewAction(MapPosition pos, Action previousAction) {
		if (beliefs.getWorldState(pos.getX(), pos.getY()).hasAnimal()) {
			actions.addLast(new HuntAction(beliefs, pos, previousAction));
			actions.getLast().setFinished(true);
		} else {
			ExplorePlan.createExploreAction(actions, beliefs, pos,
					previousAction);
		}
	}
}
