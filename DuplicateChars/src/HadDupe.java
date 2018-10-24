import java.util.Scanner;
public class HadDupe {

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		System.out.print("Enter String: ");
		String phrase = input.nextLine();
		if( hasDuplicates2(phrase) )
			System.out.print("Your string has dupes");
		else
			System.out.print("Your string has dupes");
	}
	//N^2 time
	public static boolean hasDuplicates(String phrase){
		for(int i  = 0; i < phrase.length(); i++){
			for(int j = i + 1; j < phrase.length(); j++ ){
				if( phrase.charAt(i) == phrase.charAt(j) )
					return true;
			}
		}
		return false;
	}
	//not correct
	//runs in N time
	public static boolean hasDuplicates2(String phrase){
		boolean[] array = new boolean[26];
		phrase = phrase.toUpperCase();
		for(int i = 0; i < phrase.length(); i++){
			char c = phrase.charAt(i);
			if (c >= 'A' && c <= 'Z'){
				if(array[c - 'A'])
					return true;
				else
					array[c - 'A'] = true;
			}
		}
		return false;
	}

}
