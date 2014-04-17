package pt.tecnico.aasma.wireflag.environment.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

import pt.tecnico.aasma.wireflag.GameElement;
import pt.tecnico.aasma.wireflag.agent.Agent;
import pt.tecnico.aasma.wireflag.agent.Builder;
import pt.tecnico.aasma.wireflag.environment.Perception;
import pt.tecnico.aasma.wireflag.environment.Flag;
import pt.tecnico.aasma.wireflag.environment.landscape.Landscape;
import pt.tecnico.aasma.wireflag.environment.landscape.factory.DesertFactory;
import pt.tecnico.aasma.wireflag.environment.landscape.factory.ForestFactory;
import pt.tecnico.aasma.wireflag.environment.landscape.factory.LandscapeFactory;
import pt.tecnico.aasma.wireflag.environment.landscape.factory.LimitFactory;
import pt.tecnico.aasma.wireflag.environment.landscape.factory.MountainFactory;
import pt.tecnico.aasma.wireflag.environment.landscape.factory.PlainFactory;
import pt.tecnico.aasma.wireflag.environment.landscape.factory.WaterFactory;
import pt.tecnico.aasma.wireflag.exception.LandscapeNotFoundException;
import pt.tecnico.aasma.wireflag.util.Position;

public class MapController implements GameElement {

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

		public static Landscape getTileLandscape(String landscapeName,
				int xCoord, int yCoord) throws LandscapeNotFoundException {

			for (LandscapeType land : LandscapeType.values()) {
				if (land.name.equals(landscapeName))
					return land.getLandscape(xCoord, yCoord);
			}
			throw new LandscapeNotFoundException(landscapeName);
		}

		public Landscape getLandscape(int xCoord, int yCoord) {
			return factory.createLandscape(xCoord, yCoord);
		}
	}

	private static final MapController INSTANCE = new MapController();
	private TiledMap grassMap;
	private static Landscape[][] tileMatrix;

	private MapController() {
	}

	@Override
	public void init() throws SlickException {

		grassMap = new TiledMap(System.getProperty("data") + "grassmap.tmx");
		tileMatrix = new Landscape[grassMap.getWidth()][grassMap.getHeight()];

		for (int xAxis = 0; xAxis < grassMap.getWidth(); xAxis++) {
			for (int yAxis = 0; yAxis < grassMap.getHeight(); yAxis++) {
				tileMatrix[xAxis][yAxis] = getLandscapeType(xAxis, yAxis);
			}
		}

		Agent agent = new Builder(1);
		agent.init();
		Flag flag = new Flag();
		flag.init();

		tileMatrix[0][0].setAgent(agent);
	}

	@Override
	public void render(Graphics g) {
		grassMap.render(0, 0);

		for (int xAxis = 0; xAxis < grassMap.getWidth(); xAxis++) {
			for (int yAxis = 0; yAxis < grassMap.getHeight(); yAxis++) {
				tileMatrix[xAxis][yAxis].render(g);
			}
		}
	}

	public void update(int delta) {

		for (int xAxis = 0; xAxis < grassMap.getWidth(); xAxis++) {
			for (int yAxis = 0; yAxis < grassMap.getHeight(); yAxis++) {
				tileMatrix[xAxis][yAxis].update(delta);
			}
		}
	}

	/* converte coord do agente em coord dos tiles */
	public Landscape getLandscape(Position p) {
		return tileMatrix[p.getX()][p.getY()];
	}

	public Position getMapPosition(Position p) {
		int xPos = (int) p.getX() / getNHorizontalTiles();
		int yPos = (int) p.getY() / getNVerticalTiles();
		return new Position(xPos, yPos);
	}

	public float getMovementSpeed(Position p) {
		return getLandscape(p).getMovementSpeed();
	}

	public void setExtremeWeather(Position p) throws SlickException {
		Random r = new Random();
		getLandscape(p).setExtremeWeather(r.nextInt(10000));
	}

	public boolean isBlocked(Position p) {
		return getMovementSpeed(p) == 0;
	}

	public int getNHorizontalTiles() {
		return grassMap.getWidth();
	}

	public int getNVerticalTiles() {
		return grassMap.getHeight();
	}

	public int getMapHeight() {
		return grassMap.getHeight() * grassMap.getTileHeight();
	}

	public int getMapWidth() {
		return grassMap.getWidth() * grassMap.getTileWidth();
	}

	public int getTileWidth() {
		return grassMap.getTileWidth();
	}

	public int getTileHeight() {
		return grassMap.getTileHeight();
	}

	public Position getRandomPosition() {
		Random r = new Random();

		int x = r.nextInt(getNHorizontalTiles());
		int y = r.nextInt(getNVerticalTiles());
		return new Position(x, y);
	}

	public static MapController getMap() {
		return INSTANCE;
	}

	private Landscape getLandscapeType(int xCoord, int yCoord) {
		try {
			int tileID = grassMap.getTileId(xCoord, yCoord, 0);
			String value = grassMap.getTileProperty(tileID, "terrain", "plain");
			return LandscapeType.getTileLandscape(value, xCoord, yCoord);
		} catch (LandscapeNotFoundException e) {
			System.out.println(e.toString());
			System.exit(0);
		}
		return null;
	}

	public List<Perception> getPerceptions(int teamId, Position pos) {
		List<Perception> list = new ArrayList<Perception>();

		Landscape landscape = getLandscape(getMapPosition(pos));

		if (landscape.hasFlag()) {
			Perception perception = new Perception(pos);
			perception.setFlag(true);
			list.add(perception);
		}

		if (landscape.hasAgent()) {
			if (landscape.getAgent().isEnemy(teamId)) {
				Perception perception = new Perception(pos);
				perception.setEnemy(true);
				list.add(perception);
			}
		}

		// ver se o start point da equipa coincide com a poicao que se esta a
		// ver

		if (landscape.hasAnimal()) {
			Perception perception = new Perception(pos);
			perception.setAnimal(true);
			list.add(perception);
		}

		if (landscape.getWeather().isExtremeWeather()) {
			Perception perception = new Perception(pos);
			perception.setExtremeWeather(true);
			list.add(perception);
		}

		return list;
	}
}
