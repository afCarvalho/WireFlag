package pt.tecnico.aasma.wireflag.agent;

import pt.tecnico.aasma.wireflag.environment.perception.Perception;
import pt.tecnico.aasma.wireflag.util.MapPosition;

public class WorldState {

	public static final int EXPLORED = -1;
	public static final int NEWLY_DISCOVERED = 1;
	public static final int UNKNOWN = 0;

	private Perception perception;
	private int timeOut;
	private int condition;

	public WorldState(int x, int y) {
		perception = new Perception(new MapPosition(x, y), 0);
		perception.setBlocked(true);
		timeOut = 0;
		condition = UNKNOWN;
	}

	public void updateState() {

		if (timeOut == 0) {
			perception.setExtremeWeather(false);
			perception.setFire(false);
			perception.setAnimal(false);
			perception.setTiredAgent(false);
			perception.setInjuredAgent(false);
			perception.setAgentAttack(0);
			perception.setEnemy(false);
			if (condition == NEWLY_DISCOVERED) {
				condition = EXPLORED;
			}

		} else {
			timeOut--;
		}
	}

	public void setAsExplored() {
		condition = EXPLORED;
	}

	public boolean isUnknown() {
		return condition == UNKNOWN;
	}

	public void setPerception(Perception p) {

		perception = p;
		timeOut = 500;

		if (condition == UNKNOWN) {
			condition = NEWLY_DISCOVERED;
		} else {
			condition = EXPLORED;
		}
	}

	public boolean hasEnemy() {
		return perception.hasEnemy();
	}

	public boolean hasInjuredAgent() {
		return perception.hasInjuredAgent();
	}

	public boolean hasTiredAgent() {
		return perception.hasTiredAgent();
	}

	public int getAgentAttack() {
		return perception.getAgentAttack();
	}

	public boolean isBlocked() {
		return perception.getBlocked();
	}

	public boolean hasFire() {
		return perception.hasFire();
	}

	public boolean hasExtremeWeather() {
		return perception.hasExtremeWeather();
	}

	public MapPosition getPosition() {
		return perception.getPosition();
	}

	public double getLandRating() {
		return perception.getLandRating();
	}

	public boolean hasFlag() {
		return perception.hasFlag();
	}

	public int getCondition() {
		return condition;
	}
}
