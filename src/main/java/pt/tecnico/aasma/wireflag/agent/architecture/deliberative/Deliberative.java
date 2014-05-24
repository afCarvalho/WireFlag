package pt.tecnico.aasma.wireflag.agent.architecture.deliberative;

import java.util.LinkedList;
import java.util.Random;

import pt.tecnico.aasma.wireflag.agent.Agent;
import pt.tecnico.aasma.wireflag.agent.architecture.Architecture;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.action.Action;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.desire.AbilityDesire;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.desire.BattleDesire;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.desire.CureIllDesire;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.desire.Desire;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.desire.ExploreDesire;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.desire.FleeDesire;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.desire.GetFlagDesire;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.desire.HealDesire;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.desire.HuntDesire;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.desire.RestDesire;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.desire.SurviveFireDesire;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.desire.SurviveWeatherDesire;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.desire.WaitDesire;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.desire.WinGameDesire;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.intention.Intention;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.plan.Plan;
import pt.tecnico.aasma.wireflag.agent.communication.Message;
import pt.tecnico.aasma.wireflag.agent.communication.message.Attack;
import pt.tecnico.aasma.wireflag.agent.communication.message.CoverSpawn;
import pt.tecnico.aasma.wireflag.agent.communication.message.Defend;
import pt.tecnico.aasma.wireflag.agent.communication.message.Explore;
import pt.tecnico.aasma.wireflag.agent.communication.message.FlagSpotted;
import pt.tecnico.aasma.wireflag.agent.communication.message.Halt;
import pt.tecnico.aasma.wireflag.agent.communication.message.HaveFlag;
import pt.tecnico.aasma.wireflag.agent.communication.message.LostFlag;
import pt.tecnico.aasma.wireflag.agent.communication.message.MoveTo;
import pt.tecnico.aasma.wireflag.agent.communication.message.ShareMap;
import pt.tecnico.aasma.wireflag.agent.communication.message.SpawnSpotted;
import pt.tecnico.aasma.wireflag.environment.controller.AgentController;

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

		desires = new Desire[] { new ExploreDesire(), new RestDesire(),
				new GetFlagDesire(), new WaitDesire(), new HuntDesire(),
				new WinGameDesire(), new HealDesire(),
				new SurviveWeatherDesire(), new SurviveFireDesire(),
				new CureIllDesire(), new AbilityDesire(), new FleeDesire(),
				new BattleDesire() };
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
		beliefs.setBasePos(AgentController.getAgents()
				.getTeamById(agent.getTeamId()).getTeamBasePos());

		for (Message message : getMessages()) {
			processMessage(message);
		}

		beliefs.updateBeliefs();

		if (actions.isEmpty() || actualIntention.impossible(actions, beliefs)
				|| actualIntention.suceeded(actions, beliefs)) {

			actualIntention = getIntention();
			Plan plan = actualIntention.getPlan(beliefs);
			actions = plan.makePlan(beliefs.getAgentPos());

		}

		if (actions.size() > 0) {
			Action action = actions.removeFirst();
			if (!action.act(beliefs, agent, delta)) {
				actions.addFirst(action);
			}
		}

		beliefs.updateBeliefs();

		if (beliefs.reconsider()) {
			Intention intention = getIntention();
			if (!intention.isSame(actualIntention)
					|| actualIntention.impossible(actions, beliefs)) {
				actualIntention = intention;
				Plan plan = actualIntention.getPlan(beliefs);
				actions = plan.makePlan(beliefs.getAgentPos());
			}
		}
	}

	@Override
	protected void processMessage(Message message) {
		Object content = message.getContent();
		if (content instanceof FlagSpotted) {
			beliefs.setFlagPos(((FlagSpotted) content).position);
		} else if (content instanceof HaveFlag) {
			beliefs.setFlagPos(((HaveFlag) content).position);
			beliefs.setTeamHasFlag(true);
		} else if (content instanceof LostFlag) {
			beliefs.setFlagPos(((LostFlag) content).position);
			beliefs.setTeamHasFlag(false);
		} else if (content instanceof SpawnSpotted) {
			// TODO
		} else if (content instanceof CoverSpawn) {
			// TODO
		} else if (content instanceof ShareMap) {
			// TODO
		} else if (content instanceof Halt) {
			// TODO
		} else if (content instanceof MoveTo) {
			// TODO
		} else if (content instanceof Explore) {
			// TODO
		} else if (content instanceof Attack) {
			// TODO 
		} else if (content instanceof Defend) {
			// TODO
		}
	}
}
