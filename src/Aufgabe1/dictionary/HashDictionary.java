package Aufgabe1.dictionary;

import java.util.Iterator;
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
	private static int prim = 31; // Primzahl, die die Groese der Hashliste bestimmt.
	
	private LinkedList<Entry<K, V>>[] tab = new LinkedList[prim];
	
	//Konstruktor
	private HashDictionary() {
		size = 0;
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
		int oldPrim = this.prim;
		int newPrim = genPrim(oldPrim);
		
		LinkedList[] oldList = tab; 
		tab = new LinkedList[newPrim];
		for(Entity e: oldList) {
			insert(e);
		}
		// erhhoet die Groese der Liste um eine etwa doppelt so grose Primzahl
		// Gibt es ne moeglichkeit einfach an eine Primzahl zu kommen?
	}
	
	private void checkSize() {
			if (size()/prim > 10) ensureCapcity();
	}
	
	@Override
	public V insert(K key, V value) {
		// TODO muss mod K rechnen und danach den V einordnen, bei belegten platz sucht es den nächsten freien
		
		int pos = (int) key % prim; //position in Liste bestimmen
		Entry<K, V> ent = new Dictionary.Entry<K, V>(key, value);
		tab[pos].add(ent);
		size++;
		return null;
	}

	@Override
	public V search(K key) {
		int pos = (int) key % prim;
		if(tab[pos].contains(key)) {
			return get(key.getValue);
		}
		return null;
	}
	
	public boolean tsearch(K key) {
		int pos = (int) key % prim;
		if(tab[pos].contains(key)) {
			return true;
		}
		return false;
	}

	@Override
	public V remove(K key) {
		size--;
		return null;
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
		
		public Entry<K,V> next() {
			//if(hasNext()) throw new; //TODO: Fehlerbehandlung, schau mal was da sinnvoll ist
			return currListIterator.next();
		}
		
		public boolean hasNext() {
			if(currListIterator.hasNext()) return true;
			while (++currInd < tab.length) {
				currListIterator = tab[currInd].iterator();
				if(currListIterator.hasNext()) return true;
			}
			return false;
		}
	}
	
	public static void main(String[] args) {
		HashDictionary d = new HashDictionary();
		System.out.println(d.size());
		d.insert(1, 1);
		Entry<K, V> e = new Entry<int, int>(1, 1);
		System.out.printf("Größe: %d, Wert vorhanden: %s", d.size(), d.tsearch(1));
		
	}

}
