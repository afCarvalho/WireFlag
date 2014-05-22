package pt.tecnico.aasma.wireflag.environment.landscape.factory;

import pt.tecnico.aasma.wireflag.environment.landscape.Landscape;
import pt.tecnico.aasma.wireflag.util.position.MapPosition;

public abstract class LandscapeFactory {

	public abstract Landscape createLandscape(MapPosition position);

}
