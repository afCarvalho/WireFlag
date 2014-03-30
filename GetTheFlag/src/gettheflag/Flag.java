package gettheflag;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Flag implements GameElement {

	private Animation flag;
	private int xCoord;
	private int yCoord;

	public Flag() {

	}

	public void update() {

	}

	@Override
	public void init() throws SlickException {

		flag = new Animation(new Image[] { new Image("data/SmallFlag.png") },
				new int[] { 300 }, false);

	}

	@Override
	public void render(Graphics g) {

		flag.draw(xCoord * 1.0f, yCoord * 1.0f);

	}

	public void setCoords(int xCoord, int yCoord) {
		this.xCoord = xCoord;
		this.yCoord = yCoord;
	}

}
