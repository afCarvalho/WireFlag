package pt.tecnico.aasma.wireflag.agent;

public class AgentThread implements Runnable {

	private Agent agent;
	private int delta;

	public AgentThread(Agent agent, int delta) {
		this.agent = agent;
		this.delta = delta;
	}

	@Override
	public void run() {
		agent.update(delta);
	}
}
