//Generic Tree Node used for Generic BST
//T = type of the key --> SSN
//S = type of data value --> Person
public class TreeNode <T extends Comparable<T>, S> {
	//each generic node will have a :
	//1. Value 
	//2. left 
	//3. right
	//4. Key 
	T key; //SSN
	S value; //person 
	TreeNode<T,S> left; 
	TreeNode<T,S> right; 
	
	public TreeNode(){
		this.value = null; 
		this.left = null; 
		this.right = null; 
	}
	
	public TreeNode(T v,S s){
		this.value = s; 
		this.key = v; 
	}
	
	
}
