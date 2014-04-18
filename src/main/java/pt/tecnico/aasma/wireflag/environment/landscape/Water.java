package pt.tecnico.aasma.wireflag.environment.landscape;

import org.newdawn.slick.SlickException;

import pt.tecnico.aasma.wireflag.environment.weather.ThunderStorm;
import pt.tecnico.aasma.wireflag.util.MapPosition;

public class Water extends Landscape {

	public Water(MapPosition position) {
		super(VREDUCEDSPD, position, REDUCEDVSB);
	}

	@Override
	public void setExtremeWeather(int duration) throws SlickException {
		weather = new ThunderStorm(duration, landscapePos);
	}

	@Override
	public boolean isInflammable() {
		return false;
	}
}
