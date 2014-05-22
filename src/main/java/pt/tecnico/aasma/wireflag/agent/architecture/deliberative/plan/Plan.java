package pt.tecnico.aasma.wireflag.agent.architecture.deliberative.plan;

import java.util.LinkedList;
import java.util.Random;

import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.Beliefs;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.action.Action;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.action.sequence.ActionSequence;
import pt.tecnico.aasma.wireflag.test.DeliberativeArchTest;
import pt.tecnico.aasma.wireflag.util.position.MapPosition;

public abstract class Plan {

	protected LinkedList<ActionSequence> actSequences;
	private boolean usedPerception[][];
	Beliefs beliefs;
	MapPosition initiPosition;
	int xCoords[];
	int yCoords[];
	int coords[];
	Random random;

	public Plan(Beliefs beliefs) {
		xCoords = new int[] { 1, -1, 0, 0 };
		yCoords = new int[] { 0, 0, 1, -1 };
		coords = new int[] { 0, 1, 2, 3 };

		random = new Random();
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

		// System.err.println("X " + x + " Y " + y);

		/*
		 * System.err.println(pos.isValid() + " " +
		 * !usedPerception[pos.getX()][pos.getY()] + " " +
		 * !actionSeq.isFinished());
		 */
		//System.err.println(!usedPerception[pos.getX()][pos.getY()]);
		if (pos.isValid() && !usedPerception[pos.getX()][pos.getY()]
				&& !actionSeq.isFinished()) {
			 //System.err.println("EFFECTIVELY ADDED");
			createNewAction(pos, actionSeq);
			// usedPerception[pos.getX() + x][pos.getY() + y] = true;
		}
	}

	public LinkedList<Action> makePlan(MapPosition initialPosition) {
		// System.err.println("LETS PLAN");

		createNewAction(initialPosition, null);

		ActionSequence bestSequence = null;// = actSequences.getFirst();

		// System.err.println("LETS START PLANNING");

		while (!actSequences.isEmpty()) {

			// System.err.println("INSIDE LOOP");

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

			 //System.err.println("REMOVED ONE");

			if (a.isFinished()
					&& (bestSequence == null || a.getSequenceValue() > bestSequence
							.getSequenceValue())) {
				// System.err.println("TRYING...");

				if (a.getSequenceValue() >= 0) {
					bestSequence = a;
					 //System.err.println("SET BEST");
				}
			} else if (bestSequence != null
					&& (a.getActions().size() > bestSequence.getActions()
							.size())) {
				// System.err.println("CONTINUE1");
				continue;
			} else if (a.getSequenceValue() < 0 || a.getActions().size() > 25) {
				//System.err.println("CONTINUE2");
				continue;
			}

			// System.err.println("ADD NEW");

			shuffleArray(coords);
			for (int i : coords) {
				addAction(a, xCoords[i], yCoords[i]);
			}

			/*
			 * addAction(a, 1, 0); addAction(a, -1, 0); addAction(a, 0, 1);
			 * addAction(a, 0, -1);
			 */
		}

		// System.err.println(bestSequence);

		if (bestSequence != null) {
			DeliberativeArchTest.setActions(bestSequence.getActions().clone());
		}

		if (bestSequence == null) {
			//System.out.println("NULL LIST");
			return new LinkedList<Action>();
		} else {
			//System.out.println("LIST WITH " + bestSequence.getActions().size());
			return bestSequence.getActions();
		}

	}

	public void shuffleArray(int[] ar) {
		Random rnd = new Random();
		for (int i = ar.length - 1; i > 0; i--) {
			int index = rnd.nextInt(i + 1);
			int a = ar[index];
			ar[index] = ar[i];
			ar[i] = a;
		}
	}

	public abstract void createNewAction(MapPosition pos,
			ActionSequence actionSeq);
}
