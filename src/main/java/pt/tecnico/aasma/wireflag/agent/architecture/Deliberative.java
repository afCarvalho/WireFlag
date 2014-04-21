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

		for (int i = 0; i < actions.size(); i++) {
			if (actions.get(i).getAction() == Action.STOP_ACTION) {
				agent.stop();
			} else if (actions.get(i).getAction() == Action.HABILITY_ACTION) {

			} else if (actions.get(i).getAction() == Action.MOVE_ACTION) {

				if (actions.get(i).getPerception().hasFlag()) {
					agent.catchFlag();
				}

				if (actions.get(i).getPerception().hasEndPoint()) {
					agent.dropFlag();
				}

				if (actions.get(i + 1).getPerception().hasEnemy()) {
					agent.attack(MapController.getMap()
							.getLandscape(actions.get(i + 1).getPos())
							.getAgent());
				}

				if (actions.get(i + 1).getPerception().hasAnimal()) {
					agent.increaseLife(MapController.getMap()
							.getLandscape(actions.get(i + 1).getPos())
							.killAnimal());
				}

				agent.approachTile(delta, actions.get(i + 1).getPos());

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

				Perception p1 = getPerception(pos.getX() + 1, pos.getY());
				Perception p2 = getPerception(pos.getX() - 1, pos.getY());
				Perception p3 = getPerception(pos.getX() + 1, pos.getY());
				Perception p4 = getPerception(pos.getX() + 1, pos.getY());

				if (p1 != null) {
					actions.addLast(new Action(a, p1, intention));
				}

				if (p2 != null) {
					actions.addLast(new Action(a, p2, intention));
				}

				if (p3 != null) {
					actions.addLast(new Action(a, p3, intention));
				}

				if (p4 != null) {
					actions.addLast(new Action(a, p4, intention));
				}

				if (p1 == null && p2 == null && p3 == null && p4 == null) {
					if (a.getValue(state, agent) > bestAction.getValue(state,
							agent)) {
						bestAction = a;
					}
				}
			}
		}

		ArrayList<Action> acList = new ArrayList<Action>();
		bestAction.getActionsList(acList);

		return acList;
	}
}
