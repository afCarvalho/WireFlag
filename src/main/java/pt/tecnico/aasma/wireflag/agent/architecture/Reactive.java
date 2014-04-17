package pt.tecnico.aasma.wireflag.agent.architecture;

import java.util.List;

import pt.tecnico.aasma.wireflag.agent.Agent;
import pt.tecnico.aasma.wireflag.environment.Perception;
import pt.tecnico.aasma.wireflag.environment.controller.MapController;

public class Reactive extends Architecture {

	public Reactive() {
		// TODO Auto-generated constructor stub
	}

	public void makeAction(Agent agent, int delta) {
		List<Perception> perceptions = MapController.getMap().getPerceptions(
				agent.getTeamId(), agent.getX(), agent.getY());

		// TODO em obras...

		// no ultimo caso true
		agent.randomMovement(delta);
	}

}
