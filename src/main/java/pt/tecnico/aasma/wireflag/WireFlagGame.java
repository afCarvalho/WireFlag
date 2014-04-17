package pt.tecnico.aasma.wireflag;

import java.util.List;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import pt.tecnico.aasma.wireflag.agent.Agent;
import pt.tecnico.aasma.wireflag.agent.team.Team;
import pt.tecnico.aasma.wireflag.environment.controller.AgentController;
import pt.tecnico.aasma.wireflag.environment.controller.ClimateController;
import pt.tecnico.aasma.wireflag.environment.controller.MapController;
import pt.tecnico.aasma.wireflag.environment.controller.TeamController;
import pt.tecnico.aasma.wireflag.environment.controller.TimeController;
import pt.tecnico.aasma.wireflag.exception.InvalidTeamSizeException;

public class WireFlagGame extends BasicGame {

	private GameElement[] elements;

	public WireFlagGame() {
		super("WireFlag");
	}

	public static void main(String[] arguments) {
		try {
			int maxFPS = 100;
			AppGameContainer app = new AppGameContainer(new WireFlagGame());
			app.setDisplayMode(1400, 800, false);
			app.setTargetFrameRate(maxFPS);
			// app.setVSync(true);
			app.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void init(GameContainer container) throws SlickException {
		TeamController teamController = new TeamController();
		AgentController agentController = new AgentController();
		agentController.init();

		elements = new GameElement[] { MapController.getMap(),
				new ClimateController(), new TimeController(), agentController };

		Agent leader = agentController.getAgents().get(0);
		List<Agent> members = agentController.getAgents().subList(1,
				agentController.getAgents().size());
		
		try {
			teamController.createDemocraticalTeam(leader, members);
			Team team = teamController.getTeams().get(0);
			
			MapController.getMap().getLandscape(team.getTeamPosition())
			.setAgent(team.getLeader());
			for (Agent agent : team.getMembers()) {
				MapController.getMap().getLandscape(team.getTeamPosition())
				.setAgent(agent);
			}
		} catch (InvalidTeamSizeException e1) {
			e1.printStackTrace();
		}

		for (int i = 0; i < elements.length - 1; i++) {
			elements[i].init();
		}


	}

	@Override
	public void update(GameContainer container, int delta)
			throws SlickException {

		for (GameElement e : elements) {
			e.update(delta);
		}
	}

	@Override
	public void render(GameContainer container, Graphics g)
			throws SlickException {

		for (GameElement e : elements) {
			e.render(g);
		}
	}
}