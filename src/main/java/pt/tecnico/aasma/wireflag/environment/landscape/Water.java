package pt.tecnico.aasma.wireflag.environment.landscape;

import org.newdawn.slick.SlickException;

import pt.tecnico.aasma.wireflag.environment.weather.ThunderStorm;

public class Water extends Landscape {

	public Water(int xCoord, int yCoord) {
		super(VREDUCEDSPD, xCoord, yCoord);
	}

	@Override
	public void setExtremeWeather(int duration) throws SlickException {
		weather = new ThunderStorm(duration, xCoord, yCoord);
		weather.init();
	}

	@Override
	public boolean isInflammable() {
		return false;
	}
}
