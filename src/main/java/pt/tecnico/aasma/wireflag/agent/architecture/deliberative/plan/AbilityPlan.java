package pt.tecnico.aasma.wireflag.agent.architecture.deliberative.plan;

import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.Beliefs;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.action.AbilityAction;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.action.Action;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.action.BattleAction;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.action.HuntAction;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.action.explore.ApproachTargetAction;
import pt.tecnico.aasma.wireflag.util.position.MapPosition;

public class AbilityPlan extends Plan {

	public AbilityPlan(Beliefs beliefs) {
		super(beliefs);
	}

	@Override
	public void createNewAction(MapPosition pos, Action previousAction) {
		if (beliefs.getWorldState(pos.getX(), pos.getY()).hasEnemy()) {
			actions.addLast(new AbilityAction(beliefs, pos, previousAction));
			actions.getLast().setFinished(true);
		} else {
			actions.addLast(new MoveAction(beliefs, pos,
					previousAction));
		}
	}
}
