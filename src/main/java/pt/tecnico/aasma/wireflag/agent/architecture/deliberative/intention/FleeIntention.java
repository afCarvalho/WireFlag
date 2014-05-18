package pt.tecnico.aasma.wireflag.agent.architecture.deliberative.intention;

import java.util.List;

import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.Beliefs;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.action.Action;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.plan.FleePlan;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.plan.Plan;

public class FleeIntention extends Intention {

	@Override
	public boolean suceeded(List<Action> actions, Beliefs beliefs) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean impossible(List<Action> actions, Beliefs beliefs) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Plan getPlan(Beliefs beliefs) {
		return new FleePlan(beliefs);
	}

	@Override
	protected int getIntentionId() {
		return 2;
	}
}
