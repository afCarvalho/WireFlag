package pt.tecnico.aasma.wireflag.environment.landscape;

import org.newdawn.slick.SlickException;

import pt.tecnico.aasma.wireflag.environment.object.Animal;
import pt.tecnico.aasma.wireflag.environment.weather.Sunny;
import pt.tecnico.aasma.wireflag.util.position.MapPosition;

public class Limit extends Landscape {

	public Limit(MapPosition position) {
		super(0, position, REDUCEDVSB, VHIGHFATIGUE);
	}

	/***************
	 *** SETTERS ***
	 ***************/

	@Override
	public void setExtremeWeather(int duration) throws SlickException {
		weather = new Sunny(0, landscapePos);
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
		return null;
	}
}
