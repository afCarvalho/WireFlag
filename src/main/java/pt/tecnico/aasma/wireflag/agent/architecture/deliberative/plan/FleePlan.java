package pt.tecnico.aasma.wireflag.agent.architecture.deliberative.plan;

import pt.tecnico.aasma.wireflag.agent.Agent;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.Beliefs;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.action.Action;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.action.BattleAction;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.action.explore.MoveAction;
import pt.tecnico.aasma.wireflag.util.position.MapPosition;

public class FleePlan extends Plan {

	public FleePlan(Beliefs beliefs) {
		super(beliefs);
	}

	@Override
	public void createNewAction(MapPosition pos, Action previousAction) {

		if (beliefs.getEnemyState().getPosition().getDistanceFrom(pos) < beliefs
				.getAgentVisibilityRange()
				|| beliefs.getLife() < Agent.LOW_LIFE) {
			actions.addLast(new MoveAction(beliefs, pos, previousAction));
		} else {
			actions.getLast().setFinished(true);
		}
	}
}
