package pt.tecnico.aasma.wireflag;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public interface IGameElement {
	public void render(Graphics g);
	public void update(int delta) throws SlickException;

}
