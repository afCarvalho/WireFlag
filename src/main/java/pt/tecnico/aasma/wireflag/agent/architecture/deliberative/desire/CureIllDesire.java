package pt.tecnico.aasma.wireflag.agent.architecture.deliberative.desire;

import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.Beliefs;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.intention.CureIllIntention;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.intention.Intention;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.intention.StopIntention;

public class CureIllDesire implements Desire {

	@Override
	public double getRate(Beliefs beliefs) {
		int rate = 0;

		if (beliefs.isIll()) {
			rate = 100 - beliefs.getLife() + 20;
		}

		return rate;
	}

	@Override
	public Intention getIntention() {
		return new CureIllIntention();
	}
}
