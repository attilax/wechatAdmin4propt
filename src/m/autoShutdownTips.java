package m;

import java.io.IOException;
import java.util.Date;

import org.apache.log4j.Logger;

 
import m.datepkg.dateUtil_o16;
import m.except.tryX;
import m.secury.callbackItfs;
import m.secury.securyWhile;

@SuppressWarnings("all")
public class autoShutdownTips {
 

	public static Logger logger = Logger.getLogger(new autoShutdownTips()
			.getClass());

	/**
	 * 14:24  "F:\AutoHotKey\AutoHotkey.exe F:\shut\msgOnly.ahk"  f:\shut\msgrun.log
	 * @param args
	 */
	public static void main(String[] args) {

		final String shutdown_time = args[0].trim();// 14:14
		final String msgrun_log = args[2].trim(); // f:\shut\msgrun.log
		final String msgrun = args[1].trim(); // "F:\AutoHotKey\AutoHotkey.exe F:\shut\msgOnly.ahk"

		new securyWhile(new callbackItfs() {
			int n = 0;
			boolean fisrt = true;

			@Override
			public Object callMethod(Object obj) {
				n++;

				if (dateUtil_o16.isBeforeMin(5,shutdown_time))
				{
					new tryX<String>() {

						@Override
						public String item(Object t) throws Exception {
							if (fisrt)
								showMsg();
							if (dateUtil_o16.isJendye()) {
								showMsg();
							}
							fisrt = false;
							return null;
						}
					}.$("");

				} else {
					FileService.FileAppend("�ظ��Ĵ�����" + n + "   time:  " + new Date() + "\r\n",
							msgrun_log);
				}

				return null;
			}

		 

			private void showMsg() {
				try {
					cmdx.exec(msgrun);
				} catch (IOException e) {

					e.printStackTrace();
				}
				System.out.println("showMsg  only 5 min");

			}
		}, logger, 5000, 3500 * 24 * 365, 720 * 24 * 365);

	}

}
