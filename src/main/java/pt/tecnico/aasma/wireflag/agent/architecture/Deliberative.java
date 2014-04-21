package pt.tecnico.aasma.wireflag.agent.architecture;

import java.util.List;

import pt.tecnico.aasma.wireflag.agent.Action;
import pt.tecnico.aasma.wireflag.agent.Agent;
import pt.tecnico.aasma.wireflag.agent.InternalState;
import pt.tecnico.aasma.wireflag.environment.controller.MapController;

public class Deliberative extends Architecture {

	private static int GET_FLAG;
	private static int END_GAME;
	private static int SURVIVE;
	private static int TEAM_SURVIVE;
	private static int ATTACK;
	private static int MOVE;

	InternalState internalState;

	public Deliberative() {
		internalState = new InternalState();
	}

	public void makeAction(Agent agent, int delta) {

		// TODO em obras...

		// no ultimo caso true
		agent.moveSameDirection(delta);
	}

	public void updateInternalState(Agent agent) {
		internalState.setPerceptions(MapController.getMap().getPerceptions(
				agent.getTeamId(), agent.getPos().getMapPosition(),
				agent.getVisibilityRange()));
	}

	public int getIntentions(Agent agent) {

		if (internalState.hasEndPos() && internalState.teamHasFlag()) {
			return END_GAME;
		}

		if (internalState.hasFlagPos() && !internalState.teamHasFlag()) {
			return GET_FLAG;
		}

		if (agent.hasLowLife()) {
			return SURVIVE;
		}

		if (internalState.hasWeakTeamMember()) {
			return TEAM_SURVIVE;
		}

		if (internalState.hasEnemyClose(agent) && !agent.hasLowLife()) {
			return ATTACK;
		}

		return MOVE;
	}
	
	public List<Action> plan(int intention){
		return null;
	}
}
