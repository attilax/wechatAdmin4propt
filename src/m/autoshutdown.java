package m;

import java.io.IOException;
import java.util.Date;
import java.util.HashSet;

import org.apache.log4j.Logger;

import com.attilax.util.fileC0;


import m.datepkg.dateUtil_o16;
import m.except.tryX;
import m.secury.callbackItfs;
import m.secury.securyWhile;

public class autoshutdown {
 

	public static Logger logger = Logger.getLogger(new autoshutdown()
			.getClass());

	/**
	 * 14:24  "F:\AutoHotKey\AutoHotkey.exe F:\shut\msgOnly.ahk"  f:\shut\msgrun.log
	 * @param args
	 */
	public static void main(String[] args) {
		final String shutdown_time = args[0].trim();// 14:14
		final String ShutLog =args[1];
		final String cmdShutdown = "rundll32 powrprof.dll,SetSuspendState";


 
		new HashSet() {
			public Object callMethod(Object obj) {
				// n++;
				
				if (dateUtil_o16.isNotGocyi(10,shutdown_time)) {
					FileService.FileAppend("shut now...��+ new Date()+��\r\n", ShutLog);
			     	shutdown();

				} else {
					FileService.FileAppend("chaosi ..." + new Date() + "\r\n", ShutLog);
				}

				return null;
			}

			private void shutdown() {

				try {

					cmdx.exec(cmdShutdown);
				} catch (IOException e) {

					e.printStackTrace();
				}

			}

		 

			 
		}.callMethod("");

		 
	}

	 
}
