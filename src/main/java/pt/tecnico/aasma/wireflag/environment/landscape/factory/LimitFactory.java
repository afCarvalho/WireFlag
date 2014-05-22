package pt.tecnico.aasma.wireflag.environment.landscape.factory;

import pt.tecnico.aasma.wireflag.environment.landscape.Landscape;
import pt.tecnico.aasma.wireflag.environment.landscape.Limit;
import pt.tecnico.aasma.wireflag.util.position.MapPosition;

public class LimitFactory extends LandscapeFactory {

	@Override
	public Landscape createLandscape(MapPosition position) {
		return new Limit(position);
	}
}
