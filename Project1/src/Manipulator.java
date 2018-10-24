import java.io.*;
import java.util.Scanner;

public class Manipulator {

	public static void main(String[] args) {

		Scanner userInput = new Scanner(System.in);
		System.out.print("What file would you like to edit: ");
		String fileName = userInput.nextLine();
		
		
		char command = 'z';

		File f = new File(fileName);
		
		//byte arrays to hold the values of each, then a variable for the int when converted
		byte bArray[] = null;

		byte bDataSize[] = new byte[4];
		int dataSize = 0;
		byte colorData [][] = null;
		Bitmap bitmap = null;

		try {
			FileInputStream fis = new FileInputStream(f);
			bitmap = new Bitmap(fis);

			fis.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		while(command != 'q'){
			System.out.print("What command would you like to perform (i, g, b, v, s, d, r, or q): ");
			command = userInput.next().charAt(0);
			
			if(command == 'i')
				bitmap.invertColor();
			else if(command == 'g')
				bitmap.grayScale();
			else if(command == 'b')
				bitmap.blur();
			else if(command == 'v')
				bitmap.verticalFlip();
			else if(command == 's')
				bitmap.shrinkSize();
			else if(command == 'd')
				bitmap.doubleSize();
			else if(command == 'r')
				bitmap.rotate();
			else if(command == 'q'){
				File fbmp = new File("newBMP.bmp");
				try {
				
					FileOutputStream newBMP = new FileOutputStream(fbmp);
					bitmap.write(newBMP);
					newBMP.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			else
				System.out.println("Please enter a valid command");
			
		}
		
		
		
	}
	
	
}
