package pt.tecnico.aasma.wireflag.agent.architecture.deliberative.desire;

import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.Beliefs;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.intention.Intention;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.intention.SurviveWeatherIntention;

public class SurviveWeatherDesire implements Desire {

	@Override
	public double getRate(Beliefs beliefs) {
		if (beliefs.getWorldState(beliefs.getAgentPos().getX(),
				beliefs.getAgentPos().getY()).hasExtremeWeather()) {
			return 80;
		} else {
			return 0;
		}
	}

	@Override
	public Intention getIntention() {
		return new SurviveWeatherIntention();
	}

}
