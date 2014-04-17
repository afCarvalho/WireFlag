package pt.tecnico.aasma.wireflag.environment.controller;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.SlickException;

import pt.tecnico.aasma.wireflag.agent.Agent;
import pt.tecnico.aasma.wireflag.agent.team.AnarchicalTeam;
import pt.tecnico.aasma.wireflag.agent.team.DemocraticalTeam;
import pt.tecnico.aasma.wireflag.agent.team.HierarchicalTeam;
import pt.tecnico.aasma.wireflag.agent.team.Team;
import pt.tecnico.aasma.wireflag.exception.InvalidTeamSizeException;

public class TeamController {

	/** The list of the teams in game. */
	private List<Team> teams;

	/** The last team added. */
	private Team lastTeam;

	/** The current identifier counter used to generate new id's. */
	private int currentIdentifier;

	/**
	 * Instantiates a new TeamController in order to keep a record of all teams
	 * and the identifier generator.
	 */
	public TeamController() {
		this.teams = new ArrayList<Team>();
		this.lastTeam = null;
		this.currentIdentifier = 0;
	}

	/**
	 * Gets the list of teams.
	 * 
	 * @return the teams
	 */
	public final List<Team> getTeams() {
		return teams;
	}

	/**
	 * Adds the new team to the list of teams.
	 * 
	 * @param team
	 *            the team to be added
	 */
	public final void addTeam(Team team) {
		if (teams.contains(team)) {
			return;
		}
		teams.add(team);
		lastTeam = team;
	}

	/**
	 * Gets the current identifier counter.
	 * 
	 * @return the current identifier
	 */
	public final int getCurrentIdentifier() {
		return currentIdentifier;
	}

	/**
	 * Generate a new identifier by incrementing the last one.
	 * 
	 * @return the new identifier
	 */
	private int generateNewIdentifier() {
		return currentIdentifier++;
	}

	/**
	 * Creates a new anarchical team and assigns a new identifier.
	 * 
	 * @param leader
	 *            the team leader
	 * @param members
	 *            the team members
	 * @throws SlickException
	 * @throws InvalidTeamSizeException
	 */
	public void createAnarchicalTeam(Agent leader, List<Agent> members)
			throws InvalidTeamSizeException, SlickException {
		addTeam(new AnarchicalTeam(generateNewIdentifier(), leader, members));
	}

	/**
	 * Creates a new democratical team and assigns a new identifier.
	 * 
	 * @param leader
	 *            the team leader
	 * @param members
	 *            the team members
	 * @throws SlickException
	 * @throws InvalidTeamSizeException
	 */
	public void createDemocraticalTeam(Agent leader, List<Agent> members)
			throws InvalidTeamSizeException, SlickException {
		addTeam(new DemocraticalTeam(generateNewIdentifier(), leader, members));
	}

	/**
	 * Creates a new hierarchical team and assigns a new identifier.
	 * 
	 * @param leader
	 *            the team leader
	 * @param members
	 *            the team members
	 * @throws SlickException
	 * @throws InvalidTeamSizeException
	 */
	public void createHierarchicalTeam(Agent leader, List<Agent> members)
			throws InvalidTeamSizeException, SlickException {
		addTeam(new HierarchicalTeam(generateNewIdentifier(), leader, members));
	}
}
