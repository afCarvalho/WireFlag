package pt.tecnico.aasma.wireflag.agent.architecture;

import java.util.LinkedList;
import java.util.List;

import pt.tecnico.aasma.wireflag.agent.Action;
import pt.tecnico.aasma.wireflag.agent.Agent;
import pt.tecnico.aasma.wireflag.agent.Intention;
import pt.tecnico.aasma.wireflag.agent.InternalState;
import pt.tecnico.aasma.wireflag.environment.controller.MapController;
import pt.tecnico.aasma.wireflag.environment.perception.Perception;
import pt.tecnico.aasma.wireflag.util.MapPosition;

public class Deliberative extends Architecture {

	InternalState state;
	LinkedList<Action> actions;

	public Deliberative() {
		state = new InternalState();
		actions = new LinkedList<Action>();
	}

	public InternalState getInternal() {
		return state;
	}

	public void makeAction(Agent agent, int delta) {

		System.out.println("INIT " + agent.getPos().getMapPosition().getX()
				+ " " + agent.getPos().getMapPosition().getY());

		updateInternalState(agent);

		if (actions.size() < 2) {
			actions = plan(getIntentions(agent), agent.getPos()
					.getMapPosition(), agent.getVisibilityRange(), agent);
		}

		// System.out.println("planing finished" + actions.size());

		//for (int i = 0; i < MapController.getMap().getNHorizontalTiles(); i++)
		//	for (int j = 0; j < MapController.getMap().getNVerticalTiles(); j++)
		//		MapController.getMap().getLandscape(new MapPosition(i, j)).isSet = false;

		System.out.println("size " + actions.size());

		//for (Action a : actions) {
		//	MapController.getMap().getLandscape(a.getPos()).isSet = true;
		//}

		Action a;

		a = actions.removeFirst();


		/*
		 * System.out.println("Agent pos " + agent.getPos().getX() + " " +
		 * agent.getPos().getY() + agent.getPos().getMapPosition().getX() + " "
		 * + agent.getPos().getMapPosition().getY());
		 */

		if (a.getAction() == Action.STOP_ACTION) {
			System.out.println("STOP");
			agent.stop();
		} else if (a.getAction() == Action.HABILITY_ACTION) {
			System.out.println("HAB");
		} else if (a.getAction() == Action.MOVE_ACTION) {
			System.out.println("MOVE " + actions.getFirst().getPos().getX()
					+ " " + actions.getFirst().getPos().getY());
			agent.approachTile(delta, actions.getFirst().getPos());

			/*
			 * System.out.println(MapController.getMap().isBlocked(
			 * actions.getFirst().getPos()));
			 */

			if (!agent.getPos().getMapPosition()
					.isSamePosition(actions.getFirst().getPos())) {
				actions.addFirst(a);
			}

		} else if (a.getAction() == Action.GET_FLAG) {
			System.out.println("FLAG");
			agent.catchFlag();
		} else if (a.getAction() == Action.GET_END) {
			System.out.println("END");
			agent.dropFlag();
		} else if (a.getAction() == Action.GET_ANIMAL) {
			System.out.println("ANIMAL");
			agent.increaseLife(MapController.getMap()
					.getLandscape(actions.getFirst().getPos()).killAnimal());
		} else if (a.getAction() == Action.ATTACK) {
			System.out.println("ATTACK");
			agent.attack(MapController.getMap()
					.getLandscape(actions.getFirst().getPos()).getAgent());
		}
		

		if (agent.hasFatigue()) {
			actions.clear();
			return;
		}
		
	}

	public void updateInternalState(Agent agent) {
		state.setPerceptions(MapController.getMap().getPerceptions(
				agent.getTeamId(), agent.getPos().getMapPosition(),
				agent.getVisibilityRange()));
	}

