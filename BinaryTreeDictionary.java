package aufg1;

import java.util.Iterator;
import java.util.LinkedList;

public class BinaryTreeDictionary<K extends Comparable<? super K>, V> implements Dictionary<K, V> {
	
	private class Node<K extends Comparable<? super K>, V> {
		int height;
		//Entry<K, V> e = new Entry<K, V>(K key, V value);
		K key;
		V value;
		Node<K, V> left;
		Node<K, V> right;
		Node<K, V> parent;
		Node(K x, V y) {
			height = 0;
			key = x;
			value = y;
			parent = null;
			left = null;
			right = null;
		}
	}
	
	private V oldValue;
	private Node<K, V> root = null;
	private int size = 0;
	@Override
	public V insert(K key, V value) {
		if( root == null){
			size++;
			root = new Node<K,V>(key, value);
			return null;
		}
		Node<K,V> p = insertR(key, value, root);
		
		if(p == null || value.equals(p.value))
		{
			return null;
		}
		V oldValue = p.value;
		p.value = value;
		return oldValue;
	}
	
	private Node<K,V> insertR(K key, V value, Node<K, V> p){
		if(key.compareTo(p.key) < 0) {
			if(p.left == null){
				p.left = new Node<K, V>(key, value);
				p.left.parent = p;
				return null;
			}
			return insertR(key, value, p.left);
		}
		else if(key.compareTo(p.key) > 0){
			if (p.right == null){
				p.right = new Node<K,V>(key, value);
				p.right.parent = p;
				size ++;
				return null;
			}
			return insertR(key, value, p.right);
		}
		return p;
	}

	@Override
	public V search(K key) {
		Node<K,V> p = searchR(key, root);
		if(p != null)
		{
			return p.value;
		}
		return null;
	}
	
	public Node<K,V> searchR(K key, Node<K, V> p){
		if(p == null) return null;
		else if(key.compareTo(p.key) < 0) return searchR(key, p.left);
		else if(key.compareTo(p.key) > 0) return searchR(key, p.right);
		else return p;
	}

	@Override
	public V remove(K key) {
		root = removeR(key, root);
		return oldValue;
	}
	
	public Node<K, V> removeR(K key, Node<K, V> p){
		if(p == null) oldValue = null;
		else if(key.compareTo(p.key) < 0){
			p.left = removeR(key, p.left);
		}
		else if(key.compareTo(p.key) > 0){
			p.right = removeR(key, p.right);
		}
		else if(p.left == null || p.right == null){
			oldValue = p.value;
			p = (p.left	!= null) ? p.left : p.right;
		}
		else {
			MinEntry<K,V> min = new MinEntry<K,V>();
			p.right = getRemMinR(p.right, min);
			oldValue = p.value;
			p.key = min.key;
			p.value = min.value;
			size--;
		}
		return p;
	}
	
	private Node<K,V> getRemMinR(Node<K,V> p, MinEntry<K,V> min)
	{
		assert p!= null;
		if(p.left == null){
			min.key = p.key;
			min.value = p.value;
			p = p.right;
		} else p.left = getRemMinR(p.left, min);
		return p;
	}
	private static class MinEntry<K,V>{
		private K key;
		private V value;
	}
	
	@Override
	public int size() {
		return size;
	}

	@Override
	public Iterator<aufg1.Dictionary.Entry<K, V>> iterator() {
		LinkedList<Entry<K,V>> l = new LinkedList<>();
		sortList(l, root);
		
		return l.iterator();
	}
	
	private void sortList(LinkedList<Entry<K,V>> li, Node<K,V> p){
		if(p != null) {
			sortList(li, p.left);
			li.add(p);
			sortList(li, p.right);
		}
	}
	
	void prettyPrint() {
    	StringBuilder sb = new StringBuilder();
    	appendPP(sb, 0, root);
    	System.out.println(sb.toString());
    }
	private void appendPP(StringBuilder s, int i, Node<K, V> p) {
        if (p != null) {
        	if(i != 0) {
        		s.append("\n");
        	
        	for(int j = 1; j < i; j++)
        	{
        		s.append("   ");
        	}
        	s.append("|_ ");
        	}
        	s.append(p.key);
            appendPP(s, i + 1, p.left);
            if(p.left == null && p.right != null) {
            	s.append("\n");
            	for(int j = 0; j < i; j++) {
            		s.append("   ");
            	}
            	s.append("|_ #");
            	}
            appendPP(s, i + 1, p.right);
            if(p.left != null && p.right == null) {
            	s.append("\n");
            	for(int j = 0; j < i; j++) {
            		s.append("   ");
            	}
            	s.append("|_ #");
            }
        }
	}
}
