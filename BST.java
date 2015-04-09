package recipebook;

import java.util.ArrayList;

public class BST<T extends Comparable<T>> {
	T head;
	BST<T> left;
	BST<T> right;
	
	public int size() {
		if (head == null) {
			return 0;
		} else {
			return 1 + left.size() + right.size();
		}
	}
	
	public boolean less (T a, T b) {
		if (a.compareTo(b) < 0) return true;
		return false;
	}
	
	public BST<T> find (T key) {
		if (head == null) 	  return 	null; // not in tree
		if (head.equals(key)) return 	this; // found it!
		if (less(key, head))  return 	left.find(key);  // search in left tree
		return 							right.find(key); // search in right tree
	}
	
	public void insert (T key) {
		if (head == null) {
			this.head = key;	 // found empty node to insert into
			this.left = new BST<T>();
			this.right = new BST<T>();
		}
		else if (less(key, head)) left.insert(key);	 // must go in left tree
		else 					  right.insert(key); // must go in right tree
	}
	
	public T min() {
		// find leftmost node
		
		if (this.head == null) return null;
		
		if (left != null) {
			if (left.head != null) return left.min();
		}
		
		return this.head;
	}
	
		
	public T popMin() {
		// find leftmost node
		if (this.head == null) return null;
		
		if (left != null) {
			if (left.head != null) return left.popMin();
		}
		
		T ret = this.head;
		this.head = null;
		// readjust tree if necessary
		if (right != null) {
			if (right.head != null) {

				this.head = this.right.head;
				this.left = this.right.left;
				this.right = this.right.right;
			}
		}
		return ret;
	}
	
	public void print() {
		if (head == null) return;
		left.print();
		System.out.println(head);
		right.print();
	}

	public void draw (int n) {
		if (head == null) return; 
		right.draw(n+1);
		for (int i = 0; i < 2*n; i++) System.out.print(' ');
		System.out.println(head);
		left.draw(n+1);
	}

    // returns the tree as a list
    public ArrayList<T> list() {
        ArrayList<T> list = new ArrayList<T>();
        list(list);
        return list;
    }

    // helper to build the tree as a list
    private void list(ArrayList<T> list) {
        if (left != null)
            left.list(list);
        if (head != null)
            list.add(head);
        if (right != null)
            right.list(list);
    }
	
	/* Initializer methods */
	public BST () {
		this.head = null;
	}
	
	public BST (T head) {
		this.head = head;
		this.left = new BST<T>();
		this.right = new BST<T>();
	}
	
	public BST (T head, BST<T> left, BST<T> right) {
		this.head = head;
		this.left = left;
		this.right = right;
	}
}
