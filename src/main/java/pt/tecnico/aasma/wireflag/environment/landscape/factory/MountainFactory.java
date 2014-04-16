package pt.tecnico.aasma.wireflag.environment.landscape.factory;

import pt.tecnico.aasma.wireflag.environment.landscape.Landscape;
import pt.tecnico.aasma.wireflag.environment.landscape.Mountain;

public class MountainFactory extends LandscapeFactory {

	@Override
	public Landscape createLandscape() {
		return new Mountain();
	}

}
