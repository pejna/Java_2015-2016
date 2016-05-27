/**
 * Contains demonstration classes for demonstrating classes from
 * hr.fer.zemris.java.custom.collections.
 * 
 */
package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.ObjectStack;

/**
 * Demonstrates the ObjectStack class. Recieves a string containing
 * integers and operands in postfix order and provides a solution.
 * Example: "8 -2 / -1 *" returns 4.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 *
 */
public class StackDemo {
	
	private static final String OPERATOR_ADD = "+";
	private static final String OPERATOR_SUBTRACT = "-";
	private static final String OPERATOR_MULTIPLY = "*";
	private static final String OPERATOR_DIVIDE = "/";
	private static final String OPERATOR_MODULO = "%";

	
	/**
	 * Starts the execution of the program.
	 * 
	 * @param args string containing integers and operands for 
	 * execution
	 */
	public static void main(String[] args) {
		ObjectStack stack = new ObjectStack();
		String[] input = args[0].replace('\"',  ' ').split(" ");
		
		for(String s : input){
			if(!isOperator(s)){
				stack.push(new Integer(
						Integer.parseInt(s)));
			
			} else {
				Integer b = (Integer) stack.pop();
				Integer a = (Integer) stack.pop();
				
				stack.push(new Integer(
						operate(a.intValue(), b.intValue(),s)));
			}
		}
		
		if(stack.size() != 1){
			System.err.println("Aborting - "
					+ "Incorrect argument amount!");
			System.exit(1);
		}

		System.out.println("Expression evaluates to " 
					+ ((Integer)stack.pop()).intValue()
					+ ".");
		
	}
	
	
	/**
	 * Determines whether the given string is a valid operand.
	 * Valid operands are +, -, *, / and %. Also checks if the given
	 * string is an integer. Throws {@link NumberFormatException} if
	 * not.
	 * 
	 * @param operator string we wish checked
	 * @return returns true if it is a supported operand, false if
	 * string is an int, throws {@link NumberFormatException} 
	 * otherwise
	 */
	private static boolean isOperator(String operator){
		operator = operator.trim();
		
		if(operator.equals(OPERATOR_ADD)){
			return true;
		} else if(operator.equals(OPERATOR_SUBTRACT)){
			return true;
		} else if(operator.equals(OPERATOR_MULTIPLY)){
			return true;
		} else if(operator.equals(OPERATOR_DIVIDE)){
			return true;
		} else if(operator.equals(OPERATOR_MODULO)){
			return true;
		}
		
		//checks if the sign is parsable if already not found
		//throws exception if it isn't
		Integer.parseInt(operator);
		return false;
	}
	
	
	/**
	 * Operates the operator with the given integers.
	 * Example: operate(3, 2, /) does 3 / 2.
	 * 
	 * @param a first operand
	 * @param b second operand
	 * @param operator operator to be used
	 * @return returns the solution of the operation
	 * @throws IllegalArgumentException thrown if not supported 
	 * operator, or operator is an integer
	 */
	private static int operate(int a, int b, String operator)
			throws IllegalArgumentException{
		if(!isOperator(operator)){
			throw new IllegalArgumentException("Warning - "
					+ "Given operator is not supported!");
		}
		operator = operator.trim();
		
		if(operator.equals(OPERATOR_ADD)){
			return a + b;
		
		} else if(operator.equals(OPERATOR_SUBTRACT)){
			return a - b;
		
		} else if(operator.equals(OPERATOR_MULTIPLY)){
			return a * b;
		
		} else if(operator.equals(OPERATOR_DIVIDE)){
			return a / b;
		
		} else {
			return a % b;
		}
	}
	
	
	
}
