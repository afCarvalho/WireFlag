package pt.tecnico.aasma.wireflag.agent.strategies.type;

import pt.tecnico.aasma.wireflag.agent.strategies.Strategy;

public class AlwaysAttack extends Strategy {

	public AlwaysAttack() {
		// Nothing to do here
	}

	@Override
	public boolean getPlay() {
		return ATTACK;
	}
}
