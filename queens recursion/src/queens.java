import java.util.Scanner;
public class queens {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		System.out.print("Enter board size: ");
		int size = in.nextInt();
		int[] board = new int[size];
		int answer = count(board, 0, size);
		System.out.println("Total number of possible queen locations: " + answer);
	}
	
	public static int count( int[] board, int row, final int N ){
		
		if(row == N)
			return 1;
		else{
			int total = 0;
			for(int column = 0; column < N; column++){
				board[row] = column;
				if(isLegal(board, row))
					total += count(board, row + 1, N);
				
			}
			return total;
		}
		
	}
	
	public static boolean isLegal(int[] board, int row){
		for(int i = 0; i < row; i++){
			if(board[i] == board[row] || Math.abs(row - i == board[row] - board[i]) )
				return false;
		}
		
		
		
		
		return true;
	}
	

}
