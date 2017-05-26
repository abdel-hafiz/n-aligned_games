/**
 * 
 */
package main;

import java.util.ArrayList;
import java.util.Set;

/**
 * @author abdel-hafiz
 *
 */
public class Node {

	
//	 public double[] score;
//	    public double games;
	    //le mouvement ayant conduit au nœud courant
	    public Move move;
	    public ArrayList<Node> unvisitedChildren;
	    //les nœuds enfants
	    public ArrayList<Node> children;
//	    public Set<Integer> rVisited;
	    //le nœud parent
	    public Node parent;
	    //le joueur qui à la main de jouer dans l'état courant
	    public int player;
//	    public double[] pess;
//	    public double[] opti;
	    //si le nœud est élagé ou non
	    public boolean pruned;
	    //les paramètres de l'heuristique
	    public double[][] infoTab;
	    //nombre de pierres à aligner pour remporter la partie
	    public int number;
	    //
	    public char[][] pieces;
	    
	    private ArrayList<String> visit = new ArrayList<>();
	    private ArrayList<String> visit1 = new ArrayList<>();
	    private ArrayList<String> visit2 = new ArrayList<>();
	    private ArrayList<String> visit3 = new ArrayList<>();

	    /**
	     * This creates the root node
	     *
	     * @param b
	     */
	    public Node(Board b) {
	        children = new ArrayList<Node>();
	        player = b.getCurrentPlayer();
//	        score = new double[2];
//	        pess = new double[2];
//	        opti = new double[2];
	        infoTab = new double[4][(b.number - 1) * 2 + 1];
	        number = b.number;
	        pieces = b.makeTab();
//	        for (int i = 0; i < 2; i++) {
//	            opti[i] = 1;
//	        }
	        compute();
	    }

	    public void printinfo() {

	        for (double[] t : infoTab) {
	            for (double u : t) {
	                System.out.print(u + "\t|");
	            }
	            System.out.println("\n");
	        }
	    }

	    /**
	     * This creates non-root nodes
	     *
	     * @param b
	     * @param m
	     * @param prnt
	     */
	    public Node(Board b, Move m, Node prnt) {
	        children = new ArrayList<Node>();
	        parent = prnt;
	        move = m;
	        Board tempBoard = b.duplicate();
	        tempBoard.makeMove(m);
	        player = tempBoard.getCurrentPlayer();
//	        score = new double[2];
	        //pess = new double[2];
	        //opti = new double[2];
	        infoTab = new double[4][(b.number - 1) * 2 + 1];
	        number = tempBoard.number;
	        pieces = tempBoard.makeTab();
//	        for (int i = 0; i < 2; i++) {
//	            opti[i] = 1;
//	        }
	        compute();
	    }

//	    /**
//	     * Return the upper confidence bound of this state
//	     *
//	     * @param c typically sqrt(2). Increase to emphasize exploration. Decrease
//	     * to incr. exploitation
//	     * @param t
//	     * @return
//	     */
//	    public double upperConfidenceBound(double c) {
//	        return score[parent.player - 1] / games + c
//	                * Math.sqrt(2 * Math.log(parent.games) / games);
////	                return score[parent.player-1] / games  + c * Math.sqrt(2 * Math.log(parent.games) / games);
////	            return (parent.player == 1) ? nbun - nbdeux : nbdeux - nbun;
//	    }

	    public double stateEvaluation() {
//	            compute();
	        double pos = 0, neg = 0;
	        for (int i = 2; i < infoTab[0].length; i++) {
	            pos += infoTab[0][i] * infoTab[1][i];
	            neg += infoTab[2][i] * infoTab[3][i];
	        }
	        return pos - neg;
	    }

//	    /**
//	     * Update the tree with the new score.
//	     *
//	     * @param scr
//	     */
//	    public void backPropagateScore(double[] scr) {
//	        this.games++;
//	        for (int i = 0; i < scr.length; i++) {
//	            this.score[i] += scr[i];
//	        }
//
//	        if (parent != null) {
//	            parent.backPropagateScore(scr);
//	        }
//	    }

