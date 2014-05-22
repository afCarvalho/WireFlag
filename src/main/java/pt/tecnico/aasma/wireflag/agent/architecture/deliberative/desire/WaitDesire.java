package pt.tecnico.aasma.wireflag.agent.architecture.deliberative.desire;

import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.Beliefs;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.intention.Intention;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.intention.WaitIntention;

public class WaitDesire implements Desire {

	@Override
	public double getRate(Beliefs state) {
		return 1;
	}

	@Override
	public Intention getIntention() {
		return new WaitIntention();
	}

}
