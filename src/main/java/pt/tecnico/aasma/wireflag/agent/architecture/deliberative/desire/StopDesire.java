package pt.tecnico.aasma.wireflag.agent.architecture.deliberative.desire;

import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.Beliefs;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.intention.Intention;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.intention.StopIntention;

public class StopDesire implements Desire {

	@Override
	public double getRate(Beliefs state) {
		int fatigue = 0;
		int life = 0;

		if (state.shouldStop()) {
			fatigue = state.getFatigue();
		}

		if (state.getLife() < 30) {
			life = 100 - state.getLife();
		}

		if (state.isIll()) {
			life += 20;
		}

		return Math.max(fatigue, life);
	}

	@Override
	public Intention getIntention() {
		return new StopIntention();
	}
}
