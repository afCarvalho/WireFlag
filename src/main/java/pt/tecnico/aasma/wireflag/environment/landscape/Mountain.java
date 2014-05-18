package pt.tecnico.aasma.wireflag.environment.landscape;

import org.newdawn.slick.SlickException;

import pt.tecnico.aasma.wireflag.environment.object.Animal;
import pt.tecnico.aasma.wireflag.environment.weather.SnowStorm;
import pt.tecnico.aasma.wireflag.util.AnimationLoader;
import pt.tecnico.aasma.wireflag.util.position.MapPosition;

public class Mountain extends Landscape {

	public Mountain(MapPosition position) {
		super(VREDUCEDSPD, position, HIGHVSB, HIGHFATIGUE);
	}

	/***************
	 *** SETTERS ***
	 ***************/

	@Override
	public void setExtremeWeather(int duration) throws SlickException {
		weather = new SnowStorm(duration, landscapePos);
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
		animal = new Animal(landscapePos, AnimationLoader.getLoader().getGoat());
		return animal;
	}
}
