package pt.tecnico.aasma.wireflag.environment.landscape;

import java.util.Random;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import pt.tecnico.aasma.wireflag.IGameElement;
import pt.tecnico.aasma.wireflag.agent.Agent;
import pt.tecnico.aasma.wireflag.agent.AgentThread;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.Deliberative;
import pt.tecnico.aasma.wireflag.environment.controller.AgentController;
import pt.tecnico.aasma.wireflag.environment.controller.EndGameController;
import pt.tecnico.aasma.wireflag.environment.controller.MapController;
import pt.tecnico.aasma.wireflag.environment.object.Animal;
import pt.tecnico.aasma.wireflag.environment.object.EndPoint;
import pt.tecnico.aasma.wireflag.environment.object.Fire;
import pt.tecnico.aasma.wireflag.environment.object.Flag;
import pt.tecnico.aasma.wireflag.environment.weather.Sunny;
import pt.tecnico.aasma.wireflag.environment.weather.Weather;
import pt.tecnico.aasma.wireflag.util.AnimationLoader;
import pt.tecnico.aasma.wireflag.util.position.MapPosition;

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

	/***************
	 *** GETTERS ***
	 ***************/

	public float getMovementSpeed() {
		return movementSpeed;
	}

	public Weather getWeather() {
		return weather;
	}

	public Agent getAgent() {
		return agent;
	}

	public int getVisibility() {
		return visibility;
	}

	/* return the landscape's rating */
	public double getRating() {
		return 0.4 * movementSpeed + 0.4 * (5 - fatigue) + 0.2
				* (visibility + 1);
	}

	public Animal getAnimal() {
		return animal;
	}

	/***************
	 *** SETTERS ***
	 ***************/

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	public void setFlag(Flag flag) {
		this.flag = flag;
	}

	public void setEndPoint(EndPoint endPoint) {
		this.endPoint = endPoint;
	}

	public void setFire(Fire fire) {
		this.fire = fire;
	}

	public abstract void setExtremeWeather(int duration) throws SlickException;

	public void setSunnyWeather() {
		weather = new Sunny(0, landscapePos);
	}

	public void setVisibility(int visibility) {
		this.visibility = visibility;
	}

	/************************
	 *** STATE PREDICATES ***
	 ************************/

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

	public abstract boolean isInflammable();

	/***********************
	 *** STATE MODIFIERS ***
	 ***********************/

	/* removes the flag and returns it */
	public Flag removeFlag() {
		Flag temp = flag;

		flag = null;

		return temp;
	}

	public int killAnimal() {
		if (hasAnimal()) {
			return animal.kill();
		} else {
			return 0;
		}
	}

	public void takePenalty() {
		agent.modifyFatigue(fatigue);
		if (hasFire()) {
			agent.modifyLife(-20);
		}
		if (getWeather().isExtremeWeather()) {
			agent.modifyLife(-getWeather().getLifeDamage());

			if (new Random().nextInt(100) > 80) {
				agent.setIll(true);
			}
		}

		if (agent.isIll()) {
			agent.modifyLife(-15);
		}
	}

	public abstract Animal createAnimal();

	/********************
	 *** GAME RELATED ***
	 ********************/

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
			EndGameController.getEnd().decreaseNAliveAgents();
			agent.dropFlag();
			setAgent(null);
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

		if (hasEndPoint()) {
			endPoint.render(g);
		}

		if (hasAgent()) {
			agent.render(g);
		}
	}
}
