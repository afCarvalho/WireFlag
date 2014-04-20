package pt.tecnico.aasma.wireflag.agent.team;

import java.util.List;

import org.newdawn.slick.SlickException;

import pt.tecnico.aasma.wireflag.agent.Agent;
import pt.tecnico.aasma.wireflag.exception.InvalidTeamSizeException;

public class AnarchicalTeam extends Team {

	public AnarchicalTeam(int identifier)
			throws InvalidTeamSizeException, SlickException {
		super(identifier);
	}

	@Override
	protected void electLeader() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean vote() {
		// TODO Auto-generated method stub
		return false;
	}
}
