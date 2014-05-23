package pt.tecnico.aasma.wireflag.agent.architecture.deliberative.desire;

import pt.tecnico.aasma.wireflag.agent.Agent;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.Beliefs;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.intention.BattleIntention;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.intention.Intention;

public class BattleDesire implements Desire {

	@Override
	public double getRate(Beliefs beliefs) {
		if (beliefs.getEnemyState() != null
				&& beliefs.getEnemyState().hasEnemy()) {
			double rateLife = 0;

			if (beliefs.getLife() < Agent.LOW_LIFE) {
				rateLife = beliefs.getLife() * 4.0;
			} else {
				rateLife = beliefs.getLife() / 2.0;
			}

			return Math.max(100,
					100 - rateLife + beliefs.getPercentageOfVictories());
		} else {
			return 0;
		}
	}

	@Override
	public Intention getIntention() {
		return new BattleIntention();
	}
}
