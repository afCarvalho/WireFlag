package pt.tecnico.aasma.wireflag.agent.architecture.deliberative.plan;

import java.util.LinkedList;

import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.Beliefs;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.WorldState;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.action.Action;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.action.explore.ExploreNewAction;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.action.explore.ExploreUnknownAction;
import pt.tecnico.aasma.wireflag.util.position.MapPosition;

public class ExplorePlan extends Plan {

	private int subPlan;

	public ExplorePlan(Beliefs beliefs) {
		super(beliefs);
		subPlan = setSubPlan(beliefs);

	}

	public static int setSubPlan(Beliefs beliefs) {
		for (int i = 0; i < beliefs.getHorizontalSize(); i++) {
			for (int j = 0; j < beliefs.getVerticalSize(); j++) {
				if (beliefs.getWorldState(i, j).isNewlyDiscovered()) {
					return WorldState.NEWLY_DISCOVERED;
				}
			}
		}
		return WorldState.UNKNOWN;
	}

	@Override
	public void createNewAction(MapPosition pos, Action previousAction) {
		createExploreAction(actions, beliefs, pos, previousAction, subPlan);
	}

	public static void createExploreAction(LinkedList<Action> actionsList,
			Beliefs beliefs, MapPosition pos, Action previousAction,
			int moveStrategy) {
		if (moveStrategy == WorldState.NEWLY_DISCOVERED) {
			actionsList.addLast(new ExploreNewAction(beliefs, pos,
					previousAction));
		} else {
			actionsList.addLast(new ExploreUnknownAction(beliefs, pos,
					previousAction));
		}
	}
}
