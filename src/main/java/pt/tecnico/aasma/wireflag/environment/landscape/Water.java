package pt.tecnico.aasma.wireflag.environment.landscape;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import pt.tecnico.aasma.wireflag.agent.Agent;
import pt.tecnico.aasma.wireflag.environment.controller.MapController;
import pt.tecnico.aasma.wireflag.environment.object.Animal;
import pt.tecnico.aasma.wireflag.environment.weather.ThunderStorm;
import pt.tecnico.aasma.wireflag.util.AnimationLoader;
import pt.tecnico.aasma.wireflag.util.position.MapPosition;

public class Water extends Landscape {

	private Animation boat;

	public Water(MapPosition position) {
		super(VREDUCEDSPD, position, REDUCEDVSB, VHIGHFATIGUE);
		boat = AnimationLoader.getLoader().getBoat();
	}

	/***************
	 *** SETTERS ***
	 ***************/

	@Override
	public void setExtremeWeather(int duration) throws SlickException {
		weather = new ThunderStorm(duration, landscapePos);
	}

	/************************
	 *** STATE PREDICATES ***
	 ************************/

	@Override
	public boolean isInflammable() {
		return false;
	}

	/***********************
	 *** STATE MODIFIERS ***
	 ***********************/

	@Override
	public Animal createAnimal() {
		animal = new Animal(landscapePos, AnimationLoader.getLoader()
				.getTurtle());
		return animal;
	}

	/*********************
	 *** GAME RELATED ****
	 *********************/

	@Override
	public void render(Graphics g) {
		weather.render(g);

		if (hasFire()) {
			fire.render(g);
		}

		if (hasAnimal()) {
			animal.render(g);
		}

		if (hasTeamBase()) {
			teamBase.render(g);
		}
	}
}
