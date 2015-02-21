package m;

import org.apache.log4j.Logger;

import com.attilax.util.god;

@Deprecated
public abstract class tryX<T> {

	public T itemWrap(Object t) {
		try {
			return item(t);
		} catch (Exception e) {
			System.out.println("-----catch except la ..");
			log(e);
			return this.defaultReturnValue;

		}

	}

	public abstract T item(Object t) throws Exception;

	public T $(T string) {
		this.defaultReturnValue = string;

		return this.itemWrap("");
	}

	public Logger logger = Logger.getLogger(tryX.class.getName());

	public Object log(Exception e) {
		logger.error(god.getTrace(e));
		return e;
	}

	public T defaultReturnValue;

}
