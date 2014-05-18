package pt.tecnico.aasma.wireflag.environment.landscape.factory;

import pt.tecnico.aasma.wireflag.environment.landscape.Desert;
import pt.tecnico.aasma.wireflag.environment.landscape.Landscape;
import pt.tecnico.aasma.wireflag.util.position.MapPosition;

public class DesertFactory extends LandscapeFactory {

	@Override
	public Landscape createLandscape(MapPosition position) {
		return new Desert(position);
	}
}
