package pt.tecnico.aasma.wireflag.environment.object;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;

import pt.tecnico.aasma.wireflag.IGameElement;
import pt.tecnico.aasma.wireflag.environment.controller.MapController;
import pt.tecnico.aasma.wireflag.util.AnimationLoader;
import pt.tecnico.aasma.wireflag.util.position.MapPosition;

public class TeamBase implements IGameElement {

	private Animation teamBase;
	private MapPosition endPos;
	private int teamId;

	public TeamBase(MapPosition endPos, int teamId) {
		teamBase = AnimationLoader.getLoader().getTeamBase();
		this.endPos = endPos;
		this.teamId = teamId;
	}

	public int getTeamId() {
		return teamId;
	}

	/*********************
	 *** GAME RELATED ****
	 *********************/

	public void render(Graphics g) {
		int tileWidth = MapController.getMap().getTileWidth();
		int tileHeight = MapController.getMap().getTileHeight();
		teamBase.draw(endPos.getX() * tileWidth, endPos.getY() * tileHeight);
	}

	@Override
	public void update(int delta) {
	}
}
