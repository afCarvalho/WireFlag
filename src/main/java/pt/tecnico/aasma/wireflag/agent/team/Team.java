package pt.tecnico.aasma.wireflag.agent.team;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import pt.tecnico.aasma.wireflag.environment.controller.MapController;
import pt.tecnico.aasma.wireflag.util.MapPosition;
import pt.tecnico.aasma.wireflag.agent.Agent;

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

	public void addAgent(Agent agent) {
		agentsList.add(agent);
	}
}
