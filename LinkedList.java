//Used to Store Friends for each Person Object 
public class LinkedList {
	LLNode head;
	LLNode tail; 
	public LinkedList(){
		this.head=null;
		this.tail=null;
	}
	
	public LinkedList(LLNode p){
		this.head = p; 
	}
	
	public void addFirst(LLNode p){
		if (head ==null){
			head = p; 
			tail = p; 
		}
		else{
			p.next = head; 
			head=p; 
		}
	}
	
	public void addLast(LLNode p){
		if (head ==null){
			head = p; 
			tail = p;
		}
		else{
			tail.next=p;
			tail =p;
		}
		
	}
	public int size(){ //counts length of linked list
		int count = 0; 
		LLNode current = head; 
		while(current != null){
			count ++; 
			current = current.next; 
		}
		return count;
	}
	
	//find person based on the SSN
	public LLNode find(int SSN){
		LLNode current = head; 
		while(current!=null){
			if(current.p.getSSN()==SSN){
				return current;
			}
			else{
				current=current.next;
			}
		}
		return null; //did not find
	}
	
	public void displayList(){
		LLNode current = head; 
		while(current!=null){
			System.out.println(current.p.getName() +" "+ current.p.getSSN());
			current = current.next;
		}
	}
	
	//if friend in list exists, return true 
	public boolean exists(int SSN){
		LLNode current = head; 
		while(current!=null){
			if(current.p.getSSN()==SSN){
				return true;
			}
			
			current = current.next;
		}
		return false; 
	}
	
	public String findChildren(Person x){
		LLNode current = head; 
		String result = "";
		while(current!=null){
			//match
			if(current.p.getFather()==x.getSSN() || current.p.getMother()==x.getSSN()){
				result += current.p.getName() + ", ";
			}
			current = current.next;
		}
		if (result.equals(""))result ="None";
		return result;
	}
	
	public String findSiblings(Person x){
		LLNode current = head; 
		String result ="";
		while(current!=null){
			//match
			if(current.p.getFather()==x.getFather() && current.p.getMother()==x.getMother() && current.p.getSSN()!=x.getSSN()){
				result += current.p.getName() + ", ";
			}
			current=current.next;
		}
		if (result.equals(""))result ="None";
		return result;
	}
	
	public String findHalfSiblings(Person x){
		LLNode current = head; 
		String result ="";
		while(current!=null){
			//match
			if((current.p.getFather()==x.getFather() && current.p.getMother()!=x.getMother() && current.p.getSSN()!=x.getSSN())||(current.p.getFather()!=x.getFather() && current.p.getMother()==x.getMother() && current.p.getSSN()!=x.getSSN())){
				result += current.p.getName() + ", ";
			}
			current=current.next;
		}
		
		if (result.equals(""))result ="None";
		return result;
	}
}


