package pt.tecnico.aasma.wireflag.agent.strategies.type;

import pt.tecnico.aasma.wireflag.agent.strategies.Strategy;

public class Joss extends Strategy {

	public Joss() {
		// Nothing to do here
	}

	@Override
	public boolean getPlay() {

		if (playNumber == INITIAL_PLAY) {
			return COOPERATE;
		} else {
			if (playNumber % 10 != 0) {
				return lastOpponentPlay;
			} else {
				return ATTACK;
			}
		}
	}
}
