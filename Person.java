
public class Person implements Comparable<Person>{
	private String first;
	private String last;
	private String name;
	private int SSN;
	private int mother;
	private int father;
	private String[] friendsbySSN;
	private static int mostMutualFriends; 

	//public Tree friends = new Tree();
	public LinkedList friends = new LinkedList();

	Person right;
	Person left;
	

	public Person(String first, String last, int SSN, int father, int mother, String friends) {
		super();
		this.first = first;
		this.last = last;
		this.name = first + " " + last;
		this.SSN = SSN;
		this.father = father;
		this.mother = mother;
		
		this.friendsbySSN = friends.split(",");
		this.mostMutualFriends=0;
	}
	
	public void displayInfo(){
		System.out.println("Name: "+this.name);
		System.out.println("SSN: "+this.SSN);
		System.out.println("Father: "+this.father);
		System.out.println("Mother: "+this.mother);
		System.out.println("# friends: " + friends.size());
	}

	public int getSSN() {
		return SSN;
	}
	
	public void incrementMostMutualFriends(){
		this.mostMutualFriends++;
	}
	public int getMostMutualFriends(){
		return this.mostMutualFriends;
	}
	public LinkedList getFriends() {
		return friends;
	}
	public String getName(){
		return this.name;
	}
	public String[] getFriendsbySSN(){
		return this.friendsbySSN;
	}
	
	public void displayFriends(Person p){
		if (p != null) {
			System.out.print(p.getName() + "\n");
			displayFriends(p.left);
			displayFriends(p.right);
		}
	}
	
	public int getFather(){
		return this.father;
	}
	public int getMother(){
		return this.mother;
	}

	@Override
	public int compareTo(Person o) {
		Integer i = this.SSN;
		Integer l = o.SSN;
		return  i.compareTo(l);
	}
}
