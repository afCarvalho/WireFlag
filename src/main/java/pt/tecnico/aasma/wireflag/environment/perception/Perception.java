package pt.tecnico.aasma.wireflag.environment.perception;

import pt.tecnico.aasma.wireflag.util.MapPosition;

public class Perception {

	private MapPosition position;
	private boolean flag;
	private boolean enemy;
	private boolean tiredAgent;
	private boolean injuredAgent;
	private int agentAttack;
	private boolean endPoint;
	private boolean animal;
	private boolean night;
	private boolean fire;
	/* raining, sand storm, snow storm */
	private boolean extremeWeather;

	public Perception(MapPosition position) {
		this.position = position;
		this.flag = false;
		this.enemy = false;
		this.endPoint = false;
		this.animal = false;
		this.night = false;
		this.fire = false;
		this.extremeWeather = false;
	}

	/***************
	 *** GETTERS ***
	 ***************/

	public MapPosition getPosition() {
		return position;
	}

	public int getAgentAttack() {
		return agentAttack;
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

	public void setEndPoint(boolean value) {
		this.endPoint = value;
	}

	public void setAnimal(boolean value) {
		this.animal = value;
	}

	public void setNight(boolean value) {
		this.night = value;
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

	/************************
	 *** STATE PREDICATES ***
	 ************************/

	public boolean hasFlag() {
		return flag;
	}

	public boolean hasEnemy() {
		return enemy;
	}

	public boolean hasEndPoint() {
		return endPoint;
	}

	public boolean hasAnimal() {
		return animal;
	}

	public boolean hasNight() {
		return night;
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
}
