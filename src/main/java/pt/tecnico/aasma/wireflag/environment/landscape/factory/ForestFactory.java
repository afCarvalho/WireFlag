package pt.tecnico.aasma.wireflag.environment.landscape.factory;

import pt.tecnico.aasma.wireflag.environment.landscape.Forest;
import pt.tecnico.aasma.wireflag.environment.landscape.Landscape;
import pt.tecnico.aasma.wireflag.util.position.MapPosition;

public class ForestFactory extends LandscapeFactory {

	@Override
	public Landscape createLandscape(MapPosition position) {
		return new Forest(position);
	}
}
