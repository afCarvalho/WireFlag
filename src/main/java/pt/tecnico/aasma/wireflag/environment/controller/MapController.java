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
import pt.tecnico.aasma.wireflag.agent.Doctor;
import pt.tecnico.aasma.wireflag.agent.Patrol;
import pt.tecnico.aasma.wireflag.agent.Soldier;
import pt.tecnico.aasma.wireflag.agent.team.DemocraticalTeam;
import pt.tecnico.aasma.wireflag.agent.team.Team;
import pt.tecnico.aasma.wireflag.agent.architecture.Reactive;
import pt.tecnico.aasma.wireflag.agent.type.Builder;
import pt.tecnico.aasma.wireflag.environment.EndPoint;
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
import pt.tecnico.aasma.wireflag.exception.InvalidTeamSizeException;
import pt.tecnico.aasma.wireflag.exception.LandscapeNotFoundException;
import pt.tecnico.aasma.wireflag.util.MapPosition;
import pt.tecnico.aasma.wireflag.util.WorldPosition;

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
		
		Agent leader = new Builder("1");
		List<Agent> agents = new ArrayList<Agent>();
		agents.add(new Builder("2"));
		agents.add(new Doctor("3"));
		agents.add(new Patrol("4"));
		agents.add(new Soldier("5"));
		try {
			Team team = new DemocraticalTeam("team1", leader, agents);
		} catch (InvalidTeamSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Flag flag = new Flag();
		flag.init();
		
		for (int i = 0; i < agents.size(); i++) {
			tileMatrix[0][i+1].setAgent(agents.get(i));
		}

		tileMatrix[0][0].setAgent(leader);

		Flag flag = new Flag();
		flag.init();
		EndPoint endPoint = new EndPoint();
		endPoint.init();
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
	public Landscape getLandscape(MapPosition p) {
		return tileMatrix[p.getX()][p.getY()];
	}

	public Landscape getLandscape(WorldPosition p) {
		return getLandscape(getMapPosition(p.getX(), p.getY()));
	}

	public MapPosition getMapPosition(float x, float y) {
		int xPos = (int) x / getNHorizontalTiles();
		int yPos = (int) y / getNVerticalTiles();
		return new MapPosition(xPos, yPos);
	}

	public MapPosition getMapPosition(WorldPosition p) {
		return getMapPosition(p.getX(), p.getY());
	}

	public float getMovementSpeed(MapPosition p) {
		return getLandscape(p).getMovementSpeed();
	}

	public void setExtremeWeather(MapPosition p) throws SlickException {
		Random r = new Random();
		getLandscape(p).setExtremeWeather(r.nextInt(10000));
	}

	public boolean isBlocked(MapPosition p) {
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

	public MapPosition getRandomPosition() {
		Random r = new Random();

		int x = r.nextInt(getNHorizontalTiles());
		int y = r.nextInt(getNVerticalTiles());
		return new MapPosition(x, y);
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

	/* for each tile is created a perception */
	public Perception getTilePerception(int teamId, MapPosition pos) {
		Landscape landscape = getLandscape(pos);

		Perception perception = new Perception(pos);

		if (landscape.hasFlag()) {
			perception.setFlag(true);
		}

		if (landscape.hasAgent()) {
			if (landscape.getAgent().isEnemy(teamId)) {
				perception.setEnemy(true);
			}
		}

		if (landscape.hasEndPoint()) {
			perception.setEndPoint(true);
		}

		if (landscape.hasAnimal()) {
			perception.setAnimal(true);
		}

		if (TimeController.isNight()) {
			perception.setNight(true);
		}

		if (landscape.hasFire()) {
			perception.setFire(true);

		}

		if (landscape.getWeather().isExtremeWeather()) {
			perception.setExtremeWeather(true);
		}

		return perception;
	}

	public List<Perception> getPerceptions(int teamId, MapPosition pos) {
		List<Perception> list = new ArrayList<Perception>();

		// TODO apply this code to each tile on field of vision
		list.add(getTilePerception(teamId, pos));

		return list;
	}
}
