package algorithm.basic;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Sorting   {

	public <E extends Comparable<E>> void selectionSort(List<E> items) {
		//every iteration we select the min/max value
		//and put it in position.
		int len = items.size();
		for (int i = 0; i < len; i++) {
			List<E> subItems = items.subList(i, len);
			//System.out.println("subitems: "+i+" to "+len);
			int j = findMinIndex(subItems);
			//System.out.println("swap : "+i+ " with "+(i+j));
			swap(items,i,i+j);
			//System.out.println(items);
		}
	}

	public <E extends Comparable<E>> void insertionSort(List<E> items) {
		//for a hand of items that were already sorted,
		//take the next item from the rest of the list
		//put it on the location that make the list still sorted
		
	}

	public <E extends Comparable<E>> void quickSort(List<E> items) {


	}

	public <E extends Comparable<E>> void mergeSort(List<E> items) {


	}

	//private utility functions
	private <E extends Comparable<E>> void swap (List<E> items, int i, int j) {
		if (i != j) {
			E temp = items.get(i);
			items.set(i, items.get(j));
			items.set(j, temp);
		}
	}

	private <E extends Comparable<E>> int findMaxIndex(List<E> items) {
		//iterate through all list item and find the max
		int maxIndex = 0;
		E maxVal = items.get(maxIndex);
		int currentIndex = 0;
		for (E item : items) {
			if (item.compareTo(maxVal) > 0) {
				maxIndex = currentIndex;
				maxVal = item;
			}
			currentIndex++;
		}
		return maxIndex;
	}
	
	private <E extends Comparable<E>> int findMinIndex(List<E> items) {
		//iterate through all list item and find the max
		int minIndex = 0;
		E minVal = items.get(minIndex);
		int currentIndex = 0;
		for (E item : items) {
			if (item.compareTo(minVal) < 0) {
				minIndex = currentIndex;
				minVal = item;
			}
			currentIndex++;
		}
		return minIndex;
	}
	
	private <E extends Comparable<E>> void insertItemSorted (List<E> items, int rightIndex, E value) {
		/*
			Before the insert function is called:
			the elements from array[0] to array[rightIndex] are sorted in ascending order.

			After calling the insert function:
			value and the elements that were previously in array[0] to array[rightIndex], 
			should be sorted in ascending order and stored in the elements from array[0] to array[rightIndex+1].
		*/
		
	}
	
	public static void main(String[] args) {
		List<Integer> testItems = new LinkedList<Integer>();
		final int LEN = 100;
		for (int i=0; i< LEN; i++) {
			testItems.add(new Random().nextInt(LEN));
		}
		Sorting sortEngine = new Sorting();
		System.out.println("Original list: "+testItems.toString());
		sortEngine.selectionSort(testItems);
		System.out.println("Sorted list: "+testItems.toString());
	}
}
