package pt.tecnico.aasma.wireflag.environment.controller;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import pt.tecnico.aasma.wireflag.GameElement;
import pt.tecnico.aasma.wireflag.agent.Agent;
import pt.tecnico.aasma.wireflag.agent.architecture.Reactive;
import pt.tecnico.aasma.wireflag.agent.type.Builder;
import pt.tecnico.aasma.wireflag.agent.type.Doctor;
import pt.tecnico.aasma.wireflag.agent.type.Patrol;
import pt.tecnico.aasma.wireflag.agent.type.Soldier;
import sun.management.resources.agent;

public class AgentController implements GameElement {

	private int currentIdentifier;

	private List<Agent> agents = new ArrayList<Agent>();

	public AgentController() {
		currentIdentifier = 0;
	}

	public int getNextIdentifier() {
		return currentIdentifier++;
	}

	/**
	 * Gets the list of agents in game.
	 * 
	 * @return the agents
	 */
	public final List<Agent> getAgents() {
		return agents;
	}

	public Agent getAgent(int identifier) {
		for (Agent agent : agents) {
			if (agent.getIdentifier() == identifier) {
				return agent;
			}
		}
		return null;
	}

	public void addAgent(Agent agent) {
		agents.add(agent);
	}

	@Override
	public void init() throws SlickException {
		agents.add(new Soldier(getNextIdentifier(), new Reactive()));
		agents.add(new Builder(getNextIdentifier(), new Reactive()));
		agents.add(new Doctor(getNextIdentifier(), new Reactive()));
		agents.add(new Patrol(getNextIdentifier(), new Reactive()));
		agents.add(new Soldier(getNextIdentifier(), new Reactive()));

		for (Agent agent : agents) {
			agent.init();
		}
	}

	public void update(int delta) {

	}

	@Override
	public void render(Graphics g) {

	}

}
