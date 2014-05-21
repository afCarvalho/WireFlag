package pt.tecnico.aasma.wireflag.agent.type;

import pt.tecnico.aasma.wireflag.agent.Agent;
import pt.tecnico.aasma.wireflag.agent.architecture.Architecture;
import pt.tecnico.aasma.wireflag.agent.strategies.Strategy;
import pt.tecnico.aasma.wireflag.environment.controller.MapController;
import pt.tecnico.aasma.wireflag.util.AnimationLoader;
import pt.tecnico.aasma.wireflag.util.position.MapPosition;

public class Builder extends Agent {

	public Builder(int teamId, int agentId, Architecture arquitecture,
			Strategy strategy) {
		super(NORMALSPD, NORMALATCK, teamId, agentId, arquitecture, strategy);
		up = AnimationLoader.getLoader().getBuilderUp();
		down = AnimationLoader.getLoader().getBuilderDown();
		right = AnimationLoader.getLoader().getBuilderRight();
		left = AnimationLoader.getLoader().getBuilderLeft();

		sprite = right;
	}

	/************************
	 *** STATE PREDICATES ***
	 ************************/

	@Override
	public int habilityRate(int nInjured, int nTired, int nEnemy, boolean flag) {
		return 10 * nTired + 2 * nInjured;
	}

	/*
	 * returns true if its useful that this agent uses its ability at
	 * MapPosition pos
	 */
	@Override
	public boolean isAbilityUseful(MapPosition pos) {
		Agent agent = MapController.getMap().getLandscape(pos).getAgent();

		if (agent != null) {
			return getTeamId() == agent.getTeamId() && agent.hasFatigue();
		}
		return false;
	}

	/***********************
	 *** STATE MODIFIERS ***
	 ***********************/

	/* this agent use its ability at MapPosition pos */
	@Override
	public void useAbility(MapPosition pos) {
		ballon = AnimationLoader.getLoader().getStar();
		/*
		 * Note: agents must be obtained here before the verification (if),
		 * because of synchronization
		 */
		Agent ally = MapController.getMap().getLandscape(pos).getAgent();

		if (isAbilityUseful(pos)) {

			/*
			 * try { Thread.sleep(250); } catch (InterruptedException e) { //
			 * TODO Auto-generated catch block e.printStackTrace(); }
			 */

			ally.modifyFatigue(-FATIGUE_RECOVER);
		}
	}
}
