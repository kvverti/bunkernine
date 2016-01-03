package kvverti.bnine.util;

import java.lang.annotation.*;

/**
 * Marks a class as having a String ID. This is used for classes as {@link StringID}
 * is used for objects. TileEntities and Entities must use this annotation to specify
 * their internal ID. During runtime, the ID can be retrieved using reflection:
 * <p>
 * {@code String id = cls.getAnnotation(ClassID.class).value();}
 *
 * @see StringID
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ClassID {

	/**
	 * Returns the unique String ID of this class. The returned String should
	 * be unique.
	 *
	 * @return The String ID of this class.
	 */
	String value();
}