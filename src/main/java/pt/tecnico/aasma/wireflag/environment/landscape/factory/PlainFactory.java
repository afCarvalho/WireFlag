package pt.tecnico.aasma.wireflag.environment.landscape.factory;

import pt.tecnico.aasma.wireflag.environment.landscape.Landscape;
import pt.tecnico.aasma.wireflag.environment.landscape.Plain;

public class PlainFactory extends LandscapeFactory {

	@Override
	public Landscape createLandscape() {
		return new Plain();
	}
}
