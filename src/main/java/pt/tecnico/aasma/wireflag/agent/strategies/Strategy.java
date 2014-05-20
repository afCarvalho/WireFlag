package pt.tecnico.aasma.wireflag.agent.strategies;

public abstract class Strategy {

	protected final int INITIAL_PLAY = 1;
	protected final int SECOND_PLAY = 2;

	public static final boolean ATTACK = true;
	public static final boolean COOPERATE = false;

	protected boolean lastOpponentPlay;
	protected int playNumber;

	public Strategy() {
		// Nothing to do here
	}

	public void startPlay() {
		playNumber = INITIAL_PLAY;
	}

	public abstract boolean getPlay();

	public void updateLastOpponentPlay(boolean play) {
		lastOpponentPlay = play;
	}
}