	    /**
	     * Expand this node by populating its list of unvisited child nodes.
	     *
	     * @param currentBoard
	     */
	    public void expandNode(Board currentBoard) {
	        ArrayList<Move> legalMoves = currentBoard.getMoves();
	        unvisitedChildren = new ArrayList<Node>();
	        for (int i = 0; i < legalMoves.size(); i++) {
	            Node tempState = new Node(currentBoard, legalMoves.get(i), this);
	            unvisitedChildren.add(tempState);
	        }
	    }
//
//	    /**
//	     * Set the bounds in the given node and propagate the values back up the
//	     * tree. When bounds are first created they are both equivalent to a
//	     * player's score.
//	     *
//	     * @param optimistic
//	     * @param pessimistic
//	     */
//	    public void backPropagateBounds(double[] score) {
//	        for (int i = 0; i < score.length; i++) {
//	            opti[i] = score[i];
//	            pess[i] = score[i];
//	        }
//
//	        if (parent != null) {
//	            parent.backPropagateBoundsHelper();
//	        }
//	    }
//
//	    private void backPropagateBoundsHelper() {
//	        for (int i = 0; i < opti.length; i++) {
//	            if (i == player) {
//	                opti[i] = 0;
//	                pess[i] = 0;
//	            } else {
//	                opti[i] = 1;
//	                pess[i] = 1;
//	            }
//	        }
//
//	        for (int i = 0; i < opti.length; i++) {
//	            for (Node c : children) {
//	                if (i == player) {
//	                    if (opti[i] < c.opti[i]) {
//	                        opti[i] = c.opti[i];
//	                    }
//	                    if (pess[i] < c.pess[i]) {
//	                        pess[i] = c.pess[i];
//	                    }
//	                } else {
//	                    if (opti[i] > c.opti[i]) {
//	                        opti[i] = c.opti[i];
//	                    }
//	                    if (pess[i] > c.pess[i]) {
//	                        pess[i] = c.pess[i];
//	                    }
//	                }
//	            }
//	        }
//
//	        // This compares against a dummy node with bounds 1 0
//	        // if not all children have been explored
//	        if (!unvisitedChildren.isEmpty()) {
//	            for (int i = 0; i < opti.length; i++) {
//	                if (i == player) {
//	                    opti[i] = 1;
//	                } else {
//	                    pess[i] = 0;
//	                }
//	            }
//	        }
//
//	        // TODO: This causes redundant pruning. Fix it
//	        pruneBranches();
//	        if (parent != null) {
//	            parent.backPropagateBoundsHelper();
//	        }
//	    }
//
//	    public void pruneBranches() {
//	        for (Node s : children) {
//	            if (pess[player] >= s.opti[player]) {
//	                s.pruned = true;
//	            }
//	        }
//
//	        if (parent != null) {
//	            parent.pruneBranches();
//	        }
//	    }

	    public void compute() {
	        char[][] t = this.pieces;
	        for (int i = 0; i < t.length; i++) {
	            checkRows(i, t);
	            checkColumns(i, t);
	        }
	        for (int i = 0; i < t.length; i++) {
	            for (int j = 0; j < t.length; j++) {
	                checkDiag(i, j, t);
	            }
	        }
	        int ind = (number - 1) * 2 + 1;

	        ind--;
	        infoTab[1][ind] = 1000000;
	        infoTab[3][ind] = 1000000;

	        ind--;
	        infoTab[1][ind] = 250;
	        infoTab[3][ind] = 5020;

	        ind--;
	        infoTab[1][ind] = 80;
	        infoTab[3][ind] = 2000;

	        ind--;
	        infoTab[1][ind] = 100;
	        infoTab[3][ind] = 1300;

	        int taill = (number - 2) * 2 - 1;
	        double pas = 10 / taill;
	        double tmp = pas;
	        double[] val = new double[taill];
	        int i = 0;
	        val[i] = tmp;
	        for (i = 0; i < taill - 1; i += 2) {
	            tmp += pas;
	            val[i + 2] = tmp;
	            tmp += pas;
	            val[i + 1] = tmp;
	        }
	        int j = 0;
	        for (int k = 0; k < val.length; k++) {
	            infoTab[1][j] = val[k];
	            infoTab[3][j] = val[k];
	            j++;
	        }

	    }
	    
//	    public void calc(){
//	    	int ind = (number - 1) * 2 + 1;
//
//	        ind--;
//	        infoTab[1][ind] = 10000;
//	        infoTab[3][ind] = 10000;
//
//	        ind--;
//	        infoTab[1][ind] = 250;
//	        infoTab[3][ind] = 250;
//
//	        ind--;
//	        infoTab[1][ind] = 80;
//	        infoTab[3][ind] = 80;
//
//	        ind--;
//	        infoTab[1][ind] = 100;
//	        infoTab[3][ind] = 100;
//	    }


