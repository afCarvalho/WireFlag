package pt.tecnico.aasma.wireflag.environment.landscape.factory;

import pt.tecnico.aasma.wireflag.environment.landscape.Landscape;
import pt.tecnico.aasma.wireflag.environment.landscape.Water;
import pt.tecnico.aasma.wireflag.util.MapPosition;

public class WaterFactory extends LandscapeFactory {

	@Override
	public Landscape createLandscape(MapPosition position) {
		return new Water(position);
	}
}
