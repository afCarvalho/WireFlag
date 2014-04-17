package pt.tecnico.aasma.wireflag.environment;

import pt.tecnico.aasma.wireflag.util.MapPosition;

public class Perception {

	private MapPosition position;
	private boolean flag;
	private boolean enemy;
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

	public boolean hasFlag() {
		return flag;
	}

	public void setFlag(boolean value) {
		this.flag = value;
	}

	public boolean hasEnemy() {
		return enemy;
	}

	public void setEnemy(boolean value) {
		this.enemy = value;
	}

	public boolean hasEndPoint() {
		return endPoint;
	}

	public void setEndPoint(boolean value) {
		this.endPoint = value;
	}

	public boolean hasAnimal() {
		return animal;
	}

	public void setAnimal(boolean value) {
		this.animal = value;
	}

	public boolean hasNight() {
		return night;
	}

	public void setNight(boolean value) {
		this.night = value;
	}

	public boolean hasFire() {
		return fire;
	}

	public void setFire(boolean fire) {
		this.fire = fire;
	}

	public boolean hasExtremeWeather() {
		return extremeWeather;
	}

	public void setExtremeWeather(boolean extremeWeather) {
		this.extremeWeather = extremeWeather;
	}
}
