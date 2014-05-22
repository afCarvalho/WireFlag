package pt.tecnico.aasma.wireflag.agent.architecture.deliberative.intention;

import java.util.LinkedList;
import java.util.List;

import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.Beliefs;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.action.Action;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.plan.Plan;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.plan.HealPlan;

public class StopIntention extends Intention {

	@Override
	public boolean suceeded(LinkedList<Action> actions, Beliefs beliefs) {
		return beliefs.getLife() == 100 && beliefs.getFatigue() == 0;
	}

	@Override
	public boolean impossible(LinkedList<Action> actions, Beliefs beliefs) {
		return false;
	}

	@Override
	public Plan getPlan(Beliefs beliefs) {
		return new HealPlan(beliefs);
	}

	@Override
	protected int getIntentionId() {
		return 8;
	}
}
