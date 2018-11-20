// O. Bittel
// 9.3.2018

package Aufgabe9;

import java.util.Iterator;
import java.util.LinkedList;

import java.*;

public class BinarySearchTree<T extends Comparable<? super T>> implements Iterable<T>{

    static private class Node<T extends Comparable<? super T>> {
        T data;
        Node<T> left;
        Node<T> right;
        Node(T x) {
            data = x;
            left = null;
            right = null;
        }
    }

    private Node<T> root = null;
    private int size = 0;


    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("BinarySearchTree: ");
        appendR(s,root);
        s.append(" size = ").append(size);
        return s.toString();
    }

    private static void appendR(StringBuilder s, Node p) {
        if (p != null) {
            appendR(s,p.left);
            s.append(p.data).append(", ");
            appendR(s,p.right);
        }
    }

    public boolean contains(T x) {
        return containsR(x,root);
    }

    private boolean containsR(T x, Node<T> p) {
	if (p == null)
            return false;
	else if (x.compareTo(p.data) < 0)
            return containsR(x,p.left);
	else if (x.compareTo(p.data) > 0)
            return containsR(x,p.right);
	else
            return true;
    }

    public void insert(T x) {
        root = insertR(x,root);
    }

    private Node insertR(T x, Node<T> p) {
	if (p == null) {
            size++;
            return new Node(x);
        }
	if (x.compareTo(p.data) < 0)
            p.left = insertR(x,p.left);
	else if (x.compareTo(p.data) > 0)
            p.right = insertR(x,p.right);
	// im else-Fall ist nicht zu tun; keine doppelten Werte
	return p;
    }

    public void remove(T x) {
        root = removeR(x,root);
    }

    private Node removeR(T x, Node<T> p) {
        if (p == null)
            return null;
	if (x.compareTo(p.data) < 0)
            p.left = removeR(x,p.left);
	else if (x.compareTo(p.data) > 0)
            p.right = removeR(x,p.right);
        else {
            // Knoten loeschen:
            if (p.left == null || p.right == null) {
                // One or no child can be deleted directly:
                size--;
                p = (p.left != null) ? p.left : p.right;
            }
            else {
                // Two children
                p.data = getMin(p.right);
                p.right = removeR(p.data,p.right);
            }
        }
	return p;
    }

    private T getMin(Node<T> p) {
        assert (p != null);
        while(p.left != null)
            p = p.left;
        return p.data;
    }

    public void clear() {
        root = null;
        size = 0;
    }

    public int getSize() {
        return size;
    }

    void prettyPrint() {
    	StringBuilder sb = new StringBuilder();
    	appendPP(sb, 0, root);
    	System.out.println(sb.toString());
    }
    
    private static void appendPP(StringBuilder s, int i, Node p) {
        if (p != null) {
        	if(i != 0) {
        		s.append("\n");
        	
        	for(int j = 1; j < i; j++)
        	{
        		s.append("   ");
        	}
        	s.append("|_ ");
        	}
        	s.append(p.data);
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
    
    int height() {
    	return appendH(root, 0);
    }
    private static int appendH(Node p, int tiefe) {
        if (p != null) {
        	int left;
        	int right;
            left = appendH(p.left, tiefe + 1);
            right = appendH(p.right, tiefe + 1);
            if(left >= right)
            {
            	return left;
            } else {
            	return right;
            }
        }
        return tiefe -1;
    }
    
    T removeMaxIt() {
    	T max;
    	Node<T> p = root;
    	while(p.right != null)
    	{
    		p = p.right;
    	}
		max = p.data;
    	remove(max);
    	return max;
    }
    T removeMaxRec(Node<T> p) {
    	T max = p.data;
    	if(p.right != null) {
    		max = removeMaxRec(p.right);
    	} else {
    		remove(max);
    	}
    	return max;
    }
    
    public Iterator<T> iterator(){
    	LinkedList<T> l = new LinkedList<>();
    	sortList(l, root);
    	return l.iterator();
    }
    private void sortList(LinkedList<T> li, Node<T> p) {
    	if(p != null) {
    		sortList(li, p.left);
    		li.add(p.data);
    		sortList(li, p.right);
    	}
    }
    	
    public static void main(String[] args) {

        BinarySearchTree<Integer> t = new BinarySearchTree();
        t.insert(5);
        t.insert(3);
        t.insert(1);
        t.insert(4);
        t.insert(8);
        t.insert(6);
        t.insert(7);
        System.out.println(t);
        t.prettyPrint();
        System.out.println("Höhe von t: " + t.height());
        
        System.out.println("Höchstes element von t (re): " + t.removeMaxRec(t.root));
        System.out.println("Höchstes element von t (it): " + t.removeMaxIt());
        
        BinarySearchTree<Integer> t2 = new BinarySearchTree();
        System.out.println("Ende Baum 1");
        int[] a = {7, 2, 8, 1, 4, 3, 6};
        for(int x : a) {
        	t2.insert(x);
        }
        System.out.println("Elemente von Baum 2:");
        for(Integer x : t2) {
        	System.out.println(x);
        }
    }
}
