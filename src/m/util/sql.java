/**
 * 
 */
package m.util;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author Administrator
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface sql {
	String value();

}
