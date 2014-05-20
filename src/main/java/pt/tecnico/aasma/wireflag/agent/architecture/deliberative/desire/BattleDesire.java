package pt.tecnico.aasma.wireflag.agent.architecture.deliberative.desire;

import pt.tecnico.aasma.wireflag.agent.Agent;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.Beliefs;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.intention.BattleIntention;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.intention.Intention;

public class BattleDesire implements Desire {

	@Override
	public double getRate(Beliefs state) {
		if (state.getEnemyState().hasEnemy()) {
			double rateLife = 0;
			if (state.getLife() < Agent.LOW_LIFE) {
				rateLife = state.getLife() * 4.0;
			} else {
				rateLife = state.getLife() / 2.0;
			}

			return Math.min(
					20,
					100
							- rateLife
							- state.getAgentPos().getDistanceFrom(
									state.getEnemyState().getPosition()));
		} else {
			return 0;
		}
	}

	@Override
	public Intention getIntention() {
		return new BattleIntention();
	}
}
