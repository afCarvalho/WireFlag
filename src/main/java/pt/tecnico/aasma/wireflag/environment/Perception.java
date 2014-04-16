package pt.tecnico.aasma.wireflag.environment;

public class Perception {

	private float x;
	private float y;
	private boolean flag;
	private boolean enemy;
	private boolean startPoint;
	private boolean animal;
	private boolean night;
	private boolean fire;
	/* raining, sand storm, snow storm */
	private boolean extremeWeather;

	public Perception(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
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
