package pt.tecnico.aasma.wireflag.environment.landscape;

import org.newdawn.slick.SlickException;

import pt.tecnico.aasma.wireflag.environment.landscape.factory.DesertFactory;
import pt.tecnico.aasma.wireflag.environment.landscape.factory.ForestFactory;
import pt.tecnico.aasma.wireflag.environment.landscape.factory.LandscapeFactory;
import pt.tecnico.aasma.wireflag.environment.landscape.factory.LimitFactory;
import pt.tecnico.aasma.wireflag.environment.landscape.factory.MountainFactory;
import pt.tecnico.aasma.wireflag.environment.landscape.factory.PlainFactory;
import pt.tecnico.aasma.wireflag.environment.landscape.factory.WaterFactory;
import pt.tecnico.aasma.wireflag.exception.LandscapeNotFoundException;
import pt.tecnico.aasma.wireflag.util.MapPosition;

public enum LandscapeType {
	DESERT("desert", new DesertFactory()), FOREST("trees",
			new ForestFactory()), HOLE("hole", new LimitFactory()), MOUNTAIN(
			"mountain", new MountainFactory()), WATER("water",
			new WaterFactory()), LIMIT("limit", new LimitFactory()), PLAIN(
			"plain", new PlainFactory());

	private String name;
	private LandscapeFactory factory;

	LandscapeType(String name, LandscapeFactory factory) {
		this.name = name;
		this.factory = factory;
	}

	public Landscape createLandscape(MapPosition pos) {
		return factory.createLandscape(pos);
	}

	public static Landscape getTileLandscape(String landscapeName,
			MapPosition pos) throws SlickException {

		for (LandscapeType land : LandscapeType.values()) {
			if (land.name.equals(landscapeName))
				return land.createLandscape(pos);
		}
		throw new LandscapeNotFoundException(landscapeName);
	}
}
