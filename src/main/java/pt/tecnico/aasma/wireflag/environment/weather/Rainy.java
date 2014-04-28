package pt.tecnico.aasma.wireflag.environment.weather;

import pt.tecnico.aasma.wireflag.util.AnimationLoader;
import pt.tecnico.aasma.wireflag.util.MapPosition;

public class Rainy extends Weather {

	public Rainy(int duration, MapPosition position) {
		super(duration, position, AnimationLoader.getLoader().getRain());
	}

	/***************
	 *** GETTERS ***
	 ***************/

	@Override
	public int getLifeDamage() {
		return LOWLIFEDMG;
	}
}
