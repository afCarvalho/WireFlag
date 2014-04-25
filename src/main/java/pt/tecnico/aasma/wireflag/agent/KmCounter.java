package pt.tecnico.aasma.wireflag.agent;

public class KmCounter {

	private int maxDistanceCovered;
	private int nTilesWalked;
	private int initialFatigue;
	private int lastCount;

	public int getInitialFatigue() {
		return initialFatigue;
	}

	public void setInitialFatigue(int initialFatigue) {
		this.initialFatigue = initialFatigue;
	}

	public int getMaxDistanceCovered() {
		return maxDistanceCovered;
	}

	public void setMaxDistanceCovered(int maxDistanceCovered) {
		this.maxDistanceCovered = maxDistanceCovered;
	}

	public int getnTilesWalked() {
		return nTilesWalked;
	}

	public void setnTilesWalked(int nTilesWalked) {
		this.nTilesWalked = nTilesWalked;
	}

	public boolean isBurningOut(int actualFatigue) {
		// System.out.println("Best distance " + getMaxDistanceCovered()
		// + "lastCount " + lastCount + " fatigue " + initialFatigue);
		return (lastCount > maxDistanceCovered * 2) || actualFatigue > 80;
	}

	public void reset(int actualFatigue) {
		initialFatigue = actualFatigue;
		// setnTilesWalked(0);
		lastCount = 0;
	}

	public void update(int actualFatigue) {

		nTilesWalked++;

		//System.out.println("N tile walked " + nTilesWalked + " best distance "
		//		+ maxDistanceCovered + " last count " + lastCount);

		if (actualFatigue > getInitialFatigue() + 10) {
			initialFatigue = actualFatigue;
			lastCount = getnTilesWalked();

			if (maxDistanceCovered == 0) {
				maxDistanceCovered = lastCount;
			}

			maxDistanceCovered = Math.min(getMaxDistanceCovered(), lastCount);
			maxDistanceCovered = Math.max(maxDistanceCovered, 100);

			/*
			 * System.out.println("Best distance " + getMaxDistanceCovered() +
			 * "fatigue " + actualFatigue);
			 */
			setnTilesWalked(0);
		}
	}
}
