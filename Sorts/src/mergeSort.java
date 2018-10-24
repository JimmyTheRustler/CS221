import java.util.Random;

public class mergeSort {

	public static void main(String[] args) {
		double[] array = new double[1_000_000];
		Random random = new Random();
		for(int i = 0; i < array.length; i++)
			array[i] = random.nextDouble() * 1000;
		mergeSort(array);
		
	}
	
	public static boolean isSorted(double[] values){
		
	}
	
	
	
	
	public static void mergeSort(double[] values, double[] scratch, int start, int end){
		if ( start < end - 1 ){
			int mid = (start + end) / 2;
			mergeSort(values, scratch, start, mid);
			mergeSort(values, scratch, mid, end);
			merge(values, scratch, start, mid, end);
		}
	}

	private static void merge(double[] values, double[] scratch, int start, int mid, int end){
		for( int i = start; i < end; i++)
			scratch[i] = values[i];
		int index1 = start;
		int index2 = mid;
		for(int i = start; i < end; i++){
			if(index1  >= mid )
				values[i] = scratch[index2++];
			else if (index2 >= end)
				values[i] = scratch[index1++];
			
			else if( scratch[index1] <= scratch[index2])
				values[i] = scratch[index1++];
			else
				values[i] = scratch[index2++];
		}
	}

}
