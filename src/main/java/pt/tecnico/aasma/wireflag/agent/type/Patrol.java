package pt.tecnico.aasma.wireflag.agent.type;

import pt.tecnico.aasma.wireflag.agent.Agent;
import pt.tecnico.aasma.wireflag.agent.architecture.Architecture;
import pt.tecnico.aasma.wireflag.util.AnimationLoader;

public class Patrol extends Agent {

	public Patrol(int teamId, int agentId, Architecture arquitecture) {
		super(HIGHSPD, LOWATCK, teamId, agentId, arquitecture);
		up = AnimationLoader.getLoader().getPatrolUp();
		down = AnimationLoader.getLoader().getPatrolDown();
		right = AnimationLoader.getLoader().getPatrolRight();
		left = AnimationLoader.getLoader().getPatrolLeft();

		sprite = right;
	}

	@Override
	public int habilityRate(int nInjured, int nTired, int nEnemy, boolean flag) {
		if (flag) {
			return 35;
		} else {
			return 0;
		}
	}
}
