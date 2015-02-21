package m.secury;


import java.util.Date;

import org.apache.log4j.Logger;

import com.attilax.util.god;
import com.attilax.util.timeTester;

 

 

public class securyWhile {

	public securyWhile() {

	}

	/**
	 * yaosi sleep_millSeconds too less etc 3 ,cpu 100%
	 * @param callbackImpt
	 * @param logger
	 * @param sleep_millSeconds
	 * @param timeout_sec
	 * @param timeout_num
	 */
	public securyWhile(callbackItfs callbackImpt, Logger logger,
			int sleep_millSeconds, int timeout_sec, int timeout_num) {
		if (sleep_millSeconds < 100)
			throw new RuntimeException(" sleep_millSeconds<100 ");
		int n = 0;
		timeTester t = new timeTester("securyWhile timeTester");
		while (true) {
			n++;
			if (n > timeout_num) {
				logger.info("--while break by out_num");
				break;

			}

			Date end = new Date();
			long pasttime = end.getTime() - t.start.getTime();
			if (pasttime > (timeout_sec * 1000)) {
				logger.info("--while break by time out");
				break;

			}

			try {
				callbackImpt.callMethod(null);

			} catch (Exception e) {
				logger.error(com.attilax.util.god.getTrace(e));
			}

			try {
				Thread.sleep(sleep_millSeconds);
			} catch (InterruptedException e) {
				logger.error(god.getTrace(e));
			}
		}

	}

}
