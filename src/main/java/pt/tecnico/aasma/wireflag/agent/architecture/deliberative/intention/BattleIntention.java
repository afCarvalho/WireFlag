package pt.tecnico.aasma.wireflag.agent.architecture.deliberative.intention;

import java.util.LinkedList;
import java.util.List;

import pt.tecnico.aasma.wireflag.agent.Agent;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.Beliefs;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.action.Action;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.plan.BattlePlan;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.plan.Plan;
import pt.tecnico.aasma.wireflag.util.position.MapPosition;

public class BattleIntention extends Intention {

	@Override
	public boolean suceeded(LinkedList<Action> actions, Beliefs beliefs) {

		return !beliefs.getEnemyState().hasEnemy();
	}

	@Override
	public boolean impossible(LinkedList<Action> actions, Beliefs beliefs) {
		MapPosition pos;

		if (!beliefs.getEnemyState().hasEnemy()) {
			return true;
		}

		for (Action action : actions) {
			pos = action.getPos();

			if (beliefs.blockedWay(pos.getX(), pos.getY())
					&& !beliefs.getEnemyState().getPosition()
							.isSamePosition(pos)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public Plan getPlan(Beliefs beliefs) {
		return new BattlePlan(beliefs);
	}

	@Override
	protected int getIntentionId() {
		return 0;
	}
}
