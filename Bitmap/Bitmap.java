/*
 * Names:
 * 	Nathan Cafarchio
 * 	James O'Brien
 * Date:
 * 	9/22/17
 * Class:
 * 	CS 221
 */
/*
 * While trying to thread the program we ran into an issue when a new thread was started and joined
 * it wouldn't be the same process spread across both threads, it however ran the same code at the same time
 * on both threads which wasn't correct.
 */
import java.io.*;
import java.util.Scanner;

public class Bitmap {
	private int width = 0;
	private int height = 0;
	private int size = 0;
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

		//creates the array based on width and height, width is * 3 because of the 3 RGB numbers
		colorData = new byte[height][width * 3];

		//fills each row with as much data as will fit
		for (int i = 0; i < height; i++){
			fis.read(colorData[i]);
			fis.skip(getPadding(width*3)); //skips padding 0s at the end of each row
		}
		//closes the file being read in	
		fis.close();

	}
	/*
	 * Method Write:
	 * 	This method will write out the header details for the bitmap as well as the color data
	 * 	for the user modified image. It also takes into account padding needed for special 
	 * 	case image's.
	 */
	public void write(FileOutputStream newBMP) throws IOException {

		int colorSize = height*(width*3);

		newBMP.write('B'); //B
		newBMP.write('M'); //M
		newBMP.write(intToByteArray(size)); //numbers that it said to use in project 1
		newBMP.write(intToByteArray(0)); //reserved 0
		newBMP.write(intToByteArray(54)); //always 54 to offset start of data
		newBMP.write(intToByteArray(40)); //header size always 40
		newBMP.write(intToByteArray(width)); //width pixels
		newBMP.write(intToByteArray(height)); //height pixels
		newBMP.write(shortToByteArray(1)); //planes in image, always 1 
		newBMP.write(shortToByteArray(24)); //color depth, 24bit
		newBMP.write(intToByteArray(0));
		newBMP.write(intToByteArray(colorSize)); //numbers it said to use in project 1
		newBMP.write(intToByteArray(72)); //always 72
		newBMP.write(intToByteArray(72)); //always 72
		newBMP.write(intToByteArray(0)); //colors in palate, use 0 when writing
		newBMP.write(intToByteArray(0)); //important colors, use 0 when writing


		for(int i = 0; i < height; i++) {    //fills in the rest with color data
			newBMP.write(colorData[i]);
			if (getPadding(width*3) != 0){		//adds the padding back in if needed
				for (int j = 0; j < getPadding(width*3); j++)
					newBMP.write(0);
			}

		}
		newBMP.write(0); //writing extra zero for padding
		newBMP.write(0); //writing extra zero for padding


		//System.out.println("Total bytes: "+ size + " Color size: " + colorSize + " Height: " + height + " Width: " + width);
		//closes file being written
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
	/*
	 * invertColor method:
	 * 	Use's a nested loop to step through the entire image checking each R, G, and B value
	 * 	and subtracting 255 from it.
	 */
	public void invertColor(){
		int byteValue;
		for(int i = 0; i < height; i++){
			for(int j = 0; j < width*3; j++){
				byteValue = (int) colorData[i][j];
				colorData[i][j] = (byte) (255 - byteValue);				
			}


		}
	}
	/*
	 * grayScale method:
	 * 	Use's a nested for loop to step through each pixel in the bitmap to multiply ever blue
	 * 	value by 0.3, every green value by 0.59, and every red value by 0.11. This converts
	 * 	each RGB pixel to a grey scale color.
	 */
	public void grayScale(){
		int blueValue = 0;
		int greenValue = 0;
		int redValue = 0;
		double grayValue = 0;

		for(int i = 0; i < height; i++){
			for(int j = 0; j < width*3; j+=3){
				//casts Blue, Green, and Red color byte data to int variables
				blueValue =  (int) colorData[i][j];
				greenValue =  (int) colorData[i][j+1]; 
				redValue =  (int) colorData[i][j+2]; 
				
				//checks to make sure color data is above 0, if not adds 256 to make data unsigned
				if(blueValue < 0)
					blueValue += 256;
				if(redValue < 0)
					redValue += 256;
				if(greenValue < 0)
					greenValue += 256;
				//converts RGB data to gray scale
				grayValue = (blueValue*(0.3))+(greenValue*(0.59))+(redValue*(0.11));
				grayValue = (int) Math.round(grayValue);
				
				//replaces RGB data in image with new calculated gray scale values
				colorData[i][j] = (byte) grayValue;
				colorData[i][j+1] = (byte) grayValue;
				colorData[i][j+2] = (byte) grayValue;

			}
		}

	}
/*
 * blurImage method:
 * 	This method makes a new temp array to hold a copy of the original image but with
 * 	a blurred effect on the image. To do this the method steps through the image looking
 * 	at a 5x5 quadrant of pixels at a time and averaging their colors together.
 * 	Finally it overwrites the original colorData array with the new temp array.
 */
	public void blurImage(){
		byte [][] blurredPixel = new byte [height][width*3];
		double redBlur;
		double greenBlur;
		double blueBlur;
		int rgbCounter;
		int pixelCounter;
		int specialRowTop;
		int specialRowBottom;
		int specialColLeft;
		int specialColRight;
		int byteValue;

		for(int row = 0; row < height; row++){ // goes through array, skipping 3 at a time to account for rgb values

			for(int col = 0; col < width*3; col+=3){	

				redBlur = 0;
				greenBlur = 0;
				blueBlur = 0;
				rgbCounter = 0;
				pixelCounter = 0;
				specialRowTop = row - 2;
				specialRowBottom = row + 2;
				specialColLeft = col - 6;
				specialColRight = col + 8;

				if(row == 0)
					specialRowTop = row;					
				if(row == 1)
					specialRowTop = row - 1;
				if(row == height - 1)
					specialRowBottom = row;
				if(row == height - 2)
					specialRowBottom = row + 1;

				if(col == 0)
					specialColLeft = col;
				if(col == 3)
					specialColLeft = col - 3;
				if(col == width*3-3)
					specialColRight = col + 2;
				if(col == width*3-6)
					specialColRight = col + 5;



				for(int sRow = specialRowTop; sRow <= specialRowBottom; sRow++){// goes through 5 X 15 array around pixel

					byteValue = 0;
					for(int sCol = specialColLeft; sCol <= specialColRight; sCol++){

						//convert to int

						if(colorData[sRow][sCol] < 0){
							byteValue = (int) colorData[sRow][sCol];
							byteValue += 256;
						}
						else if (colorData[sRow][sCol] >= 0) {
							byteValue = (int) colorData[sRow][sCol];
						}

						if(rgbCounter % 3 == 0)// adds rgb values together seperately
							blueBlur += byteValue;
						else if(rgbCounter % 3 == 1)
							greenBlur += byteValue;
						else if(rgbCounter % 3 == 2)
							redBlur += byteValue;

						rgbCounter++; // keeps track of number of rgb values (should be 75 in general case)
					}
				}
				pixelCounter+= rgbCounter/3; //gets number of pixels (25 in general case)
				blueBlur /= pixelCounter; //gets average values
				greenBlur /= pixelCounter;
				redBlur /= pixelCounter;
								
				blueBlur = Math.round(blueBlur);
				greenBlur = Math.round(greenBlur);
				redBlur = Math.round(redBlur);
				
				blurredPixel[row][col] = (byte) blueBlur;
				blurredPixel[row][col+1] = (byte) greenBlur;
				blurredPixel[row][col+2] = (byte) redBlur;

			}
		}
		colorData = blurredPixel;

	}

	/*
	 * verticalFlip method:
	 * 	Use's a for loop to step through image and save pixel data to a temp
	 * 	byte array that is flipped 180 degree's of the original picture.
	 */
	public void verticalFlip(){
		for(int i = 0; i < (height / 2); i++) {
			byte [] temp = colorData[i];
			colorData[i] = colorData[height - i - 1];
			colorData[height - i - 1] = temp;
		}
	}

	/*
	 * byteToInt method:
	 * 	method to convert byte values to int values and return the
	 * 	calculated int value.
	 */
	public int byteToInt(byte data) {
		int intData = data;
		if(intData < 0)
			intData += 256;
		return intData;
	}

	/*
	 * shrinkSize method:
	 * 	Creates a new array half the size of the original pictures colorData array.
	 * 	Then steps though the original picture colorData array and averages every
	 * 	2x2 pixel block's RGB data to convert to a single pixel in the newly created
	 * 	smaller array. Once the smaller array is filled the original colorData array
	 * 	is overwritten by it and height, width, and size is calculated for the bitmap header.
	 */
	public void shrinkSize(){
		byte[][] smallColorData = new byte[height / 2][(width / 2) * 3];

		float red = 0;
		float green = 0;
		float blue = 0;

		for(int row = 0; row / 2 < height / 2; row+=2){
			for(int col = 0; col / 2< width / 2; col+=2){
				//adds 2x2 quadrant of red pixels together
				red += byteToInt(colorData[row][col * 3 + 2]);
				red += byteToInt(colorData[row][col * 3 + 5]);
				red += byteToInt(colorData[row + 1][col * 3 + 2]);
				red += byteToInt(colorData[row + 1][col * 3 + 5]);
				//adds 2x2 quadrant of green pixels together
				green += byteToInt(colorData[row][col * 3 + 1]);
				green += byteToInt(colorData[row][col * 3 + 4]);
				green += byteToInt(colorData[row + 1][col * 3 + 1]);
				green += byteToInt(colorData[row + 1][col * 3 + 4]);
				//adds 2x2 quadrant of blue pixels together
				blue += byteToInt(colorData[row][col * 3]);
				blue += byteToInt(colorData[row][col * 3 + 3]);
				blue += byteToInt(colorData[row + 1][col * 3]);
				blue += byteToInt(colorData[row + 1][col * 3 + 3]);

				blue = blue / 4; 	//Avg Blue
				green = green / 4;	//Avg Green
				red = red / 4;		//Avg Red

				blue = Math.round(blue);	//rounds blue for byte cast
				green = Math.round(green);	//rounds green for byte cast
				red = Math.round(red);		//rounds red for byte cast

				//casts blue, green, and red values to correct spots in the smaller colorData array
				smallColorData[(row/2)][(col/2) * 3] = (byte) blue;
				smallColorData[(row/2)][(col/2) * 3 + 1] = (byte) green;
				smallColorData[(row/2)][(col/2) * 3 + 2] = (byte) red;
				//resets RGB values for next quadrant pass
				red = 0;
				blue = 0;
				green = 0;
			}
		}

		//make Height/Width/Size half the original size
		colorData = smallColorData;
		height /=2;
		width /=2;
		size = width*height;

	}
	
	/*
	 * doubleSize method:
	 * 	Use's 3 nested for loops to step through each pixels RGB values in the original picture colorData
	 * 	array and copies that to a 2x2 pixel quadrant in a newly created temp array to double the size
	 * 	of the original image. Then overwrites original colorData array with the new array to double
	 * 	the pictures size. Finally double height, width, and size header info for bitmap
	 */
	public void doubleSize(){
		//new temp array
		byte[][] largeColorData = new byte[height*2][width*6];

		for(int row = 0; row < height; row++){
			for(int col = 0; col < width; col++){
				for(int color = 0; color < 3; color++){
					/*
					 * Copies a single pixel's Red, Green, and Blue values from the original picture to
					 * a 2x2 pixel quadrant in the new temp array.
					 */
					largeColorData[row*2][col*2*3+color] = colorData[row][col*3+color];
					largeColorData[row*2+1][col*2*3+color] = colorData[row][col*3+color];
					largeColorData[row*2][(col*2+1)*3+color] = colorData[row][col*3+color];
					largeColorData[row*2+1][(col*2+1)*3+color] = colorData[row][col*3+color];
				}
			}
		}
		//make Height/Width/Size double the original size
		colorData = largeColorData;
		height *=2;
		width *=2;
		size = width*height;
	}
	/*
	 * rotate method:
	 * 	Takes the original image and rotates it counter-clockwise 90 degree's.
	 * 	To achieve that a new temp array is made with the size set to fit the original
	 * 	image rotated 90's counter-clockwise. Then the original image is ran through a 
	 * 	for loop to copy and relocated the original pixels to the new rotated array.
	 * 	Then overwrites original colorData array with the temp array.
	 * 	Finally sets the height, and width accordingly for bitmap header.
	 */
	public void rotate(){
		byte [][] rotated = new byte [width][height*3]; //creates new array with correct dimensions
		int k = width - 1;  
		int z = (height*3)-3;
		
		//outer loop to loop over new array's rows
		for (int i=0; i<height; i++){
			k = width-1;
			//inner loop to loop over new array's columns
			for (int j=0;j<width*3; j+=3){
				//copies pixel RGB data for each pixel from original colorData array to new array
				rotated[k][z] = colorData[i][j];
				rotated[k][z+1] = colorData[i][j+1];
				rotated[k][z+2] = colorData[i][j+2];
				k--;
			}
			z-=3;
		}
		//overwrites colorData with new rotated picture array
		colorData = rotated;
		//sets height and width accordingly
		int temp = height;
		height = width;
		width = temp;

	}
	/*
	 * getPadding method:
	 * 	This method takes in the width of a picture to determine if the picture will
	 * 	need special padding on its edges. This method is needed to keep images
	 * 	from having pixels all out of place, and does so by adding 1-3 bytes at the
	 * 	edge of each row.
	 */
	public int getPadding(int width){
		//finds any padding needed
		int padding = 0;
		if((width % 4) == 3)
			padding = 1;	

		if((width % 4) == 2)
			padding = 2;

		if((width % 4) == 1)
			padding = 3;

		return padding;

	}
}
