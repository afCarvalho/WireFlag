package pt.tecnico.aasma.wireflag.agent.architecture.deliberative.desire;

import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.Beliefs;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.intention.ExploreIntention;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.intention.Intention;

public class ExploreDesire implements Desire {

	@Override
	public double getRate(Beliefs beliefs) {
		if (beliefs.getWorldExploredPercentage() < 92) {
			return (50 - beliefs.getWorldExploredPercentage()/2.0);
		} else {
			return 0;
		}
	}

	@Override
	public Intention getIntention() {
		return new ExploreIntention();
	}
}
