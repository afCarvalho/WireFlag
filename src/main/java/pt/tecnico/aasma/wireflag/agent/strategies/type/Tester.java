package pt.tecnico.aasma.wireflag.agent.strategies.type;

import pt.tecnico.aasma.wireflag.agent.strategies.Strategy;

public class Tester extends Strategy {

	private boolean isTitForTat;

	public Tester() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean getPlay() {
		if (playNumber == INITIAL_PLAY) {
			return ATTACK;
		} else if (playNumber == SECOND_PLAY && lastOpponentPlay == ATTACK) {
			isTitForTat = true;
			return lastOpponentPlay;
		} else if (isTitForTat) {
			return lastOpponentPlay;
		} else if (playNumber % 2 == 0) {
			return COOPERATE;
		} else {
			return ATTACK;
		}
	}
}
