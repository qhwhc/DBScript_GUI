package common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * �ȼ��أ��Ķ������ļ�����Ҫ���������̶߳�ʱ����ȥ����
 */
public class PropertiesUtil {
	static Properties p = null;

	// ���캯��˽�л�
	private PropertiesUtil() {

	}

	// ���������ļ�
	private static void load() {
		p = new Properties();
		// �������ö���
		InputStream is = null;
		try {
			File file = new File("config/jdbc.properties");
			is = new FileInputStream(file);	
//			is = PropertiesUtil.class.getClassLoader().getResourceAsStream(
//					"jdbc.properties");
			p.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					throw new RuntimeException(e.getMessage());
				}
			}
		}
	}
	// �̰߳�ȫ��ͬ������
	private static void load_() {
		if(p==null){
			synchronized (PropertiesUtil.class) {
				if (p == null) {
					load();	
				}
			}
		}
	}
    //����key��ȡ������Ϣ
	public static String getValueByKey(String key) {
		return p.getProperty(key);
	}
	//����key��ȡ���ã�������Բ����� �򷵻�ʹ��Ĭ��ֵ
	public static String getValueByKey(String key,String defaultValue) {
		return p.getProperty(key, defaultValue);
	}
	
	//����key�޸����ã�������Բ����� �򷵻�ʹ��Ĭ��ֵ
	//����ΪҪ�޸ĵ��ļ�·��  �Լ�Ҫ�޸ĵ�������������ֵ  
    public static Boolean setValueByKey(String key,String value){
    	if(p==null){
    		load();
    	}
        p.setProperty(key, value);   
        // �ļ������   
        try {  
            FileOutputStream fos = new FileOutputStream("config/jdbc.properties");   
            // ��Properties���ϱ��浽����   
            p.store(fos, "Copyright (c) Boxcode Studio");
            fos.flush();
            fos.close();// �ر���   
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
            return false;  
        } catch (IOException e) {  
            e.printStackTrace();  
            return false;  
        }  
        return true;  
    } 
	//��̬���ڲ���
	private static class LoadThread extends Thread{
		@Override
		public void run() {
			while(true){
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				PropertiesUtil.load();
			}
		}
	}
	static{
		load_();//��ʼ������
		new LoadThread().start();//�����̶߳������
	}
	
}
