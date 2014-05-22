package pt.tecnico.aasma.wireflag.agent.architecture;

import pt.tecnico.aasma.wireflag.agent.Agent;
import pt.tecnico.aasma.wireflag.agent.communication.Message;

public class Hybrid extends Architecture {

	public Hybrid() {
		// TODO Auto-generated constructor stub
	}

	public void makeAction(Agent agent, int delta) {

		// TODO em obras...

		// no ultimo caso true
		agent.moveSameDirection(delta);
	}

	@Override
	public void processMessage(Message message) {
		// TODO Auto-generated method stub
		
	}
}
