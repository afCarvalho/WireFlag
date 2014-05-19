package pt.tecnico.aasma.wireflag.agent.architecture.deliberative;

import java.util.LinkedList;
import java.util.Random;

import pt.tecnico.aasma.wireflag.agent.Agent;
import pt.tecnico.aasma.wireflag.agent.architecture.Architecture;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.action.Action;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.desire.CureIllDesire;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.desire.Desire;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.desire.ExploreDesire;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.desire.HealDesire;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.desire.RestDesire;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.intention.Intention;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.plan.Plan;

public class Deliberative extends Architecture {

	Beliefs beliefs;
	LinkedList<Action> actions;
	Random random;
	Desire desires[];
	Intention actualIntention;

	public Deliberative() {
		beliefs = new Beliefs();
		actions = new LinkedList<Action>();
		random = new Random();
		/*
		 * desires = new Desire[] { new BattleDesire(), new ExploreDesire(), new
		 * FleeDesire(), new GetFlagDesire(), new HuntDesire(), new
		 * CureIllDesire(), new WinGameDesire() };
		 */
		desires = new Desire[] { new ExploreDesire(), new RestDesire(),
				new CureIllDesire(), new HealDesire() };
	}

	public Beliefs getInternal() {
		return beliefs;
	}

	public Intention getIntention() {
		double desireRate = 0;
		Intention intention = null;

		int i = 0;

		for (Desire desire : desires) {
			// System.out.println("DESIRE RATE " + i++ + " " +
			// desire.getRate(beliefs));

			if (desire.getRate(beliefs) > desireRate) {
				desireRate = desire.getRate(beliefs);
				intention = desire.getIntention();
			}
		}

		// System.out.println("HIGHEST RATE " + desireRate);

		return intention;
	}

	public void makeAction(Agent agent, int delta) {
		beliefs.setAgent(agent);
		beliefs.updateBeliefs();

		if (actions.isEmpty() || actualIntention.impossible(actions, beliefs)
				|| actualIntention.suceeded(actions, beliefs)) {
			actualIntention = getIntention();
			Plan plan = actualIntention.getPlan(beliefs);
			actions = plan.makePlan(beliefs.getAgentPos());

			System.out.println("PLANING " + actualIntention.getId()
					+ " DONE WITH " + actions.size() + " ACTIONS "
					+ actualIntention.impossible(actions, beliefs) + " "
					+ actualIntention.suceeded(actions, beliefs) + " "
					+ actions.isEmpty() + " " + actions.size());

		}

		Action action = actions.removeFirst();
		if (!action.act(agent, delta)) {
			//System.out.println("MOVING TO POS " + action.getPos().getX() + " "
			//		+ action.getPos().getY());
			actions.addFirst(action);
		}

		if (beliefs.reconsider()) {
			System.out.println("RECONSIDER");
			Intention intention = getIntention();
			if (!intention.isSame(actualIntention)
					|| actualIntention.impossible(actions, beliefs)) {
				System.out.println("REPLAN");
				actualIntention = intention;
				Plan plan = actualIntention.getPlan(beliefs);
				actions = plan.makePlan(beliefs.getAgentPos());
			}
		}
	}
}
