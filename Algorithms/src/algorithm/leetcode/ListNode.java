package algorithm.leetcode;

//single linked list
public class ListNode<T> {
	T value; //default null
	ListNode<T> next; //default null
	public ListNode (T val) {
		value = val;
	}
	public ListNode() {

	}
	
//	public void reverse() {
//		//reverse a linkedlist
//		// []->[]->[]
//		// []<-[*]->[]
//		// []<-[]<-[]
//		if (this.next == null) return;
//		ListNode<T> head = this;
//		ListNode<T> temp = head;
//		ListNode<T> prev = null;
//		while(head.next != null) {
//			head = head.next;
//			//now fix the pointer
//			temp.next=prev;
//			prev = temp;
//			temp = head;
//		}
//		head.next = prev; //fix the edge case
//		this.value = head.value;
//		this.next = head.next;
//	}
	
	@Override
	public String toString() {
		ListNode<T> temp = this.next;
		StringBuilder res = new StringBuilder("ListNode: "+this.value);
		while (temp != null) {
			res.append(" -->" + temp.value);
			temp = temp.next;
		}
		return res.toString();
	}
}
