package pt.tecnico.aasma.wireflag.environment.controller;

import java.awt.Font;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.Rectangle;

import pt.tecnico.aasma.wireflag.WireFlagGame;
import pt.tecnico.aasma.wireflag.agent.Agent;
import pt.tecnico.aasma.wireflag.agent.team.Team;
import pt.tecnico.aasma.wireflag.util.AnimationLoader;
import pt.tecnico.aasma.wireflag.util.WorldPosition;

public class EndGameController implements IController {

	private static final EndGameController INSTANCE = new EndGameController();
	private int winnerTeam;
	Font font = new Font("Verdana", Font.BOLD, 32);
	TrueTypeFont ttf = new TrueTypeFont(font, true);
	private int nAliveAgents;
	private int endDays;
	private int endHours;
	private int endMinutes;
	private int endSeconds;
	private boolean gameFinished;

	private EndGameController() {
	}

	/***************
	 *** GETTERS ***
	 ***************/

	public static EndGameController getEnd() {
		return INSTANCE;
	}

	public boolean getGameFinished() {
		return gameFinished;
	}

	/**********************
	 *** STATE MODIFIERS **
	 **********************/

	public void increaseNAliveAgents() {
		this.nAliveAgents++;
	}

	public void decreaseNAliveAgents() {
		this.nAliveAgents--;

		if (nAliveAgents == 0) {
			WireFlagGame.win(-1);
		}

		int winnerTeam = -1;
		for (Team team : AgentController.getAgents().getTeams()) {
			for (Agent a : team.getMembers()) {
				if (a.isAlive() && winnerTeam == -1) {
					winnerTeam = a.getTeamId();
				}

				if (a.isAlive() && winnerTeam != a.getTeamId()) {
					return;
				}
			}
		}

		System.out.println("ALTERNATIVE WAY");
		WireFlagGame.win(winnerTeam);
	}

	public void endGame(int winnerTeam) {
		gameFinished = true;
		this.winnerTeam = winnerTeam;
		int tileWidth = MapController.getMap().getTileWidth();
		int tileHeight = MapController.getMap().getTileWidth();
		int x = 15 * tileWidth;
		int y = 4 * tileHeight;

		endDays = TimeController.getTime().getDays();
		endHours = TimeController.getTime().getHours();
		endMinutes = TimeController.getTime().getMinutes();
		endSeconds = TimeController.getTime().getSeconds();
		TimeController.getTime().setHours(0);

		for (Team team : AgentController.getAgents().getTeams()) {
			for (Agent agent : team.getMembers()) {
				agent.setPos(new WorldPosition(x += 3 * tileWidth, y));
				MapController.getMap().getLandscape(new WorldPosition(x, y))
						.setVisibility(-1);
			}
			y += 5 * tileHeight;
			x = 15 * tileWidth;
		}
	}

	/**********************
	 *** GAME RELATED *****
	 **********************/

	@Override
	public void render(Graphics g) {
		int x = 20 * MapController.getMap().getTileWidth();
		int y = 1 * MapController.getMap().getTileWidth();

		g.setColor(new Color(0f, 0f, 0f, 0f));
		Rectangle r = new Rectangle(0, 0, MapController.getMap().getMapWidth(),
				MapController.getMap().getMapHeight());
		g.draw(r);
		g.fill(r);

		for (Team team : AgentController.getAgents().getTeams()) {
			g.setColor(new Color(1f, 1f, 1f, 1f));
			if (team.getID() == winnerTeam) {
				ttf.drawString(x, y, "Team " + team.getID() + " Won",
						Color.green);
			} else {
				ttf.drawString(x, y, "Team " + team.getID() + " Lost",
						Color.red);
			}

			y += 5 * MapController.getMap().getTileWidth();

			for (Agent agent : team.getMembers()) {
				agent.moveFlag();
				agent.render(g);
				WorldPosition agentPos = agent.getPos();
				if (!agent.isAlive()) {
					AnimationLoader.getLoader().getCross()
							.draw(agentPos.getX(), agentPos.getY());
				}
			}
		}

		ttf.drawString(12 * MapController.getMap().getTileWidth(), y,
				"Duration: " + endDays + " days " + endHours + " hours "
						+ endMinutes + " minutes " + endSeconds + " seconds",
				Color.lightGray);

		y += 5 * MapController.getMap().getTileWidth();

		ttf.drawString(12 * MapController.getMap().getTileWidth(), y,
				"Done by:", Color.lightGray);
		ttf.drawString(18 * MapController.getMap().getTileWidth(), y,
				"Jose Cavalheiro ", Color.lightGray);
		y += 1 * MapController.getMap().getTileWidth();
		ttf.drawString(18 * MapController.getMap().getTileWidth(), y,
				"Alberto Carvalho ", Color.lightGray);
		y += 1 * MapController.getMap().getTileWidth();
		ttf.drawString(18 * MapController.getMap().getTileWidth(), y,
				"David Duarte ", Color.lightGray);
	}

	@Override
	public void update(int delta) throws SlickException {
	}

	@Override
	public void init() throws SlickException {
	}

}
