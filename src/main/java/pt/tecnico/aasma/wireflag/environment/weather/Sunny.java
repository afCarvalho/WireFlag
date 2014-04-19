package pt.tecnico.aasma.wireflag.environment.weather;

import pt.tecnico.aasma.wireflag.util.MapPosition;

public class Sunny extends Weather {

	public Sunny(int duration, MapPosition position) {
		super(duration, position, null);
	}

	@Override
	public void draw(int x, int y) {
	}

	@Override
	public int getLifeDamage() {
		return NOLIFEDMG;
	}
}
