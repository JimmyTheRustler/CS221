/*
 * Authors: Jimmy O'Brien and Austin Reth
 * CS 221 Database Structures
 * Dr. Barry Wittman
 * Project 4
 * Created: 11/27/2017
 * Description: Loads an undirected graph and supports a number of different operations
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Graphs {


	public static void main(String[] args) {
		int input = 1;
		Scanner in = new Scanner (System.in);
		boolean existsFile = false;
		boolean legalInput = false;
		boolean existsInput = false;
		Scanner file = null;
		//takes in user defined graph from a text file.
		System.out.print("Enter your graph file: ");
		String graphFile = in.nextLine();
		while(!existsFile){
			try{	//try - catch to make sure user enters a legal file.
				file = new Scanner(new File(graphFile));
				existsFile = true;
			}catch(FileNotFoundException e){
				System.out.print("File not found! Try another file: ");
				existsFile = false ;
				graphFile = in.nextLine();
			}
		}
		int size = file.nextInt();
		if(size == 0){	//check for a empty graph
			System.out.println("Graph needs to have nodes.");
			return;
		}

		Graph graphWeight = new Graph(size); //calls Graph constructor method
		Graph.makeMatrix(file, graphWeight); //creates an adjacency matrix from graph

		while(input != 8) { //while loop to take in user input for options 1 to 8
			System.out.println("1. Is Connected");
			System.out.println("2. Minimum Spanning Tree");
			System.out.println("3. Shortest Path");
			System.out.println("4. Is Metric");
			System.out.println("5. Make Metric");
			System.out.println("6. Traveling Salesman Problem");
			System.out.println("7. Approximate TSP");
			System.out.println("8. Quit\n");


			Scanner userInput = new Scanner(System.in);
			System.out.print("Make your choice (1 - 8): ");
			String stringInput = userInput.nextLine();
			while(!existsInput) { //try-catch to make sure user enters a legal input.
				try {
					input = Integer.parseInt(stringInput);
					existsInput = true;
				}catch(NumberFormatException e) {

					System.out.println("\nOnly integers allowed.");
					System.out.println("1. Is Connected");
					System.out.println("2. Minimum Spanning Tree");
					System.out.println("3. Shortest Path");
					System.out.println("4. Is Metric");
					System.out.println("5. Make Metric");
					System.out.println("6. Traveling Salesman Problem");
					System.out.println("7. Approximate TSP");
					System.out.println("8. Quit\n");
					System.out.print("Make your choice (1 - 8): ");
					existsInput = false;
					stringInput = in.nextLine();
				}
			}			
			existsInput = false;
			if(input >= 1 && input <8) {
				if(input == 1){ //perform Is connected
					if(Graph.isConnected(graphWeight))
						System.out.println("Graph is connected.");
				} 
				else if(input == 2){ //perform Minimum Spanning Tree
					int[][] mst = Graph.MST(graphWeight);
					if(mst[0][0] != -1)
						printMST(mst);
				}
				else if(input == 3){ //perform Shortest Path
					int highest = size - 1;
					int start = -1;
					boolean existsInput1 = false;

					while( (start < 0 || highest < start) ){
						System.out.print("From which node would you like to find the shortest paths (0 - " + highest + "): ");
						String input1 = in.nextLine();
						while(!existsInput1){
							try{
								start = Integer.parseInt(input1);
								if(start >= 0 && start <= highest)
									existsInput1 = true;
								else{
									System.out.print("Please enter a number between 0-" + highest + ": ");
									input1 = in.nextLine();
								}
							}catch(NumberFormatException e){
								System.out.print("Please enter a number between 0-" + highest + ": ");
								input1 = in.nextLine();
							}

						}
					}
				} 
				else if(input == 4){ //perform Is Metric
					Graph.isMetric(graphWeight);
				} 
				else if(input == 5){ //perform Make Metric
					Graph.makeMetric(graphWeight);
				} 
				else if(input == 6){ //perform TSP
					Graph.tsp(graphWeight);
				} 
				else if(input == 7){ //perform Approximate TSP
					Graph.ATSP(graphWeight);
				} 
			}
			else {
				if(input != 8)
					System.out.println("Please only enter integers between 1 and 8\n");
			}
			System.out.println();
		}
		System.out.println("Program Closed");
	}

	/**
	 * helper method to print out Minimum Spanning Tree of graph.
	 * @param mst
	 */
	private static void printMST(int[][] mst) {
		for(int i = 0; i < mst.length; i++){
			for(int j = 0; j < mst[i].length; j++)
				System.out.print(mst[i][j] + " ");
			System.out.println();
		}
	}
}