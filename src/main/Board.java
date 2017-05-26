/**
 * 
 */
package main;

import java.util.ArrayList;

/**
 * @author abdel-hafiz
 *
 */
public abstract class Board {
	 protected int currentPlayer;
	 
	 public int getCurrentPlayer() {
		return currentPlayer;
	}


		protected int winner;
	    protected boolean draw;
	    protected boolean gameWon;
	    protected int freeslots;
	    //Player 1 pieces collection
	    protected ArrayList<Piece> playerOnePieces;
	    //Player 2 pieces collection
	    protected ArrayList<Piece> playerTwoPieces;
	    //board dimension
	    protected int length;
	    //nombre de pierres à aligner pour remporter la partie
	    protected int number;

	    public abstract ArrayList<Move> getMoves();
	    
	    public abstract Board duplicate();
	    
	    //conversion des positions actuelles des pieces en une structure
	    //de tableau facilement manipulable
	    public char[][] makeTab() {
	        char[][] tic = new char[this.length][this.length];
	        for (Piece playerOnePiece : this.playerOnePieces) {
	            int xa = (playerOnePiece.getPosition()).x;
	            int ya = (playerOnePiece.getPosition()).y;
	            tic[ya][xa] = 'O';
	        };

	        for (Piece playerTwoPiece : this.playerTwoPieces) {
	            int xa = (playerTwoPiece.getPosition()).x;
	            int ya = (playerTwoPiece.getPosition()).y;
	            tic[ya][xa] = 'X';
	        }

	        for (int i = 0; i < length; i++) {
	            for (int j = 0; j < length; j++) {
	                if (tic[i][j] != 'X' && tic[i][j] != 'O') {
	                    tic[i][j] = ' ';
	                }
	            }
	        }
	        return tic;
	    }
	    
	    //affichage du plateau
	    public void bPrint() {
	        /*
			
	         | X | O | O |
	         -------------
	         | O | X | O |
	         -------------
	         | O | O | X |
			
	         */
	        char[][] tic = makeTab();

	        for (int i = 0; i < tic.length; i++) {
	            System.out.print("|");
	            for (int j = 0; j < tic[0].length; j++) {
	                System.out.print(" " + tic[i][j] + " |");
	            }
	            System.out.println("");
	            for (char[] tic1 : tic) {
	                System.out.print("----");
	            }
	            for (int j = 1; j <= this.length / 2; j++) {
	                System.out.print("-");
	            }
	            System.out.println("");
	        }
	        System.out.println("\n");
	    }

	    
	    
	    /**
	     * Apply the move m to the current state of the board. So it changes the
	     * board attributes.
	     *
	     * @param move
	     */
	    public void makeMove(Move move) {

	        if (getCurrentPlayer() == 1) {
	            this.playerOnePieces.add(new Piece(1, move.getNextPosition()));
	        } else {
	            this.playerTwoPieces.add(new Piece(2, move.getNextPosition()));
	        }
	        currentPlayer = (getCurrentPlayer() == 1) ? 2 : 1;
	        //gameOver();
	    }
	    
	    
	    protected boolean checkRow(int row, char[][] t) {
	        for (int i = 0; i < t.length - 1; i++) {
	            if (t.length - i >= number) {
	                boolean flag = true;
	                for (int j = i; j < i + number - 1 && flag == true; j++) {
	                    if ((t[row][j] != t[row][j + 1]) || t[row][j] == ' ') {
	                        flag = false;
	                    }
	                }
	                if (flag) {
	                    return true;
	                }
	            } else {
	            }
	        }
	        return false;
	    }

	    
	    protected boolean checkColumn(int column, char[][] t) {
	        for (int i = 0; i < t.length - 1; i++) {
	            if (t.length - i >= number) {
	                boolean flag = true;
	                for (int j = i; j < i + number - 1 && flag == true; j++) {
	                    if ((t[j][column] != t[j + 1][column]) || t[j][column] == ' ') {
	                        flag = false;
	                    }
	                }
	                if (flag) {
	                    return true;
	                }
	            } else {
	            }
	        }
	        return false;
	    }
	    
	    
	    protected boolean checkDiag(int row, int column, char[][] t) {
	        if (checkDiagHG(row, column, t)) {
	            return true;
	        }
	        if (checkDiagHD(row, column, t)) {
	            return true;
	        }
	        if (checkDiagBG(row, column, t)) {
	            return true;
	        }

	        if (checkDiagBD(row, column, t)) {
	            return true;
	        }
	        return false;
	    }

