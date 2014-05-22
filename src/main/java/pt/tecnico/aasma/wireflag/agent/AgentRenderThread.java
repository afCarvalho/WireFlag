package pt.tecnico.aasma.wireflag.agent;

import org.newdawn.slick.Graphics;

import pt.tecnico.aasma.wireflag.environment.controller.EndGameController;

public class AgentRenderThread implements Runnable {

	private Agent agent;
	private Thread thread;
	private boolean active;
	private Graphics graphics;

	public AgentRenderThread(Agent agent) {
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
				agent.render(graphics);
				active = false;
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

	public synchronized void setGraphics(Graphics g) {
		if (!active) {
			this.graphics = g;
			active = true;
			this.notify();
		}
	}
}
