package pt.tecnico.aasma.wireflag.agent.architecture.deliberative.desire;

import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.Beliefs;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.intention.HuntIntention;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.intention.Intention;

public class HuntDesire implements Desire {

	@Override
	public double getRate(Beliefs beliefs) {
		if (beliefs.getAnimalState().hasAnimal()) {
			return 100
					- (beliefs.getLife() / 2.0)
					- beliefs.getAgentPos().getDistanceFrom(
							beliefs.getAnimalState().getPosition());
		} else {
			return 0;
		}
	}

	@Override
	public Intention getIntention() {
		return new HuntIntention();
	}
}
