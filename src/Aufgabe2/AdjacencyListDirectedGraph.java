// O. Bittel;
// 19.03.2018

package Aufgabe2;

import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.Set;

/**
 * Implementierung von DirectedGraph mit einer doppelten TreeMap 
 * für die Nachfolgerknoten und einer einer doppelten TreeMap 
 * für die Vorgängerknoten. 
 * <p>
 * Beachte: V muss vom Typ Comparable&lt;V&gt; sein.
 * <p>
 * Entspicht einer Adjazenzlisten-Implementierung 
 * mit schnellem Zugriff auf die Knoten.
 * @author Oliver Bittel
 * @since 19.03.2018
 * @param <V> Knotentyp.
 */
public class AdjacencyListDirectedGraph<V> implements DirectedGraph<V> {
    // doppelte Map für die Nachfolgerknoten:
    private final Map<V, Map<V, Double>> succ = new TreeMap<>(); 
    
    // doppelte Map für die Vorgängerknoten:
    private final Map<V, Map<V, Double>> pred = new TreeMap<>(); 

    private int numberEdge = 0;

	@Override
	public boolean addVertex(V v) {
		return true;
		/*succ.put(v, new TreeMap<V, Double>());
		pred.put(v, new TreeMap<V, Double>());
		if(containsVertex(v)) return true;
		return false;*/
		//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean addEdge(V v, V w, double weight) {
    	/*if(this.containsEdge(v, w)) {
    		succ.get(v).put(w, weight);
    		pred.get(w).put(v, weight);
    		this.numberEdge++;
    		return true;
    	}
    	if(containsVertex(v)) if(!addVertex(v)) return false;
    	if(containsVertex(w)) if(!addVertex(w)) return false;
    	succ.get(v).put(w, weight);
    	pred.get(w).put(v, weight);
    	if(containsEdge(v, w)){
    		this.numberEdge++;
    		return true;
    	}*/
    	return false;
		//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean addEdge(V v, V w) {
    	if(addEdge(v, w, 1.0)) return true;
    	/*if(containsEdge(v, w)) return false;
    	if(containsVertex(v)) if(!addVertex(v)) return false;
    	if(containsVertex(w)) if(!addVertex(w)) return false;
    	succ.get(v).put(w, 1.0);
    	pred.get(w).put(v, 1.0);
    	if(containsEdge(v, w)) return true;*/
    	return false;
		//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean containsVertex(V v) {
    	if(succ.containsKey(v)) return true;
    	return false;
		//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean containsEdge(V v, V w) {
    	if(succ.get(v).containsKey(w) && pred.get(w).containsKey(v)) return true;
		return false;
    	//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double getWeight(V v, V w) {
    	return succ.get(v).get(w);
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

	
    @Override
    public int getInDegree(V v) {
    	return pred.get(v).size();
		//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getOutDegree(V v) {
    	return succ.get(v).size();
		//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
	
	@Override
    public Set<V> getVertexSet() {
		return Collections.unmodifiableSet(succ.keySet()); // nicht modifizierbare Sicht
    }

    @Override
    public Set<V> getPredecessorVertexSet(V v) {
    	return Collections.unmodifiableSet(pred.get(v).keySet());
		//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Set<V> getSuccessorVertexSet(V v) {
    	return Collections.unmodifiableSet(succ.get(v).keySet());
		//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getNumberOfVertexes() {
		return succ.size();
    	//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getNumberOfEdges() {
    	//int size = 0;
    	return this.numberEdge;
    	/*for(Entry<V, Map<V, Double>> entry : succ.entrySet()){
    		size = size + entry.getKey().size();
    	}
    	return size;*/
		//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
	
	@Override
    public 
	DirectedGraph<V> invert() {
		DirectedGraph<V> inv = new AdjacencyListDirectedGraph<V>();
		for(V v: getVertexSet()){
			for(V w: getPredecessorVertexSet(v)){
				inv.addEdge(w, v, getWeight(v, w));
			}
		}
		return inv;
		//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(V v: getVertexSet()){
			for(V w: getSuccessorVertexSet(v)){
				sb.append(v);
				sb.append(" --> ");
				sb.append(w);
				sb.append(" = ");
				sb.append(getWeight(v, w));
				sb.append("\n");
			}
		}
		return sb.toString();
		//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
	
	
	public static void main(String[] args) {
		System.out.println("Hallo Welt");
		/*DirectedGraph<Integer> g = new AdjacencyListDirectedGraph<>();
		g.addEdge(1,2);
		g.addEdge(2,5);
		g.addEdge(5,1);
		g.addEdge(2,6);
		g.addEdge(3,7);
		g.addEdge(4,3);
		g.addEdge(4,6);
		g.addEdge(7,4);
		
		
		System.out.println(g.getNumberOfVertexes());	// 7
		System.out.println(g.getNumberOfEdges());		// 8
		System.out.println(g.getVertexSet());	// 1, 2, ..., 7
		System.out.println(g);
			// 1 --> 2 weight = 1.0 
			// 2 --> 5 weight = 1.0
			// 2 --> 6 weight = 1.0
			// 3 --> 7 weight = 1.0
			// ...
		
		System.out.println("");
		System.out.println(g.getOutDegree(2));				// 2
		System.out.println(g.getSuccessorVertexSet(2));	// 5, 6
		System.out.println(g.getInDegree(6));				// 2
		System.out.println(g.getPredecessorVertexSet(6));	// 2, 4
		
		System.out.println("");
		System.out.println(g.containsEdge(1,2));	// true
		System.out.println(g.containsEdge(2,1));	// false
		System.out.println(g.getWeight(1,2));	// 1.0	
		g.addEdge(1, 2, 5.0);
		System.out.println(g.getWeight(1,2));	// 5.0	
		
		System.out.println("");
		System.out.println(g.invert());
			// 1 --> 5 weight = 1.0
			// 2 --> 1 weight = 1.0
			// 3 --> 4 weight = 1.0 
			// 4 --> 7 weight = 1.0
			// ...
			
		Set<Integer> s = g.getSuccessorVertexSet(2);
		System.out.println(s);
		s.remove(5);	// Laufzeitfehler! Warum? */
	}
}
