package pt.tecnico.aasma.wireflag.environment.landscape;

import org.newdawn.slick.SlickException;

import pt.tecnico.aasma.wireflag.environment.weather.SandStorm;
import pt.tecnico.aasma.wireflag.util.MapPosition;

public class Desert extends Landscape {

	public Desert(MapPosition position) {
		super(REDUCEDSPD, position,NORMALVSB);
	}

	@Override
	public void setExtremeWeather(int duration) throws SlickException {
		weather = new SandStorm(duration, landscapePos);
	}

	@Override
	public boolean isInflammable() {
		return false;
	}
}
