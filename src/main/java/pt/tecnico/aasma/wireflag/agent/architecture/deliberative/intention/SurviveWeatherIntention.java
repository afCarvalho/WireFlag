package pt.tecnico.aasma.wireflag.agent.architecture.deliberative.intention;

import java.util.LinkedList;

import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.Beliefs;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.action.Action;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.plan.Plan;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.plan.SurviveWeatherPlan;
import pt.tecnico.aasma.wireflag.util.position.MapPosition;

public class SurviveWeatherIntention extends Intention {

	@Override
	protected int getIntentionId() {
		return WEATHER;
	}

	@Override
	public boolean suceeded(LinkedList<Action> actions, Beliefs beliefs) {
		return !beliefs.getWorldState(beliefs.getAgentPos().getX(),
				beliefs.getAgentPos().getY()).hasExtremeWeather();
	}

	@Override
	public boolean impossible(LinkedList<Action> actions, Beliefs beliefs) {
		MapPosition pos;
		for (Action action : actions) {
			pos = action.getPos();
			if (beliefs.blockedWay(pos.getX(), pos.getY())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Plan getPlan(Beliefs beliefs) {
		return new SurviveWeatherPlan(beliefs);
	}

}
