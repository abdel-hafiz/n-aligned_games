/**
 * 
 */
package main;

import java.awt.Point;

/**
 * @author abdel-hafiz
 *
 */
public class Move {

	protected Point prevPosition;
	protected Point nextPosition;
	
	
	public Point getPrevPosition() {
		return prevPosition;
	}

	public void setPrevPosition(Point prevPosition) {
		this.prevPosition = prevPosition;
	}

	public Point getNextPosition() {
		return nextPosition;
	}

	public void setNextPosition(Point nextPosition) {
		this.nextPosition = nextPosition;
	}


	public Move(Point next) {
		this.nextPosition = next;
		this.prevPosition = null;
	} // end constructor

	public Move(Point prev, Point next) {
		this.prevPosition = prev;
		this.nextPosition = next;
	} // end constructor

	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Move other = (Move) obj;
		if (nextPosition == null) {
			if (other.nextPosition != null)
				return false;
		} else if (!nextPosition.equals(other.nextPosition))
			return false;
		if (prevPosition == null) {
			if (other.prevPosition != null)
				return false;
		} else if (!prevPosition.equals(other.prevPosition))
			return false;
		return true;
	}
}
