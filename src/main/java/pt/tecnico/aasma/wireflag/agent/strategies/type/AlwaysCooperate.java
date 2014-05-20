package pt.tecnico.aasma.wireflag.agent.strategies.type;

import pt.tecnico.aasma.wireflag.agent.strategies.Strategy;

public class AlwaysCooperate extends Strategy {
	
	public AlwaysCooperate() {
		// Nothing to do here
	}

	@Override
	public boolean getPlay() {
		return COOPERATE;
	}
}
