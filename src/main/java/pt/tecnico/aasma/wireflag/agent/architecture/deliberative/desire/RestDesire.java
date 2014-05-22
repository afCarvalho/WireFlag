package pt.tecnico.aasma.wireflag.agent.architecture.deliberative.desire;

import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.Beliefs;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.intention.Intention;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.intention.RestIntention;

public class RestDesire implements Desire {

	@Override
	public double getRate(Beliefs state) {
		int rate = 0;

		if (state.shouldStop()) {
			rate = state.getFatigue();
		}

		return rate;
	}

	@Override
	public Intention getIntention() {
		return new RestIntention();
	}
}
