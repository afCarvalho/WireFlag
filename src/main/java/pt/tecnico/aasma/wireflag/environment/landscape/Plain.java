package pt.tecnico.aasma.wireflag.environment.landscape;

import java.util.Random;

import org.newdawn.slick.SlickException;

import pt.tecnico.aasma.wireflag.environment.object.Animal;
import pt.tecnico.aasma.wireflag.environment.weather.Rainy;
import pt.tecnico.aasma.wireflag.util.AnimationLoader;
import pt.tecnico.aasma.wireflag.util.position.MapPosition;

public class Plain extends Landscape {

	public Plain(MapPosition position) {
		super(NORMALSPD, position, NORMALVSB, NORMALFATIGUE);
	}

	/***************
	 *** SETTERS ***
	 ***************/

	@Override
	public void setExtremeWeather(int duration) throws SlickException {
		weather = new Rainy(duration, landscapePos);
	}

	/************************
	 *** STATE PREDICATES ***
	 ************************/

	@Override
	public boolean isInflammable() {
		return true;
	}

	/***********************
	 *** STATE MODIFIERS ***
	 ***********************/

	@Override
	public Animal createAnimal() {
		if (new Random().nextInt(2) == 0) {
			animal = new Animal(landscapePos, AnimationLoader.getLoader()
					.getPig());
		} else {
			animal = new Animal(landscapePos, AnimationLoader.getLoader()
					.getGoat());
		}
		return animal;
	}
}
