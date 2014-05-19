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

	public Beliefs getBeliefs() {
		return beliefs;
	}

	public Intention getIntention() {
		double desireRate = 0;
		Intention intention = null;

		for (Desire desire : desires) {
			if (desire.getRate(beliefs) > desireRate) {
				desireRate = desire.getRate(beliefs);
				intention = desire.getIntention();
			}
		}

		return intention;
	}

	public void makeAction(Agent agent, int delta) {
		beliefs.setAgent(agent);
		beliefs.updateBeliefs();

		Intention actualIntention = getIntention();
		Plan plan = actualIntention.getPlan(beliefs);
		LinkedList<Action> actionsList = plan.makePlan(beliefs.getAgentPos());

		while (!actualIntention.impossible(actions, beliefs)
				|| actualIntention.suceeded(actions, beliefs)
				|| actions.isEmpty()) {

			Action action = actionsList.removeFirst();
			if (!action.act(agent, delta)) {
				actionsList.addFirst(action);
			}

			if (beliefs.reconsider()) {
				Intention intention = getIntention();
				if (!intention.isSame(actualIntention)
						|| actualIntention.impossible(actionsList, beliefs)) {
					actualIntention = intention;
					plan = actualIntention.getPlan(beliefs);
					actionsList = plan.makePlan(beliefs.getAgentPos());
				}
			}
		}
	}
}
