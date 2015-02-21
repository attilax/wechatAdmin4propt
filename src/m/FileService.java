package m;


import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.attilax.util.AppendFile;


public class FileService {
	public static void main(String[] args)
	{
		//FileService.writeFile("c:\\test.txt", "data");
		FileService.FileAppend( "datax\r\n","c:\\test.txt");
		System.out.println("f");
	}
public static ByteArrayOutputStream readFile(String filename) {
		try {
			FileInputStream fileInStream = new FileInputStream(filename);
			ByteArrayOutputStream fileByteStream = new ByteArrayOutputStream();
			int i = 0;
			while ((i = fileInStream.read()) != -1) {
				fileByteStream.write(i);
			}
			fileInStream.close();
			return fileByteStream;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void writeFile(String fileName, byte[] data) {
		try {
			FileOutputStream fos = new FileOutputStream(fileName);
			fos.write(data);
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	} 
	
	public static void writeFile(String fileName, String str) {
		 byte[] data=str.getBytes();
		try {
			FileOutputStream fos = new FileOutputStream(fileName);
			fos.write(data);
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	} 
	//transfor InputStream to byte
	public static byte[] getBytes(InputStream is) throws Exception {
		byte[] data = null;

		Collection chunks = new ArrayList();
		byte[] buffer = new byte[1024 * 1000];
		int read = -1;
		int size = 0;

		while ((read = is.read(buffer)) != -1) {
			if (read > 0) {
				byte[] chunk = new byte[read];
				System.arraycopy(buffer, 0, chunk, 0, read);
				chunks.add(chunk);
				size += chunk.length;
			}
		}

		if (size > 0) {
			ByteArrayOutputStream bos = null;
			try {
				bos = new ByteArrayOutputStream(size);
				for (Iterator itr = chunks.iterator(); itr.hasNext();) {
					byte[] chunk = (byte[]) itr.next();
					bos.write(chunk);
				}
				data = bos.toByteArray();
			} finally {
				if (bos != null) {
					bos.close();
				}
			}
		}
		return data;
	}
	public static void FileAppend(String string, String file) {
		AppendFile.method1(file, string);
		
	} 
  
}