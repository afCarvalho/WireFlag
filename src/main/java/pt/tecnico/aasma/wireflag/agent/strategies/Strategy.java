package pt.tecnico.aasma.wireflag.agent.strategies;

public abstract class Strategy {

	protected final int INITIAL_PLAY = 1;
	protected final int SECOND_PLAY = 2;

	public static final boolean ATTACK = true;
	public static final boolean COOPERATE = false;

	protected boolean lastOpponentPlay;
	protected int playNumber;

	private int numPlays;
	private int numVictories;
	private int numDefeats;

	public Strategy() {
		// Nothing to do here
	}

	public double getPercentageOfVictories() {
		return numVictories * 100.0 / numPlays;
	}

	public double getPercentageOfDefeats() {
		return numDefeats * 100.0 / numPlays;
	}

	public void startPlay() {
		playNumber = INITIAL_PLAY;
		numPlays++;
	}

	public abstract boolean getPlay();

	public void updateLastOpponentPlay(boolean play) {
		lastOpponentPlay = play;
	}

	public void incVictories() {
		numVictories++;
	}

	public void incDefeats() {
		numDefeats++;
	}
}
