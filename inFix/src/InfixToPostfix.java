/*
 * Project 2: Inifx to postfix Expression
 * 
 * 
 * cases:
 * 	(num)(num) == num*num
 * 
 * if charAt(0) == '-' it's negative
 */
import java.util.Scanner;

public class InfixToPostfix {

	public static void main(String[] args) {
		Scanner in=new Scanner(System.in);
		int i=0;
		double num=0;
		String expression="";//used to parse from character to double
		String stdInfix="";
		String postfixExp="";

		double answer = 0;
		double value1 = 0;
		double value2=0;
		char c = 0;

		boolean operatorFlag=false;//to check for multiple operators for optional negative and positive numbers
		boolean decimalFlag=false;//to check for more decimals after inputting a decimal

		Queue input=new Queue();
		Queue output = null;
		OperatorStack stack=new OperatorStack();

		System.out.print("Enter infix notation: ");
		String infixExp=in.nextLine();

		/**
		 * while loop converts input to infix notation
		 */
		while(i<infixExp.length()){

			if(infixExp.charAt(i)==' ') i++;//iterates through whitespace and skips

			if((infixExp.charAt(i)=='+' || infixExp.charAt(i)=='-' || infixExp.charAt(i)=='/' || infixExp.charAt(i)=='*')) {
				if(!operatorFlag) {
					stdInfix+=infixExp.charAt(i)+" ";
					input.enqueue(infixExp.charAt(i));
					i++;
					operatorFlag=true;
				}
				else{
					if(infixExp.charAt(i)=='+' || infixExp.charAt(i)=='-')
						expression+=infixExp.charAt(i);
					i++;
				}
			}


			else if(infixExp.charAt(i)=='(' || infixExp.charAt(i)==')') {
				if(infixExp.charAt(i)=='(') operatorFlag=true;
				stdInfix+=infixExp.charAt(i)+" ";
				input.enqueue(infixExp.charAt(i));
				i++;
			}

			try{
				while(i<infixExp.length() && (Character.isDigit(infixExp.charAt(i)) || infixExp.charAt(i)=='.')) {//if the character is a digit or a decimal
					if(!decimalFlag && infixExp.charAt(i) == '.')
						expression+=infixExp.charAt(i);//adds the double to a
					else if(Character.isDigit(infixExp.charAt(i)))
						expression+=infixExp.charAt(i);
					else {
						return;
					}
					i++;
				}
				if(expression.length()>0){
					num=Double.parseDouble(expression);//parses the data into a Double value
					stdInfix+=num+" ";
					input.enqueue(num);//puts it in the queue
					operatorFlag=false;
				}
				expression="";//clears the string
			}catch(Exception e){
				System.out.println("Invalid Operand");
				return;
			}

		}

		System.out.print("Standard Infix Notation: "+stdInfix+"\n");

		try {
			output=infixToPostfix(input);
		} catch (UnbalancedRightParenthesesException e) {
			System.out.println("Unbalanced Right Parentheses ')'");
			return;
		} catch (UnbalancedLeftParenthesesException e) {
			System.out.println("Unbalanced Left Parentheses '('");
			return;
		}

		System.out.print("Postfix Notation: ");
		while(output.size()>0){
			if(output.front().equals('+') || output.front().equals('-') || output.front().equals('/') || output.front().equals('*')){
				c=(char) output.front();//sets 'c' to the value inside of output.front
				value2=(double) stack.pop();
				value1=(double) stack.pop();
			}

			else{//if operand
				stack.push(output.front());
			}
			if(c=='+' || c=='-' || c=='*' || c=='/'){
				answer=calc(value1,value2,c);
				stack.push(answer);
			}
			System.out.print(output.dequeue()+" ");
			c=' ';
		}
		System.out.print("\nAnswer: "+stack.pop());
	}

	public static double calc(double a,double b,char c){
		switch(c){
		case '+': 
			return a+b;		
		case '-':
			return a-b;
		case('/'):
			return a/b;
		default:
		case('*'):
			return a*b;
		}

	}

	public static Queue infixToPostfix(Queue input) throws UnbalancedRightParenthesesException, UnbalancedLeftParenthesesException{
		/**
		 * method to convert from infix to postfix
		 */
		Queue output=new Queue();
		OperatorStack stack=new OperatorStack();

		String postfixExp="";
		while(input.size()>0) {
			if(input.front().equals(')')){//when you hit a ')'
				while(stack.size()>0 && !stack.top().equals('(')) {//until you hit a left parentheses
					output.enqueue(stack.pop());//add the operator to the queue and pop from the stack
				}
				if(stack.size()==0)
					throw new UnbalancedRightParenthesesException();
				stack.pop();//once you get to a left parentheses, pop
				input.dequeue();
			}

			else if(input.front().equals('*') || input.front().equals('/')) {//highest precedence, so no need to pop, unless '*' or '/'
				if(stack.size()==0)
					stack.push(input.dequeue());

				else {
					while(stack.size()>0 && (stack.top().equals('*') || stack.top().equals('/'))) {//if our operator is either of these
						output.enqueue(stack.pop());
					}
					stack.push(input.dequeue());//then push our operator
				}
			}

			else if(input.front().equals('+') || input.front().equals('-')) {
				if(stack.size()==0)//if the stack is empty, just push the operator
					stack.push(input.dequeue());

				else {
					while(stack.size()>0 && (stack.top().equals('*') || stack.top().equals('/') || stack.top().equals('+')  || stack.top().equals('-'))) {//while the top of the stack is eitherof these operators
						output.enqueue(stack.pop());//add those operators to the queue
					}
					stack.push(input.dequeue());
				}
			}

			else if(input.front().equals('(')) {
				stack.push(input.dequeue());
			}

			else {
				output.enqueue(input.dequeue());//adds operands to the queue
			}
		}
		while(stack.size()>0){
			Object temp=(stack.pop());
			if(temp.equals('('))
				throw new UnbalancedLeftParenthesesException();
			output.enqueue(temp);
		}
		return output;
	}
}

