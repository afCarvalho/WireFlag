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
	}

	public void init(int teamSize) {
		Random random = new Random();

		int width = MapController.getMap().getMapWidth()
				/ MapController.getMap().getTileWidth();
		int height = MapController.getMap().getMapHeight()
				/ MapController.getMap().getTileHeight();
		initialX = random.nextInt(width);
		initialY = random.nextInt(height);

		// verifies if it is inside the limits
		while (initialX > width - teamSize || initialY > height - teamSize) {
			initialX = random.nextInt(width);
			initialY = random.nextInt(height);
		}
	}

	public int getId() {
		return id;
	}

	public float getInitialX() {
		return initialX;
	}

	public float getInitialY() {
		return initialY;
	}

	public void addAgent(Agent agent) {
		agentsList.add(agent);
	}
}
