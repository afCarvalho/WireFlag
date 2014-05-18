package pt.tecnico.aasma.wireflag.agent.architecture.deliberative.plan;

import java.util.LinkedList;

import pt.tecnico.aasma.wireflag.agent.Agent;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.Beliefs;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.action.Action;
import pt.tecnico.aasma.wireflag.test.DeliberativeArchTest;
import pt.tecnico.aasma.wireflag.util.position.MapPosition;

public abstract class Plan {

	protected LinkedList<Action> actions;
	boolean usedPerception[][];
	Beliefs beliefs;
	MapPosition initiPosition;

	public Plan(Beliefs beliefs) {
		actions = new LinkedList<Action>();
		usedPerception = new boolean[beliefs.getHorizontalSize()][beliefs
				.getVerticalSize()];
		this.beliefs = beliefs;

		for (int i = 0; i < beliefs.getHorizontalSize(); i++) {
			for (int j = 0; j < beliefs.getVerticalSize(); j++) {
				if (beliefs.getWorldState(i, j).isBlocked()) {
					usedPerception[i][j] = true;
				}
			}
		}
	}

	public void addAction(Action action, int x, int y) {
		MapPosition pos = new MapPosition(action.getPos().getX() + x, action
				.getPos().getY() + y);

		if (pos.isValid() && !usedPerception[pos.getX()][pos.getY()]) {
			createNewAction(pos, action);
			// usedPerception[pos.getX() + x][pos.getY() + y] = true;
		}
	}

	public LinkedList<Action> makePlan(MapPosition initialPosition) {
		createNewAction(initialPosition, null);

		Action bestAction = actions.getFirst();

		while (!actions.isEmpty()) {
			Action a = actions.removeFirst();
			usedPerception[a.getPos().getX()][a.getPos().getY()] = true;

			if (a.getValue() > bestAction.getValue()) {
				bestAction = a;
			}

			addAction(a, 1, 0);
			addAction(a, -1, 0);
			addAction(a, 0, 1);
			addAction(a, 0, -1);
		}

		LinkedList<Action> actionsList = new LinkedList<Action>();
		bestAction.fillActionsList(actionsList);

		DeliberativeArchTest.setActions(actionsList);

		return actionsList;
	}

	public abstract void createNewAction(MapPosition pos, Action previousAction);

	/*public void execute(Agent agent) {
		actions.removeFirst().act(agent);
	}*/
}
