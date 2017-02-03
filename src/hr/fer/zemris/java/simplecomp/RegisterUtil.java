package hr.fer.zemris.java.simplecomp;

/**
 * Utility class which contains static methods for getting parts of a register
 * descriptor for addressing the memory locations stored in the register with
 * the given offset, if it exists.
 * 
 * @author labramusic
 *
 */
public class RegisterUtil {

	/**
	 * Gets the register index from the given register descriptor.
	 * 
	 * @param registerDescriptor
	 *            register descriptor
	 * @return register index from the given descriptor
	 */
	public static int getRegisterIndex(int registerDescriptor) {
		int bitMask = 0x000000FF;
		return registerDescriptor & bitMask;
	}

	/**
	 * Checks if register descriptor defines indirect addressing of register and
	 * returns true if so.
	 * 
	 * @param registerDescriptor
	 *            register descriptor
	 * @return true if descriptor defines indirect addressing, false otherwise
	 */
	public static boolean isIndirect(int registerDescriptor) {
		return (registerDescriptor >> 24) == 1;
	}

	/**
	 * Returns the memory address offset from the register descriptor.
	 * 
	 * @param registerDescriptor
	 *            register descriptor
	 * @return address offset
	 */
	public static int getRegisterOffset(int registerDescriptor) {
		int bitMask = 0x00FFFF00;
		registerDescriptor = registerDescriptor & bitMask;
		registerDescriptor = registerDescriptor >> 8;
		return (short)registerDescriptor;
	}

}
