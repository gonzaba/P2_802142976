package classes;

import interfaces.Queue;

public class ArrayQueue<E> implements Queue<E> {
	private final static int INITCAP = 4; 
	private E[] elements; 
	private int first, size; 
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

	public E dequeue() {
		if (isEmpty())
			return null;
		E etr=elements[first];
		elements[first]=null;
		first=(first+1)%elements.length;
		size--;
		
		return etr;
		
	}
	private void changeCapacity(int newCapacity) {
		E[] temp=(E[]) new Object[newCapacity];
		for (int i=0; i<size;i++) {
			temp[i]=elements[(first+i)%size];
			elements[(first+i)%size]=null;
			
		}
		elements=temp;
		first=0;
	}
	@Override
	public void enqueue(E element) {
		if (size==elements.length)
			changeCapacity(2*size);
		elements[(first+size)%elements.length]=element;
		size++;
			
		
		
	}
}
