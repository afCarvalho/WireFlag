package pt.tecnico.aasma.wireflag.environment.weather;

import java.util.Random;

import pt.tecnico.aasma.wireflag.util.AnimationLoader;
import pt.tecnico.aasma.wireflag.util.MapPosition;

public class ThunderStorm extends Weather {

	public ThunderStorm(int duration, MapPosition position) {
		super(duration, position, AnimationLoader.getLoader().getThunderStorm());
	}

	@Override
	public int getLifeDamage() {
		if (new Random().nextInt(100) == 0) {
			return MAXLIFEDMG;
		} else {
			return LOWLIFEDMG;
		}
	}
}
