package hr.fer.zemris.java.cstr;


/**
 * A reconstruction of java.lang.String from Java7. For all other
 * informations look for guideance in {@link String}.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */
public class CString {

	/**
	 * Length of CString.
	 */
	private int length;
	
	/**
	 * Start of CString in value array.
	 */
	private int offset;
	
	/**
	 * Array containing values in CString.
	 */
	private char[] value;
	
	/**
	 * Used in indexOf, sent when character is not present in string.
	 */
	private static final int NOT_PRESENT = -1;
	
	
	/*
	 * ******** Constructor methods **********************************
	 */
	
	
	/**
	 * Creates a new CString from the given char array with the start
	 * of new CString at the offset position of the array and with
	 * given length. Throws {@link IllegalArgumentException} if given
	 * null array or invalid offset or length.
	 * 
	 * @param data array with characters fro new CString
	 * @param offset where does the CString start in relation to
	 * the given array
	 * @param length length of the CString
	 */
	public CString(char[] data, int offset, int length){
		if(data == null){
			throw new IllegalArgumentException("Warning - "
					+ "CString cannot have null as it's data!");
		}
		
		if(offset < 0){
			throw new IllegalArgumentException("Warning - "
					+ "Offset cannot be below zero!");
		}
		
		if(length < 0){
			throw new IllegalArgumentException("Warning - "
					+ "New CString cannot have negative length!");
		}
		
		if(offset + length > data.length){
			throw new IllegalArgumentException("Warning - "
					+ "Desired CString cannot be "
					+ "outside the given array!");
		}
		
		char[] newValue = new char[length];
		for(int i = 0; i < length; i++){
			newValue[i] = data[offset + i];
		}
		
		value = newValue;
		this.offset = 0;
		this.length = length;
	}
	
	
	/**
	 * Creates a CString from the entire given char array. Throws
	 * {@link IllegalArgumentException} if given null.
	 * 
	 * @param data array containing elements of the new CString
	 */
	public CString(char[] data){
		this(data, 0, data.length);		
	}
	
	
	/**
	 * Creatres a new CString with the contents the same as the given
	 * CString.
	 * 
	 * @param original original CString
	 */
	public CString(CString original){
		if(original == null){
			throw new IllegalArgumentException("Warning - "
					+ "Cannot create a copy of null CString!");
		}
		
		if(original.length < original.value.length){
			this.value = original.toCharArray();
			this.offset = 0;
		} else {
			this.value = original.value;
			this.offset = original.offset;
		}
		this.length = original.length;
	}
	
	
	/**
	 * Package private constructor that does not create a new array
	 * for the value of the CString.
	 * 
	 * @param offset offstet of the start of CString from the start
	 * of given array
	 * @param length length of CString
	 * @param data array containing CString
	 */
	CString(int offset, int length, char[] data){
		this.offset = offset;
		this.length = length;
		this.value = data;
	}
	
