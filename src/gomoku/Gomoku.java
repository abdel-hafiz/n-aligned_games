/**
 * 
 */
package gomoku;

import java.awt.Point;
import java.util.ArrayList;
import main.Board;
import main.Cloneur;
import main.Move;
import main.Piece;

/**
 * @author abdel-hafiz
 *
 */
public class Gomoku extends Board {
	
	 public Gomoku(int length, int num) {
	        this.playerOnePieces = new ArrayList<>();
	        this.playerTwoPieces = new ArrayList<>();
	        this.length = length;
	        freeslots = length * length;
	        number = num;
	        currentPlayer = 1;
	    }

	@Override
	public ArrayList<Move> getMoves() {
		 ArrayList<Move> moves = new ArrayList<Move>();
	        if (this.isSolved()) {
//	            System.out.println("solved");
	            return moves;
	        }
	        if (this.playerOnePieces.size() + this.playerTwoPieces.size() == freeslots) {
	            return moves;
	        }

	        char[][] grid = makeTab();
	        for (int i = 0; i < grid.length; i++) {
	            for (int j = 0; j < grid[0].length; j++) {
	                if (grid[i][j] == ' ') {
	                    moves.add(new Move(new Point(j, i)));
	                }
	            }
	        }
	        return moves;
	}

	@Override
	public Board duplicate() {
        Gomoku t = new Gomoku(length, number);
        t.winner = winner;
        t.currentPlayer = currentPlayer;
        t.draw = draw;
        t.freeslots = freeslots;
        t.gameWon = gameWon;
        t.playerOnePieces = (ArrayList<Piece>) Cloneur.deepClone(playerOnePieces);
        t.playerTwoPieces = (ArrayList<Piece>) Cloneur.deepClone(playerTwoPieces);
        t.length = length;
        

        return t;
    }

}
