package pt.tecnico.aasma.wireflag.agent.architecture.deliberative.intention;

import java.util.List;

import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.OldAction;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.Beliefs;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.action.Action;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.plan.BattlePlan;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.plan.Plan;
import pt.tecnico.aasma.wireflag.util.position.MapPosition;

public class BattleIntention extends Intention {

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
		return new BattlePlan(beliefs);
	}

	@Override
	protected int getIntentionId() {
		return 0;
	}
}
