package pt.tecnico.aasma.wireflag.environment.landscape;

import org.newdawn.slick.SlickException;

import pt.tecnico.aasma.wireflag.environment.object.Animal;
import pt.tecnico.aasma.wireflag.environment.weather.Rainy;
import pt.tecnico.aasma.wireflag.util.AnimationLoader;
import pt.tecnico.aasma.wireflag.util.MapPosition;

public class Forest extends Landscape {

	public Forest(MapPosition position) {
		super(REDUCEDSPD, position, REDUCEDVSB, NORMALFATIGUE);
	}

	@Override
	public void setExtremeWeather(int duration) throws SlickException {
		weather = new Rainy(duration, landscapePos);
	}

	@Override
	public boolean isInflammable() {
		return true;
	}

	@Override
	public Animal createAnimal() {
		animal = new Animal(landscapePos, AnimationLoader.getLoader()
				.getRabbit());
		return animal;
	}
}
