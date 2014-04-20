package pt.tecnico.aasma.wireflag.environment.controller;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import pt.tecnico.aasma.wireflag.agent.Agent;
import pt.tecnico.aasma.wireflag.agent.architecture.Reactive;
import pt.tecnico.aasma.wireflag.agent.team.DemocraticalTeam;
import pt.tecnico.aasma.wireflag.agent.team.Team;
import pt.tecnico.aasma.wireflag.agent.type.Builder;
import pt.tecnico.aasma.wireflag.agent.type.Doctor;
import pt.tecnico.aasma.wireflag.agent.type.Patrol;
import pt.tecnico.aasma.wireflag.agent.type.Soldier;

public class AgentController implements IController {

	private int actualTeamId;
	private List<Team> teams = new ArrayList<Team>();
	private static final AgentController INSTANCE = new AgentController();

	private AgentController() {
		actualTeamId = 0;
	}

	public static AgentController getAgents() {
		return INSTANCE;
	}

	public int getNextTeamId() {
		return actualTeamId++;
	}

	public List<Team> getTeams() {
		return teams;
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
		int teamSize = 5;

		Team team1 = new DemocraticalTeam(getNextTeamId());
		team1.init(teamSize);
		Agent doctor1 = new Doctor(team1.getId(), 0, new Reactive());
		Agent builder1 = new Builder(team1.getId(), 1, new Reactive());
		Agent patrol1 = new Patrol(team1.getId(), 2, new Reactive());
		Agent soldier1 = new Soldier(team1.getId(), 3, new Reactive());
		team1.addAgent(builder1);
		team1.addAgent(doctor1);
		team1.addAgent(patrol1);
		team1.addAgent(soldier1);
		addTeam(team1);

		Team team2 = new DemocraticalTeam(getNextTeamId());
		team2.init(teamSize);
		Agent doctor2 = new Doctor(team2.getId(), 0, new Reactive());
		Agent builder2 = new Builder(team2.getId(), 1, new Reactive());
		Agent patrol2 = new Patrol(team2.getId(), 2, new Reactive());
		Agent soldier2 = new Soldier(team2.getId(), 3, new Reactive());
		team2.addAgent(builder2);
		team2.addAgent(doctor2);
		team2.addAgent(patrol2);
		team2.addAgent(soldier2);
		addTeam(team2);
	}

	public void update(int delta) {
	}

	@Override
	public void render(Graphics g) {
	}

}
