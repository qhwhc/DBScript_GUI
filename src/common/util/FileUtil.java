package common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;


public class FileUtil {
	public static String readFile(String filePath) {
		FileInputStream fileInputStream = null;
		FileChannel fileChannel = null;
		ByteBuffer byteBuffer = null;
		String encoding = null;
		try {
			File file = new File(filePath);
			if(!file.exists()){
				file.createNewFile();
			}
			fileInputStream = new FileInputStream(filePath);
			fileChannel = fileInputStream.getChannel();
			/*���û����С*/
			byteBuffer = ByteBuffer.allocate(1024000);
			fileChannel.read(byteBuffer);
			/*��ȡλ������*/
			byteBuffer.flip();
			/*��ȡλ������*/
			byteBuffer.rewind();
			/*��ȡϵͳ�ֽ���*/
			encoding = System.getProperty("file.encoding");
			/*�����ָ���ַ�*/
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(fileChannel != null) {
					fileChannel.close();
				}
				if(fileInputStream != null) {
					fileInputStream.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			
		}
		return Charset.forName(encoding).decode(byteBuffer).toString();
		
	}
	
	public static void wirte(String filePath,String str,boolean isAppend) {
		 FileOutputStream outputStream = null;
		 FileChannel channel = null;
		try {
			File file = new File(filePath);
			if(!file.exists()){
				file.createNewFile();
			}
	        outputStream = new FileOutputStream(file,isAppend);
	        channel = outputStream.getChannel();
	        ByteBuffer buffer = ByteBuffer.allocate(1024000);
	        buffer.put(str.getBytes());
	        buffer.flip();
	        channel.write(buffer);
	        channel.close();
	        outputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(channel != null) {
					channel.close();
				}
				if(outputStream != null) {
					outputStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		 
	}
	
}