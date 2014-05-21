package pt.tecnico.aasma.wireflag.agent.architecture;

import java.util.List;

import pt.tecnico.aasma.wireflag.agent.Agent;
import pt.tecnico.aasma.wireflag.agent.architecture.deliberative.Deliberative;
import pt.tecnico.aasma.wireflag.environment.perception.Perception;

/**
 * The hybrid architecture.
 */
public class Hybrid extends Architecture {

	/** The reactive layer. */
	private final Reactive reactiveLayer;

	/** The deliberative layer. */
	private final Deliberative deliberativeLayer;

	/**
	 * Instantiates a new hybrid architecture.
	 */
	public Hybrid() {
		this.reactiveLayer = new Reactive();
		this.deliberativeLayer = new Deliberative();
	}

	/**
	 * Checks if a perception is urgent.
	 * 
	 * @param agent
	 *            - the agent
	 * @param perception
	 *            - the perception
	 * @return true, if is urgent perception
	 */
	private boolean isUrgentPerception(Agent agent, Perception perception) {

		// endpoint da equipa && flag
		// fire na pos do agente
		// extreme weather na pos do agente
		// cansado
		// vida
		// enemy

		// TODO muralha do inimigo

		return agent.hasFlag()
				&& perception.hasEndPoint()
				|| agent.hasVeryLowLife()
				|| agent.hasFatigue()
				|| perception.hasEnemy()
				|| (agent.getPos().getMapPosition()
						.isSamePosition(perception.getPosition()) && perception
						.hasFire())
				|| (agent.getPos().getMapPosition()
						.isSamePosition(perception.getPosition()) && perception
						.hasExtremeWeather());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * pt.tecnico.aasma.wireflag.agent.architecture.Architecture#makeAction(
	 * pt.tecnico.aasma.wireflag.agent.Agent, int)
	 */
	public void makeAction(Agent agent, int delta) {
		List<Perception> perceptions = agent.getPerceptions();

		for (Perception perception : perceptions) {
			if (isUrgentPerception(agent, perception)) {
				reactiveLayer.makeAction(agent, delta);
				/* updates the deliberative architecture's state */
				deliberativeLayer.getBeliefs().updateBeliefs();
			} else {
				deliberativeLayer.makeAction(agent, delta);
			}
		}
	}
}
