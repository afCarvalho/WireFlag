package pt.tecnico.aasma.wireflag.agent.architecture.deliberative.desire;

import pt.tecnico.aasma.wireflag.agent.Agent;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.Beliefs;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.intention.BattleIntention;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.intention.AbilityIntention;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.intention.Intention;

public class AbilityDesire implements Desire {

	@Override
	public double getRate(Beliefs beliefs) {

		if (beliefs.isAgentAbilityUseful()) {
			return 70;// TODO confirmar se este valor serve
		}
		return 0;
	}

	@Override
	public Intention getIntention() {
		return new AbilityIntention();
	}
}
