import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Main {

	// linked list of people objects
	// Tree of people
	// Linked List of friends inside those people

	// 1.Read in all the people, store in an array
	// 2.Convert that array to a Balanced Binary Search Tree of People Objects
	// 3.Create a Linked List of Friends inside each Person Object

	// static LinkedList list1 = new LinkedList();
	public static Database db = new Database();

	@SuppressWarnings({ "resource", "unchecked" })
	public static void main(String[] args) {

		File file = new File(args[0]);//communityfile.txt
		File file2 = new File(args[1]);//queriesfile.txt
		try {
			Scanner input = new Scanner(file);
			int total_lines = 0;
			while (input.hasNextLine()) {
				input.nextLine();
				total_lines++;
			}

			int objects = (total_lines - (total_lines / 7)) / 6; // amount of ppl in file
			Person[] people = new Person[objects]; // should be 11 people

			// reset scanner
			input = new Scanner(file);
			int i = 0;
			//Reading through data, Creating Person objects and storing in Person array
			while (input.hasNextLine()) {
				String line = input.nextLine();
				if (line.length() > 0) {
					String first = line.substring(12);
					String last = input.nextLine().substring(11);
					int ssn = Integer.parseInt(input.nextLine().substring(5));
					int dad = Integer.parseInt(input.nextLine().substring(8));
					int mom = Integer.parseInt(input.nextLine().substring(8));
					String friends = input.nextLine().substring(9);
					Person p = new Person(first, last, ssn, dad, mom, friends);
					people[i] = p;
					i++;
				}

			}
			// Success! All people objects have been read in and stored in the PPL Array

			//Intermediary Step: Create a GenericNode array of People
			TreeNode<Integer,Person>[] nodes = new TreeNode[people.length];
			for(int k=0;k<people.length;k++){
				nodes[k]= new TreeNode<Integer, Person>(people[k].getSSN(),people[k]);
			}
			// NOW. lets convert the People Array in to a Balanced Binary Search Tree of People
			db.peopleTree.root = db.peopleTree.sortedArrayToBST(nodes, 0, people.length - 1);

			// NEXT. lets turn the friends list per person in to a unordered
			// linked list of friends
			for (Person x : people) {
				// start with Person 0
				// find the corresponding person in the tree
				Person tmp = db.peopleTree.find(x.getSSN()).value;
				// Now that we have the person, let's formulate the data so that
				// the String array of
				// friends can be converted in to a linked list of friends
				for (String c : tmp.getFriendsbySSN()) {
					int ssn = Integer.parseInt(c);
					LLNode newNode = new LLNode((Person)db.peopleTree.find(ssn).value);
					tmp.friends.addFirst(newNode);
				}
			}
			
			//Finding how many mutual friends each Person has
			calcMutualFriends(db.peopleTree.root);

			Scanner input2 = new Scanner(file2);
			/**1.Read in a line and store it as a string
			 * 2.Determine which Query it is asking for
			 * 3.Depending on the Query, Print the proper result
			 * 4.Continue until there are no more Queries
			 */
			while(input2.hasNextLine()){
				String line = input2.nextLine();
				String chunk = line.substring(0, 3).toLowerCase();
				int num;
				ArrayList<Person> folk; 
				switch(chunk){
				case "nam": num = Integer.parseInt(line.substring(8)); 
							System.out.printf("Name of %d: %s ", num, db.peopleTree.find(num).value.getName());
							break; 
				case "fat": num = Integer.parseInt(line.substring(10));
							System.out.printf("\nFather of %d: %s ", num,
									db.peopleTree.find(db.peopleTree.find(num).value.getFather()).value.getName());
							break; 
				case "mot": num=Integer.parseInt(line.substring(10));
							System.out.printf("\nMother of %d: %s ", num,
									db.peopleTree.find(db.peopleTree.find(num).value.getMother()).value.getName());
							break; 
				case "hal": num = Integer.parseInt(line.substring(17));
							System.out.printf("\nHalf-Siblings of %d: ", num);
							folk = new ArrayList<>();
							findHalfSibling(db.peopleTree.find(num), db.peopleTree.root, folk);
							Collections.sort(folk);
							for(Person p:folk){
								System.out.print(p.getName()+", ");
							}
							break; 
				case "ful": num = Integer.parseInt(line.substring(17));
							System.out.printf("\nFull Siblings of %d: ", num);
							folk = new ArrayList<>();
							findSibling(db.peopleTree.find(num), db.peopleTree.root, folk);
							Collections.sort(folk);
							for(Person p:folk){
								System.out.print(p.getName()+", ");
							}
							break; 
				case "chi": num = Integer.parseInt(line.substring(12));
							System.out.printf("\nChildren of %d: ", num);
							folk = new ArrayList<>();
							findChildren(db.peopleTree.find(num), db.peopleTree.root, folk);
							Collections.sort(folk);
							for(Person p:folk){
								System.out.print(p.getName()+", ");
							}
							break; 
				case "mut": num = Integer.parseInt(line.substring(18));
							System.out.printf("\nMutual Friends of %d: ", num);
							folk = new ArrayList<>();
							findMutualFriends(db.peopleTree.find(num),folk);
							Collections.sort(folk);
							for(Person p:folk){
								System.out.print(p.getName()+", ");
							}
							break;
				case "inv": num = Integer.parseInt(line.substring(19));
							System.out.printf("\nInverse Friends of %d: ", num);
							folk = new ArrayList<>();
							findInverseFriends(db.peopleTree.find(num), db.peopleTree.root, folk);
							Collections.sort(folk);
							for(Person p:folk){
								System.out.print(p.getName()+", ");
							}
							break;
				case "who": System.out.print("\nWho has the most mutual friends: ");
							Person p = mostMutualFriends(db.peopleTree.root);
							System.out.println(p.getName());
							break;
				  default: System.out.println("Error");
				}
			}
			
			input.close();
			input2.close();
		} catch (FileNotFoundException e) {
			System.out.println(e);
		}

	}
	
	// for a Given person, find all the children of this person
	public static void findChildren(TreeNode<Integer,Person> x, TreeNode<Integer,Person> current,ArrayList<Person>p) {
		if (current != null) {
			if (current.value.getFather() == x.value.getSSN() || current.value.getMother() == x.value.getSSN()) {
				//System.out.print(current.value.getName() + ", ");
				p.add(current.value);
			}
			findChildren(x, current.left,p);
			findChildren(x, current.right,p);

		}
		
	}

	// For a given person x, find all the siblings of this person
	public static void findSibling(TreeNode<Integer,Person> x, TreeNode<Integer,Person> current,ArrayList<Person>p) {
		if (current != null) {
			if (current.value.getFather() == x.value.getFather() && current.value.getMother() == x.value.getMother()
					&& current.value.getSSN() != x.value.getSSN()) {
				//System.out.print(current.value.getName() + ", ");
				p.add(current.value);
			}
			findSibling(x, current.left,p);
			findSibling(x, current.right,p);

		}
	}

	// For a given person x, find all the half siblings of this person
	public static void findHalfSibling(TreeNode<Integer,Person> x, TreeNode<Integer,Person> current,ArrayList<Person>p) {
		if (current != null) {
			if ((current.value.getFather() == x.value.getFather() && current.value.getMother() != x.value.getMother()
					&& current.value.getSSN() != x.value.getSSN())
					|| current.value.getFather() != x.value.getFather()
							&& (current.value.getMother() == x.value.getMother() && current.value.getSSN() != x.value.getSSN())) {
				//System.out.print(current.value.getName() + ", ");
				p.add(current.value);

			}
			findHalfSibling(x, current.left,p);
			findHalfSibling(x, current.right,p);

		}
	}

	// For a given person x, find all the friends that mutually "friend" each
	// other
	public static void findMutualFriends(TreeNode<Integer,Person> x,ArrayList<Person>p) {
		// go through friends of Person X.
		LinkedList friends = x.value.friends;
		LLNode current = friends.head;
		while (current != null) {
			// Take Friend 1.
			// Go to Friend 1's Friends. If person x is there...then its a match
			Person p1 = (Person)db.peopleTree.find(current.p.getSSN()).value;
			LLNode curr2 = p1.friends.head;
			while (curr2 != null) {
				// Traverse Through Friend 1's Friends
				// If Friend 1 Has Person X as a Friend, then they are mutual
				// Friends!
				if (curr2.p.getSSN() == x.value.getSSN()) {
					//System.out.print(current.p.getName() + ", ");
					p.add(current.p);
					break;
				}
				curr2 = curr2.next;
			}
			current = current.next;
		}

	}

	// For a given person x, find all the People that view x as a friend
	public static void findInverseFriends(TreeNode<Integer,Person> x, TreeNode<Integer,Person> current,ArrayList<Person>p) {
		if (current != null) {
			// take friend list of current
			LinkedList friends = current.value.friends;
			if (friends.exists(x.value.getSSN())) {
				//System.out.print(current.value.getName() + ", ");
				p.add(current.value);
			}

			findInverseFriends(x, current.left,p);
			findInverseFriends(x, current.right,p);

		}
	}

	// Find person that has most mutual friends
	public static Person mostMutualFriends(TreeNode<Integer,Person> root) {
		// Compare two smallest
		if (root != null) {
			int rootCt = root.value.getMostMutualFriends();
			int leftCt = 0;
			int rightCt = 0;
			if (root.left != null) {
				leftCt = root.left.value.getMostMutualFriends();
			}
			if (root.right != null) {
				rightCt = root.right.value.getMostMutualFriends();
			}
			int z = Math.max(Math.max(leftCt, rightCt), rootCt);
			if (z == leftCt) {
				Person p = mostMutualFriends(root.left);
				return p;
			} else if (z == rightCt) {
				Person p = mostMutualFriends(root.right);
				return p;
			} else {
				return root.value;
			}

		}
		return null;
	}

	// Helper Method, calculate how many mutual friends each person has
	public static void calcMutualFriends(TreeNode<Integer,Person> root) {
		// 1. Start with the root
		if (root != null) {
			countMutualFriends(root.left);
			countMutualFriends(root.right);
		} 
	}

	public static void countMutualFriends(TreeNode<Integer,Person> x) {
		// go through friends of Person X.
		
		LinkedList friends = x.value.friends;
		LLNode current = friends.head;
		while (current != null) {
			// Take Friend 1.
			// Go to Friend 1's Friends. If person x is there...then its a match
			Person p1 = db.peopleTree.find(current.p.getSSN()).value;
			LLNode curr2 = p1.friends.head;
			while (curr2 != null) {
				// Traverse Through Friend 1's Friends
				// If Friend 1 Has Person X as a Friend, then they are mutual
				// Friends!
				if (curr2.p.getSSN() == x.value.getSSN()) {
					x.value.incrementMostMutualFriends(); // add 1
					break;
				}
				curr2 = curr2.next;
			}
			current = current.next;
		}

	}
	
	

}
