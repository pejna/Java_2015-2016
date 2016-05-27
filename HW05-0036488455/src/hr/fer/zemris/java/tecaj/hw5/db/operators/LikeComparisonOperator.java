package hr.fer.zemris.java.tecaj.hw5.db.operators;


/**
 * Determines whether the first given value is simmilar
 *  to the second given value. Used * to represent uncompared
 *  part of the second string.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */
public class LikeComparisonOperator implements IComparisonOperator {

	/**
	 * Used as a character representing unspecified number of
	 * unspecified characters.
	 */
	public static final char WILDCARD = '*';
	
	@Override
	public boolean satisfied(String value1, String value2) {
		if(value1 == null){
			throw new IllegalArgumentException("Warning - "
					+ "Value1 cannot be null!");
		}
		if(value2 == null){
			throw new IllegalArgumentException("Warning - "
					+ "Value 2 cannot be null!");
		}
		
		
		if(value2.endsWith(WILDCARD + "")){
			return value1.startsWith(
					value2.substring(0, value2.indexOf(WILDCARD)));
		}
		
		if(value2.startsWith(WILDCARD + "")){
			return value1.endsWith(
					value2.substring(
							value2.
							indexOf(WILDCARD) + 1, value2.length()));
		}
		
		if(value2.indexOf(WILDCARD) == -1){
			return value1.equals(value2);
		}
		
		String[] parts = value2.split("\\" + WILDCARD);
		
		boolean startsWith = value1.startsWith(parts[0]);
		boolean endsWith = value1.endsWith(parts[1]);
		
		return startsWith && endsWith;
	}

}
