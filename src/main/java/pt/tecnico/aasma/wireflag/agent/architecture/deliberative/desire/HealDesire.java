package pt.tecnico.aasma.wireflag.agent.architecture.deliberative.desire;

import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.Beliefs;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.intention.HealIntention;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.intention.Intention;

public class HealDesire implements Desire {

	@Override
	public double getRate(Beliefs state) {
		return 100 - state.getLife();
	}

	@Override
	public Intention getIntention() {
		return new HealIntention();
	}

}
