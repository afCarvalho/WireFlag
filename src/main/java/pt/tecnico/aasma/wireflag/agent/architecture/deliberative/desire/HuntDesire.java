package pt.tecnico.aasma.wireflag.agent.architecture.deliberative.desire;

import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.Beliefs;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.intention.HuntIntention;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.intention.Intention;

public class HuntDesire implements Desire {

	@Override
	public double getRate(Beliefs state) {
		// TODO Auto-generated method stub
		return 100 - state.getLife() / 2.0
				- state.getAgentPos().getDistanceFrom(state.getAnimalPos());
	}

	@Override
	public Intention getIntention() {
		return new HuntIntention();
	}

}
