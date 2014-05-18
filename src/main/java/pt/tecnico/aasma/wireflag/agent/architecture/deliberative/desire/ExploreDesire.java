package pt.tecnico.aasma.wireflag.agent.architecture.deliberative.desire;

import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.Beliefs;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.intention.ExploreIntention;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.intention.Intention;

public class ExploreDesire implements Desire {

	@Override
	public double getRate(Beliefs state) {
		return Math.max(50 - state.getHorizontalSize(), 20);
	}

	@Override
	public Intention getIntention() {
		return new ExploreIntention();
	}
}
