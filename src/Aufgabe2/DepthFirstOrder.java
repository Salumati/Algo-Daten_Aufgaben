// O. Bittel;
// 22.02.2017
package Aufgabe2;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Klasse für Tiefensuche.
 *
 * @author Oliver Bittel
 * @since 22.02.2017
 * @param <V> Knotentyp.
 */
public class DepthFirstOrder<V> {

    private final List<V> preOrder = new LinkedList<>();
    private final List<V> postOrder = new LinkedList<>();
    private final DirectedGraph<V> myGraph;
    private int numberOfDFTrees = 0;
	// ...

    /**
     * Führt eine Tiefensuche für g durch.
     *
     * @param g gerichteter Graph.
     */
    public DepthFirstOrder(DirectedGraph<V> g) {
        myGraph = g;
        Set<V> test = myGraph.getVertexSet();
        // ...
        for(V v : test){
        	if(!preOrder.contains(v)){
        		visitDF(v, myGraph, preOrder);
        		numberOfDFTrees++;
        	}
        }
        List<V> temp = new LinkedList<>();
        for(V v : test){
        	if(!postOrder.contains(v)){
        		visitDFPost(v, myGraph, postOrder, temp);
        	}
        }
    }

    /**
     * Liefert eine nicht modifizierbare Liste (unmodifiable view) mit einer
     * Pre-Order-Reihenfolge zurück.
     *
     * @return Pre-Order-Reihenfolge der Tiefensuche.
     */
    public List<V> preOrder() {
    	return preOrder;
        //return Collections.unmodifiableList(preOrder);
    }
    
    void visitDF(V v, DirectedGraph<V> g, List<V> besucht){
    	besucht.add(v);
    	//Set<V> check = g.getSuccessorVertexSet(v);
    	for(V w : g.getSuccessorVertexSet(v)){
    		if(!besucht.contains(w)) visitDF(w, g, besucht);
    	}
    }
    
    void visitDFPost(V v, DirectedGraph<V> g, List<V> addList, List<V> besucht) {
    	if(besucht.contains(v)) return;
    	besucht.add(v);
    	//Set<V> check = g.getSuccessorVertexSet(v);
    	for(V w : g.getSuccessorVertexSet(v)){
    		if(!addList.contains(w)) visitDFPost(w, g, addList, besucht);
    	}
    	if(!addList.contains(v)) addList.add(v);
    }
    
    /*void visitDFPost(V v, DirectedGraph<V> g, List<V> besucht){
    	besucht.add(v);
    	Set<V> check = g.getPredecessorVertexSet(v);
    	for(V w : g.getPredecessorVertexSet(v)){
    		if(!besucht.contains(w)) visitDFPost(w, g, besucht);
    	}
    }*/
    
    /**
     * Liefert eine nicht modifizierbare Liste (unmodifiable view) mit einer
     * Post-Order-Reihenfolge zurück.
     *
     * @return Post-Order-Reihenfolge der Tiefensuche.
     */
    public List<V> postOrder() {
    	return Collections.unmodifiableList(postOrder);
    }

    /**
     *
     * @return Anzahl der Bäume des Tiefensuchwalds.
     */
    public int numberOfDFTrees() {
    	return numberOfDFTrees;
    }

    public static void main(String[] args) {
        DirectedGraph<Integer> g = new AdjacencyListDirectedGraph<>();
        g.addEdge(1, 2);
        g.addEdge(2, 5);
        g.addEdge(5, 1);
        g.addEdge(2, 6);
        g.addEdge(3, 7);
        g.addEdge(4, 3);
        g.addEdge(4, 6);
        //g.addEdge(7,3);
        g.addEdge(7, 4);

        DepthFirstOrder<Integer> dfs = new DepthFirstOrder<>(g);
        System.out.println(dfs.numberOfDFTrees());	// 2
        System.out.println(dfs.preOrder());		// [1, 2, 5, 6, 3, 7, 4]
        System.out.println(dfs.postOrder());		// [5, 6, 2, 1, 4, 7, 3]

    }
}
