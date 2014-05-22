package pt.tecnico.aasma.wireflag.agent.architecture.deliberative.intention;

import java.util.LinkedList;
import java.util.List;

import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.Beliefs;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.action.Action;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.plan.HealPlan;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.plan.Plan;

public class HealIntention extends Intention {

	private int agentLife;

	@Override
	protected int getIntentionId() {
		return HEAL;
	}

	@Override
	public boolean suceeded(LinkedList<Action> actions, Beliefs beliefs) {
		return beliefs.getLife() == 100;
	}

	@Override
	public boolean impossible(LinkedList<Action> actions, Beliefs beliefs) {
		boolean result = beliefs.getLife() <= agentLife;
		agentLife = beliefs.getLife();
		return result;
	}

	@Override
	public Plan getPlan(Beliefs beliefs) {
		// TODO Auto-generated method stub
		return new HealPlan(beliefs);
	}

}
