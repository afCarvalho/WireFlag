package pt.tecnico.aasma.wireflag.environment.controller;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import pt.tecnico.aasma.wireflag.GameElement;
import pt.tecnico.aasma.wireflag.agent.Agent;
import pt.tecnico.aasma.wireflag.agent.architecture.Reactive;
import pt.tecnico.aasma.wireflag.agent.team.DemocraticalTeam;
import pt.tecnico.aasma.wireflag.agent.team.Team;
import pt.tecnico.aasma.wireflag.agent.type.Builder;
import pt.tecnico.aasma.wireflag.util.MapPosition;

public class AgentController implements GameElement {

	private int actualTeamId;

	private List<Team> teams = new ArrayList<Team>();

	public AgentController() {
		actualTeamId = 0;
	}

	public int getNextTeamId() {
		return actualTeamId++;
	}

	public Team getTeam(int teamId) {
		for (Team team : teams) {
			if (team.getId() == teamId) {
				return team;
			}
		}
		return null;
	}

	public void addTeam(Team team) {
		teams.add(team);
	}

	@Override
	public void init() throws SlickException {
		int teamSize = 1;
		Team team1 = new DemocraticalTeam(getNextTeamId());
		team1.init(teamSize);

		Agent builder1 = new Builder(team1.getId(), new Reactive());
		builder1.init(); // put builde1 in team1's start point
		MapController.getMap().getLandscape(team1.getTeamPosition())
				.setAgent(builder1);
		team1.addAgent(builder1);

		addTeam(team1);

	}

	public void update(int delta) {

		/* if an agent is dead is removed from the team and from the map */
		for (Team team : teams) {
			for (Agent agent : team.getAgents()) {
				if (!agent.isAlive()) {
					team.removeAgent(agent);
					MapPosition agentPos = agent.getPos().getMapPosition();
					MapController.getMap().getLandscape(agentPos)
							.setAgent(null);
				}
			}
		}
	}

	@Override
	public void render(Graphics g) {

	}

}
