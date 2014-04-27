package pt.tecnico.aasma.wireflag.agent;

import java.util.LinkedList;

public class AgentThread implements Runnable {

	private Agent agent;
	private LinkedList<Integer> deltas;

	public AgentThread(Agent agent) {
		this.agent = agent;
		deltas = new LinkedList<Integer>();
	}

	@Override
	public void run() {
		while (agent.isAlive()) {
			if (deltas.size() > 0) {
				agent.update(deltas.removeFirst());
			} else {
				stop();
			}
		}
	}

	public synchronized void stop() {
		try {
			this.wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public synchronized void setDelta(int delta) {
		this.notify();
		deltas.add(delta);
	}
}
