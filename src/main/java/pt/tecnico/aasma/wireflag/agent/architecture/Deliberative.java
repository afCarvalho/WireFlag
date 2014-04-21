package pt.tecnico.aasma.wireflag.agent.architecture;

import java.util.ArrayList;
import java.util.List;

import pt.tecnico.aasma.wireflag.agent.Action;
import pt.tecnico.aasma.wireflag.agent.Agent;
import pt.tecnico.aasma.wireflag.agent.InternalState;
import pt.tecnico.aasma.wireflag.environment.controller.MapController;
import pt.tecnico.aasma.wireflag.environment.perception.Perception;
import pt.tecnico.aasma.wireflag.util.MapPosition;

public class Deliberative extends Architecture {

	private static int GET_FLAG;
	private static int END_GAME;
	private static int SURVIVE;
	private static int TEAM_SURVIVE;
	private static int ATTACK;
	private static int MOVE;

	InternalState state;

	public Deliberative() {
		state = new InternalState();
	}

	public void makeAction(Agent agent, int delta) {

		// TODO em obras...

		// no ultimo caso true
		agent.moveSameDirection(delta);
	}

	public void updateInternalState(Agent agent) {
		state.setPerceptions(MapController.getMap().getPerceptions(
				agent.getTeamId(), agent.getPos().getMapPosition(),
				agent.getVisibilityRange()));
	}

	public int getIntentions(Agent agent) {

		if (state.hasEndPos() && state.teamHasFlag()) {
			return END_GAME;
		}

		if (state.hasFlagPos() && !state.teamHasFlag()) {
			return GET_FLAG;
		}

		if (agent.hasLowLife()) {
			return SURVIVE;
		}

		if (state.hasWeakTeamMember()) {
			return TEAM_SURVIVE;
		}

		if (state.hasEnemyClose(agent) && !agent.hasLowLife()) {
			return ATTACK;
		}

		return MOVE;
	}

	public List<Action> plan(int intention, MapPosition initialPos,
			int visibility) {
		
		boolean usedPerception[] = new boolean[state.getPerceptions().size()];
		ArrayList<Action> actions = new ArrayList<Action>();

		for (Perception p : state.getPerceptions()) {
			if (p.getPosition().isSamePosition(initialPos)) {
				actions.add(new Action(p));
				usedPerception[p.getId()] = true;
				break;
			}
		}

		return null;
	}
}
