package pt.tecnico.aasma.wireflag.agent.architecture.deliberative.plan;

import java.util.LinkedList;

import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.Beliefs;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.WorldState;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.action.Action;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.action.explore.ExploreNewAction;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.action.explore.ExploreUnknownAction;
import pt.tecnico.aasma.wireflag.util.position.MapPosition;

public class ExplorePlan extends Plan {

	public static final int DISCOVER_NEW = 0;
	public static final int DISCOVER_UNKNOWN = 1;

	public ExplorePlan(Beliefs beliefs) {
		super(beliefs);
	}

	public static int getMoveStrategy(Beliefs beliefs) {
		for (int i = 0; i < beliefs.getHorizontalSize(); i++) {
			for (int j = 0; j < beliefs.getVerticalSize(); j++) {
				if (beliefs.getWorldState(i, j).isNewlyDiscovered()) {
					return DISCOVER_NEW;
				}
			}
		}
		return DISCOVER_UNKNOWN;
	}

	@Override
	public void createNewAction(MapPosition pos, Action previousAction) {
		createExploreAction(actions, beliefs, pos, previousAction);
	}

	public static void createExploreAction(LinkedList<Action> actionsList,
			Beliefs beliefs, MapPosition pos, Action previousAction) {

		int moveStrategy = getMoveStrategy(beliefs);

		if (moveStrategy == DISCOVER_NEW) {
			actionsList.addLast(new ExploreNewAction(beliefs, pos,
					previousAction));

			if (beliefs.getWorldState(pos.getX(), pos.getY())
					.isNewlyDiscovered()) {
				actionsList.getLast().setFinished(true);
			}
		} else if (moveStrategy == DISCOVER_UNKNOWN) {
			actionsList.addLast(new ExploreUnknownAction(beliefs, pos,
					previousAction));

			if (getNUnknownAdjacent(beliefs, pos)) {
				actionsList.getLast().setFinished(true);
			}
		}
	}

	private static boolean getNUnknownAdjacent(Beliefs beliefs,
			MapPosition position) {

		MapPosition pos = beliefs.getWorldState(position.getX(),
				position.getY()).getPosition();

		WorldState p1 = beliefs.getWorldState(pos.getX() + 1, pos.getY());
		WorldState p2 = beliefs.getWorldState(pos.getX() - 1, pos.getY());
		WorldState p3 = beliefs.getWorldState(pos.getX(), pos.getY() + 1);
		WorldState p4 = beliefs.getWorldState(pos.getX(), pos.getY() - 1);

		if (p1 != null && p1.isUnknown()) {
			return true;
		} else if (p2 != null && p2.isUnknown()) {
			return true;
		} else if (p3 != null && p3.isUnknown()) {
			return true;
		} else if (p4 != null && p4.isUnknown()) {
			return true;
		}
		return false;
	}
}