	    private void checkRows(int row, char[][] t) {
	        for (int i = 0; i < t.length; i++) {
	            if (t[row][i] == ' ' || t[row][i] == ((player == 1) ? 'O' : 'X')) {
	                continue;
	            }
	            if (i - 1 >= 0 && ((t[row][i - 1] == ((player == 1) ? 'X' : 'O')))) {//Simuler parent qui n'existe pas pour le premier noeud
	                continue;
	            }

	            /*Vérifie qu'on peut avoir n pieces dans cet alignement*/
	            int deb = 0, fini = 0;
	            boolean ch = false;
	            for (int u = i - 1; u >= 0; u--) {
	                if (t[row][u] == ((player == 1) ? 'O' : 'X')) {
	                    deb = u;
	                    ch = true;
	                    break;
	                }
	            }
	            if (!ch) {
	                deb = -1;
	            }
	            ch = false;
	            for (int u = i + 1; u < t[0].length; u++) {
	                if (t[row][u] == ((player == 1) ? 'O' : 'X')) {
	                    fini = u;
	                    ch = true;
	                    break;
	                }
	            }
	            if (!ch) {
	                fini = t[0].length;
	            }
	            int plage = fini - deb - 1;
	            if (plage < number) {
	                continue;
	            }
	            /*Fin de vérification*/

	            int nbre = 1, v1, v2;
	            int saut = 0;
	            for (v1 = i, v2 = i + 1; v2 < t.length;) {
	                if ((t[row][v1] == t[row][v2])) {
	                    nbre++;
	                    if (v2 + 1 < t.length && ((t[row][v2] == t[row][v2 + 1]) || (t[row][v2 + 1] == ' '))) {
	                        v1 = v2;
	                        v2++;
	                    } else {
	                        break;
	                    }
	                } else if (t[row][v2] == ' ') {
	                    saut++;
	                    if (saut < 2) {
	                        if (v2 + 1 < t.length) {
	                            if (t[row][v1] == t[row][v2 + 1]) {
	                                v2++;
	                            } else if(t[row][v2 + 1] == ' ') {
	                                v2--;
	                                saut++;
	                                break;
	                            } else {
	                                v2--;
	                                break;
	                            }
	                        } else {
	                            v2--;
	                            break;
	                        }
	                    } else {
	                        v2--;
	                        saut--;
	                        break;
	                    }
	                } else {
	                    v2--;
	                    break;
	                }
	            }
	            int init = i;
	            int fin = v2;
	            boolean trou = false;
	            if(nbre == number - 1){
	                for (int u = init; u <= fin; u++) {
	                    if (t[row][u] == ' ') {
	                        trou = true;
	                        break;
	                    }
	                }
	            }
	            if (nbre < 1 || nbre > number) {

	            } else if (nbre == number - 1 && trou) {
	                infoTab[0][(nbre - 1) * 2] += 1;
	            } else if (nbre == number) {
	                boolean flag = false;
	                for (int k = init; k < fin; k++) {
	                    if (t[row][k] != t[row][k + 1]) {
	                        flag = true;
	                    }
	                }
	                if (!flag) {
	                    infoTab[0][infoTab[0].length - 1] += 1;
	                }
	            } else {
	                if (plage == number) {
	                    infoTab[0][(nbre - 1) * 2] += 1;
	                } else {
	                    if (t[row][deb + 1] == ' ' && t[row][fini - 1] == ' ') {
	                        infoTab[0][(nbre - 1) * 2 + 1] += 1;
	                    } else {
	                        infoTab[0][(nbre - 1) * 2] += 1;
	                    }
	                }

	            }
	            i = fin + saut;
	        }

	        /*%%%%%%%%%%%%%Adverse%%%%%%%%%%%%%%%%%*/
	        for (int i = 0; i < t.length; i++) {
	            if (t[row][i] == ' ' || t[row][i] == ((player == 1) ? 'X' : 'O')) {
	                continue;
	            }
	            if (i - 1 >= 0 && ((t[row][i - 1] == ((player == 1) ? 'O' : 'X')))) {
	                continue;
	            }

	            /*Vérifie qu'on peut avoir n pieces dans cet alignement*/
	            int deb = 0, fini = 0;
	            boolean ch = false;
	            for (int u = i - 1; u >= 0; u--) {
	                if (t[row][u] == ((player == 1) ? 'X' : 'O')) {
	                    deb = u;
	                    ch = true;
	                    break;
	                }
	            }
	            if (!ch) {
	                deb = -1;
	            }
	            ch = false;
	            for (int u = i + 1; u < t[0].length; u++) {
	                if (t[row][u] == ((player == 1) ? 'X' : 'O')) {
	                    fini = u;
	                    ch = true;
	                    break;
	                }
	            }
	            if (!ch) {
	                fini = t[0].length;
	            }
	            int plage = fini - deb - 1;
	            if (plage < number) {
	                continue;
	            }
	            /*Fin de vérification*/

	            int nbre = 1, v1, v2;
	            int saut = 0;
	            for (v1 = i, v2 = i + 1; v2 < t.length;) {
	                if ((t[row][v1] == t[row][v2])) {
	                    nbre++;
	                    if (v2 + 1 < t.length && ((t[row][v2] == t[row][v2 + 1]) || (t[row][v2 + 1] == ' '))) {
	                        v1 = v2;
	                        v2++;
	                    } else {
	                        break;
	                    }
	                } else if (t[row][v2] == ' ') {
	                    saut++;
	                    if (saut < 2) {
	                        if (v2 + 1 < t.length) {
	                            if (t[row][v1] == t[row][v2 + 1]) {
	                                v2++;
	                            } else if(t[row][v2 + 1] == ' ') {
	                                v2--;
	                                saut++;
	                                break;
	                            } else {
	                                v2--;
	                                break;
	                            }
	                        } else {
	                            v2--;
	                            break;
	                        }
	                    } else {
	                        v2--;
	                        saut--;
	                        break;
	                    }
	                } else {
	                    v2--;
	                    break;
	                }
	            }
	            int init = i;
	            int fin = v2;
	            boolean trou = false;
	            if(nbre == number - 1){
	                for (int u = init; u <= fin; u++) {
	                    if (t[row][u] == ' ') {
	                        trou = true;
	                        break;
	                    }
	                }
	            }
	            if (nbre < 1 || nbre > number) {

	            } else if (nbre == number - 1 && trou) {
	                infoTab[2][(nbre - 1) * 2] += 1;
	            } else if (nbre == number) {
	                boolean flag = false;
	                for (int k = init; k < fin; k++) {
	                    if (t[row][k] != t[row][k + 1]) {
	                        flag = true;
	                    }
	                }
	                if (!flag) {
	                    infoTab[2][infoTab[0].length - 1] += 1;
	                }
	            } else {
	                if (plage == number) {
	                    infoTab[2][(nbre - 1) * 2] += 1;
	                } else {
	                    if (t[row][deb + 1] == ' ' && t[row][fini - 1] == ' ') {
	                        infoTab[2][(nbre - 1) * 2 + 1] += 1;
	                    } else {
	                        infoTab[2][(nbre - 1) * 2] += 1;
	                    }
	                }

	            }
	            i = fin + saut;
	        }
	    }


