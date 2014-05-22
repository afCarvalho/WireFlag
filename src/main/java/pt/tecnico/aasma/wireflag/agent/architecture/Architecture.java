package pt.tecnico.aasma.wireflag.agent.architecture;

import pt.tecnico.aasma.wireflag.agent.Agent;
import pt.tecnico.aasma.wireflag.agent.communication.Message;

public abstract class Architecture {

	public Architecture() {
		// TODO Auto-generated constructor stub
	}
	
	public abstract void makeAction(Agent agent, int delta);
	
	public abstract void processMessage(Message message);

}
