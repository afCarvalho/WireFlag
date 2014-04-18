package pt.tecnico.aasma.wireflag.agent.type;

import pt.tecnico.aasma.wireflag.agent.Agent;
import pt.tecnico.aasma.wireflag.agent.architecture.Architecture;
import pt.tecnico.aasma.wireflag.util.AnimationLoader;

public class Patrol extends Agent {

	public Patrol(int teamId, Architecture arquitecture) {
		super(HIGHSPD, LOWATCK, teamId, arquitecture);
		up = AnimationLoader.getLoader().getPatrolUp();
		down = AnimationLoader.getLoader().getPatrolDown();
		right = AnimationLoader.getLoader().getPatrolRight();
		left = AnimationLoader.getLoader().getPatrolLeft();
		
		sprite = right;
	}

}
