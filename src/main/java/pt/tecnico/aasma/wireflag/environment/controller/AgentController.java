package pt.tecnico.aasma.wireflag.environment.controller;

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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

	/**
	 * FIXME: If TeamController is going to be use, this method should go there.
	 * 
	 * Gets the team matching the id.
	 * 
	 * @param id
	 *            the ID of the team to be retrieved
	 * @return the actual team or null if not founds
	 */
	public Team getTeamById(int id) {
		for (Team team : teams) {
			if (team.getID() == id) {
				return team;
			}
		}
		return null;
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

		Team t1;
		try {
			t1 = new DemocraticalTeam(getNextTeamId());
			t1.addAgent(new Doctor(t1.getID(), t1.getMemberID(), new Reactive()));
			t1.addAgent(new Builder(t1.getID(), t1.getMemberID(), new Reactive()));
			t1.addAgent(new Patrol(t1.getID(), t1.getMemberID(), new Reactive()));
			t1.addAgent(new Soldier(t1.getID(), t1.getMemberID(), new Reactive()));
			t1.setTeamUp();
			addTeam(t1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			Team t2 = new DemocraticalTeam(getNextTeamId());
			t2.addAgent(new Doctor(t2.getID(), t2.getMemberID(), new Reactive()));
			t2.addAgent(new Builder(t2.getID(), t2.getMemberID(), new Reactive()));
			t2.addAgent(new Patrol(t2.getID(), t2.getMemberID(), new Reactive()));
			t2.addAgent(new Soldier(t2.getID(), t2.getMemberID(), new Reactive()));
			t2.setTeamUp();
			addTeam(t2);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			Team t3 = new DemocraticalTeam(getNextTeamId());
			t3.addAgent(new Doctor(t3.getID(), t3.getMemberID(), new Reactive()));
			t3.addAgent(new Builder(t3.getID(), t3.getMemberID(), new Reactive()));
			t3.addAgent(new Patrol(t3.getID(), t3.getMemberID(), new Reactive()));
			t3.addAgent(new Soldier(t3.getID(), t3.getMemberID(), new Reactive()));
			t3.setTeamUp();
			addTeam(t3);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			Team t4 = new DemocraticalTeam(getNextTeamId());
			t4.addAgent(new Doctor(t4.getID(), t4.getMemberID(), new Reactive()));
			t4.addAgent(new Builder(t4.getID(), t4.getMemberID(), new Reactive()));
			t4.addAgent(new Patrol(t4.getID(), t4.getMemberID(), new Reactive()));
			t4.addAgent(new Soldier(t4.getID(), t4.getMemberID(), new Reactive()));
			t4.setTeamUp();
			addTeam(t4);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (Team t : getTeams()) {
			for (Agent a : t.getMembers()) {
				a.init();
			}
		}

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
		for (Team t : getTeams()) {
			for (Agent a : t.getMembers()) {
				a.getAgentThread().setDelta(delta);
			}
		}
	}

	@Override
	public void render(Graphics g) {
	}
}
