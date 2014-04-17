package pt.tecnico.aasma.wireflag.environment.landscape;

import org.newdawn.slick.SlickException;

import pt.tecnico.aasma.wireflag.environment.weather.ThunderStorm;
import pt.tecnico.aasma.wireflag.util.MapPosition;

public class Water extends Landscape {

	public Water(MapPosition position) {
		super(VREDUCEDSPD, position);
	}

	@Override
	public void setExtremeWeather(int duration) throws SlickException {
		weather = new ThunderStorm(duration, landscapePos);
		weather.init();
	}

	@Override
	public boolean isInflammable() {
		return false;
	}
}
