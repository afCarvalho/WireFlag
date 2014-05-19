package pt.tecnico.aasma.wireflag.agent.architecture.deliberative.intention;

import java.util.List;

import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.Beliefs;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.action.Action;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.plan.CureIllPlan;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.plan.Plan;

public class CureIllIntention extends Intention {

	@Override
	protected int getIntentionId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean suceeded(List<Action> actions, Beliefs beliefs) {
		return !beliefs.isIll();
	}

	@Override
	public boolean impossible(List<Action> actions, Beliefs beliefs) {
		return false;
	}

	@Override
	public Plan getPlan(Beliefs beliefs) {
		return new CureIllPlan(beliefs);
	}

}
