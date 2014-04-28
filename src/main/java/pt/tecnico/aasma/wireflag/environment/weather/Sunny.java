package pt.tecnico.aasma.wireflag.environment.weather;

import pt.tecnico.aasma.wireflag.util.MapPosition;

public class Sunny extends Weather {

	public Sunny(int duration, MapPosition position) {
		super(duration, position, null);
	}

	/***************
	 *** GETTERS ***
	 ***************/

	@Override
	public int getLifeDamage() {
		return NOLIFEDMG;
	}

	/*********************
	 *** GAME RELATED ****
	 *********************/

	@Override
	public void draw(int x, int y) {

	}
}
