package pt.tecnico.aasma.wireflag.agent.team;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import pt.tecnico.aasma.wireflag.environment.controller.MapController;
import pt.tecnico.aasma.wireflag.agent.Agent;

public abstract class Team {

	private int id;

	private float initialX;
	private float initialY;

	private List<Agent> agentsList = new ArrayList<Agent>();

	public Team(int id) {
		this.id = id;

		Random random = new Random();
		initialX = random.nextInt(MapController.getMap().getTileWidth());
		initialY = random.nextInt(MapController.getMap().getTileHeight());

		// verifies if it is inside the limits
		while (initialX > initialX - 5 || initialY > initialY - 5) {
			initialX = random.nextInt(MapController.getMap().getTileWidth());
			initialY = random.nextInt(MapController.getMap().getTileHeight());
		}
	}

	public int getId() {
		return id;
	}

	public void addAgent(Agent agent) {
		agentsList.add(agent);
	}
}
