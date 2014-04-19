package pt.tecnico.aasma.wireflag.environment.weather;

import pt.tecnico.aasma.wireflag.util.AnimationLoader;
import pt.tecnico.aasma.wireflag.util.MapPosition;

public class SandStorm extends Weather {

	public SandStorm(int duration, MapPosition position) {
		super(duration, position, AnimationLoader.getLoader().getSandStorm());
	}

	@Override
	public int getLifeDamage() {
		return MODERATELIFEDMG;
	}
}
