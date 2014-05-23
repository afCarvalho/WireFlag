package pt.tecnico.aasma.wireflag.environment.object;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
	private List<Fire> fires;

	public TeamBase(MapPosition basePos, int teamId) {
		teamBase = AnimationLoader.getLoader().getTeamBase();
		this.basePos = basePos;
		this.teamId = teamId;
		fires = new ArrayList<Fire>();
	}

	public int getTeamId() {
		return teamId;
	}

	public boolean isOnFire() {
		return !fires.isEmpty();
	}

	public void turnOnFire() {
		int x = basePos.getX();
		int y = basePos.getY();
		fires = new ArrayList<Fire>();
		MapPosition firePos;
		Fire fire;

		for (int j = y + 1; j >= y - 1; j--) {
			for (int i = x - 1; i <= x + 1; i++) {
				if (j < MapController.getMap().getNVerticalTiles()
						&& i < MapController.getMap().getNHorizontalTiles()
						&& j > 0 && i > 0) {

					firePos = new MapPosition(i, j);
					fire = new Fire(10000, firePos);
					fires.add(fire);
					MapController.getMap().getLandscape(firePos).setFire(fire);
				}
			}
		}
	}

	public void turnOffFire() {
		fires = new ArrayList<Fire>();
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
				basePos.getX() * tileWidth + teamBase.getWidth() / 4.0f,
				basePos.getY() * tileHeight + teamBase.getHeight());
	}

	@Override
	public void update(int delta) {
		if (!fires.isEmpty()) {
			if (new Random().nextInt(4) == 0) {
				for (Fire fire : fires) {
					fire.incDuration(10000);
				}
			}
		}
	}
}
