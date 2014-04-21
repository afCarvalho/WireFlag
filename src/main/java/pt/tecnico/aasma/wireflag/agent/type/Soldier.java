package pt.tecnico.aasma.wireflag.agent.type;

import pt.tecnico.aasma.wireflag.agent.Agent;
import pt.tecnico.aasma.wireflag.agent.architecture.Architecture;
import pt.tecnico.aasma.wireflag.util.AnimationLoader;

public class Soldier extends Agent {

	public Soldier(int teamId, int agentId, Architecture arquitecture) {
		super(NORMALSPD, HIGHTATCK, teamId, agentId, arquitecture);
		up = AnimationLoader.getLoader().getSoldierUp();
		down = AnimationLoader.getLoader().getSoldierDown();
		right = AnimationLoader.getLoader().getSoldierRight();
		left = AnimationLoader.getLoader().getSoldierLeft();

		sprite = right;
	}

	@Override
	public int habilityRate(int nInjured, int nTired, int nEnemy, boolean flag) {
		return 10 * nEnemy;
	}
}
