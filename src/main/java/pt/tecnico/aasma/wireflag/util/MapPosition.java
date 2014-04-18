package pt.tecnico.aasma.wireflag.util;

/* Position dividing the map in tiles :41x23 */
public class MapPosition {

	private int x;
	private int y;

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public MapPosition(int x, int y) {
		this.setX(x);
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	/* returns true if the position, pos, is the left position of this position */
	public boolean isCloseOnLeft(MapPosition pos) {
		return (this.x - 1 == pos.getX()) && (this.y == pos.getY());
	}

	/* returns true if the position, pos, is the right position of this position */
	public boolean isCloseOnRight(MapPosition pos) {
		return (this.x + 1 == pos.getX()) && (this.y == pos.getY());
	}

	/* returns true if the position, pos, is right ahead this position */
	public boolean isJustAhead(MapPosition pos) {
		return (this.x == pos.getX()) && (this.y + 1 == pos.getY());
	}

	/* returns true if the position, pos, is right behind this position */
	public boolean isJustBehind(MapPosition pos) {
		return (this.x == pos.getX()) && (this.y - 1 == pos.getY());
	}

	/* returns true if the position, pos, is a left position of this position */
	public boolean isLeft(MapPosition pos) {
		return this.x > pos.getX();
	}

	/* returns true if the position, pos, is a right position of this position */
	public boolean isRight(MapPosition pos) {
		return this.x < pos.getX();
	}

	/* returns true if the position, pos, is ahead this position */
	public boolean isAhead(MapPosition pos) {
		return this.y < pos.getY();
	}

	/* returns true if the position, pos, is behind this position */
	public boolean isBehind(MapPosition pos) {
		return this.y > pos.getY();
	}
}
