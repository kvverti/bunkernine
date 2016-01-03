package kvverti.bnine.util;

/**
 * Provides a common access point for an object's unique String ID.
 * Instances of classes that implement this interface have a (preferably) unique
 * String ID that can be used to refer to the instance. Blocks, Items, and other
 * objects with a String ID must implement this interface.
 * @see ClassID
 */
public interface StringID {

	/**
	 * Returns the unique String ID of this object. The returned String is
	 * unique within its class and should remains consistent throughout the
	 * lifetime of the instance.
	 *
	 * @return The unique String ID of this object.
	 */
	String id();
}