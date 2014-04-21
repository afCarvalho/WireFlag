package pt.tecnico.aasma.wireflag.agent.type;

import pt.tecnico.aasma.wireflag.agent.Agent;
import pt.tecnico.aasma.wireflag.agent.architecture.Architecture;
import pt.tecnico.aasma.wireflag.util.AnimationLoader;

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

}
