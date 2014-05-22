package pt.tecnico.aasma.wireflag.agent.architecture.deliberative.intention;

import java.util.LinkedList;
import java.util.List;

import pt.tecnico.aasma.wireflag.agent.Agent;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.Beliefs;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.action.Action;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.plan.BattlePlan;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.plan.AbilityPlan;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.plan.Plan;
import pt.tecnico.aasma.wireflag.util.position.MapPosition;

public class AbilityIntention extends Intention {

	@Override
	public boolean suceeded(LinkedList<Action> actions, Beliefs beliefs) {

		return !beliefs.isAgentAbilityUseful();
	}

	@Override
	public boolean impossible(LinkedList<Action> actions, Beliefs beliefs) {
		MapPosition pos;
		boolean result = false;
		for (Action action : actions) {
			pos = action.getPos();

			result = result || beliefs.blockedWay(pos.getX(), pos.getY());
		}

		return result;
	}

	@Override
	public Plan getPlan(Beliefs beliefs) {
		return new AbilityPlan(beliefs);
	}

	@Override
	protected int getIntentionId() {
		return 0;
	}
}
