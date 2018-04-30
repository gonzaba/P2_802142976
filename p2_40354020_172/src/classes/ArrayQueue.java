package classes;

import interfaces.Queue;

/**
 * @author Barbara P. Gonzalez Rivera - 802-14-2976
 * @author Ramineh Lopez - 402-12-3657
 * ICOM4035 - 030
 */

//This class was originally in the Queue laboratory

public class ArrayQueue<E> implements Queue<E> {
	private final static int INITCAP = 4; 
	private E[] elements; 
	private int first, size, last; 
	@SuppressWarnings("unchecked")
	public ArrayQueue() { 
		elements = (E[]) new Object[INITCAP]; 
		first = 0; 
		size = 0; 
	}
	public int size() {
		return size;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public E first() {
		if (isEmpty()) return null; 
		return elements[first]; 
	
	}

	//dequeue from the beginning of the line
	public E dequeue() {
		if (isEmpty())
			return null;
		E etr=elements[first];
		elements[first]=null;
		first=(first+1)%elements.length;
		size--;
		
		return etr;
		
	}
	
	//dequeue from the end of the list method
	
	public E deque() {
		if(isEmpty()) {
			return null;
		}
		E etr = elements[last];
		elements[last]=null;
		size--;
		return etr;
	}
	
	//look at the element at the end of the line
	public E peakAtLast() {
		if(isEmpty()) {
			return null;
		}
		E etr = elements[last];
		return etr;
	}
	
	private void changeCapacity(int newCapacity) {
		@SuppressWarnings("unchecked")
		E[] temp=(E[]) new Object[newCapacity];
		for (int i=0; i<size;i++) {
			temp[i]=elements[(first+i)%size];
			elements[(first+i)%size]=null;
			
		}
		elements=temp;
		first=0;
	}
	
	//place the new element at the end of the Arrayqueue.
	@Override
	public void enqueue(E element) {
		if (size==elements.length) {
			changeCapacity(2*size);
		}
		last = (first+size)%elements.length;
		elements[(first+size)%elements.length]=element;
		size++;
			
		
		
	}
}