	    private void checkColumns(int column, char[][] t) {

//	        Recherche des miens
	        for (int i = 0; i < t.length; i++) {
	            if (t[i][column] == ' ' || t[i][column] == ((player == 1) ? 'O' : 'X')) {
	                continue;
	            }
	            if (i - 1 >= 0 && ((t[i - 1][column] == ((player == 1) ? 'X' : 'O')))) {
	                continue;
	            }

	            /*Vérifie qu'on peut avoir n pieces dans cet alignement*/
	            int deb = 0, fini = 0;
	            boolean ch = false;
	            for (int u = i - 1; u >= 0; u--) {
	                if (t[u][column] == ((player == 1) ? 'O' : 'X')) {
	                    deb = u;
	                    ch = true;
	                    break;
	                }
	            }
	            if (!ch) {
	                deb = -1;
	            }
	            ch = false;
	            for (int u = i + 1; u < t.length; u++) {
	                if (t[u][column] == ((player == 1) ? 'O' : 'X')) {
	                    fini = u;
	                    ch = true;
	                    break;
	                }
	            }
	            if (!ch) {
	                fini = t.length;
	            }
	            int plage = fini - deb - 1;
	            if (plage < number) {
	                continue;
	            }
	            /*Fin de vérification*/

	            int nbre = 1, v1, v2;
	            int saut = 0;
	            for (v1 = i, v2 = i + 1; v2 < t.length;) {
	                if ((t[v1][column] == t[v2][column])) {
	                    nbre++;
	                    if (v2 + 1 < t.length && ((t[v2][column] == t[v2 + 1][column]) || (t[v2 + 1][column] == ' '))) {
	                        v1 = v2;
	                        v2++;
	                    } else {
	                        break;
	                    }

	                } else if (t[v2][column] == ' ') {
	                    saut++;
	                    if (saut < 2) {
	                        if (v2 + 1 < t.length) {
	                            if (t[v1][column] == t[v2 + 1][column]) {
	                                v2++;
	                            } else if(t[v2 + 1][column] == ' '){
	                                v2--;
	                                saut++;
	                                break;
	                            } else {
	                                v2--;
	                                break;
	                            }
	                        } else {
	                            v2--;
	                            break;
	                        }
	                    } else {
	                        v2--;
	                        saut--;
	                        break;
	                    }
	                } else {
	                    v2--;
	                    break;
	                }
	            }

	            int init = i;
	            int fin = v2;
	            boolean trou = false;
	            if(nbre == number - 1){
	                for (int u = init; u <= fin; u++) {
	                    if (t[u][column] == ' ') {
	                        trou = true;
	                        break;
	                    }
	                }
	            }
	            if (nbre < 1 || nbre > number) {

	            } else if (nbre == number - 1 && trou) {
	                infoTab[0][(nbre - 1) * 2] += 1;
	            } else if (nbre == number) {
	                boolean flag = false;
	                for (int k = init; k < fin; k++) {
	                    if (t[k][column] != t[k + 1][column]) {
	                        flag = true;
	                    }
	                }
	                if (!flag) {
	                    infoTab[0][infoTab[0].length - 1] += 1;
	                }
	            } else {
	                if (plage == number) {
	                    infoTab[0][(nbre - 1) * 2] += 1;
	                } else {
	                    if (t[deb + 1][column] == ' ' && t[fini - 1][column] == ' ') {
	                        infoTab[0][(nbre - 1) * 2 + 1] += 1;
	                    } else {
	                        infoTab[0][(nbre - 1) * 2] += 1;
	                    }
	                }

	            }
	            i = fin + saut;
	        }


//	        Recherche des adversaires
	        for (int i = 0; i < t.length; i++) {
	            if (t[i][column] == ' ' || t[i][column] == ((player == 1) ? 'X' : 'O')) {
	                continue;
	            }
	            if (i - 1 >= 0 && ((t[i - 1][column] == ((player == 1) ? 'O' : 'X')))) {
	                continue;
	            }

	            /*Vérifie qu'on peut avoir n pieces dans cet alignement*/
	            int deb = 0, fini = 0;
	            boolean ch = false;
	            for (int u = i - 1; u >= 0; u--) {
	                if (t[u][column] == ((player == 1) ? 'X' : 'O')) {
	                    deb = u;
	                    ch = true;
	                    break;
	                }
	            }
	            if (!ch) {
	                deb = -1;
	            }
	            ch = false;
	            for (int u = i + 1; u < t.length; u++) {
	                if (t[u][column] == ((player == 1) ? 'X' : 'O')) {
	                    fini = u;
	                    ch = true;
	                    break;
	                }
	            }
	            if (!ch) {
	                fini = t.length;
	            }
	            int plage = fini - deb - 1;
	            if (plage < number) {
	                continue;
	            }
	            /*Fin de vérification*/

	            int nbre = 1, v1, v2;
	            int saut = 0;
	            for (v1 = i, v2 = i + 1; v2 < t.length;) {
	                if ((t[v1][column] == t[v2][column])) {
	                    nbre++;
	                    if (v2 + 1 < t.length && ((t[v2][column] == t[v2 + 1][column]) || (t[v2 + 1][column] == ' '))) {
	                        v1 = v2;
	                        v2++;
	                    } else {
	                        break;
	                    }

	                } else if (t[v2][column] == ' ') {
	                    saut++;
	                    if (saut < 2) {
	                        if (v2 + 1 < t.length) {
	                            if (t[v1][column] == t[v2 + 1][column]) {
	                                v2++;
	                            } else if(t[v2 + 1][column] == ' ') {
	                                v2--;
	                                saut++;
	                                break;
	                            }else{
	                                v2--;
	                                break;
	                            }
	                        } else {
	                            v2--;
	                            break;
	                        }
	                    } else {
	                        v2--;
	                        saut--;
	                        break;
	                    }
	                } else {
	                    v2--;
	                    break;
	                }
	            }
	            int init = i;
	            int fin = v2;
	            boolean trou = false;
	            if(nbre == number - 1){
	                for (int u = init; u <= fin; u++) {
	                    if (t[u][column] == ' ') {
	                        trou = true;
	                        break;
	                    }
	                }
	            }
	            if (nbre < 1 || nbre > number) {

	            } else if (nbre == number - 1 && trou) {
	                infoTab[2][(nbre - 1) * 2] += 1;
	            } else if (nbre == number) {
	                boolean flag = false;
	                for (int k = init; k < fin; k++) {
	                    if (t[k][column] != t[k + 1][column]) {
	                        flag = true;
	                    }
	                }
	                if (!flag) {
	                    infoTab[2][infoTab[0].length - 1] += 1;
	                }
	            } else {
	                if (plage == number) {
	                    infoTab[2][(nbre - 1) * 2] += 1;
	                } else {
	                    if (t[deb + 1][column] == ' ' && t[fini - 1][column] == ' ') {
	                        infoTab[2][(nbre - 1) * 2 + 1] += 1;
	                    } else {
	                        infoTab[2][(nbre - 1) * 2] += 1;
	                    }
	                }

	            }
	            i = fin + saut;
	        }
	    }

	    
	    private void checkDiag(int row, int column, char[][] t) {
	            checkDiagBGs(row, column, t);
	        
	            checkDiagBDs(row, column, t);
	    }
	    
	    

