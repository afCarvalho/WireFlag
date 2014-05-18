package pt.tecnico.aasma.wireflag.environment.object;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import pt.tecnico.aasma.wireflag.IGameElement;
import pt.tecnico.aasma.wireflag.util.AnimationLoader;
import pt.tecnico.aasma.wireflag.util.position.WorldPosition;

public class Flag implements IGameElement {

	private Animation flag;
	private WorldPosition flagPos;

	public Flag(WorldPosition flagPosition) {
		flag = AnimationLoader.getLoader().getFlag();
		flagPos = flagPosition;
	}

	/***************
	 *** SETTERS ***
	 ***************/

	public void setFlagPos(WorldPosition flagPos) {
		this.flagPos = flagPos;
	}

	/*********************
	 *** GAME RELATED ****
	 *********************/

	public void draw(float x, float y) {
		flag.draw(x, y);
	}

	public void render(Graphics g) {
		flag.draw(flagPos.getX(), flagPos.getY());
	}

	@Override
	public void update(int delta) throws SlickException {
	}
}
