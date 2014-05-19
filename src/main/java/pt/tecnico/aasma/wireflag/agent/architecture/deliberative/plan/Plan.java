package pt.tecnico.aasma.wireflag.agent.architecture.deliberative.plan;

import java.util.LinkedList;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MoveAction;

import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.Beliefs;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.action.Action;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.action.explore.ExploreNewAction;
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

		// System.out.println("WANT TO ADD " + action.getPos().getX() + " " +
		// action.getPos().getY() + " " + x + " " + y );
		if (pos.isValid() && !usedPerception[pos.getX()][pos.getY()]
				&& !action.isFinished()) {
			usedPerception[pos.getX()][pos.getY()] = true;
			// System.out.println("ADDED " + x + " " + y );
			createNewAction(pos, action);
			// usedPerception[pos.getX() + x][pos.getY() + y] = true;
		}
	}

	public LinkedList<Action> makePlan(MapPosition initialPosition) {
		createNewAction(initialPosition, null);
		
		System.out.println("LETS PLAN");

		Action bestAction = actions.getFirst();

		while (!actions.isEmpty()) {
			Action a = actions.removeFirst();
			//usedPerception[a.getPos().getX()][a.getPos().getY()] = true;
			
			System.out.println("PLANNING " + actions.size());

			// System.out.println("ACTION HAS " + a.getNActions() + " ACTIONS "
			// +a.getSequenceValue());
			// LinkedList<Action> ll = new LinkedList<Action>();
			// a.fillActionsList(ll);
			// for (Action aa : ll) {
			// System.out.println(aa.getPos().getX() + " " +
			// aa.getPos().getY());
			// }

			/*for (Action ac : actions) {
				Action prev = ac;
				System.out.println("S--------------"
						+ beliefs.getAgentPos().getX() + " "
						+ beliefs.getAgentPos().getY());
				while (prev != null) {
					System.out.println("LAND UTIL "
							+ ((ExploreNewAction) prev).getLandUtility()
							+ " DANGER UTIL "
							+ ((ExploreNewAction) prev).getDangerUtility()
							+ " CONDITION "
							+ (1 + beliefs.getWorldState(
									((ExploreNewAction) prev).getPos().getX(),
									((ExploreNewAction) prev).getPos().getY())
									.getCondition()) + " N ACTIONS "
							+ prev.getNActions() + " X " + prev.getPos().getX()
							+ " Y " + prev.getPos().getY());

					prev = prev.getPrevious();
				}
				System.out.println("E--------------");
			}*/

			if (a.getSequenceValue() > bestAction.getSequenceValue()
					&& a.isFinished()) {
				bestAction = a;
			}

			addAction(a, 1, 0);
			addAction(a, -1, 0);
			addAction(a, 0, 1);
			addAction(a, 0, -1);
		}

		LinkedList<Action> actionsList = new LinkedList<Action>();
		bestAction.fillActionsList(actionsList);

		LinkedList<MapPosition> positions = new LinkedList<MapPosition>();
		for (Action a : actionsList) {
			positions.add(a.getPos());
		}

		DeliberativeArchTest.setActions(positions);

		return actionsList;
	}

	public abstract void createNewAction(MapPosition pos, Action previousAction);

	/*
	 * public void execute(Agent agent) { actions.removeFirst().act(agent); }
	 */
}