	    private void checkDiagBDs(int row, int column, char[][] t) {
	        if(visit.contains(""+row+"-"+column)){
//	            System.out.println(""+row+"-"+column+" déjà");
	        }
	        else if (t[row][column] == ' ' || t[row][column] == ((player == 1) ? 'O' : 'X')) {
	        } else if ((row - 1 >= 0 && column - 1 >= 0) && ((t[row - 1][column - 1] == ((player == 1) ? 'X' : 'O')))) {
	        } else {

	            /*Vérifie qu'on peut avoir n pieces dans cet alignement*/
	            int deb = 0, fini = 0;
	            int z1 = row - 1, z2 = column - 1;
	            while (z1 >= 0 && z2 >= 0) {
	                if (t[z1][z2] != ((player == 1) ? 'O' : 'X')) {
	                    deb++;
	                } else {
	                    break;
	                }
	                z1--;
	                z2--;
	            }

	            z1 = row + 1;
	            z2 = column + 1;
	            while (z1 < t.length && z2 < t[0].length) {
	                if (t[z1][z2] != ((player == 1) ? 'O' : 'X')) {
	                    fini++;
	                } else {
	                    break;
	                }
	                z1++;
	                z2++;
	            }
	            int plage = fini + deb + 1;
//	            }
	            if (plage < number) {
	                return;
	            }
	            /*Fin de vérification*/
	            
	            visit.add(""+row+"-"+column);
//	            System.out.println("cacaca"+row+"-"+column+" début");
	            int nbre = 1, v1, v2, v3, v4;
	            int saut = 0;
	            for (v1 = row, v2 = row + 1, v3 = column, v4 = column + 1; v2 < t.length && v4 < t.length;) {
	                if ((t[v1][v3] == t[v2][v4])) {
	                    nbre++;
	                    visit.add(""+v2+"-"+v4);
//	                    System.out.println("a "+v2+"-"+v4);
	                    if (v2 + 1 < t.length && v4 + 1 < t.length && ((t[v2][v4] == t[v2 + 1][v4 + 1]) || (t[v2 + 1][v4 + 1] == ' '))) {
	                        v1 = v2;
	                        v3 = v4;
	                        v2++;
	                        v4++;
	                        visit.add(""+v2+"-"+v4);
//	                        System.out.println("b "+(v2+1)+"-"+(v4+1));
	                    } else {
	                        break;
	                    }
	                } else if (t[v2][v4] == ' ') {
	                    saut++;
	                    visit.add(""+v2+"-"+v4);
//	                    System.out.println("c "+v2+"-"+v4);
	                    if (saut < 2) {
	                        if (v2 + 1 < t.length && v4 + 1 < t.length) {
	                            if (t[v1][v3] == t[v2 + 1][v4 + 1]) {
	                                v2++;
	                                v4++;
	                                visit.add(""+v2+"-"+v4);
//	                                System.out.println("d "+(v2+1)+"-"+(v4+1));
	                            } else if(t[v2 + 1][v4 + 1] == ' ') {
	                                saut--;
	                                v2--;
	                                v4--;
	                                visit.add(""+v2+"-"+v4);
//	                                System.out.println("e "+(v2+1)+"-"+(v4+1));
	                                break;
	                            }
	                            else{
	                               saut--;
	                                v2--;
	                                v4--;
	                                break; 
	                            }
	                        } else {
	                            saut--;
	                            v2--;
	                            v4--;
	                            break;
	                        }
	                    } else {
	                        v2--;
	                        v4--;
	                        break;
	                    }
	                } else {
	                    v2--;
	                    v4--;
	                    break;
	                }
	            }
	            
	            boolean trou = false;
	            if(nbre == number - 1){
	                int u = row + 1, v = column + 1;
	                while (u < v2 && v < v4) {
	                    if (t[u][v] == ' ') {
	                        trou = true;
	                        break;
	                    }
	                    u++;
	                    v++;
	                }
	            }
	            if (nbre < 1 || nbre > number) {

	            } else if (nbre == number - 1 && trou) {
	                infoTab[0][(nbre - 1) * 2] += 1;
	            } else if (nbre == number) {
	                boolean flag = false;
	                int m = row, n = column;
	                while (m < v2 && n < v4) {
	                    if (t[m][n] != t[m + 1][n + 1]) {
	                        flag = true;
	                    }
	                    m++;
	                    n++;
	                }
	                if (!flag) {
	                    infoTab[0][infoTab[0].length - 1] += 1;
	                }
	            } else {
	                if (plage == number) {
	                    infoTab[0][(nbre - 1) * 2] += 1;
	                } else {
	                    if (t[row - deb][column - deb] == ' ' && t[row + fini][column + fini] == ' ') {
	                        infoTab[0][(nbre - 1) * 2 + 1] += 1;
	                    } else {
	                        infoTab[0][(nbre - 1) * 2] += 1;
	                    }
	                }

	            }
	        }


	        if(visit1.contains(""+row+"-"+column)){
	        }
	        else if (t[row][column] == ' ' || t[row][column] == ((player == 1) ? 'X' : 'O')) {
	        } else if ((row - 1 >= 0 && column - 1 >= 0) && ((t[row - 1][column - 1] == ((player == 1) ? 'O' : 'X')))) {
	        } else {

	            /*Vérifie qu'on peut avoir n pieces dans cet alignement*/
	            int deb = 0, fini = 0;
	            int z1 = row - 1, z2 = column - 1;
	            while (z1 >= 0 && z2 >= 0) {
	                if (t[z1][z2] != ((player == 1) ? 'X' : 'O')) {
	                    deb++;
	                } else {
	                    break;
	                }
	                z1--;
	                z2--;
	            }

	            z1 = row + 1;
	            z2 = column + 1;
	            while (z1 < t.length && z2 < t[0].length) {
	                if (t[z1][z2] != ((player == 1) ? 'X' : 'O')) {
	                    fini++;
	                } else {
	                    break;
	                }
	                z1++;
	                z2++;
	            }
	            int plage = fini + deb + 1;
	            if (plage < number) {
	                return;
	            }
	            /*Fin de vérification*/
	            
	            visit1.add(""+row+"-"+column);
	            int nbre = 1, v1, v2, v3, v4;
	            int saut = 0;
	            for (v1 = row, v2 = row + 1, v3 = column, v4 = column + 1; v2 < t.length && v4 < t.length;) {
	                if ((t[v1][v3] == t[v2][v4])) {
	                    visit1.add(""+v2+"-"+v4);
	                    nbre++;
	                    if (v2 + 1 < t.length && v4 + 1 < t.length && ((t[v2][v4] == t[v2 + 1][v4 + 1]) || (t[v2 + 1][v4 + 1] == ' '))) {
	                        v1 = v2;
	                        v3 = v4;
	                        v2++;
	                        v4++;
	                        visit1.add(""+v2+"-"+v4);
	                    } else {
	                        break;
	                    }

	                } else if (t[v2][v4] == ' ') {
	                    saut++;
	                    visit1.add(""+v2+"-"+v4);
	                    if (saut < 2) {
	                        if (v2 + 1 < t.length && v4 + 1 < t.length) {
	                            if (t[v1][v3] == t[v2 + 1][v4 + 1]) {
	                                v2++;
	                                v4++;
	                                visit1.add(""+v2+"-"+v4);
	                            } else if(t[v2 + 1][v4 + 1] == ' ') {
	                                saut--;
	                                v2--;
	                                v4--;
	                                visit1.add(""+v2+"-"+v4);
	                                break;
	                            } else{
	                                saut--;
	                                v2--;
	                                v4--;
	                                break;
	                            }
	                        } else {
	                            saut--;
	                            v2--;
	                            v4--;
	                            break;
	                        }
	                    } else {
	                        v2--;
	                        v4--;
	                        break;
	                    }
	                } else {
	                    v2--;
	                    v4--;
	                    break;
	                }
	            }
	            
	            boolean trou = false;
	            if(nbre == number - 1){
	                int u = row + 1, v = column + 1;
	                while (u < v2 && v < v4) {
	                    if (t[u][v] == ' ') {
	                        trou = true;
	                        break;
	                    }
	                    u++;
	                    v++;
	                }
	            }
	            if (nbre < 1 || nbre > number) {

	            } else if (nbre == number - 1 && trou) {
	                infoTab[2][(nbre - 1) * 2] += 1;
	            } else if (nbre == number) {
	                boolean flag = false;
	                int m = row, n = column;
	                while (m < v2 && n < v4) {
	                    if (t[m][n] != t[m + 1][n + 1]) {
	                        flag = true;
	                    }
	                    m++;
	                    n++;
	                }
	                if (!flag) {
	                    infoTab[2][infoTab[0].length - 1] += 1;
	                }
	            } else {
	                if (plage == number) {
	                    infoTab[2][(nbre - 1) * 2] += 1;
	                } else {
	                    if (t[row - deb][column - deb] == ' ' && t[row + fini][column + fini] == ' ') {
	                        infoTab[2][(nbre - 1) * 2 + 1] += 1;
	                    } else {
	                        infoTab[2][(nbre - 1) * 2] += 1;
	                    }
	                }
	            }
	        }
	    }


