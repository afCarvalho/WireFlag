package pt.tecnico.aasma.wireflag.environment.landscape;

import java.util.Random;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import pt.tecnico.aasma.wireflag.IGameElement;
import pt.tecnico.aasma.wireflag.agent.Agent;
import pt.tecnico.aasma.wireflag.environment.object.Animal;
import pt.tecnico.aasma.wireflag.environment.object.EndPoint;
import pt.tecnico.aasma.wireflag.environment.object.Fire;
import pt.tecnico.aasma.wireflag.environment.object.Flag;
import pt.tecnico.aasma.wireflag.environment.weather.Sunny;
import pt.tecnico.aasma.wireflag.environment.weather.Weather;
import pt.tecnico.aasma.wireflag.util.MapPosition;

public abstract class Landscape implements IGameElement {

	protected final static float NORMALSPD = 1.0f;
	protected final static float REDUCEDSPD = 0.5f;
	protected final static float VREDUCEDSPD = 0.1f;
	protected final static float NOSPD = 0f;

	protected final static int NORMALVSB = 0;
	protected final static int REDUCEDVSB = -1;
	protected final static int HIGHVSB = 1;

	protected final static int VHIGHFATIGUE = 5;
	protected final static int HIGHFATIGUE = 3;
	protected final static int NORMALFATIGUE = 2;

	protected MapPosition landscapePos;
	protected float movementSpeed;
	protected Weather weather;
	protected Flag flag;
	protected EndPoint endPoint;
	protected Agent agent;
	protected Fire fire;
	protected Animal animal;
	protected int visibility;
	protected int fatigue;

	public Landscape(float movementSpeed, MapPosition position, int visibility,
			int fatigue) {
		this.movementSpeed = movementSpeed;
		this.landscapePos = position;
		setSunnyWeather();
		this.visibility = visibility;
		this.fatigue = fatigue;
	}

	public boolean hasAgent() {
		return agent != null;
	}

	public boolean hasFlag() {
		return flag != null;
	}

	public boolean hasEndPoint() {
		return endPoint != null;
	}

	public boolean hasFire() {
		return fire != null;
	}

	public boolean hasAnimal() {
		return animal != null;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	public void setFlag(Flag flag) {
		this.flag = flag;
	}

	public void setEndPoint(EndPoint endPoint) {
		this.endPoint = endPoint;
	}

	public void setOnFire(Fire fire) {
		this.fire = fire;
	}

	public void setAnimal(Animal animal) {
		this.animal = animal;
	}

	public void killAnimal() {
		animal.kill();
	}

	public float getMovementSpeed() {
		return movementSpeed;
	}

	public Weather getWeather() {
		return weather;
	}

	public abstract void setExtremeWeather(int duration) throws SlickException;

	public Agent getAgent() {
		return agent;
	}

	public void setSunnyWeather() {
		weather = new Sunny(0, landscapePos);
	}

	public void takePenalty() {
		agent.increaseFatigue(fatigue);
		if (hasFire()) {
			agent.decreaseLife(20);
		}
		if (getWeather().isExtremeWeather()) {
			agent.decreaseLife(getWeather().getLifeDamage());

			if (new Random().nextInt(100) > 80) {
				agent.setIll(true);
			}
		}
		
		if(agent.isIll()){
			agent.decreaseLife(15);
		}
	}

	public abstract boolean isInflammable();

	@Override
	public void update(int delta) throws SlickException {

		if (hasFire()) {
			fire.update(delta);
		}

		if (hasAnimal()) {
			if (animal.isAlive() && !hasFire()) {
				animal.update(delta);
			} else {
				animal = null;
			}
		}

		if (hasFire() && !fire.isActive()) {
			fire = null;
		}
		
		if (hasAgent() && !agent.isAlive()) {
			setAgent(null);
		}

		if (hasAgent()) {
			agent.update(delta);
		}
		
		weather.update(delta);

		if (!weather.isExtremeWeather()) {
			setSunnyWeather();
		} else {
			fire = null;
		}
	}

	@Override
	public void render(Graphics g) {
		weather.render(g);

		if (hasFire()) {
			fire.render(g);
		}

		if (hasAnimal()) {
			animal.render(g);
		}

		if (hasFlag()) {
			flag.render(g);
		}

		if (hasEndPoint()) {
			endPoint.render(g);
		}

		if (hasAgent()) {
			agent.render(g);
		}
	}

	public int getVisibility() {
		return visibility;
	}
}
