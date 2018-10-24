/*Project 3: Are You a Good Witch or a Bad Witch?s
 *Names:
 * Anh Nguyen
 * James O'Brien
 *Date:
 *	11/9/17
 *Class:
 *	CS 221
 */
import java.util.*;
import java.io.*;
public class SentimentAnalyzer {

	public static void main(String[] args) {
		Scanner in = new Scanner (System.in);
		Scanner file = null;

		boolean existStop =false;
		boolean existReviews =false;
		WordTable stopWords = new WordTable();
		WordTable reviewsWords = new WordTable();

		System.out.print("Enter a stop word file: ");
		String stop = in.nextLine() ;
		
		//While loop to take in a stop word text document
		while (!existStop){
			try{
				file = new Scanner(new File (stop));
				existStop = true;
			}
			catch ( FileNotFoundException e){ //if user entered document not found does catch
				System.out.println("Stop File not found!");
				existStop = false ;
				System.out.print("Enter a stop word file: ");
				stop = in.nextLine();
			}
		}
		//while loop to read in each word from the stop word document
		while(file.hasNext()){
			String word = file.next();
			stopWords.add(word,2);
		}
		System.out.print("Enter a review file: ");
		String review = in.nextLine() ;
		//While loop to take in a review text document
		while (!existReviews){
			try{
				file = new Scanner(new File (review));
				existReviews = true;
			}
			catch ( FileNotFoundException e){
				System.out.println("Review File not found!");
				existReviews = false ;
				System.out.print("Enter a review file: ");
				review = in.nextLine();
			}
		}
		/*
		 * while loop that loops through phrase given, evaluates every word and assigns a 
		 * score for each word and add's the word to the red-black tree.
		 */
		while(file.hasNextInt())
		{
			int score = file.nextInt();
			String line = file.nextLine();
			line = line.toLowerCase();
			int firstPosition = 0;
			int endPosition = 0; 
			boolean checker = false;
			
			//For loop to go through each line in the review document.
			for ( int i = 0; i < line.length(); i++){
				char letter = line.charAt(i);
				//if statement to find beginning letter of word
				if ( !checker){
					if(Character.isLetter(letter)){
						checker = true; 
						firstPosition = i;
						endPosition = i;
					}
				}
				else {
					//if statement to update end position of each word.
					if(Character.isLetter(letter))
						endPosition = i;
					/*if it finds a character other than a-z or - or \ and makes sure it's not a stop word
					it then appends the word plus its score to the tree.*/
					else if(letter !='-' && letter != '\''){
						checker = false;
						String word = line.substring(firstPosition,endPosition+1);
						if(!stopWords.contains(word))
							reviewsWords.add(word,score);
					}
				}
			}
		}
		// try-catch creates a .words file with every word and score from red-black tree.
		try{
			PrintWriter out = new PrintWriter(review + ".words");
			reviewsWords.print(out);
			out.close();
		}
		catch( FileNotFoundException e){
			e.printStackTrace();
		}
		System.out.println("Word statistics written to file " + review + ".words" );


		String analyze;
		String input;
		double score;
		/*
		 * Do-While loop that takes in a user defined phrase to analyze from the review document.
		 * If the user gives the code just a single word the code with calculate the word to always
		 * have a score of 2 which is neutral. If a sentence is entered then the code will take the
		 * average score of all of the review words in the sentence. If the score is below 2 then the
		 * sentence is overall negative, if over a score of 2 then it is overall positive. If exactly
		 * 2 then the sentence is neutral. If a word or sentence is entered that contains no review words
		 * then the program will return a message saying that no calculations could be made and will
		 * prompt the user to enter another word or sentence if they desire.
		 */
		do{
			System.out.print("Would you like to analyze a phrase? (yes/no) ");
			analyze = in.nextLine().toLowerCase(); //converts everything to lowercase for easier comparing.
			if ( analyze.equals("yes")){
				System.out.print("Enter phrase: "); 
				input = in.nextLine();
				score= parse(input, stopWords, reviewsWords);
				//if statement that occurs when there are no words from review words document in the user input
				if (score < 0 )
					System.out.print(("Your phrase contained no words that affect sentiment. \n"));
				//else statement that outputs the score and tells user if their input was positive or negative
				else{
					System.out.format("Score: %.3f \n",score);
					if ( score > 2.0)
						System.out.println(("Your phrase was positive."));
					else if (score < 2.0 )
						System.out.println(("Your phrase was negative."));
					else
						System.out.println(("Your phrase was perfectly neutral."));
				}
			}
			//if user did not want to analyze another phrase or word then the program quits.
			else if ( analyze.equals("no")) 
				System.exit(0);
			//if user entered an option other than "yes" or "no" then this else will catch it an stop the program.
			else 
				System.out.printf("Option  does not exist!");
		}
		while ( analyze.equals("yes") );


	}
	/*
	 * Parse method takes in user defined input, along with the stop word list and the reviews word list to compare to.
	 * Goes through every word in input and adds their score to a score total, once it reaches the end of input it
	 * divides the total by how many words there are and returns that as the average score for the sentence.
	 */
	public static double parse (String input, WordTable stopWords, WordTable reviewsWords){
		double score = 0;
		input = input.toLowerCase();
		int firstPosition = 0;
		int endPosition = 0; 
		boolean checker = false;
		int count = 0 ;
		//for loop that reads through user given input
		for ( int i= 0 ; i < input.length(); i++){
			char letter = input.charAt(i);
			//if statement that finds the starting letter for each word.
			if ( !checker){
				if(Character.isLetter(letter)){
					checker = true; 
					firstPosition = i;
					endPosition = i;
				}
			}
			//else statement that will find the end point of the word and will add its score to the total and increment count.
			else {
				if(Character.isLetter(letter))
					endPosition = i;
				/*if it runs into a character that is not a legal character, then it sets word to the word found, and checks if the word found is in
				the review list. If it is then the words score gets added to the main score and count get incremented.*/
				else if(letter !='-' && letter != '\''){
					checker = false;
					String word = input.substring(firstPosition,endPosition + 1);
					if(reviewsWords.contains(word)){
						score += reviewsWords.getScore(word);
						count++;
					}
					//if the word was a stop word then it wont add a custom score, however it will just increment total score by two and increment count.
					else if (!stopWords.contains(word)){
						score += 2.0;
						count++;
					}
				}
			}
		}
		if (checker){
			String word = input.substring(firstPosition,endPosition + 1);
			/*if a word is also found in the review file, then it gets it score from the getScore method and 
			has it added to the main score. Count is also incremented.*/
			if(reviewsWords.contains(word)){
				score += reviewsWords.getScore(word);
				count++;
			}
			//if stop word found then score is incremented by 2 and count is also incremented.
			else if (!stopWords.contains(word)){
				score += 2.0;
				count ++;
			}
		}
		//if there were no words that could be scored
		if ( count == 0)
			return - 2.0 ;
		//average score for an entire sentence.
		double average = score / count; 
		return average;
	}
}
