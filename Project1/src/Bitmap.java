import java.io.*;
import java.util.Scanner;

public class Bitmap {
	private int width = 0;
	private int height = 0;
	private int size = 0;
	private int padding = 0;
	private byte[][] colorData;
	public Bitmap(FileInputStream fis) throws IOException {

		//skips data in the header that isn't needed and fills the arrays with corresponding information

		byte bHeight[] = new byte[4];
		byte bWidth[] = new byte[4];
		byte bSize[] = new byte[4];



		fis.skip(2);
		fis.read(bSize);
		fis.skip(12);
		fis.read(bWidth);
		fis.read(bHeight);
		fis.skip(28);

		//converts the arrays to int's
		size = byteArrayToInt(bSize);
		width = byteArrayToInt(bWidth);
		height = byteArrayToInt(bHeight);

		//finds any padding needed
		if(((width*3) % 4) == 3)
			padding = 1;	

		if(((width*3) % 4) == 2)
			padding = 2;

		if(((width*3) % 4) == 1)
			padding = 3;


		//creates the array based on width and height, width is * 3 because of the 3 RGB numbers
		colorData = new byte[height][width * 3];

		//fills each row with as much data as will fit
		for (int i = 0; i < height; i++){
			fis.read(colorData[i]);
			fis.skip(padding); //skips padding 0s at the end of each row
		}



		fis.close();

	}

	public void write(FileOutputStream newBMP) throws IOException {

		int total = size;
		int colorSize1 = height*(width*3);

		newBMP.write('B'); //B
		newBMP.write('M'); //M
		newBMP.write(intToByteArray(total)); //numbers that it said to use in project 1   ***Not showing correct values should be 324,856
		newBMP.write(intToByteArray(0)); //reserved 0
		newBMP.write(intToByteArray(54)); //always 54 to offset start of data
		newBMP.write(intToByteArray(40)); //header size always 40
		newBMP.write(intToByteArray(width)); //width pixels
		newBMP.write(intToByteArray(height)); //height pixels
		newBMP.write(shortToByteArray(1)); //planes in image, always 1 
		newBMP.write(shortToByteArray(24)); //color depth, 24bit
		newBMP.write(intToByteArray(0));
		newBMP.write(intToByteArray(colorSize1)); //numbers it said to use in project 1  **size of color in bytes
		newBMP.write(intToByteArray(72)); //always 72
		newBMP.write(intToByteArray(72)); //always 72
		newBMP.write(intToByteArray(0)); //colors in palate, use 0 when writing
		newBMP.write(intToByteArray(0)); //important colors, use 0 when writing




		for(int i = 0; i < height; i++) {    //fills in the rest with color data
			newBMP.write(colorData[i]);
			if (padding != 0){		//adds the padding back in if needed
				for (int j = 0; j < padding; j++)
					newBMP.write(0);
			}

		}
		newBMP.write(0);
		newBMP.write(0);


		System.out.println("Total bytes: "+total + " Color size: " + colorSize1 + " Height: " + height + " Width: " + width);

		newBMP.close();
	}

	//converts int to byte array
	public static byte[] intToByteArray(int num){
		byte[] array = new byte[4];
		array[0] = (byte) (num & 0xFF);  
		array[1] = (byte) ((num >> 8) & 0xFF);  
		array[2] = (byte) ((num >> 16) & 0xFF);  
		array[3] = (byte) ((num >> 24) & 0xFF);
		return array;
	}
	//converts short to byte array
	public static byte[] shortToByteArray(int num){
		byte[] array = new byte[2];
		array[0] = (byte) (num & 0xFF);  
		array[1] = (byte) ((num >> 8) & 0xFF);
		return array;
	}

	//converts byte array to int
	public static int byteArrayToInt(byte[] array) {
		int i = 0;
		i = (array[3] & 0xff) << 24| 
				(array[2] & 0xff) << 16|
				(array[1] & 0xff) << 8 |
				(array[0] & 0xff) << 0 ;


		return i;

	}






	public void invertColor(){
		int byteValue;
		for(int i = 0; i < height; i++){
			for(int j = 0; j < width*3; j++){
				byteValue = (int) colorData[i][j];
				colorData[i][j] = (byte) (255 - byteValue);				
			}


		}
	}

