package pt.tecnico.aasma.wireflag.environment.landscape;

import org.newdawn.slick.SlickException;

import pt.tecnico.aasma.wireflag.environment.weather.Rainy;

public class Plain extends Landscape {

	public Plain(int xCoord, int yCoord) {	
		super(NORMALSPD, xCoord, yCoord);
	}

	@Override
	public void setExtremeWeather(int duration) throws SlickException {
		weather = new Rainy(duration, xCoord, yCoord);
		weather.init();
	}

	@Override
	public boolean isInflammable() {
		return true;
	}

}
