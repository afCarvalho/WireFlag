package pt.tecnico.aasma.wireflag.agent.architecture;

import java.util.ArrayList;
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

	public Deliberative() {
		state = new InternalState();
	}

	public void makeAction(Agent agent, int delta) {

		updateInternalState(agent);
		List<Action> actions = plan(getIntentions(agent), agent.getPos()
				.getMapPosition(), agent.getVisibilityRange(), agent);

		System.out.println("planing finished" + actions.size());

		for (int i = 0; i < actions.size() - 1; i++) {
			System.out.println(actions.get(i).getAction() + " "
					+ actions.get(i + 1));
			if (actions.get(i).getAction() == Action.STOP_ACTION) {
				System.out.println("STOP");
				agent.stop();
			} else if (actions.get(i).getAction() == Action.HABILITY_ACTION) {
				System.out.println("HAB");
			} else if (actions.get(i).getAction() == Action.MOVE_ACTION) {
				System.out.println("MOVE");
				agent.approachTile(delta, actions.get(i + 1).getPos());
			} else if (actions.get(i).getAction() == Action.GET_FLAG) {
				System.out.println("FLAG");
				agent.catchFlag();
			} else if (actions.get(i).getAction() == Action.GET_END) {
				System.out.println("END");
				agent.dropFlag();
			} else if (actions.get(i).getAction() == Action.GET_ANIMAL) {
				System.out.println("ANIMAL");
				agent.increaseLife(MapController.getMap()
						.getLandscape(actions.get(i + 1).getPos()).killAnimal());
			} else if (actions.get(i).getAction() == Action.ATTACK) {
				System.out.println("ATTACK");
				agent.attack(MapController.getMap()
						.getLandscape(actions.get(i + 1).getPos()).getAgent());
			}
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

		if (agent.hasLowLife()) {
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
		for (Perception p : state.getPerceptions()) {
			if (p.getPosition().isSamePosition(new MapPosition(x, y))) {
				return p;
			}
		}
		return null;
	}

	public List<Action> plan(int intention, MapPosition initialPos,
			int visibility, Agent agent) {

		boolean usedPerception[] = new boolean[state.getPerceptions().size()];
		LinkedList<Action> actions = new LinkedList<Action>();


		for (Perception p : state.getPerceptions()) {
			if (p.getPosition().isSamePosition(initialPos)) {
				actions.add(new Action(null, p, intention));
				usedPerception[p.getId()] = true;
				break;
			}
		}

		Action bestAction = actions.getFirst();

		while (!actions.isEmpty()) {
			Action a = actions.removeFirst();

			if (a != null) {
				MapPosition pos = a.getPos();
				System.out.println("pos" + a.getPos().getX() + " "
						+ a.getPos().getY());

				Perception p1 = getPerception(pos.getX() + 1, pos.getY());
				Perception p2 = getPerception(pos.getX() - 1, pos.getY());
				Perception p3 = getPerception(pos.getX(), pos.getY() + 1);
				Perception p4 = getPerception(pos.getX(), pos.getY() - 1);

				if (p1 != null && !usedPerception[p1.getId()]) {
					actions.addLast(new Action(a, p1, intention));
					usedPerception[p1.getId()] = true;
				}

				if (p2 != null && !usedPerception[p2.getId()]) {
					actions.addLast(new Action(a, p2, intention));
					usedPerception[p2.getId()] = true;
				}

				if (p3 != null && !usedPerception[p3.getId()]) {
					actions.addLast(new Action(a, p3, intention));
					usedPerception[p3.getId()] = true;
				}

				if (p4 != null && !usedPerception[p4.getId()]) {
					actions.addLast(new Action(a, p4, intention));
					usedPerception[p4.getId()] = true;
				}

				System.out.println("value "
						+ a.getValue(state, agent, initialPos));

				if (a.getValue(state, agent, initialPos) > bestAction.getValue(
						state, agent, initialPos)) {
					bestAction = a;
				}
			}
		}

		ArrayList<Action> acList = new ArrayList<Action>();
		bestAction.getActionsList(acList);

		return acList;
	}
}
