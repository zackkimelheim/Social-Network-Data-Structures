//Generic Tree used for BST 
public class Tree<T extends Comparable<T>, S> {
	public TreeNode<T, S> root;

	public Tree(TreeNode<T, S> p) {
		this.root = p;
	}

	public Tree() {
		this.root = null;
	}

	public TreeNode<T,S> find(T SSN) {
		TreeNode<T, S> current = root;
		while (current != null) {
			if (current.key == SSN)
				return current;
			else {
				if (current.key.compareTo(SSN) <= 0) {
					current = current.right;
				} else {
					current = current.left;
				}
			}

		}
		return null;
	}

	public void insert(T ssn, S person) {

		// person was name of parameter
		TreeNode<T, S> node = new TreeNode<T,S>(ssn, person);

		if (root == null) { // no node in root
			root = node;
			System.out.println("Added to root.");
		} else // root occupied
		{
			TreeNode<T,S> current = root; // start at root
			TreeNode<T,S> parent;

			while (true) { // (exits internally)
				parent = current;
				if (node.key.compareTo((T) current.key) <= 0) { // go left?
					current = current.left;
					if (current == null) { // if end of the line,
						// insert on left
						parent.left = node;
						System.out.println("Added to tree.");
						return;
					} else {
						System.out.println("Left node filled.");
					}
				} // end if go left
				else { // or go right?
					current = current.right;
					if (current == null) { // if end of the line
						// insert on right
						parent.right = node;
						System.out.println("Added to tree.");
						return;
					} else {
						System.out.println("Right node filled.");

					}
				} // end else go right
			} // end while

		} // end else not root
	} // end insert()

	// get amount of nodes in tree
	int size() {
		return size(root);
	}

	/* computes number of nodes in tree */
	int size(TreeNode<T, S> p) {
		if (p == null)
			return 0;
		else
			return (size(p.left) + 1 + size(p.right));
	}

	// create balanced BST from sorted array of people
	public TreeNode<T,S> sortedArrayToBST(TreeNode<T,S>[] arr, int start, int end) {
		if (start > end) {
			return null;
		}

		int mid = start + (end-start)/2; 
		TreeNode<T,S> temp = arr[mid];
		temp.left = sortedArrayToBST(arr, start, mid - 1);
		temp.right = sortedArrayToBST(arr, mid + 1, end);

		return temp;
		
		
	}

}
