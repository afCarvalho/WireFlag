package pt.tecnico.aasma.wireflag.agent.architecture;

import pt.tecnico.aasma.wireflag.agent.Agent;

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
