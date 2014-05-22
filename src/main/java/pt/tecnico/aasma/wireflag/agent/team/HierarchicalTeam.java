package pt.tecnico.aasma.wireflag.agent.team;

import java.io.IOException;

import org.newdawn.slick.SlickException;

import pt.tecnico.aasma.wireflag.exception.InvalidTeamSizeException;

public class HierarchicalTeam extends Team {

	public HierarchicalTeam(int identifier)
			throws InvalidTeamSizeException, SlickException, IOException {
		super(identifier);
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
