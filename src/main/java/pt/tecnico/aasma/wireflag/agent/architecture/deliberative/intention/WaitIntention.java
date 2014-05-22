package pt.tecnico.aasma.wireflag.agent.architecture.deliberative.intention;

import java.util.LinkedList;
import java.util.List;

import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.Beliefs;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.action.Action;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.plan.Plan;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.plan.WaitPlan;

public class WaitIntention extends Intention {

	@Override
	protected int getIntentionId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean suceeded(LinkedList<Action> actions, Beliefs beliefs) {
		return beliefs.reconsider();
	}

	@Override
	public boolean impossible(LinkedList<Action> actions, Beliefs beliefs) {
		return false;
	}

	@Override
	public Plan getPlan(Beliefs beliefs) {
		return new WaitPlan(beliefs);
	}

}
