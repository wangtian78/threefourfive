/**
 * CSC345-project1
 * 
 * This LLInt class implements the non-negative integer data type using a
 * singly- linked list, where each node will store a limited-range value (like a
 * single decimal digit). This class has add() and multiply() methods, which can
 * add or multiply another LLInt object, each returning a new LLInt object.
 * 
 * Programmer: WANG TIAN
 */
public class LLInt {
	// private class Node inside LLInt, each Node contains a integer(0 to
	// RADIX-1) and a link to the next Node
	private class Node {
		private Integer data;
		private Node next;

		// node constructor
		public Node() {
			data = null;
			next = null;
		}
	}

	private Node head;
	private Node tail;
	private int size;
	private static final int RADIX = 10;// RADIX may be other integer based on
										// requirements

	// empty constructor
	public LLInt() {
		head = new Node();
		tail = new Node();
		head.next = tail;
		size = 0;
	}

	// constructor from integer, each digit of this integer will be stored in
	// the singly linked list reversely,for example:
	// 123 will be head -> 3 -> 2 -> 1 -> tail
	public LLInt(int anInt) {
		this();
		int temp = anInt;
		while (temp > RADIX - 1) {
			append(temp % RADIX);
			temp = temp / RADIX;
		}
		append(temp);
	}

	// constructor from string
	public LLInt(String str) {
		this();
		int temp;
		for (int i = str.length() - 1; i >= 0; i--) {
			temp = str.charAt(i) - '0';
			if (temp<0||temp>9)
				throw new BadInputException();
			append(temp);
		}
	}

	// constructor from another LLInt object
	public LLInt(LLInt other) {
		this();
		Node ref = other.head.next;
		while (ref != other.tail) {
			append(ref.data);
			ref = ref.next;
		}
	}

	// private method, add the number to the list end
	private boolean append(int number) {
		tail.next = new Node();
		tail.data = number;
		tail = tail.next;
		size++;
		return true;
	}

	// return the size of the list
	public int size() {
		return size;
	}

	// a textual representation of this linked list integer.
	public String toString() {
		StringBuffer sb = new StringBuffer();
		for (Node ref = head.next; ref != tail; ref = ref.next)
			sb.append(ref.data);
		return sb.reverse().toString();
	}

	// add another LLInt object to this one and return a new LLInt object
	// this method first compare the sizes of these two LLInt objects, and
	// add some zeros to the smaller one, making them have same size.
	// for example,
	// 12345        12345
	//       ---->
	// + 123      + 00123
	//
	public LLInt add(LLInt other) {
		LLInt temp;
		if (size >= other.size) {
			temp = new LLInt(other);
			addZero(temp, size - other.size);
			return addForSameSize(this, temp);
		} else {
			temp = new LLInt(this);
			for (int i = 0; i < other.size - size; i++)
				temp.append(0);
			return addForSameSize(temp, other);
		}
	}

	// add two LLInt objects with same size, returning a new LLInt object
	private LLInt addForSameSize(LLInt one, LLInt two) {
		LLInt sum = new LLInt();
		int carry = 0;
		int result;
		Node ref1 = one.head.next;
		Node ref2 = two.head.next;
		while (ref1 != one.tail) {
			result = carry + ref1.data + ref2.data;
			sum.append(result % RADIX);
			carry = result / RADIX;
			ref1 = ref1.next;
			ref2 = ref2.next;
		}
		if (carry > 0)
			sum.append(carry);
		return sum;
	}

	// this LLInt object will be multiplied by another LLInt object, returning
	// a new LLInt object
	public LLInt multiply(LLInt other) {
		LLInt multi = new LLInt();
		
		//if either LLInt object represents 0, then the product is 0
		if ((size==1&&head.next.data==0)||(other.size==1&&other.head.next.data==0)){
			multi.append(0);
			return multi;
		}

		Node ref2 = other.head.next;
		for (int i = 0; i < other.size; i++) {
			int carry = 0;
			int result;
			LLInt temp = new LLInt();
			addZero(temp, i);
			Node ref1 = head.next;

			while (ref1 != tail) {
				result = carry + ref2.data * ref1.data;
				temp.append(result % RADIX);
				carry = result / RADIX;
				ref1 = ref1.next;
			}
			if (carry > 0)
				temp.append(carry);

			multi = multi.add(temp);
			ref2 = ref2.next;
		}
		return multi;
	}

	// add n zeros to the list end
	private void addZero(LLInt temp, int n) {
		for (int j = 0; j < n; j++) {
			temp.append(0);
		}
	}
}
