/**
 * CSC345-project1
 * 
 * This StackCalc class maintains the LLInt objects using a linked stack,
 * it has push(), add() and multiply() methods which can be instantiated by
 * driver class Calculator
 * 
 * Programmer: WANG TIAN
 */
public class StackCalc {
	// private class Node inside StackCalc, each Node contains a LLInt object
	// and a link to the next Node
	private class Node {
		private LLInt data;
		private Node next;
		
		 public Node(LLInt data, Node next) {
		 this.data = data;
		 this.next = next;
		 }
		// Node constructor
		public Node() {
			data = null;
			next = null;
		}
	}
	
	private Node top;
	private int size;
	
	//constructor
	public StackCalc(){
		top= null;
		size=0;
	}
	
	//converts a string to an LLInt and pushes it on the top of the stack
	public void push (String n){
		LLInt anLLInt = new LLInt (n);
		push(anLLInt);
	}

	//add a LLInt object on the top
	private boolean push(LLInt anLLInt) {
		top = new Node (anLLInt, top);
		size++;
		return true;
	}
	
	//pops the top two values off the stack, multiplies them, 
	//pushes the result onto the stack, and returns a string 
	//representation of the product.
	public String multiply(){
		LLInt first = pop();
		LLInt second = pop();
		LLInt result = first.multiply(second);
		push(result);
		return result.toString();
	}
	
	//pops the top two values off the stack, adds them, 
	//pushes the result onto the stack, and returns a string 
	//representation of the product.
	public String add(){
		LLInt first = pop();
		LLInt second = pop();
		LLInt result = first.add(second);
		push(result);
		return result.toString();
	}

	//remove the top LLInt and return its value
	private LLInt pop() {
		if (top==null)
			return null;
		LLInt temp = top.data;
		top=top.next;
		size--;
		return temp;
	}
	
	//return the top value
	private LLInt peek(){
		if (top==null)
			return null;
		return top.data;
	}

}
