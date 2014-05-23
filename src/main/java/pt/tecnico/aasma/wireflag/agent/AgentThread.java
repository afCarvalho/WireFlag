package pt.tecnico.aasma.wireflag.agent;

import pt.tecnico.aasma.wireflag.environment.controller.EndGameController;

public class AgentThread implements Runnable {

	private Agent agent;
	private int delta;
	private Thread thread;
	private boolean active;

	public AgentThread(Agent agent) {
		this.agent = agent;
	}

	public void init() {
		this.thread = new Thread(this);
		thread.start();

	}

	@Override
	public void run() {
		while (agent.isAlive() && !EndGameController.getEnd().getGameFinished()) {
			if (active) {
				agent.update(delta);
				active = false;
			} else {
				stop();
			}
		}
	}

	public synchronized void stop() {
		try {
			while (!active) {
				this.wait();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public synchronized void setDelta(int delta) {
		if (!active) {
			this.delta = delta;
			active = true;
			this.notify();
		}
	}
}