	/*
	 * ******** Factory class methods ********************************
	 */
	
	
	/**
	 * Creates a CString from the given String. Throws 
	 * {@link IllegalArgumentException} if given null.
	 * 
	 * @param string template of the new CString
	 * @return returns the created CString
	 */
	public static CString fromString(String string){
		if(string == null){
			throw new IllegalArgumentException("Warning - "
					+ "Cannot create CString from null String!");
		}
		
		return new CString(string.toCharArray());
	}
	
	
	/*
	 * ******** Utility methods **************************************
	 */
	
	
	/**
	 * Gets the length of the CString.
	 * 
	 * @return returns the length of the CString
	 */
	public int length(){
		return length;
	}
	
	
	/**
	 * Gets the character in the CString at the requested index. 
	 * Throws {@link IllegalArgumentException} if requested index is
	 * out of bounds.
	 * 
	 * @param index index of the requested character
	 * @return returns the character at the requested index
	 */
	public char charAt(int index){
		if(index >= length){
			throw new IllegalArgumentException("Warnign - "
					+ "Cannot access index outside of CString!");
		}
		
		if(index < 0){
			throw new IllegalArgumentException("Warning - "
					+ "Cannot acces index outside of CString!");
		}
		
		return value[offset + index];
	}
	
	
	/**
	 * Creates an array from the contens of CString.
	 * 
	 * @return returns the array containing contents of CString
	 */
	public char[] toCharArray(){
		char[] newArray = new char[length];
		
		for(int i = 0; i< length ; i++){
			newArray[i] = value[i + offset];
		}
		
		return newArray;
	}
	
	
	@Override
	public String toString(){
		return new String(value, offset, length);
	}
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CString other = (CString) obj;
		if (length != other.length)
			return false;
		return this.toString().equals(other.toString());
	}
	
	
	/**
	 * Gets the index of the first occurence of given character in 
	 * CString. Returns -1 if not present.
	 * 
	 * @param c char we want found
	 * @return returns index of first occurence of given character
	 */
	public int indexOf(char c){
		for(int i = 0; i < length; i++){
			if(value[i] == c){
				return i;
			}
		}
		
		return NOT_PRESENT;
	}
	
	/*
	 * ******** Contains methods *************************************
	 */
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		
		return (this.toCharArray().toString() + length).hashCode();
	}


	/**
	 * Returns true if CString begins with the given CString, 
	 * otherwise returns false. Thros {@link IllegalArgumentException}
	 * if given null.
	 * 
	 * @param cstring possible start of the CString
	 * @return true if true, false if false
	 */
	public boolean startsWith(CString cstring){
		if(cstring == null){
			throw new IllegalArgumentException("Warning - "
					+ "CString cannot contain null!");
		}
		
		if(cstring.length > length){
			return false;
		}
		
		for(int i = 0; i < cstring.length; i++){
			if(value[offset + i] != 
					cstring.value[cstring.offset + i]){
				return false;
			}
		}
		
		return true;
	}
	
	
	/**
	 * Returns true if this CString ends with the given CString, 
	 * otherwise returns false.
	 * 
	 * @param cstring possible end of this CString
	 * @return true if true, false if false
	 */
	public boolean endsWith(CString cstring){
		if(cstring == null){
			throw new IllegalArgumentException("Warning - "
					+ "CString cannot contain null!");
		}
		
		if(cstring.length > length){
			return false;
		}
		
		for(int i = 0; i < cstring.length; i++){
			if(value[offset + length - cstring.length + i] != 
					cstring.value[cstring.offset + i]){
				return false;
			}
		}
		
		return true;
	}
	
	
	/**
	 * Returns true if this CString contains the given CString, 
	 * otherwise returns false.
	 * 
	 * @param cstring possible part of this CString
	 * @return true if true, false if false
	 */
	public boolean contains(CString cstring){
		if(cstring == null){
			throw new IllegalArgumentException("Warning - "
					+ "CString cannot contain null!");
		}
		
		if(cstring.length > length){
			return false;
		}
		
		if(cstring.length == 0){
			return true;
		}
		
		int index = 0;
		while(index <= length - cstring.length){
			
			if(value[offset+index] == cstring.value[cstring.offset]){
				boolean found = true;
				
				for(int i = 0; i < cstring.length; i++){
					if(value[offset + index + i] != 
							cstring.value[cstring.offset + i]){
						found = false;
						break;
					}
				}
				if(found == true){
					return true;
				}
			}
			index++;
		}
		
		return false;
	}
	
	
	/*
	 * ******** Substring methods ************************************
	 */
	
	
	/**
	 * Creates a substring of CString starting and ending at the
	 * requested indexes. Substring starts at given startIndex, but
	 * endIndex is the index after the last character.
	 * 
	 * @param startIndex starting index of the substring
	 * @param endIndex one after the last index of the substring
	 * @return returns the new substring as a CString
	 */
	public CString substring(int startIndex, int endIndex){
		if(startIndex <0){
			throw new IllegalArgumentException("Warning - "
					+ "Cannot create substring starting at "
					+ "given index!");
		}
		
		if(endIndex < startIndex){
			throw new IllegalArgumentException("Warning - "
					+ "Cannot create CString with ending index "
					+ "smaller than starting index!");
		}
		
		if(endIndex > length){
			throw new IllegalArgumentException("Warning - "
					+ "Unable to have substring outside "
					+ ""
					+ "of the string!");
		}
		
		return new CString(offset + startIndex, 
				endIndex - startIndex,
				value);
	}
	
	
	/**
	 * Creates a substring form CString's first n characters. 
	 * Throws {@link IllegalArgumentException} if
	 * given length is out of bounds.
	 * 
	 * @param n number of characters we want extracted
	 * @return returns the requested substring
	 */
	public CString left(int n){
		if(n > length){
			throw new IllegalArgumentException("Warning - "
					+ "Not enough characters in CString!");
		}
		
		return this.substring(0, n);
	}
	
	
	/**
	 * Creates a substring form CString's last n characters. 
	 * Throws {@link IllegalArgumentException} if
	 * given length is out of bounds.
	 * 
	 * @param n number of characters we want extracted
	 * @return returns the requested substring
	 */
	public CString right(int n){
		if(n > length){
			throw new IllegalArgumentException("Warning - "
					+ "Not enough characters in CString!");
		}
		
		return this.substring(length - n, length);
	}
	
	
	/*
	 * ******** Adding methods ***************************************
	 */
	
	
	/**
	 * Adds the given CString to the end of this CString to create
	 * a new CString.
	 * 
	 * @param cstring CString to be added to this CString
	 * @return created CString from this and given CString
	 */
	public CString add(CString cstring){
		if(cstring == null){
			throw new IllegalArgumentException("Warning - "
					+ "Cannot add null to CString!");
		}
		
		char[] newArray = new char[length + cstring.length];
		
		for(int i = 0; i < length; i++){
			newArray[i] = value[offset + i]; 
		}
		
		int newStart = length;
		
		for(int i = 0; i < cstring.length; i++){
			newArray[newStart + i] = 
					cstring.value[cstring.offset + i];
		}
		
		return new CString(newArray);
	}
	
	/*
	 * ******** Replacing methods ************************************
	 */
	
	
	/**
	 * Creates a new CString with requested characters replaced with
	 * given characters.
	 * 
	 * @param oldChar characters to be replaced
	 * @param newChar characters to be replaced with
	 * @return returns new CString with replaced characters
	 */
	public CString replaceAll(char oldChar, char newChar){
		char[] newArray = new char[length];
		for(int i = 0; i < length; i++){
			if(value[offset+ i] == oldChar){
				newArray[i] = newChar;
			} else {
				newArray[i] = value[offset + i];
			}
		}
		
		return new CString(newArray);
	}
	
	
	/**
	 * Creates a new CString with requested substrings replaced with
	 * given substrings. Throws {@link IllegalArgumentException} if
	 * either is null.
	 * 
	 * @param oldStr substring to be replaced
	 * @param newStr substring to be replaced with
	 * @return returns new CString with replaced substrings
	 */
	public CString replaceAll(CString oldStr, CString newStr){
		if(oldStr == null){
			throw new IllegalArgumentException("Warning - "
					+ "CString does not contain null!");
		}
		
		if(newStr == null){
			throw new IllegalArgumentException("Warning - "
					+ "Cannot replace given substring with null!");
		}
		
		if(oldStr.equals(newStr)){
			return new CString(this);
		}
		
		char[] newStrValue = newStr.toCharArray();
		
		/*
		 * Puts newStr between every character in array if oldStr is
		 * empty
		 */
		if(oldStr.length == 0){
			char[] newArray = new char[length + 
			                           (length + 1) * newStr.length];
			int arrayCounter = 0;
			for(int i = 0; i < newStr.length; i++){
				newArray[arrayCounter] = newStrValue[i];
				arrayCounter++;
			}
			
			for(int i = 0; i < length; i++){
				newArray[arrayCounter] = value[offset + i];
				arrayCounter++;
				
				for(int j = 0; j < newStrValue.length; j++){
					newArray[arrayCounter] = newStrValue[j];
					arrayCounter++;
				}
			}
			
			return new CString(0, newArray.length, newArray);
		}
		
		boolean[] toReplace = new boolean[length];
		char[] oldStrValue = oldStr.toCharArray();
		int numberOfOccurences = 0;
		
		/*
		 * Goes through array once and marks and counts the starts
		 * of oldStr in the array
		 */
		for(int i = 0; i <= length - oldStr.length; i++){
			if(value[offset + i] != oldStrValue[0]){
				toReplace[i] = false;
				
			} else {
				boolean isOldStr = true;
				
				for( int j = 0 ; j < oldStrValue.length; j++){
					if(value[offset + i + j] != oldStrValue[j]){
						isOldStr = false;
						break;
					}
				}
				
				if(isOldStr){
					toReplace[i] = true;
					numberOfOccurences++;
				} else {
					toReplace[i] = false;
				}
			}
		}
		
		if(numberOfOccurences == 0){
			return new CString(offset, length, value);
		}
		
		char[] newArray = new char[
		                           length + numberOfOccurences * 
		                           (newStr.length - oldStr.length)
		                           ];
		
		/*
		 * goes through original array and if character is a start
		 * of oldStr then it is replaced
		 */
		for(int i = 0, arrayCounter = 0; i < length; i++){
			if(!toReplace[i]){
				newArray[arrayCounter] = value[offset + i];
				arrayCounter++;
				continue;
			}
			
			for(int j = 0; j < newStr.length; j++){
				newArray[arrayCounter] = newStrValue[j];
				arrayCounter++;
			}
			i += oldStr.length - 1;
		}
		
		return new CString(0, newArray.length, newArray);
	}
	
	
	
}
