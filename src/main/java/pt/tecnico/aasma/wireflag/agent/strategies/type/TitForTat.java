package pt.tecnico.aasma.wireflag.agent.strategies.type;

import pt.tecnico.aasma.wireflag.agent.strategies.Strategy;

public class TitForTat extends Strategy {

	public TitForTat() {
		// Nothing to do here
	}

	@Override
	public boolean getPlay() {
		if (playNumber == INITIAL_PLAY) {
			return COOPERATE;
		} else {
			return lastOpponentPlay;
		}
	}
}
