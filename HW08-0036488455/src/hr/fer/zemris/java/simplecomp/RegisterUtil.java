package hr.fer.zemris.java.simplecomp;


/**
 * Utility class osed for getting important bits from registers used
 * by classes in simplecomp package.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */
public class RegisterUtil {

	/**
	 * Mask used for getting index bits from the register. 
	 */
	private static final int INDEX_MASK = 0xFF;
	
	/**
	 * Mask used for getting bit signifying indirection from the register.
	 */
	private static final int INDIRECT_MASK = 0x1000000;
	
	/**
	 * Mask used for getting offset bits from the register
	 */
	private static final int OFFSET_MASK = 0xFFFF00;
	
	/**
	 * Position of first offset bit.
	 */
	private static final int OFFSET_OFFSET = 8;
	
	
	/**
	 * Gets the index from the given integer register. Index is
	 * composed by the least significant 8 bits on positions
	 * 0 through 7.
	 * 
	 * @param registerDescriptor register with index
	 * @return returns the extracted index
	 */
	public static int getRegisterIndex(int registerDescriptor){
		int mask = INDEX_MASK;
		return registerDescriptor & mask;
	}
	
	
	/**
	 * Checks if the given register is indirect. Indirect bit is placed
	 * at bit number 25. If 1 it is indirect, if 0 it is not indirect.
	 * 
	 * @param registerDescriptor register to be checked
	 * @return returns true if indirect, false if direct
	 */
	public static boolean isIndirect(int registerDescriptor){
		int mask = INDIRECT_MASK;
		if((registerDescriptor & mask) == 0){
			return false;
		}
		
		return true;
	}
	
	
	/**
	 * Extracts the offset from the given register. Offset bits are
	 * on positions 8 through 15.
	 * 
	 * @param registerDescriptor register with offset
	 * @return returns integer offset of the register
	 */
	public static int getRegisterOffset(int registerDescriptor){
		int mask = OFFSET_MASK;
		short offset = (short)((registerDescriptor & mask) >> OFFSET_OFFSET);
		
		return (int)offset;
	}
	
	
}
