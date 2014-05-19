package pt.tecnico.aasma.wireflag.agent.architecture.deliberative.desire;

import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.Beliefs;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.intention.ExploreIntention;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.intention.Intention;

public class ExploreDesire implements Desire {

	@Override
	public double getRate(Beliefs beliefs) {
		return Math.max(50 - beliefs.getWorldExploredPercentage() / 2.0, 20);
	}

	@Override
	public Intention getIntention() {
		return new ExploreIntention();
	}
}
