package pt.tecnico.aasma.wireflag.agent.architecture.deliberative.desire;

import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.Beliefs;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.intention.BattleIntention;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.intention.Intention;

public class BattleDesire implements Desire {

	@Override
	public double getRate(Beliefs state) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Intention getIntention() {
		return new BattleIntention();
	}
}
