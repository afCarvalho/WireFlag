package pt.tecnico.aasma.wireflag.environment;

import pt.tecnico.aasma.wireflag.util.Position;

public class Perception {

	private Position position;
	private boolean flag;
	private boolean enemy;
	private boolean startPoint;
	private boolean animal;
	private boolean night;
	private boolean fire;
	/* raining, sand storm, snow storm */
	private boolean extremeWeather;

	public Perception(Position position) {
		this.position = position;
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

	public boolean hasStartPoint() {
		return startPoint;
	}

	public void setStartPoint(boolean value) {
		this.startPoint = value;
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
