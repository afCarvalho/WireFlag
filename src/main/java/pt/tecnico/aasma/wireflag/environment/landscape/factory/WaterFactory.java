package pt.tecnico.aasma.wireflag.environment.landscape.factory;

import pt.tecnico.aasma.wireflag.environment.landscape.Landscape;
import pt.tecnico.aasma.wireflag.environment.landscape.Water;

public class WaterFactory extends LandscapeFactory {

	@Override
	public Landscape createLandscape(int xCoord, int yCoord) {
		return new Water(yCoord, yCoord);
	}
}
