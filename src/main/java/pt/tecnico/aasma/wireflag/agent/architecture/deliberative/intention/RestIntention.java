package pt.tecnico.aasma.wireflag.agent.architecture.deliberative.intention;

import java.util.LinkedList;
import java.util.List;

import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.Beliefs;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.action.Action;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.plan.Plan;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.plan.RestPlan;

public class RestIntention extends Intention {

	@Override
	protected int getIntentionId() {
		return 7;
	}

	@Override
	public boolean suceeded(LinkedList<Action> actions, Beliefs beliefs) {
		return beliefs.getFatigue() == 0;
	}

	@Override
	public boolean impossible(LinkedList<Action> actions, Beliefs beliefs) {
		return false;
	}

	@Override
	public Plan getPlan(Beliefs beliefs) {
		return new RestPlan(beliefs);
	}

}
