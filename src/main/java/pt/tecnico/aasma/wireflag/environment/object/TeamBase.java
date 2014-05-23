package pt.tecnico.aasma.wireflag.environment.object;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import pt.tecnico.aasma.wireflag.IGameElement;
import pt.tecnico.aasma.wireflag.environment.controller.MapController;
import pt.tecnico.aasma.wireflag.util.AnimationLoader;
import pt.tecnico.aasma.wireflag.util.position.MapPosition;

public class TeamBase implements IGameElement {

	private Animation teamBase;
	private MapPosition basePos;
	private int teamId;

	public TeamBase(MapPosition basePos, int teamId) {
		teamBase = AnimationLoader.getLoader().getTeamBase();
		this.basePos = basePos;
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
		teamBase.draw(basePos.getX() * tileWidth, basePos.getY() * tileHeight);

		g.setColor(new Color(new Color(Color.white)));
		g.drawString("T:" + teamId,
				basePos.getX() * tileWidth + teamBase.getWidth()/4.0f,
				basePos.getY() * tileHeight + teamBase.getHeight());
	}

	@Override
	public void update(int delta) {
	}
}
