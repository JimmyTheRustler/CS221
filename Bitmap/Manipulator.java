import java.io.*;
import java.util.Scanner;

public class Manipulator {

	public static void main(String[] args) {
		//Scanner to take in user input, saved to variable userInput
		Scanner userInput = new Scanner(System.in);
		System.out.print("What file would you like to edit: ");
		String fileName = userInput.nextLine();
		
		
		char command = ' ';
		//generates a new file
		File f = new File(fileName);
		
		//byte arrays to hold the values of each, then a variable for the int when converted
		byte bArray[] = null;
		byte bDataSize[] = new byte[4];
		int dataSize = 0;
		byte colorData [][] = null;
		Bitmap bitmap = null;
		double startTime = 0;
		double endTime = 0;
		/*try catch to read in a bitmap file, if file is corrupt or wrong type
		 * then catch will return a FileNotFoundException
		 */
		try {
			FileInputStream fis = new FileInputStream(f);
			bitmap = new Bitmap(fis);
			fis.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		/*
		 * While loop that loops through the commands to manipulate image, print run time, and
		 * write the new bitmap file when user requests.
		 */
		while(command != 'q'){
			System.out.print("What command would you like to perform (i, g, b, v, s, d, r, or q): ");
			command = userInput.next().charAt(0);
			//calls invertColor method
			if(command == 'i') {
				startTime = System.nanoTime();
				bitmap.invertColor();
				endTime = System.nanoTime();
				double totalTime = Math.round(((endTime - startTime)/1_000_000_000)*1000.0) / 1000.0;
				System.out.println("Command took " + totalTime + " seconds to execute");
			}
			//calls grayScale method
			else if(command == 'g') {
				startTime = System.nanoTime();
				bitmap.grayScale();
				endTime = System.nanoTime();
				double totalTime = Math.round(((endTime - startTime)/1_000_000_000)*1000.0) / 1000.0;
				System.out.println("Command took " + totalTime + " seconds to execute");
			}
			//calls blur method
			else if(command == 'b') {
				startTime = System.nanoTime();
				bitmap.blurImage();
				endTime = System.nanoTime();
				double totalTime = Math.round(((endTime - startTime)/1_000_000_000)*1000.0) / 1000.0;
				System.out.println("Command took " + totalTime + " seconds to execute");
			}
			//calls verticalMirror method
			else if(command == 'v') {
				startTime = System.nanoTime();
				bitmap.verticalFlip();
				endTime = System.nanoTime();
				double totalTime = Math.round(((endTime - startTime)/1_000_000_000)*1000.0) / 1000.0;
				System.out.println("Command took " + totalTime + " seconds to execute");
			}
			//calls shrink method
			else if(command == 's') {
				startTime = System.nanoTime();
				bitmap.shrinkSize();
				endTime = System.nanoTime();
				double totalTime = Math.round(((endTime - startTime)/1_000_000_000)*1000.0) / 1000.0;
				System.out.println("Command took " + totalTime + " seconds to execute");
			}
			//calls doubleSize method
			else if(command == 'd') {
				startTime = System.nanoTime();
				bitmap.doubleSize();
				endTime = System.nanoTime();
				double totalTime = Math.round(((endTime - startTime)/1_000_000_000)*1000.0) / 1000.0;
				System.out.println("Command took " + totalTime + " seconds to execute");
			}
			//calls rotate method
			else if(command == 'r') {
				startTime = System.nanoTime();
				bitmap.rotate();
				endTime = System.nanoTime();
				double totalTime = Math.round(((endTime - startTime)/1_000_000_000)*1000.0) / 1000.0;
				System.out.println("Command took " + totalTime + " seconds to execute");
			}
			//will close program and save changes to new bitmap image
			else if(command == 'q'){
				//takes in user defined file name
				Scanner userInput1 = new Scanner(System.in);
				System.out.print("What would you like to name the modified image? ");
				String newFileName = userInput1.nextLine();
				File fbmp = new File(newFileName);
				try {
					//writes out modified image data to new file
					FileOutputStream newBMP = new FileOutputStream(fbmp);
					bitmap.write(newBMP);
					newBMP.close();
				} 
				//if an error occurs returns printStackTrace to user
				catch (IOException e) {
					e.printStackTrace();
				}
			}
			//if user enters a char other than the one's specified this will print and program closes.
			else
				System.out.println("Please enter a valid command");	
		}	
	}
}
