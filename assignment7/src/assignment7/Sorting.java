/* Name: James O'Brien
 * Date: 11/30/17
 * Class: CS 221 
 * Assignment 7
 */
package assignment7;
import java.util.Random;
import java.util.Arrays;

public class Sorting {
	public static void main(String[] args) {
		int max = 100; // 1 = 10,000  10 = 100,000 100 = 1,000,000 etc...
		int[] randArray = new int[1_000_000];
		double[] scratchArr = new double[1_000_000];
		Random rand = new Random();
		double time;
		boolean sorted = true;

		//Generates array of random numbers between 1 -> max
		for(int i = 0; i < randArray.length; i++) {
			randArray[i] = rand.nextInt(max) + 1;
		}


		for(int i = 1; i <= max; i*=10) {
			int[] temp = Arrays.copyOfRange(randArray, 0, i*10000);
			System.out.println("Array Length: " + temp.length);
			time = System.nanoTime();
			quickSort(temp, 0, temp.length-1);
			time = (System.nanoTime() - time) / 1000000000.0;
			System.out.printf("Quick Sort: \t" + "%.4f" + " seconds.\n", time);
			
			temp = Arrays.copyOfRange(randArray, 0, i*10000);
			time = System.nanoTime();
			mergeSort(temp, scratchArr, 0, temp.length-1);
			time = (System.nanoTime() - time) / 1000000000.0;
			System.out.printf("Merge Sort: \t" + "%.4f" + " seconds.\n", time);
			
			temp = Arrays.copyOfRange(randArray, 0, i*10000);
			time = System.nanoTime();
			heapSort(temp);
			time = (System.nanoTime() - time) / 1000000000.0;
			System.out.printf("Heap Sort: \t" + "%.4f" + " seconds.\n", time);
			
			temp = Arrays.copyOfRange(randArray, 0, i*10000);
			time = System.nanoTime();
			//radixSort(temp, 6);
			time = (System.nanoTime() - time) / 1000000000.0;
			System.out.printf("Radix Sort: \t" + "%.4f" + " seconds.\n\n", time);
		
		}


	}
	/************************************************************************************************************************************************************************************
	 * Quick Sort
	 ***********************************************************************************************************************************************************************************/
	public static void quickSort(int[] array, int start, int end) { 
		return;
	}
	/************************************************************************************************************************************************************************************
	 * Merge Sort
	 ***********************************************************************************************************************************************************************************/
	public static void mergeSort(int[] array, double[] scratch, int start, int end) {
		if ( start < end - 1 ){
			int mid = (start + end) / 2;
			mergeSort(array, scratch, start, mid);
			mergeSort(array, scratch, mid, end);
			merge(array, scratch, start, mid, end);
		}
	}
	private static void merge(int[] array, double[] scratch, int start, int mid, int end){
		for( int i = start; i < end; i++)
			scratch[i] = array[i];
		int index1 = start;
		int index2 = mid;
		for(int i = start; i < end; i++){
			if(index1  >= mid )
				array[i] = (int) scratch[index2++];
			else if (index2 >= end)
				array[i] = (int) scratch[index1++];

			else if( scratch[index1] <= scratch[index2])
				array[i] = (int) scratch[index1++];
			else
				array[i] = (int) scratch[index2++];
		}
	}

	/************************************************************************************************************************************************************************************
	 * Heap Sort
	 ***********************************************************************************************************************************************************************************/
	public static void heapSort(int[] array) {
		heapify(array, array.length);

		int end = array.length - 1;
		while(end > 0){
			int temp = array[end];
			array[end] = array[0];
			array[0] = temp;
			bubbleDown(array, 0, end - 1);
			end--;
		}
	}
	public static void heapify(int[] array, int arrayLength){
		int start = (arrayLength - 2) / 2;
		while(start >= 0){
			bubbleDown(array, start, arrayLength - 1);
			start--;
		}

	}

	public static void bubbleDown(int[] array, int start, int end) {
		int root = start;

		while(root * 2 + 1 <= end){
			int lChild = root * 2 + 1; //Left Child
			int rChild = root * 2 + 2; //Right Child

			if(rChild <= end && array[lChild] < array[rChild])
				lChild = rChild;
			if(array[root] < array[lChild]){
				int tmp = array[root];
				array[root] = array[lChild];
				array[lChild] = tmp;
				root = lChild;
			}
			else
				return;
		}
	}

	/************************************************************************************************************************************************************************************
	 * Radix Sort
	 ***********************************************************************************************************************************************************************************/
	//not working
	public static void radixSort(int[] array, int digits) {
		int[] scratch = new int[array.length];
		int[] buckets = new int[10];
		int power = 1;

		for( int i = 0; i < digits; i++) {
			for(int value: array) {
				int num = ( (value / (10 * power))/power );
				buckets[num]++;
			}
			int current = 0;
			int prev = 0;
			for(int j = 1; j < buckets.length; j++) {
				current = buckets[j];
				buckets[j] = prev;
				prev += current;
			}
			buckets[0] = 0;
			for(int value: array) {
				int num = ((value % (10 * power)) / power);
				scratch[buckets[num]++] = value;
			}
			for(int k = 0; k < array.length; k++ ) 
				array[k] =scratch[k];
		}

	}
}
