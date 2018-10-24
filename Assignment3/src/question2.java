
public class question2 {

	public static void main(String[] args) {
		double[] array = new double[30];
		int index = 0;
		double answer = 0.0;
		
		for (int i = 0; i < array.length; i++){
			array[i] = i;
		}
		answer = sumArray(array, 0); //array length 3; answer should be 3
		System.out.println(answer);
		
	}
	
	public static double sumArray(double[] array, int index) {
		
		if(array.length == 0)
			return 0.0;
		else if( array.length == index + 1)
			return array[index];
		else{
			return array[index] + sumArray(array, index + 1);
		}
		
	}

}
