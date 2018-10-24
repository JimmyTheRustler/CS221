
public class question3 {

	public static void main(String[] args) {
		int arraySize = 10;
		double[] array = new double[arraySize];
		int index = 0;
		double answer = 0.0;

		for (int i = 0; i < array.length; i++){
			array[i] = i;
		}
		answer = largest(array, 0);
		System.out.println(answer);

	}


	public static double largest( double[] array, int index ){
		double val;
		if(array.length == 0)
			return Double.NaN;

		if( index < array.length){
			val = largest(array, index + 1);
			if(array[index] < val)
				return val;
			else
				return array[index];
		}
		return Double.NaN;
	}

}
