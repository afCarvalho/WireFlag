package gettheflag;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public interface GameElement {

	public void init() throws SlickException;

	public void render(Graphics g);

}
