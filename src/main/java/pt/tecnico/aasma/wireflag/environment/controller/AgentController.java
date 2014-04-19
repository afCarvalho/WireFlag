package pt.tecnico.aasma.wireflag.environment.controller;

import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.Rectangle;

import pt.tecnico.aasma.wireflag.agent.Agent;
import pt.tecnico.aasma.wireflag.agent.architecture.Reactive;
import pt.tecnico.aasma.wireflag.agent.team.DemocraticalTeam;
import pt.tecnico.aasma.wireflag.agent.team.Team;
import pt.tecnico.aasma.wireflag.agent.type.Builder;
import pt.tecnico.aasma.wireflag.agent.type.Doctor;
import pt.tecnico.aasma.wireflag.agent.type.Patrol;
import pt.tecnico.aasma.wireflag.agent.type.Soldier;
import pt.tecnico.aasma.wireflag.util.AnimationLoader;
import pt.tecnico.aasma.wireflag.util.MapPosition;
import pt.tecnico.aasma.wireflag.util.WorldPosition;

public class AgentController implements IController {

	private int actualTeamId;
	private boolean endGame;
	private int winnerTeam;
	Font font = new Font("Verdana", Font.BOLD, 32);
	TrueTypeFont ttf = new TrueTypeFont(font, true);

	private List<Team> teams = new ArrayList<Team>();
	private static final AgentController INSTANCE = new AgentController();

	private AgentController() {
		actualTeamId = 0;
		endGame = false;
	}

	public static AgentController getAgents() {
		return INSTANCE;
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
		int teamSize = 5;

		Team team1 = new DemocraticalTeam(getNextTeamId());
		team1.init(teamSize);
		Agent doctor1 = new Doctor(team1.getId(), new Reactive());
		Agent builder1 = new Builder(team1.getId(), new Reactive());
		Agent patrol1 = new Patrol(team1.getId(), new Reactive());
		Agent soldier1 = new Soldier(team1.getId(), new Reactive());
		//team1.addAgent(builder1);
		//team1.addAgent(doctor1);
		team1.addAgent(patrol1);
		//team1.addAgent(soldier1);
		addTeam(team1);

		/*Team team2 = new DemocraticalTeam(getNextTeamId());
		team2.init(teamSize);
		Agent doctor2 = new Doctor(team1.getId(), new Reactive());
		Agent builder2 = new Builder(team1.getId(), new Reactive());
		Agent patrol2 = new Patrol(team1.getId(), new Reactive());
		Agent soldier2 = new Soldier(team1.getId(), new Reactive());
		team2.addAgent(builder2);
		team2.addAgent(doctor2);
		team2.addAgent(patrol2);
		team2.addAgent(soldier2);
		addTeam(team2);*/

	}

	public boolean isEndGame() {
		return endGame;
	}

	public void endGame(int winnerTeam) {
		this.endGame = true;
		this.winnerTeam = winnerTeam;
		int tileWidth = MapController.getMap().getTileWidth();
		int tileHeight = MapController.getMap().getTileWidth();
		int x = 20 * tileWidth;
		int y = 4 * tileHeight;

		for (Team team : teams) {
			for (Agent agent : team.getAgents()) {
				agent.setPos(new WorldPosition(x += tileWidth, y));
			}
			y += y;
			x = 20 * tileWidth;
		}
	}

	public void update(int delta) {
	}

	@Override
	public void render(Graphics g) {
		if (isEndGame()) {
			int x = 20 * MapController.getMap().getTileWidth();
			int y = 1 * MapController.getMap().getTileWidth();

			g.setColor(new Color(1f, 1f, 1f, 1f));
			Rectangle r = new Rectangle(0, 0, MapController.getMap()
					.getMapWidth(), MapController.getMap().getMapHeight());
			g.draw(r);
			g.fill(r);

			for (Team team : teams) {
				g.setColor(new Color(1f, 1f, 1f, 1f));
				if (team.getId() == winnerTeam) {
					ttf.drawString(x, y, "Team " + team.getId() + " Won",
							Color.green);
				} else {
					ttf.drawString(x, y, "Team " + team.getId() + " Lost",
							Color.red);
				}

				y += 5 * y;

				for (Agent agent : team.getAgents()) {
					agent.render(g);
					WorldPosition agentPos = agent.getPos();
					if (!agent.isAlive()) {
						AnimationLoader.getLoader().getCross()
								.draw(agentPos.getX(), agentPos.getY());
					}
				}
			}
		}
	}

}
