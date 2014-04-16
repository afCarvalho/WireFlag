package pt.tecnico.aasma.wireflag.environment.landscape.factory;

import pt.tecnico.aasma.wireflag.environment.landscape.Landscape;
import pt.tecnico.aasma.wireflag.environment.landscape.Limit;

public class LimitFactory extends LandscapeFactory {

	@Override
	public Landscape createLandscape() {
		return new Limit();
	}
}
