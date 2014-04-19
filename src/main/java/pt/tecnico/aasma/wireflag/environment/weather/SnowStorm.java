package pt.tecnico.aasma.wireflag.environment.weather;

import java.util.Random;

import pt.tecnico.aasma.wireflag.util.AnimationLoader;
import pt.tecnico.aasma.wireflag.util.MapPosition;

public class SnowStorm extends Weather {

	public SnowStorm(int duration, MapPosition position) {
		super(duration, position, AnimationLoader.getLoader().getSnowStorm());
	}

	@Override
	public int getLifeDamage() {
		if (new Random().nextInt(100) > 80) {
			return HIGHLIFEDMG;
		} else {
			return MODERATELIFEDMG;
		}
	}
}
