/**
 * 
 */
package main;

import java.awt.Point;

/**
 * @author abdel-hafiz
 *
 */
public class Piece {

	
	private int couleur;
    private Point position;

    public Piece(int c) {
        this.couleur = c;
    }

    public Piece(int c, Point p) {
        this.couleur = c;
        this.position = p;
    }

    @Override
    public String toString() {
        return "[Piece couleur=" + couleur + ", position=" + position + "]";
    }

    public int getCouleur() {
        return this.couleur;
    }

    public void setCouleur(int c) {
        this.couleur = c;
    }

    public Point getPosition() {
        return this.position;
    }

    public void setPosition(Point p) {
        this.position = p;
    }
}
