package pt.tecnico.aasma.wireflag.util;

import pt.tecnico.aasma.wireflag.agent.Agent;

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

	public MapPosition getAhedPosition(int direction) {
		if (direction == Agent.DOWN) {
			return new MapPosition(getX(), getY() + 1);
		} else if (direction == Agent.UP) {
			return new MapPosition(getX(), getY() - 1);
		} else if (direction == Agent.RIGHT) {
			return new MapPosition(getX() + 1, getY());
		} else if (direction == Agent.LEFT) {
			return new MapPosition(getX() - 1, getY());
		} else {
			return new MapPosition(-1, -1);
		}

	}

	/* returns true if position, pos, is an adjacent position of this position */
	public boolean isAdjacentPosition(MapPosition pos, int direction) {
		return isJustAhead(pos, direction) || isJustBehind(pos, direction)
				|| isCloseOnLeft(pos, direction)
				|| isCloseOnRight(pos, direction);
	}

	/* returns true if position, pos, is the left position of this position */
	public boolean isCloseOnLeft(MapPosition pos, int direction) {

		if (direction == Agent.DOWN) {
			return (this.x + 1 == pos.getX()) && (this.y == pos.getY());
		} else if (direction == Agent.UP) {
			return (this.x - 1 == pos.getX()) && (this.y == pos.getY());
		} else if (direction == Agent.RIGHT) {
			return (this.x == pos.getX()) && (this.y - 1 == pos.getY());
		} else if (direction == Agent.LEFT) {
			return (this.x == pos.getX()) && (this.y + 1 == pos.getY());
		} else {
			return false;
		}
	}

	/* returns true if position, pos, is the right position of this position */
	public boolean isCloseOnRight(MapPosition pos, int direction) {

		if (direction == Agent.DOWN) {
			return (this.x - 1 == pos.getX()) && (this.y == pos.getY());
		} else if (direction == Agent.UP) {
			return (this.x + 1 == pos.getX()) && (this.y == pos.getY());
		} else if (direction == Agent.RIGHT) {
			return (this.x == pos.getX()) && (this.y + 1 == pos.getY());
		} else if (direction == Agent.LEFT) {
			return (this.x == pos.getX()) && (this.y - 1 == pos.getY());
		} else {
			return false;
		}
	}

	/* returns true if position, pos, is right ahead this position */
	public boolean isJustAhead(MapPosition pos, int direction) {

		if (direction == Agent.DOWN) {
			return (this.x == pos.getX()) && (this.y + 1 == pos.getY());
		} else if (direction == Agent.UP) {
			return (this.x == pos.getX()) && (this.y - 1 == pos.getY());
		} else if (direction == Agent.RIGHT) {
			return (this.x + 1 == pos.getX()) && (this.y == pos.getY());
		} else if (direction == Agent.LEFT) {
			return (this.x - 1 == pos.getX()) && (this.y == pos.getY());
		} else {
			return false;
		}
	}

	/* returns true if position, pos, is right behind this position */
	public boolean isJustBehind(MapPosition pos, int direction) {

		if (direction == Agent.DOWN) {
			return (this.x == pos.getX()) && (this.y - 1 == pos.getY());
		} else if (direction == Agent.UP) {
			return (this.x == pos.getX()) && (this.y + 1 == pos.getY());
		} else if (direction == Agent.RIGHT) {
			return (this.x - 1 == pos.getX()) && (this.y == pos.getY());
		} else if (direction == Agent.LEFT) {
			return (this.x + 1 == pos.getX()) && (this.y == pos.getY());
		} else {
			return false;
		}
	}

	/* returns true if position, pos, is a left position of this position */
	public boolean isLeft(MapPosition pos, int direction) {

		if (direction == Agent.DOWN) {
			return this.x < pos.getX();
		} else if (direction == Agent.UP) {
			return this.x > pos.getX();
		} else if (direction == Agent.RIGHT) {
			return this.y > pos.getY();
		} else if (direction == Agent.LEFT) {
			return this.y < pos.getY();
		} else {
			return false;
		}
	}

	/* returns true if position, pos, is a right position of this position */
	public boolean isRight(MapPosition pos, int direction) {

		if (direction == Agent.DOWN) {
			return this.x > pos.getX();
		} else if (direction == Agent.UP) {
			return this.x < pos.getX();
		} else if (direction == Agent.RIGHT) {
			return this.y < pos.getY();
		} else if (direction == Agent.LEFT) {
			return this.y > pos.getY();
		} else {
			return false;
		}
	}

	/* returns true if position, pos, is ahead this position */
	public boolean isAhead(MapPosition pos, int direction) {

		if (direction == Agent.DOWN) {
			return this.y < pos.getY();
		} else if (direction == Agent.UP) {
			return this.y > pos.getY();
		} else if (direction == Agent.RIGHT) {
			return this.x < pos.getX();
		} else if (direction == Agent.LEFT) {
			return this.x > pos.getX();
		} else {
			return false;
		}
	}

	/* returns true if position, pos, is behind this position */
	public boolean isBehind(MapPosition pos, int direction) {

		if (direction == Agent.DOWN) {
			return this.y > pos.getY();
		} else if (direction == Agent.UP) {
			return this.y < pos.getY();
		} else if (direction == Agent.RIGHT) {
			return this.x > pos.getX();
		} else if (direction == Agent.LEFT) {
			return this.x < pos.getX();
		} else {
			return false;
		}
	}

	/* returns true if position, pos, is this position */
	public boolean isSamePosition(MapPosition pos) {
		return this.y == pos.getY() && this.x == pos.getX();
	}
}
