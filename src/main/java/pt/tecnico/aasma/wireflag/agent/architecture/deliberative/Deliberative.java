package pt.tecnico.aasma.wireflag.agent.architecture.deliberative;

import java.util.LinkedList;
import java.util.Random;

import pt.tecnico.aasma.wireflag.agent.Agent;
import pt.tecnico.aasma.wireflag.agent.architecture.Architecture;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.action.Action;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.desire.CureIllDesire;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.desire.Desire;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.desire.ExploreDesire;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.desire.GetFlagDesire;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.desire.HealDesire;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.desire.RestDesire;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.desire.WaitDesire;
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
				new GetFlagDesire(), new WaitDesire() };
	}

	public Beliefs getBeliefs() {
		return beliefs;
	}

	public Intention getIntention() {
		double desireRate = 0;
		Intention intention = null;

		// int i = 0;

		for (Desire desire : desires) {
			// System.out.println("DESIRE RATE " + i++ + " " +
			// desire.getRate(beliefs));

			if (desire.getRate(beliefs) > desireRate) {
				desireRate = desire.getRate(beliefs);
				intention = desire.getIntention();
			}
		}

		//System.err.println("HIGHEST RATE " + desireRate);

		return intention;
	}

	public void makeAction(Agent agent, int delta) {
		beliefs.setAgent(agent);
		beliefs.updateBeliefs();

		if (actions.isEmpty() || actualIntention.impossible(actions, beliefs)
				|| actualIntention.suceeded(actions, beliefs)) {

			/*if (actualIntention != null) {
				System.err.println(actions.isEmpty() + " "
						+ actualIntention.impossible(actions, beliefs) + " "
						+ actualIntention.suceeded(actions, beliefs));
			}*/

			actualIntention = getIntention();
			//System.err.println("LETS GET A PLAN");
			Plan plan = actualIntention.getPlan(beliefs);
			//System.err.println("LETS GET ACTIONS");
			actions = plan.makePlan(beliefs.getAgentPos());

			/*System.err.println("PLANING " + actualIntention.getId()
					+ " DONE WITH " + actions.size() + " ACTIONS "
					+ actualIntention.impossible(actions, beliefs) + " "
					+ actualIntention.suceeded(actions, beliefs) + " "
					+ actions.isEmpty() + " " + actions.size());*/

		}

		if (actions.size() > 0) {
			Action action = actions.removeFirst();
			if (!action.act(beliefs, agent, delta)) {
				actions.addFirst(action);
				// System.err.println("ADD ACTION");
			}
			// System.err.println("IT NOW HAS " + actions.size());
		}

		beliefs.updateBeliefs();

		if (beliefs.reconsider()) {
			// System.err.println("RECONSIDER");
			Intention intention = getIntention();
			if (!intention.isSame(actualIntention)
					|| actualIntention.impossible(actions, beliefs)) {
				// System.err.println("REPLAN");
				actualIntention = intention;
				Plan plan = actualIntention.getPlan(beliefs);
				actions = plan.makePlan(beliefs.getAgentPos());
			}
		}
	}
}
