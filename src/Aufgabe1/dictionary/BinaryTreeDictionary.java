package aufg1;

import Aufgabe1.dictionary.Dictionary;

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
				size++;
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
			size--;
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

	private int	getHeight(Node<K,V> p) {
		if (p ==null)
			return -1;
		else
			return	p.height;
	}

	private int	getBalance(Node<K,V> p) {
		if	(p == null)
			return 0;
		else
			return	getHeight(p.right) - getHeight(p.left);
	}
	private	Node<K,V> balance (Node<K,V> p) {
		if(p ==	null)
			return null;
		p.height = Math.max(getHeight(p.left), getHeight(p.right)) + 1;
		if(getBalance(p) ==	-2) {
			if(getBalance(p.left) <= 0)
				p = rotateRight(p);
			else
				p = rotateLeftRight(p);
		}
		else if(getBalance(p) == +2) {
			if(getBalance(p.right) >= 0)
				p = rotateLeft(p);
			else
				p = rotateRightLeft(p);
		}
		return	p;
	}

	private	Node<K,V> rotateRight(Node<K,V> p) {
		assert	p.left !=null;
		Node<K, V>	q = p.left;
		p.left = q.right;
		q.right = p;
		p.height = Math.max(getHeight(p.left), getHeight(p.right)) + 1;
		q.height = Math.max(getHeight(q.left), getHeight(q.right)) + 1;
		return	q;
	}

	private	Node<K,V> rotateLeft(Node<K,V> p) {
		assert	p.right !=null;
		Node<K, V>	q = p.right;
		p.right = q.left;
		q.left = p;
		q.height = Math.max(getHeight(q.right), getHeight(q.left)) + 1;
		p.height = Math.max(getHeight(p.right), getHeight(p.left)) + 1;

		return	q;
	}

	private	Node<K,V> rotateLeftRight(Node<K,V> p) {
		assert p.left !=null;
		p.left = rotateLeft(p.left);
		return rotateRight(p);
	}

	private	Node<K,V> rotateRightLeft(Node<K,V> p) {
		assert	p.right !=	null;
		p.right = rotateRight(p.right);
		return rotateLeft(p);
	}
	@Override
	public Iterator<Dictionary.Entry<K, V>> iterator() {
		return new TreeIterator();
	}
	class TreeIterator implements Iterator<aufg1.Dictionary.Entry<K, V>>{
		Node<K,V> current = null;
		int i = 0;
		public aufg1.Dictionary.Entry<K, V> next() {
			Node<K,V> p = null;
			if (root != null)
				p = leftMostDescendant(root);

			while(p != null){
				//return new Entry<K,V>(p.key, p.value);
				if(current ==  null || p.key.compareTo(current.key) > 0) {
					current = p;
					i++;
					return new Entry<K,V>(p.key, p.value);
				}

				if (p.right != null)
					p= leftMostDescendant(p.right);
				else
					p = parentOfLeftMostAncestor(p);


			}
			return null;
		}

		@Override
		public boolean hasNext() {
			if(i < size()) return true;
			return false;
		}

		private Node<K,V>  parentOfLeftMostAncestor(Node<K,V>  p) {
			assert p != null;
			while (p.parent != null && p.parent.right  == p)
				p = p.parent;
			return p.parent;	// kann auch null sein
		}

		private Node<K,V>  leftMostDescendant(Node<K,V>  p) {
			assert p != null;
			while (p.left  != null)
				p = p.left;return p;
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
					s.append("   ");//Abstand halten
				}
				s.append("|_ ");//zweig anzeigen
			}
			s.append(p.key);//Schluessel anzeigen
			if(p.parent != null) s.append("->" + p.parent.key);
			appendPP(s, i + 1, p.left);//linkes Kind anzeigen
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