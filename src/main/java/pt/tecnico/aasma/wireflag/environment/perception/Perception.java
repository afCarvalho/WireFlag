package pt.tecnico.aasma.wireflag.environment.perception;

import pt.tecnico.aasma.wireflag.agent.Agent;
import pt.tecnico.aasma.wireflag.environment.controller.MapController;
import pt.tecnico.aasma.wireflag.util.position.MapPosition;

public class Perception {

	private MapPosition position;
	private double landRating;
	private boolean flag;
	private boolean enemy;
	private boolean tiredAgent;
	private boolean injuredAgent;
	private int agentAttack; // TODO apagar
	private boolean teamBase;
	private int teamBaseId;
	private boolean animal;
	private boolean fire;
	/* raining, sand storm, snow storm */
	private boolean extremeWeather;
	private boolean blocked;

	public Perception(MapPosition position, double landRating) {
		this.position = position;
		this.landRating = landRating;
	}

	/***************
	 *** GETTERS ***
	 ***************/

	/* returns the position that the perception refers */
	public MapPosition getPosition() {
		return position;
	}

	/* returns the position's rating that the perception refers */
	public double getLandRating() {
		return landRating;
	}

	public int getAgentAttack() {
		return agentAttack;
	}

	public boolean getBlocked() {
		return blocked;
	}

	public int getTeamBaseId() {
		return teamBaseId;
	}

	/**
	 * Gets the agent in the current position.
	 * 
	 * @return the agent
	 */
	public Agent getAgent() {
		return MapController.getMap().getLandscape(position).getAgent();
	}

	/***************
	 *** SETTERS ***
	 ***************/

	public void setFlag(boolean value) {
		this.flag = value;
	}

	public void setEnemy(boolean value) {
		this.enemy = value;
	}

	public void setTeamBase(boolean value) {
		this.teamBase = value;
	}

	public void setTeamBaseId(int value) {
		this.teamBaseId = value;
	}

	public void setAnimal(boolean value) {
		this.animal = value;
	}

	public void setFire(boolean fire) {
		this.fire = fire;
	}

	public void setExtremeWeather(boolean extremeWeather) {
		this.extremeWeather = extremeWeather;
	}

	public void setTiredAgent(boolean tiredAgent) {
		this.tiredAgent = tiredAgent;
	}

	public void setInjuredAgent(boolean injuredAgent) {
		this.injuredAgent = injuredAgent;
	}

	public void setAgentAttack(int agentAttack) {
		this.agentAttack = agentAttack;
	}

	public void setBlocked(boolean blocked) {
		this.blocked = blocked;
	}

	/************************
	 *** STATE PREDICATES ***
	 ************************/

	public boolean hasFlag() {
		return flag;
	}

	public boolean hasEnemy() {
		return enemy;
	}

	public boolean hasTeamBase() {
		return teamBase;
	}

	public boolean hasAnimal() {
		return animal;
	}

	public boolean hasFire() {
		return fire;
	}

	public boolean hasExtremeWeather() {
		return extremeWeather;
	}

	public boolean hasTiredAgent() {
		return tiredAgent;
	}

	public boolean hasInjuredAgent() {
		return injuredAgent;
	}

	public boolean isBlocked() {
		return blocked;
	}
}
