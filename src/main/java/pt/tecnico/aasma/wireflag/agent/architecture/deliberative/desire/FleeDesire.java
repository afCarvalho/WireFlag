package pt.tecnico.aasma.wireflag.agent.architecture.deliberative.desire;

import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.Beliefs;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.intention.FleeIntention;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.intention.Intention;

public class FleeDesire implements Desire {

	@Override
	public double getRate(Beliefs beliefs) {
		if (beliefs.getEnemyState() != null
				&& beliefs.getEnemyState().hasEnemy() && beliefs.getLife() < 50) {
			return 100 - beliefs.getLife() - beliefs.getFatigue();
		} else {
			return 0;
		}
	}

	@Override
	public Intention getIntention() {
		return new FleeIntention();
	}

}
