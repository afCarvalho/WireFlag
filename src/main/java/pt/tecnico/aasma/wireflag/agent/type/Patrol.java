package pt.tecnico.aasma.wireflag.agent.type;

import java.util.List;

import pt.tecnico.aasma.wireflag.agent.Agent;
import pt.tecnico.aasma.wireflag.agent.architecture.Architecture;
import pt.tecnico.aasma.wireflag.agent.strategies.Strategy;
import pt.tecnico.aasma.wireflag.environment.controller.AgentController;
import pt.tecnico.aasma.wireflag.environment.controller.MapController;
import pt.tecnico.aasma.wireflag.util.AnimationLoader;
import pt.tecnico.aasma.wireflag.util.position.MapPosition;

public class Patrol extends Agent {

	public Patrol(int teamId, int agentId, Architecture arquitecture,
			Strategy strategy) {
		super(HIGHSPD, LOWATCK, teamId, agentId, arquitecture, strategy);
		up = AnimationLoader.getLoader().getPatrolUp();
		down = AnimationLoader.getLoader().getPatrolDown();
		right = AnimationLoader.getLoader().getPatrolRight();
		left = AnimationLoader.getLoader().getPatrolLeft();

		sprite = right;
	}

	@Override
	public List<Agent> getNearTeammates() {
		List<Agent> agents = AgentController.getAgents()
				.getTeamById(getTeamId()).toList();
		agents.remove(this);
		return agents;
	}

	/************************
	 *** STATE PREDICATES ***
	 ************************/

	@Override
	public int habilityRate(int nInjured, int nTired, int nEnemy, boolean flag) {
		if (flag) {
			return 35;
		} else {
			return 0;
		}
	}

	/*
	 * returns true if its useful that this agent uses its ability at
	 * MapPosition pos
	 */
	@Override
	public boolean isAbilityUseful(MapPosition pos) {
		Agent agent = MapController.getMap().getLandscape(pos).getAgent();

		/*
		 * if (agent != null) { return getTeamId() != agent.getTeamId(); }
		 */

		return false;
	}

	/***********************
	 *** STATE MODIFIERS ***
	 ***********************/

	/* this agent use its ability at MapPosition pos */
	@Override
	public void useAbility(MapPosition pos) {
		ballon = AnimationLoader.getLoader().getStar();
		// TODO se precisar de aceder a algum agente deve-se
		// obte-los aqui antes do if por causa da sincronizacao

		// if (isAbilityUseful(pos)) {
		// TODO comunica a todos os agentes da equipa que viu o enemy
		// }
	}
}
