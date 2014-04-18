package pt.tecnico.aasma.wireflag.agent.type;

import pt.tecnico.aasma.wireflag.agent.Agent;
import pt.tecnico.aasma.wireflag.agent.architecture.Architecture;
import pt.tecnico.aasma.wireflag.util.AnimationLoader;

public class Soldier extends Agent {

	public Soldier(int teamId, Architecture arquitecture) {
		super(NORMALSPD, HIGHTATCK, teamId, arquitecture);
		up = AnimationLoader.getLoader().getSoldierUp();
		down = AnimationLoader.getLoader().getSoldierDown();
		right = AnimationLoader.getLoader().getSoldierRight();
		left = AnimationLoader.getLoader().getSoldierLeft();
		
		sprite = right;
	}

}