	public int getIntentions(Agent agent) {

		if (state.hasEndPos() && state.teamHasFlag()) {
			return Intention.END_GAME;
		}

		if (state.hasFlagPos() && !state.teamHasFlag()) {
			return Intention.GET_FLAG;
		}

		if (agent.hasLowLife() || agent.hasFatigue()) {
			return Intention.SURVIVE;
		}

		if (state.hasWeakTeamMember() > 0) {
			return Intention.TEAM_SURVIVE;
		}

		if (state.hasEnemyClose(agent) > 0 && !agent.hasLowLife()) {
			return Intention.ATTACK;
		}

		return Intention.MOVE;
	}

	public Perception getPerception(int x, int y) {
		return state.getWorld()[x][y];
	}

	public void addAction(Action a, LinkedList<Action> actions,
			boolean usedPerception[][], int intention, int x, int y) {
		MapPosition pos = a.getPos();
		Perception percept = getPerception(pos.getX() + x, pos.getY() + y);

		if (percept != null && !usedPerception[pos.getX() + x][pos.getY() + y]) {
			actions.addLast(new Action(a, percept, intention));
			usedPerception[pos.getX() + x][pos.getY() + y] = true;
		}
	}

	public LinkedList<Action> plan(int intention, MapPosition initialPos,
			int visibility, Agent agent) {

		boolean usedPerception[][] = new boolean[state.getHorizontalSize()][state
				.getVerticalSize()];
		LinkedList<Action> actions = new LinkedList<Action>();

		Perception initialPercept = getPerception(initialPos.getX(),
				initialPos.getY());
		actions.add(new Action(null, initialPercept, intention));
		usedPerception[initialPos.getX()][initialPos.getY()] = true;

		for (int i = 0; i < state.getHorizontalSize(); i++) {
			for (int j = 0; j < state.getVerticalSize(); j++) {
				if (getPerception(i, j).isBlocked()) {
					usedPerception[i][j] = true;
				}
			}
		}

		Action bestAction = actions.getFirst();

		while (!actions.isEmpty()) {
			Action a = actions.removeFirst();

			if (a != null) {

				addAction(a, actions, usedPerception, intention, 1, 0);
				addAction(a, actions, usedPerception, intention, -1, 0);
				addAction(a, actions, usedPerception, intention, 0, 1);
				addAction(a, actions, usedPerception, intention, 0, -1);

				// MapPosition pos = a.getPos();
				// System.out.println("pos" + a.getPos().getX() + " "
				// + a.getPos().getY());

				/*
				 * Perception p1 = getPerception(pos.getX() + 1, pos.getY());
				 * Perception p2 = getPerception(pos.getX() - 1, pos.getY());
				 * Perception p3 = getPerception(pos.getX(), pos.getY() + 1);
				 * Perception p4 = getPerception(pos.getX(), pos.getY() - 1);
				 * 
				 * if (p1 != null && !usedPerception[p1.getId()]) {
				 * actions.addLast(new Action(a, p1, intention));
				 * usedPerception[p1.getId()] = true; }
				 * 
				 * if (p2 != null && !usedPerception[p2.getId()]) {
				 * actions.addLast(new Action(a, p2, intention));
				 * usedPerception[p2.getId()] = true; }
				 * 
				 * if (p3 != null && !usedPerception[p3.getId()]) {
				 * actions.addLast(new Action(a, p3, intention));
				 * usedPerception[p3.getId()] = true; }
				 * 
				 * if (p4 != null && !usedPerception[p4.getId()]) {
				 * actions.addLast(new Action(a, p4, intention));
				 * usedPerception[p4.getId()] = true; }
				 */

				// System.out.println("value "
				// + a.getValue(state, agent, initialPos));

				if (a.getValue(state, agent, initialPos) > bestAction.getValue(
						state, agent, initialPos)) {
					bestAction = a;
				}
			}
		}

		LinkedList<Action> acList = new LinkedList<Action>();
		bestAction.getActionsList(acList);

		return acList;
	}
}
