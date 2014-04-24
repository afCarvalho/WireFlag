package pt.tecnico.aasma.wireflag.agent.type;

import pt.tecnico.aasma.wireflag.agent.Agent;
import pt.tecnico.aasma.wireflag.agent.architecture.Architecture;
import pt.tecnico.aasma.wireflag.environment.controller.MapController;
import pt.tecnico.aasma.wireflag.util.AnimationLoader;
import pt.tecnico.aasma.wireflag.util.MapPosition;

public class Doctor extends Agent {

	public Doctor(int teamId, int agentId, Architecture arquitecture) {
		super(NORMALSPD, LOWATCK, teamId, agentId, arquitecture);
		up = AnimationLoader.getLoader().getDoctorUp();
		down = AnimationLoader.getLoader().getDoctorDown();
		right = AnimationLoader.getLoader().getDoctorRight();
		left = AnimationLoader.getLoader().getDoctorLeft();

		sprite = right;
	}

	@Override
	public int habilityRate(int nInjured, int nTired, int nEnemy, boolean flag) {
		return 10 * nInjured;
	}

	/*
	 * returns true if its useful that this agent uses its ability at
	 * MapPosition pos
	 */
	@Override
	public boolean isAbilityUseful(MapPosition pos) {
		Agent agent = MapController.getMap().getLandscape(pos).getAgent();

		if (agent != null) {
			return getTeamId() == agent.getTeamId() && agent.hasLowLife();
		}
		return false;
	}

	/* this agent use its ability at MapPosition pos */
	@Override
	public void useAbility(MapPosition pos) {
		if (isAbilityUseful(pos)) {
			Agent ally = MapController.getMap().getLandscape(pos).getAgent();
			ally.increaseLife(LIFE_RECOVER);
		}
	}
}
