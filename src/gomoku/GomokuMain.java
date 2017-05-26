/**
 * 
 */
package gomoku;

import java.awt.Point;
import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import gomoku.GomokuMain;
import gomoku.Gomoku;
import main.AlphaBeta;
import main.Move;
import main.Node;

/**
 * @author abdel-hafiz
 *
 */
public class GomokuMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	        AlphaBeta ab = new AlphaBeta();
	        Move move;
	        int[] scores = new int[3];
	        boolean maximizingPlayer = true;

	        for (int i = 0; i < 1; i++) {
	            Gomoku ttt = new Gomoku(11, 5);
	                        if(ttt == null)
	                            System.out.println("is null");
	                        else
	                            System.out.println("is not null");
	            while (!ttt.gameOver()) {
	                //joueur 1 utilisant l'UCB standard en utilisant la ronde
	                move = ab.alphaBeta(ttt, 1000, -1000000, 1000000, maximizingPlayer);
//	                            System.out.println("A vous!!!");
//	                                    Scanner sc = new Scanner(System.in);
//	                                    String chaine = sc.nextLine();
//	                                    String[] val = chaine.split("-");
//	                                    move = new Move(new Point(Integer.parseInt(val[0])-1, Integer.parseInt(val[1])-1));
	                ttt.makeMove(move);
	                Node n = new Node(ttt);
	                n.printinfo();
	                System.out.println("sa valeur est " + n.stateEvaluation());
	                ttt.bPrint();
//	                            new Scanner(System.in).nextLine();
	                System.out.println("\n\n\n");
	                try {
	                    Thread.sleep(1000);
	                } catch (InterruptedException ex) {
	                    Logger.getLogger(GomokuMain.class.getName()).log(Level.SEVERE, null, ex);
	                }
//	                            joueur 2 utilisant la croix
	                if (!ttt.gameOver()) {
	                                    System.out.println("eya");
	                	move = ab.alphaBeta(ttt, 500, -1000000, 1000000, maximizingPlayer);
//	                	System.out.println(move);
                                   // System.out.println("efo");
//	                                    System.out.println("A vous!!!");
//	                                    Scanner sc = new Scanner(System.in);
//	                                    String chaine = sc.nextLine();
//                                    String [] val = chaine.split("-");
//	                                   move = new Move(new Point(Integer.parseInt(val[0])-1, Integer.parseInt(val[1])-1));
	                    ttt.makeMove(move);
	                    Node no = new Node(ttt);
	                    //no.calc();
	                    no.printinfo();
	                    System.out.println("sa valeur est " + no.stateEvaluation());
	                    ttt.bPrint();
	                    System.out.println("\n\n\n");
	                    try {
	                        Thread.sleep(1000);
	                    } catch (InterruptedException ex) {
	                        Logger.getLogger(GomokuMain.class.getName()).log(Level.SEVERE, null, ex);
	                    }
	                }
	            }

	            System.out.println("---");
	            ttt.bPrint();

	            double[] scr = ttt.getScore();
	            if (scr[0] > 0.9) {
	                scores[0]++; // player 1
	            } else if (scr[1] > 0.9) {
	                scores[1]++; // player 2
	            } else {
	                scores[2]++; // draw
	            }
	            System.out.println(Arrays.toString(scr));
//	            System.out.println(Arrays.toString(scores));

	        }
	    }
	}
