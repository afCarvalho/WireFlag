package pt.tecnico.aasma.wireflag.agent.architecture.deliberative.desire;

import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.Beliefs;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.intention.HealIntention;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.intention.Intention;

public class HealDesire implements Desire {

	@Override
	public double getRate(Beliefs state) {
		int rate = 0;

		if (state.getLife() < 30) {
			rate = 100 - state.getLife();
		}

		return rate;
	}

	@Override
	public Intention getIntention() {
		return new HealIntention();
	}

}
