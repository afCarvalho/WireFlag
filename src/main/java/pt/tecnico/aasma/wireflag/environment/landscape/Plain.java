package pt.tecnico.aasma.wireflag.environment.landscape;

import org.newdawn.slick.SlickException;

import pt.tecnico.aasma.wireflag.environment.weather.Rainy;
import pt.tecnico.aasma.wireflag.util.MapPosition;

public class Plain extends Landscape {

	public Plain(MapPosition position) {
		super(NORMALSPD, position);
	}

	@Override
	public void setExtremeWeather(int duration) throws SlickException {
		weather = new Rainy(duration, landscapePos);
		weather.init();
	}

	@Override
	public boolean isInflammable() {
		return true;
	}

}
