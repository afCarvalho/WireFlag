package pt.tecnico.aasma.wireflag.environment.controller;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

import pt.tecnico.aasma.wireflag.GameElement;
import pt.tecnico.aasma.wireflag.agent.Agent;
import pt.tecnico.aasma.wireflag.agent.team.Team;
import pt.tecnico.aasma.wireflag.agent.type.Builder;

public class AgentController implements GameElement {

	private int actualTeamId;

	private List<Team> teams = new ArrayList<Team>();

	public AgentController() {
		actualTeamId = 0;
	}

	public int getNextTeamId(Agent agent) {
		return actualTeamId++;
	}
	
	public Team getTeam(int teamId){
		for (Team team : teams) {
			if(team.getId() == teamId){
				return team;
			}
		}
		return null;
	}

	public void addTeam(Team team) {
		teams.add(team);
	}

	@Override
	public void init() {

	}

	public void update(int delta) {

	}

	@Override
	public void render(Graphics g) {

	}

}
