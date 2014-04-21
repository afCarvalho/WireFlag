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
import pt.tecnico.aasma.wireflag.exception.InvalidTeamSizeException;

public class AgentController implements IController {

	private int actualTeamId;
	private List<Team> teams = new ArrayList<Team>();
	private static final AgentController INSTANCE = new AgentController();

	private AgentController() {
		actualTeamId = 0;
	}

	/***************
	 *** GETTERS ***
	 ***************/

	public static AgentController getAgents() {
		return INSTANCE;
	}

	public int getNextTeamId() {
		return actualTeamId++;
	}

	public List<Team> getTeams() {
		return teams;
	}

	/***********************
	 *** STATE MODIFIERS ***
	 **********************/

	public void addTeam(Team team) {
		teams.add(team);
	}

	/**********************
	 *** GAME RELATED *****
	 **********************/

	@Override
	public void init() throws SlickException, InvalidTeamSizeException {

		Team t1 = new DemocraticalTeam(getNextTeamId());
		Agent d1 = new Doctor(t1.getID(), t1.getMemberID(), new Reactive());
		Agent b1 = new Builder(t1.getID(), t1.getMemberID(), new Reactive());
		Agent p1 = new Patrol(t1.getID(), t1.getMemberID(), new Reactive());
		Agent s1 = new Soldier(t1.getID(), t1.getMemberID(), new Reactive());
		t1.addAgent(b1);
		t1.addAgent(d1);
		t1.addAgent(p1);
		t1.addAgent(s1);
		t1.setTeamUp();
		addTeam(t1);

		Team t2 = new DemocraticalTeam(getNextTeamId());
		Agent d2 = new Doctor(t2.getID(), t2.getMemberID(), new Reactive());
		Agent b2 = new Builder(t2.getID(), t2.getMemberID(), new Reactive());
		Agent p2 = new Patrol(t2.getID(), t2.getMemberID(), new Reactive());
		Agent s2 = new Soldier(t2.getID(), t2.getMemberID(), new Reactive());
		t2.addAgent(b2);
		t2.addAgent(d2);
		t2.addAgent(p2);
		t2.addAgent(s2);
		t2.setTeamUp();
		addTeam(t2);

		/*
		 * Agent leader = agentController.getAgents().get(0); List<Agent>
		 * members = agentController.getAgents().subList(1,
		 * agentController.getAgents().size());
		 * 
		 * try { teamController.createDemocraticalTeam(leader, members); Team
		 * team = teamController.getTeams().get(0);
		 * 
		 * MapController.getMap().getLandscape(team.getTeamPosition())
		 * .setAgent(team.getLeader()); for (Agent agent : team.getMembers()) {
		 * MapController.getMap().getLandscape(team.getTeamPosition())
		 * .setAgent(agent); } } catch (InvalidTeamSizeException e1) {
		 * e1.printStackTrace(); }
		 */

	}

	public void update(int delta) {
	}

	@Override
	public void render(Graphics g) {
	}

}