	    private void checkDiagBGs(int row, int column, char[][] t) {
	        if(visit2.contains(""+row+"-"+column)){
//	            System.out.println(""+row+"-"+column+" dejàà");
	        }
	        else if (t[row][column] == ' ' || t[row][column] == ((player == 1) ? 'O' : 'X')) {
	        } else if ((row - 1 >= 0 && column + 1 < t.length) && ((t[row - 1][column + 1] == ((player == 1) ? 'X' : 'O')))) {
	        } else {

	            /*Vérifie qu'on peut avoir n pieces dans cet alignement*/
	            int deb = 0, fini = 0;
	            int z1 = row - 1, z2 = column + 1;
	            while (z1 >= 0 && z2 < t[0].length) {
	                if (t[z1][z2] != ((player == 1) ? 'O' : 'X')) {
	                    deb++;
	                } else {
	                    break;
	                }
	                z1--;
	                z2++;
	            }

	            z1 = row + 1;
	            z2 = column - 1;
	            while (z1 < t.length && z2 >= 0) {
	                if (t[z1][z2] != ((player == 1) ? 'O' : 'X')) {
	                    fini++;
	                } else {
	                    break;
	                }
	                z1++;
	                z2--;
	            }
	            int plage = fini + deb + 1;
	            if (plage < number) {
	                return;
	            }
	            /*Fin de vérification*/
	            
	            visit2.add(""+row+"-"+column);
//	            System.out.println(""+row+"-"+column+" debut");
	            int nbre = 1, v1, v2, v3, v4;
	            int saut = 0;
	            for (v1 = row, v2 = row + 1, v3 = column, v4 = column - 1; v2 < t.length && v4 >= 0;) {
	                if ((t[v1][v3] == t[v2][v4])) {
	                    nbre++;
	                    visit2.add(""+v2+"-"+v4);
//	                    System.out.println(""+v2+"-"+v4);
	                    if (v2 + 1 < t.length && v4 - 1 >= 0 && ((t[v2][v4] == t[v2 + 1][v4 - 1]) || (t[v2 + 1][v4 - 1] == ' '))) {
	                        v1 = v2;
	                        v3 = v4;
	                        v2++;
	                        v4--;
	                        visit2.add(""+v2+"-"+v4);
//	                        System.out.println(""+v2+"-"+v4);
	                    } else {
	                        break;
	                    }

	                } else if (t[v2][v4] == ' ') {
	                    saut++;
	                    visit2.add(""+v2+"-"+v4);
//	                    System.out.println(""+v2+"-"+v4);
	                    if (saut < 2) {
	                        if (v2 + 1 < t.length && v4 - 1 >= 0) {
	                            if (t[v1][v3] == t[v2 + 1][v4 - 1]) {
	                                v2++;
	                                v4--;
	                                visit2.add(""+v2+"-"+v4);
//	                                System.out.println(""+v2+"-"+v4);
	                            } else if(t[v2 + 1][v4 - 1] == ' ') {
	                                v2--;
	                                v4++;
	                                visit2.add(""+v2+"-"+v4);
//	                                System.out.println(""+v2+"-"+v4);
	                                break;
	                            } else {
	                                v2--;
	                                v4++;
	                                break;
	                            }
	                        } else {
	                            v2--;
	                            v4++;
	                            break;
	                        }
	                    } else {
	                        v2--;
	                        v4++;
	                        break;
	                    }
	                } else {
	                    v2--;
	                    v4++;
	                    break;
	                }
	            }
	            
	            boolean trou = false;
	            if(nbre == number - 1){
	                int u = row + 1, v = column - 1;
	                while (u < v2 && v > v4) {
	                    if (t[u][v] == ' ') {
	                        trou = true;
	                        break;
	                    }
	                    u++;
	                    v--;
	                }
	            }
	            if (nbre < 1 || nbre > number) {

	            } else if (nbre == number - 1 && trou) {
	                infoTab[0][(nbre - 1) * 2] += 1;
	            } else if (nbre == number) {
	                boolean flag = false;
	                int m = row, n = column;
	                while (m < v2 && n > v4) {
	                    if (t[m][n] != t[m + 1][n - 1]) {
	                        flag = true;
	                    }
	                    m++;
	                    n--;
	                }
	                if (!flag) {
	                    infoTab[0][infoTab[0].length - 1] += 1;
	                }
	            } else {
	                if (plage == number) {
	                    infoTab[0][(nbre - 1) * 2] += 1;
	                } else {
	                    if (t[row - deb][column + deb] == ' ' && t[row + fini][column - fini] == ' ') {
	                        infoTab[0][(nbre - 1) * 2 + 1] += 1;
	                    } else {
	                        infoTab[0][(nbre - 1) * 2] += 1;
	                    }
	                }

	            }
	        }


	        if(visit3.contains(""+row+"-"+column)){
	        }
	        else if (t[row][column] == ' ' || t[row][column] == ((player == 1) ? 'X' : 'O')) {
	        } else if ((row - 1 >= 0 && column + 1 < t.length) && ((t[row - 1][column + 1] == ((player == 1) ? 'O' : 'X')))) {
	        } else {

	            /*Vérifie qu'on peut avoir n pieces dans cet alignement*/
	            int deb = 0, fini = 0;
	            int z1 = row - 1, z2 = column + 1;
	            while (z1 >= 0 && z2 < t[0].length) {
	                if (t[z1][z2] != ((player == 1) ? 'X' : 'O')) {
	                    deb++;
	                } else {
	                    break;
	                }
	                z1--;
	                z2++;
	            }

	            z1 = row + 1;
	            z2 = column - 1;
	            while (z1 < t.length && z2 >= 0) {
	                if (t[z1][z2] != ((player == 1) ? 'X' : 'O')) {
	                    fini++;
	                } else {
	                    break;
	                }
	                z1++;
	                z2--;
	            }
	            int plage = fini + deb + 1;
	            if (plage < number) {
	                return;
	            }
	            /*Fin de vérification*/
	            
	            visit3.add(""+row+"-"+column);
	            int nbre = 1, v1, v2, v3, v4;
	            int saut = 0;
	            for (v1 = row, v2 = row + 1, v3 = column, v4 = column - 1; v2 < t.length && v4 >= 0;) {
	                if ((t[v1][v3] == t[v2][v4])) {
	                    nbre++;
	                    visit3.add(""+v2+"-"+v4);
	                    if (v2 + 1 < t.length && v4 - 1 >= 0 && ((t[v2][v4] == t[v2 + 1][v4 - 1]) || (t[v2 + 1][v4 - 1] == ' '))) {
	                        v1 = v2;
	                        v3 = v4;
	                        v2++;
	                        v4--;
	                        visit3.add(""+v2+"-"+v4);
	                    } else {
	                        break;
	                    }

	                } else if (t[v2][v4] == ' ') {
	                    saut++;
	                    visit3.add(""+v2+"-"+v4);
	                    if (saut < 2) {
	                        if (v2 + 1 < t.length && v4 - 1 >= 0) {
	                            if (t[v1][v3] == t[v2 + 1][v4 - 1]) {
	                                v2++;
	                                v4--;
	                                visit3.add(""+v2+"-"+v4);
	                            } else if(t[v2 + 1][v4 - 1] == ' ') {
	                                v2--;
	                                v4++;
	                                visit3.add(""+v2+"-"+v4);
	                                break;
	                            } else {
	                                v2--;
	                                v4++;
	                                break;
	                            }
	                        } else {
	                            v2--;
	                            v4++;
	                            break;
	                        }
	                    } else {
	                        v2--;
	                        v4++;
	                        break;
	                    }
	                } else {
	                    v2--;
	                    v4++;
	                    break;
	                }
	            }
	            
	            boolean trou = false;
	            if(nbre == number - 1){
	                int u = row + 1, v = column - 1;
	                while (u < v2 && v > v4) {
	                    if (t[u][v] == ' ') {
	                        trou = true;
	                        break;
	                    }
	                    u++;
	                    v--;
	                }
	            }
	            if (nbre < 1 || nbre > number) {

	            } else if (nbre == number - 1 && trou) {
	                infoTab[2][(nbre - 1) * 2] += 1;
	            } else if (nbre == number) {
	                boolean flag = false;
	                int m = row, n = column;
	                while (m < v2 && n > v4) {
	                    if (t[m][n] != t[m + 1][n - 1]) {
	                        flag = true;
	                    }
	                    m++;
	                    n--;
	                }
	                if (!flag) {
	                    infoTab[2][infoTab[0].length - 1] += 1;
	                }
	            } else {
	                if (plage == number) {
	                    infoTab[2][(nbre - 1) * 2] += 1;
	                } else {
	                    if (t[row - deb][column + deb] == ' ' && t[row + fini][column - fini] == ' ') {
	                        infoTab[2][(nbre - 1) * 2 + 1] += 1;
	                    } else {
	                        infoTab[2][(nbre - 1) * 2] += 1;
	                    }
	                }

	            }
	        }
	    }


}
