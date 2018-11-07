package Aufgabe1.dictionary;

import java.util.NoSuchElementException;
import java.util.Iterator;

//import java.util.Dictionary;

public class SortedArrayDictionary<K extends Comparable<? super K>, V> implements Dictionary<K,V>{
	
	private static final int DEF_CAPACITY = 20;
	private int size = 0;
	private Entry<K, V>[] data = new Entry[DEF_CAPACITY];
	
	private void ensureCapacity(int newCAPACITY)
	{
		if(newCAPACITY < size) {
			return;
		}
		Dictionary.Entry<K, V>[] oldList = data;
		data = new Dictionary.Entry[newCAPACITY];
		System.arraycopy(oldList, 0, data, 0, size);	
	}
	
	private int searchKey(K key) {
		int li = 0;
		int re = size - 1;
		
		while(re >= li) {
			int mid = (li + re)/2;
			
			if(key.compareTo(data[mid].getKey()) < 0) {
				re = mid - 1;
			} else if(key.compareTo(data[mid].getKey()) > 0) {
				li = mid + 1;
			} else {
				return mid;
			}
		}
		return -1;
	}
	
	public V insert(K key, V value) {
		int i = searchKey(key);
		
		if(i != -1) {
			V oldValue = data[i].getValue();
			data[i].setValue(value);
			return oldValue;
		}
		
		if(data.length == size) {
			ensureCapacity(2*size);
		}
		i = size - 1;
		
		while(i >= 0 && key.compareTo(data[i].getKey()) < 0) {
			data[i+1] = data[i];
			i--;
		}
		data[i+1] = new Entry<K,V>(key, value);
		size++;
		return null;
	}
	
	public V search(K key) {
		int i = searchKey(key);
		
		if(i != -1) {
			return data[i].getValue();
		}
		return null;
	}
	
	public V remove(K key) {
		int i = searchKey(key);
		
		if(i != -1) {
			V delValue = data[i].getValue();
			for(i = i; i < size-1; i++) {
				data[i] = data[i+1];
			}
			size--;
			return delValue;
		}
		return null;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Iterator<Entry<K, V>> iterator() {
		// TODO Auto-generated method stub
		return new DictIterator();
	}
	
	class DictIterator implements Iterator<Entry<K, V>> {
		int current = 0;
		public boolean hasNext() {
			if(current < size) {
				return true;
			}
			return false;
		}
		@Override
		public Entry<K, V> next() throws NoSuchElementException {
			// TODO Auto-generated method stub
			if(!hasNext()) {
				throw new NoSuchElementException();
			}
			return data[current++];
		}
	}
} 
