package pt.tecnico.aasma.wireflag.agent.architecture;

import java.util.List;

import pt.tecnico.aasma.wireflag.agent.Agent;
import pt.tecnico.aasma.wireflag.environment.Perception;
import pt.tecnico.aasma.wireflag.environment.controller.MapController;

public class Deliberative extends Architecture {

	public Deliberative() {
		// TODO Auto-generated constructor stub
	}

	public void makeAction(Agent agent, int delta) {

		// TODO em obras...

		// no ultimo caso true
		agent.moveSameDirection(delta);
	}
}
