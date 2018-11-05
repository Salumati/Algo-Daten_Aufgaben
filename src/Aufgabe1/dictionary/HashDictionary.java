package Aufgabe1.dictionary;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.LinkedList;

public class HashDictionary<K,V> implements Dictionary<K, V> {

	/*
	 * (non-Javadoc)
	 * @see Aufgabe1.dictionary.Dictionary#insert(java.lang.Object, java.lang.Object)
	 * 		
	 * TODO:	1. implementiere ein HAshtabelle
	 * 			2. Überprüfe die Hashtabelle mit den Testprogramm
	 * 			3. Überprüfe die Tabelle mit einer Performancemessung
	 */
	
	private static int size = 0; // Anzahl Elemente
	private static int prim; // Primzahl, die die Groese der Hashliste bestimmt.
	private static int loadFactor = 10;
	
	private LinkedList<Entry<K, V>>[] tab;
	
	//Konstruktor
	public HashDictionary(int p) {
		size = 0;
		prim = p;
		tab  = new LinkedList[prim];
		for(int i = 0; i < tab.length; i++) {
			tab[i] = new LinkedList<Entry<K, V>>();
		}
	}
	public HashDictionary() {
		size = 0;
		prim = 31;
		tab  = new LinkedList[prim];
		for(int i = 0; i < tab.length; i++) {
			tab[i] = new LinkedList<Entry<K, V>>();
		}
	}
	
	private int genPrim(int prim) { //soll eine neue Primzahl generieren, die etwa doppelt so gros oder Groeser als die alte ist
		int i = 2 * prim;
		if(i%2 == 0) i++;
		while(i%3 == 0 || i%5 == 0 || i%7 == 0) {
			i = i+2;
		}
		return i;
	}
	
	private void ensureCapacity() {
		int oldPrim = prim;
		prim = genPrim(prim);
		LinkedList<Entry<K,V>>[] temp = new LinkedList[prim];
		for(int i = 0; i < temp.length; i++) {
			temp[i] = new LinkedList<Entry<K, V>>();
		}
		
		for(int i = 0; i < oldPrim; i++) {
			for(Entry<K,V> e: tab[i]) {
				int pos = getPos(e.getKey());
				Entry<K,V> ent = new Entry<>(e.getKey(), e.getValue());
				temp[pos].add(ent);
			}
		}
		
		tab = temp;
	}

	@Override
	public V insert(K key, V value) {
		if (size()/prim > loadFactor) ensureCapacity();
		int pos = getPos(key); //position in Liste bestimmen
		for(Entry<K,V> e: tab[pos]) {
		if(e.getKey().equals(key)) return e.setValue(value);
	}
		Entry<K, V> ent = new Dictionary.Entry<K, V>(key, value);
		tab[pos].add(ent);
		size++;
		return null;
	}

	@Override
	public V search(K key) {
		int pos = getPos(key);
		for(Entry<K, V> e: tab[pos]) {
			if(e.getKey().equals(key)) return e.getValue();
		}
		return null;
	}

	@Override
	public V remove(K key) {
		int pos = getPos(key);
		int rpos = 0;
		for(Entry<K, V> e: tab[pos]) {
			if(e.getKey().equals(key)) {
				V val = e.getValue();
				//Entry<K, V> ent = new Entry<>(key, val);
				tab[pos].remove(rpos);
				size--;
				return val;
			}
			rpos++;
		}
		
		return null;
	}
	
	private int getPos(K key) {
		return key.hashCode() % prim;
	}

	@Override
	public int size() { //gibt ANzahl der Elemente in der Liste zurueck
		return size;
	}

	@Override
	public Iterator<Entry<K, V>> iterator() {
		// TODO Auto-generated method stub
		return new HashIterator();
	}
	
	private class HashIterator implements Iterator<Entry<K, V>>{
		int currInd = 0;
		Iterator<Entry<K,V>> currListIterator = tab[0].iterator();
		
		public boolean hasNext() {
			if(currListIterator.hasNext()) return true;
			while (++currInd < tab.length) {
				currListIterator = tab[currInd].iterator();
				if(currListIterator.hasNext()) return true;
			}
			return false;
		}
		public Entry<K,V> next() {
			try {
				if(!hasNext()) {
					throw new Exception("kein hasNext");
					}
				return currListIterator.next();
				} catch(Exception x) {
					System.out.println("kein Next");
					return null;
				}
		}
	}
	
	/*public static void main(String[] args) {
		HashDictionary<String, String> d = new HashDictionary<>(1);
		System.out.println(d.size());
		d.insert("a", "Hello");
		System.out.printf("Größe: %d, Wert vorhanden: %s%n", d.size(), d.search("a"));
		d.remove("a");
		System.out.printf("Größe: %d, Wert vorhanden: %s%n", d.size(), d.search("a"));
		d.insert("blau", "bl");
		d.insert("Katze", "cat");
		d.insert("Hund", "dog");
		d.insert("die", "the");
		d.insert("stirb", "die");
		d.insert("blau", "blue");
		System.out.println(d.search("bl"));
		System.out.println(d.search("Katze"));
		System.out.println(d.search("Hund"));
		System.out.println(d.search("die"));
		System.out.println(d.search("stirb"));
		d.remove("Hund");
		System.out.println(d.search("Hund"));
		System.out.println(d.search("blau"));

		
	}*/

}
