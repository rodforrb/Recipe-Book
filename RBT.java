package recipebook;

import java.util.ArrayList;

public class RBT<T extends Comparable<T>> {
	private T head;
	RBT<T> left;
	RBT<T> right;
	boolean red;
	
	public int size() {
		if (head == null) {
			return 0;
		} else {
			return 1 + left.size() + right.size();
		}
	}
	
	// return true if a < b
	public boolean less (T a, T b) { return (a.compareTo(b) < 0); }
	
	// return true if a > b
	public boolean greater (T a, T b) { return (a.compareTo(b) > 0); }
	
	// find a given key in the tree
	public RBT<T> find (T key) {
		if (head == null) 	  return 	null; // not in tree
		if (head.equals(key)) return 	this; // found it!
		if (less(key, head))  return 	left.find(key);  // search in left tree
		return 							right.find(key); // search in right tree
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
		// find leftmost node and remove it
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
	
	// print the entire tree
	public void print() {
		if (head == null) return;
		left.print();
		System.out.println(head);
		right.print();
	}
	
	// draw the tree with spaces (horizontally)
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
		if (left != null) left.list(list);
		if (head != null) list.add(head);
		if (right != null) right.list(list);
	}
	
	/* Red-Black tree methods */
	
	private boolean isRed() {
		return red;
	}

	// add a new key to the tree
	public void insert (T key) {
		if (this.head == null) {
			this.head = key;	 // found empty node to insert into
			this.red = true;
			this.left = new RBT<T>();
			this.right = new RBT<T>();
		}
		else if (less(key, head)) left.insert(key);	 // must go in left tree
		else 					  right.insert(key); // must go in right tree
		
		// realign tree //
		// if tree is skewed right instead of left
		if (right.isRed() && !left.isRed()) 	this.rotateLeft();
		// if there are two 'double' nodes in a row
		if (left.isRed() && left.left.isRed())	this.rotateRight();
		// if both edges are double ('triple' node)
		if (left.isRed() && right.isRed())		this.flipColours();
	}
	
	// rotate a segment left to maintain balance
	private void rotateLeft () {
		this.left.left = new RBT<T>(this.left.head, this.left.left, this.left.right);
		this.left.right = this.right.left;
		this.left.head = this.head;
		this.head = this.right.head;
		this.right = this.right.right;
		this.left.red = true;
	}
	
	// rotate a segment right to maintain balance
	private void rotateRight () {
		this.right.right = new RBT<T>(this.right.head, this.right.right, this.right.left);
		this.right.left = this.left.right;
		this.right.head = this.head;
		this.head = this.left.head;
		this.left = this.left.left;
		this.right.red = true;
	}
	
	// swap colours if needed
	private void flipColours () {
		this.red = true;
		this.right.red = false;
		this.left.red = false;
	}
	
	/* Initializer methods */
	public RBT () {
		this.head = null;
		this.red = false;
	}
	
	// create a tree from just the head
	public RBT (T head) {
		this.head = head;
		this.left = new RBT<T>();
		this.right = new RBT<T>();
		this.red = false;
	}
	
	// create a tree given head and children
	public RBT (T head, RBT<T> left, RBT<T> right) {
		this.head = head;
		this.left = left;
		this.right = right;
		this.red = false;
	}
}