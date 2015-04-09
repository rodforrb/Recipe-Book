package recipebook;

import java.util.ArrayList;

public class ListRBT<T> {
    private String key;
    private ArrayList<T> items;
    ListRBT<T> left;
    ListRBT<T> right;
    boolean red;

    public int size() {
        if (key == null) {
            return 0;
        } else {
            return 1 + left.size() + right.size();
        }
    }

    // return true if a < b
    public boolean less(String a, String b) {
        return (a.compareTo(b) < 0);
    }

    // return true if a > b
    public boolean greater(String a, String b) {
        return (a.compareTo(b) > 0);
    }

    // find a given key in the tree
    public ArrayList<T> find(String key) {
        ArrayList<T> ret = new ArrayList<T>();
        if (this.key == null)
            return ret; // not in tree
        if (this.key.contains(key)) {
            ret.addAll(this.items);     // found a match
        }
        left.find(key, ret);  // search in left tree
        right.find(key, ret); // search in right tree
        
        return ret;
    }
    
    // helper to find a given key in the tree
    private void find(String key, ArrayList<T> ret) {
        if (this.key == null)
            return; // not in tree
        if (this.key.contains(key)) {
            ret.addAll(this.items);
        }
        left.find(key, ret);  // search in left tree
        right.find(key, ret); // search in right tree
    }

    /* Red-Black tree methods */

    private boolean isRed() { return red; }

    // add a new key to the tree
    public void insert(String key, T item) {
        if (this.key == null) {
            this.key = key; // found empty node to insert into
            this.items.add(item);
            this.red = true;
            this.left  = new ListRBT<T>();
            this.right = new ListRBT<T>();
        } else if (this.key.equals(key)) {
            this.items.add(item);
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
        this.left.left = new ListRBT<T>(this.left.key, this.left.items, this.left.left, this.left.right);
        this.left.right = this.right.left;
        
        this.left.key = this.key;
        this.left.items = this.items;
        
        this.key = this.right.key;
        this.items = this.right.items;
        
        this.right = this.right.right;
        this.left.red = true;
    }

    // rotate a segment right to maintain balance
    private void rotateRight() {
        this.right.right = new ListRBT<T>(this.right.key, this.right.items, this.right.right, this.right.left);
        this.right.left = this.left.right;
        
        this.right.key = this.key;
        this.right.items = this.items;
        
        this.key = this.left.key;
        this.items = this.left.items;
        
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
    public ListRBT() {
        this.key  = null;
        this.items = new ArrayList<T>();
        this.red  = false;
    }

    // create a tree given item and children
    public ListRBT(String key, ArrayList<T> items, ListRBT<T> left, ListRBT<T> right) {
        this.key = key;
        this.items = items;
        this.left = left;
        this.right = right;
        this.red = false;
    }
}
