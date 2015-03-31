package recipebook;

// separate chaining hash table
public class HashTable<S extends Comparable<S>,T> {
	int M = 30;	// number of lists
	Node<S,T>[] table;
	
	private class Node<S,T> {
		S key;
		T value;
		Node<S,T> tail;
		
		private void insert(S key, T value) {
			// top node is empty
			if (this.key == null) {
				this.key = key;
				this.value = value;
				return;
			}
			// found the key
			if (this.key.equals(key)) {
				this.value = value;
				return;
			}
			// next node is empty
			if (this.tail == null) {
				this.tail = new Node<S,T>(key, value);
				return;
			} else {
				// find next empty node
				this.tail.insert(key, value);
			}
		}
		
		private T get(S key) {
			if (this.key.equals(key)) return this.value; // found the key
			if (this.key == null) return null;		// nothing in the list
			if (this.tail == null) return null;		// end of the list
			return this.tail.get(key);				// keep looking
		}
		
		private void delete(S key) {
			// found the key
			if (this.key == key) {
				// if there's no tail
				if (this.tail == null) {
					// remove single node
					this.key = null;
					return;
				} else {
					// link to next node
					this.key = this.tail.key;
					this.value = this.tail.value;
					this.tail = this.tail.tail;
				}
			} else {
				// otherwise keep looking
				if (this.tail != null) this.tail.delete(key);
			}
		}
		
		private Node (S key, T value) {
			this.key = key;
			this.value = value;
		}
	}
	
	public int hash(S key) {
		return (key.hashCode() & 0x7fffffff) % M;
	}
	
	public void insert(S key, T value) {
		int hash = hash(key);
		if (table[hash] == null) {
			table[hash] = new Node<S,T>(key, value);
			return;
		}
		table[hash].insert(key, value);
	}
	
	public T get(S key) {
		int hash = hash(key);
		if (table[hash] == null) return null;
		return table[hash].get(key);
	}
	
	public void delete(S key) {
		int hash = hash(key);
		table[hash].delete(key);
	}
	
	public HashTable() {
		this.table = new Node[M];
	}
}
