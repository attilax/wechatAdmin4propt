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

		// Process p = rt.exec(String[] cmdarray); ����
		//f:\\time.bat
		Process p = rt.exec(cmd);
	 

		// cmd�����ʽΪ "cmd.exe /c ipconfig /all"
		//
		// ����pΪ����,�ڸ�p��ֵ��ǰ,���뱣֤pΪ��
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