	    private boolean checkDiagHG(int row, int column, char[][] t) {
	        if (row >= number - 1 && column >= number - 1) {
	            for (int i = column, j = row; i > column - number + 1 && j > row - number + 1; i--, j--) {
	                if ((t[j][i] != t[j - 1][i - 1]) || t[j][i] == ' ') {
	                    return false;
	                }
	            }
	        } else {
	            return false;
	        }
	        return true;
	    }

	    private boolean checkDiagHD(int row, int column, char[][] t) {
	        if (row >= number - 1 && t.length - column >= number) {
	            for (int i = column, j = row; i < column + number - 1 && j > row - number + 1; i++, j--) {
	                if ((t[j][i] != t[j - 1][i + 1]) || t[j][i] == ' ') {
	                    return false;
	                }
	            }
	        } else {
	            return false;
	        }
	        return true;
	    }

	    private boolean checkDiagBG(int row, int column, char[][] t) {
	        if (t.length - row >= number && column >= number - 1) {
	            for (int i = column, j = row; i > column - number + 1 && j < row + number - 1; i--, j++) {
	                if ((t[j][i] != t[j + 1][i - 1]) || t[j][i] == ' ') {
	                    return false;
	                }
	            }
	        } else {
	            return false;
	        }
	        return true;
	    }

	    private boolean checkDiagBD(int row, int column, char[][] t) {
	        if (t.length - row >= number && t.length - column >= number) {
	            for (int i = column, j = row; i < column + number - 1 && j < row + number - 1; i++, j++) {
	                if ((t[j][i] != t[j + 1][i + 1]) || t[j][i] == ' ') {
	                    return false;
	                }
	            }
	        } else {
	            return false;
	        }
	        return true;
	    }
	    
	    
	    /**
	     * Check if the board is solved
	     *
	     * @return true if the {@link Board} is solved false if not
	     */
	    public boolean isSolved() {
	        char[][] t = this.makeTab();
	        for (int i = 0; i < t.length; i++) {
//	            System.out.println("\nligne "+(i+1));
	            if (checkRow(i, t)) {
//	                System.out.println("lgnes");
	                return true;
	            }

	            if (checkColumn(i, t)) {
//	                System.out.println("col");
	                return true;
	            }
	        }
	        for (int i = 0; i < t.length; i++) {
	            for (int j = 0; j < t.length; j++) {
	                if (checkDiag(i, j, t)) {
	                    return true;
	                }
	            }
	        }
	        return false;
	    }
	    
	    
	    /**
	     * Returns true if the game is over.
	     *
	     * @return
	     */
	    public boolean gameOver() {
	        if (isSolved()) {
	            winner = (getCurrentPlayer() == 1) ? 2 : 1;
	            return true;
	        } else {
	            if (getMoves().isEmpty()) {
	                draw = true;
	                return true;
	            }
	        }

	        return false;
	    }
	    
	    /**
	     * Returns a score vector. [1.0, 0.0] indicates a win for player 0. [0.0,
	     * 1.0] indicates a win for player 1 [0.5, 0.5] indicates a draw
	     *
	     * @return score array
	     */
	    public double[] getScore() {
	        double[] score;
	        score = new double[2];
	        if (!draw) {
	            score[winner - 1] = 1.0d;
	        } else {
	            score[0] = 0.5d;
	            score[1] = 0.5d;
	        }

	        return score;
	    }

}
