package pt.tecnico.aasma.wireflag.agent.architecture;

import java.util.List;

import pt.tecnico.aasma.wireflag.agent.Agent;
import pt.tecnico.aasma.wireflag.environment.Perception;
import pt.tecnico.aasma.wireflag.environment.controller.MapController;
import pt.tecnico.aasma.wireflag.util.MapPosition;

public class Reactive extends Architecture {

	public Reactive() {
		// TODO Auto-generated constructor stub
	}

	public void makeAction(Agent agent, int delta) {

		MapPosition pos = MapController.getMap().getMapPosition(agent.getPos());

		List<Perception> perceptions = MapController.getMap().getPerceptions(
				agent.getTeam().getIdentifier(), pos);

		// TODO em obras...

		// no ultimo caso true
		agent.randomMovement(delta);
	}

}
