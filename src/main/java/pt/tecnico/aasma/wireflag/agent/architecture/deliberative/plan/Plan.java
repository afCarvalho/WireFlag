package pt.tecnico.aasma.wireflag.agent.architecture.deliberative.plan;

import java.util.LinkedList;

import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.Beliefs;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.action.Action;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.action.sequence.ActionSequence;
import pt.tecnico.aasma.wireflag.test.DeliberativeArchTest;
import pt.tecnico.aasma.wireflag.util.position.MapPosition;

public abstract class Plan {

	protected LinkedList<ActionSequence> actSequences;
	boolean usedPerception[][];
	Beliefs beliefs;
	MapPosition initiPosition;

	public Plan(Beliefs beliefs) {
		actSequences = new LinkedList<ActionSequence>();
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

	public void addAction(ActionSequence actionSeq, int x, int y) {
		MapPosition pos = new MapPosition(actionSeq.getTailPos().getX() + x,
				actionSeq.getTailPos().getY() + y);

		if (beliefs.getWorldState(pos.getX(), pos.getY()).hasFire()
				|| beliefs.getWorldState(pos.getX(), pos.getY())
						.hasExtremeWeather()
				|| beliefs.getWorldState(pos.getX(), pos.getY()).isBlocked()
				|| beliefs.getWorldState(pos.getX(), pos.getY()).hasAnimal()
				|| beliefs.getWorldState(pos.getX(), pos.getY()).hasAgent()) {
			return;
		}

		// System.out.println("WANT TO ADD " + action.getPos().getX() + " " +
		// action.getPos().getY() + " " + x + " " + y );
		if (pos.isValid() && !usedPerception[pos.getX()][pos.getY()]
				&& !actionSeq.isFinished()) {

			// System.out.println("ADDED " + x + " " + y );
			createNewAction(pos, actionSeq);
			// usedPerception[pos.getX() + x][pos.getY() + y] = true;
		}
	}

	public LinkedList<Action> makePlan(MapPosition initialPosition) {
		createNewAction(initialPosition, null);

		// System.out.println("LETS PLAN");

		ActionSequence bestSequence = null;// = actSequences.getFirst();

		while (!actSequences.isEmpty()) {

			/*
			 * System.err.println("START --------------"); for (ActionSequence
			 * seq : actSequences) { String message = ""; for (Action ac :
			 * seq.getActions()) { message += ac.getPos().getX() + " " +
			 * ac.getPos().getY() + " "; } System.err.println(message + " " +
			 * seq.isFinished() + " " + seq.getSequenceValue()); }
			 * 
			 * System.err.println("END ---------------");
			 */

			ActionSequence a = actSequences.removeFirst();
			usedPerception[a.getTailPos().getX()][a.getTailPos().getY()] = true;

			// System.err.println("PLANNING " + actSequences.size() + " "
			// + a.isFinished() + " " + beliefs.hasNewPosition() + " " +
			// beliefs.hasUnknownPosition());

			if (a.isFinished()
					&& (bestSequence == null || a.getSequenceValue() > bestSequence
							.getSequenceValue())) {
				bestSequence = a;
			} else if (bestSequence != null
					&& a.getActions().size() > bestSequence.getActions().size()) {
				continue;
			}

			addAction(a, 1, 0);
			addAction(a, -1, 0);
			addAction(a, 0, 1);
			addAction(a, 0, -1);
		}

		System.err.println(beliefs.hasNewPosition() + " "
				+ beliefs.hasUnknownPosition());
		System.err.println("BEST " + bestSequence.isFinished());

		LinkedList<MapPosition> positions = new LinkedList<MapPosition>();
		for (Action a : bestSequence.getActions()) {
			positions.add(a.getPos());
		}

		DeliberativeArchTest.setActions(positions);

		// System.exit(0);

		return bestSequence.getActions();
	}

	public abstract void createNewAction(MapPosition pos,
			ActionSequence actionSeq);
}
