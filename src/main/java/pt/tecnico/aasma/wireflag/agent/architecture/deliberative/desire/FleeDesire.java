package pt.tecnico.aasma.wireflag.agent.architecture.deliberative.desire;

import pt.tecnico.aasma.wireflag.agent.Agent;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.Beliefs;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.intention.FleeIntention;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.intention.Intention;

public class FleeDesire implements Desire {

	@Override
	public double getRate(Beliefs state) {
		if (state.getEnemyState().hasEnemy() && state.getLife() < Agent.LOW_LIFE) {
			return 50;//TODO confirmar se este valor serve
		} else {
			return 0;
		}
	}

	@Override
	public Intention getIntention() {
		return new FleeIntention();
	}

}
