/**
 * 
 */
package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Map;
import java.awt.Point;
import java.util.Random;

/**
 * @author abdel-hafiz
 *
 */
public class AlphaBeta {
	
	//private Random random;
	//private Board startingBoard;
	private Node rootNode;
	Point p= new Point(0,0);
	 Move bestMove=new Move(p);
	//private Node rootNode= new Node(startingBoard);
	
	 public AlphaBeta() {
			
		}

	 
	 
	public Move alphaBeta(Board startingBoard, int depth, int alpha, int beta, boolean maximizingPlayer) {
		
		rootNode = new Node(startingBoard);
		rootNode.expandNode(startingBoard);
		//Move bestMove = null;
//		if (depth == 0 || startingBoard.draw) return (int) rootNode.stateEvaluation();
//		long startTime = System.nanoTime();
	
		//if (rootNode.unvisitedChildren == null || rootNode.unvisitedChildren.size()==0) return rootNode.move;
		if (maximizingPlayer) {
			int currentAlpha= Integer.MIN_VALUE;
			//if (System.currentTimeMillis() - startTime >= timeLimit) return evaluate(startingBoard);
			for (int i = 0; i < rootNode.unvisitedChildren.size(); i++) {
				currentAlpha = (int) Math.max(currentAlpha, rootNode.unvisitedChildren.get(i).stateEvaluation());
				if(currentAlpha > alpha)
				{
					alpha = currentAlpha;
				
					 bestMove = rootNode.unvisitedChildren.get(i).move;
					 System.out.println(bestMove);
				}
				if (alpha >= beta) break;
				
			}
			 System.out.println(alpha);
			return bestMove;
		}
		else {
			int currentBeta= Integer.MAX_VALUE;
			for (int i = 0; i < rootNode.unvisitedChildren.size(); i++) {
				currentBeta = (int) Math.min(currentBeta, rootNode.unvisitedChildren.get(i).stateEvaluation());
				//beta = Math.min(beta, currentBeta);
				if(currentBeta  < beta)
				{
					beta = currentBeta;
					bestMove = rootNode.unvisitedChildren.get(i).move;
				}
				if (alpha >= beta) break;
			}
			return bestMove;
		}
	}
}
	