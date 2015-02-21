package m;

import java.io.IOException;

public class cmdx {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		exec("");
//		while(true)
//		{
//			try {
//				Thread.sleep(5000);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}

	}

	public static void exec(String cmd) throws IOException {
		Runtime rt = Runtime.getRuntime();

		// Process p = rt.exec(String[] cmdarray); 或者
		//f:\\time.bat
		Process p = rt.exec(cmd);
	 

		// cmd命令格式为 "cmd.exe /c ipconfig /all"
		//
		// 对像p为进程,在给p赋值以前,必须保证p为空
//
//		if (p != null) {
//
//			p.destroy();
//
//			p = null;
//
//		}
	}

}