	public void grayScale(){
		int blueValue = 0;
		int greenValue = 0;
		int redValue = 0;
		double grayValue = 0;

		for(int i = 0; i < height; i++){
			for(int j = 0; j < width*3; j+=3){
				blueValue =  (int) colorData[i][j]; 
				greenValue =  (int) colorData[i][j+1]; 
				redValue =  (int) colorData[i][j+2]; 

				if(blueValue < 0)
					blueValue += 256;
				if(redValue < 0)
					redValue += 256;
				if(greenValue < 0)
					greenValue += 256;
				grayValue = (blueValue*(0.3))+(greenValue*(0.59))+(redValue*(0.11));
				grayValue = (int) Math.round(grayValue);

				colorData[i][j] = (byte) grayValue;
				colorData[i][j+1] = (byte) grayValue;
				colorData[i][j+2] = (byte) grayValue;

			}
		}

	}

	public void blur(){

	}

	public void verticalFlip(){
		for(int i = 0; i < (height / 2); i++) {
			byte [] temp = colorData[i];
			colorData[i] = colorData[height - i - 1];
			colorData[height - i - 1] = temp;
		}
	}
	public int byteToInt(byte data) {
		int intData = data;
		if(intData < 0)
			intData += 256;
		return intData;
	}
	public void shrinkSize(){
		byte[][] smallColorData = new byte[height / 2][(width / 2) * 3];
		
		float red = 0;
		float green = 0;
		float blue = 0;
		
		for(int row = 0; row / 2 < height / 2; row+=2){
			for(int col = 0; col / 2< width / 2; col+=2){
				red += byteToInt(colorData[row][col * 3 + 2]);
				red += byteToInt(colorData[row][col * 3 + 5]);
				red += byteToInt(colorData[row + 1][col * 3 + 2]);
				red += byteToInt(colorData[row + 1][col * 3 + 5]);
				
				green += byteToInt(colorData[row][col * 3 + 1]);
				green += byteToInt(colorData[row][col * 3 + 4]);
				green += byteToInt(colorData[row + 1][col * 3 + 1]);
				green += byteToInt(colorData[row + 1][col * 3 + 4]);
				
				blue += byteToInt(colorData[row][col * 3]);
				blue += byteToInt(colorData[row][col * 3 + 3]);
				blue += byteToInt(colorData[row + 1][col * 3]);
				blue += byteToInt(colorData[row + 1][col * 3 + 3]);
				
				blue = blue / 4; 	//Avg Blue
				green = green / 4;	//Avg Green
				red = red / 4;		//Avg Red
				
				blue = Math.round(blue);
				green = Math.round(green);
				red = Math.round(red);
				
				smallColorData[(row/2)][(col/2) * 3] = (byte) blue;
				smallColorData[(row/2)][(col/2) * 3 + 1] = (byte) green;
				smallColorData[(row/2)][(col/2) * 3 + 2] = (byte) red;
				
				red = 0;
				blue = 0;
				green = 0;
			}
		}
		
		//make H/W/S 2x small
		colorData = smallColorData;
		height /=2;
		width /=2;
		size = width*height;

	}

	public void doubleSize(){

		byte[][] largeColorData = new byte[height*2][width*6];


		for(int row = 0; row < height; row++){
			for(int col = 0; col < width; col++){
				for(int color = 0; color < 3; color++){
					largeColorData[row*2][col*2*3+color] = colorData[row][col*3+color];
					largeColorData[row*2+1][col*2*3+color] = colorData[row][col*3+color];
					largeColorData[row*2][(col*2+1)*3+color] = colorData[row][col*3+color];
					largeColorData[row*2+1][(col*2+1)*3+color] = colorData[row][col*3+color];
				}
			}
		}
		colorData = largeColorData;

		height *=2;
		width *=2;
		size = width*height;

	}



	public void rotate(){
		/*byte [][] rotated = new byte [width][height*3]; //creates new array with correct dimensions
		int k = width - 1; // 
		int z = (height*3)-3;

		for (int i=0; i<height; i++){
			k = width-1;

			for (int j=0;j<width*3; j+=3){
				rotated[k][z] = colorData[i][j];
				rotated[k][z+1] = colorData[i][j+1];
				rotated[k][z+2] = colorData[i][j+2];
				k--;
			}
			z-=3;
		}


		colorData = rotated;

		int temp = height;
		height = width;
		width = temp;
		 */



	}



}

