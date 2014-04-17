package pt.tecnico.aasma.wireflag.agent.team;

import java.util.List;

import org.newdawn.slick.SlickException;

import pt.tecnico.aasma.wireflag.agent.Agent;
import pt.tecnico.aasma.wireflag.exception.InvalidTeamSizeException;

public class DemocraticalTeam extends Team {

	public DemocraticalTeam(String identifier, Agent leader, List<Agent> members)
			throws InvalidTeamSizeException, SlickException {
		super(identifier, leader, members);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void electLeader() {
		// TODO Auto-generated method stub

	}

	@Override
	protected boolean vote() {
		// TODO Auto-generated method stub
		return false;
	}

}
