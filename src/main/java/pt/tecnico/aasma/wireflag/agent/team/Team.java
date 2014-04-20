package pt.tecnico.aasma.wireflag.agent.team;

import java.util.ArrayList;
import java.util.List;

import pt.tecnico.aasma.wireflag.agent.Agent;
import pt.tecnico.aasma.wireflag.environment.controller.EndGameController;
import pt.tecnico.aasma.wireflag.environment.controller.MapController;
import pt.tecnico.aasma.wireflag.util.MapPosition;
import pt.tecnico.aasma.wireflag.util.WorldPosition;

public abstract class Team {

	private int id;
	private MapPosition teamPosition;
	private List<Agent> agentsList = new ArrayList<Agent>();

	public Team(int id) {
		this.id = id;
	}

	public void init(int teamSize) {
		int width = MapController.getMap().getNHorizontalTiles();
		int height = MapController.getMap().getNVerticalTiles();
		teamPosition = MapController.getMap().getRandomPosition();

		// verifies if it is inside the limits
		while (teamPosition.getX() > width - teamSize
				|| teamPosition.getY() > height - teamSize) {
			teamPosition = MapController.getMap().getRandomPosition();
		}
	}

	public int getId() {
		return id;
	}

	public MapPosition getTeamPosition() {
		return teamPosition;
	}

	public void setTeamPosition(MapPosition teamPosition) {
		this.teamPosition = teamPosition;
	}

	private void increaseTeamPositionX() {
		this.teamPosition.setX(teamPosition.getX() + 1);
	}

	public List<Agent> getAgents() {
		return agentsList;
	}

	public void addAgent(Agent agent) {
		int tileWidth = MapController.getMap().getTileWidth();
		int tileHeight = MapController.getMap().getTileWidth();
		agentsList.add(agent);
		agent.setPos(new WorldPosition(teamPosition.getX() * tileWidth,
				teamPosition.getY() * tileHeight));
		agent.setTeamId(id);
		MapController.getMap().getLandscape(teamPosition).setAgent(agent);
		EndGameController.getEnd().increaseNAliveAgents();
		increaseTeamPositionX();
	}

	public void removeAgent(Agent agent) {
		agentsList.remove(agent);
	}
}
