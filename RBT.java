package recipebook;

import java.util.ArrayList;

public class RBT<S extends Comparable<S>,T> {
    private S key;
    private T item;
    RBT<S,T> left;
    RBT<S,T> right;
    boolean red;

    public int size() {
        if (key == null) {
            return 0;
        } else {
            return 1 + left.size() + right.size();
        }
    }

    // return true if a < b
    public boolean less(S a, S b) {
        return (a.compareTo(b) < 0);
    }

    // return true if a > b
    public boolean greater(S a, S b) {
        return (a.compareTo(b) > 0);
    }

    // find a given key in the tree
    public T find(S key) {
        if (this.key == null)
            return null; // not in tree
        if (this.key.equals(key))
            return this.item; // found it!
        if (less(key, this.key))
            return left.find(key); // search in left tree
        return right.find(key);    // search in right tree
    }

    public T min() {
        // find leftmost node

        if (this.key == null)
            return null;

        if (left != null) {
            if (left.key != null)
                return left.min();
        }

        return this.item;
    }

    public T popMin() {
        // find leftmost node and remove it
        if (this.key == null)
            return null;

        if (left != null) {
            if (left.key != null)
                return left.popMin();
        }

        T ret = this.item;
        this.item = null;
        this.key = null;
        
        // readjust tree if necessary
        if (right != null) {
            if (right.key != null) {
                this.key   = this.right.key;
                this.item  = this.right.item;
                this.left  = this.right.left;
                this.right = this.right.right;
            }
        }
        return ret;
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
        if (item != null)
            list.add(item);
        if (right != null)
            right.list(list);
    }

    // get all keys in tree
    public ArrayList<S> keys() {
        ArrayList<S> list = new ArrayList<S>();
        keys(list);
        return list;
    }

    // helper to build the tree as a list
    private void keys(ArrayList<S> list) {
        if (left != null)
            left.keys(list);
        if (key != null)
            list.add(key);
        if (right != null)
            right.keys(list);
    }

    /* Red-Black tree methods */

    private boolean isRed() { return red; }

    // add a new key to the tree
    public void insert(S key, T item) {
        if (this.key == null) {
            this.key = key; // found empty node to insert into
            this.item = item;
            this.red = true;
            this.left  = new RBT<S,T>();
            this.right = new RBT<S,T>();
            
        } else if (this.key.equals(key)) {
            this.key = key; // found matching node to insert into
            this.item = item;
            this.red = true;
            this.left  = new RBT<S,T>();
            this.right = new RBT<S,T>();
            
        } else if (less(key, this.key)) left.insert(key, item); // must go in left tree
          else                         right.insert(key, item); // must go in right tree

        // realign tree //
        // if tree is skewed right instead of left
        if (right.isRed() && !left.isRed())
            this.rotateLeft();
        // if there are two 'double' nodes in a row
        if (left.isRed() && left.left.isRed())
            this.rotateRight();
        // if both edges are double ('triple' node)
        if (left.isRed() && right.isRed())
            this.flipColours();
    }

    // rotate a segment left to maintain balance
    private void rotateLeft() {
        this.left.left = new RBT<S,T>(this.left.key, this.left.item, this.left.left, this.left.right);
        this.left.right = this.right.left;
        
        this.left.key = this.key;
        this.left.item = this.item;
        
        this.key = this.right.key;
        this.item = this.right.item;
        
        this.right = this.right.right;
        this.left.red = true;
    }

    // rotate a segment right to maintain balance
    private void rotateRight() {
        this.right.right = new RBT<S,T>(this.right.key, this.right.item, this.right.right, this.right.left);
        this.right.left = this.left.right;
        
        this.right.key = this.key;
        this.right.item = this.item;
        
        this.key = this.left.key;
        this.item = this.left.item;
        
        this.left = this.left.left;
        this.right.red = true;
    }

    // swap colours if needed
    private void flipColours() {
        this.red = true;
        this.right.red = false;
        this.left.red = false;
    }

    /* Initializer methods */
    
    // create an empty tree
    public RBT() {
        this.key  = null;
        this.item = null;
        this.red  = false;
    }

    // create a tree given item and children
    public RBT(S key, T item, RBT<S,T> left, RBT<S,T> right) {
        this.key = key;
        this.item = item;
        this.left = left;
        this.right = right;
        this.red = false;
    }
}
