package common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 热加载：改动配置文件不需要重启服务：线程定时调度去加载
 */
public class PropertiesUtil {
	static Properties p = null;

	// 构造函数私有化
	private PropertiesUtil() {

	}

	// 加载配置文件
	private static void load() {
		p = new Properties();
		// 创建配置对象
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
	// 线程安全的同步加载
	private static void load_() {
		if(p==null){
			synchronized (PropertiesUtil.class) {
				if (p == null) {
					load();	
				}
			}
		}
	}
    //根据key获取配置信息
	public static String getValueByKey(String key) {
		return p.getProperty(key);
	}
	//根据key获取配置，如果可以不存在 则返回使用默认值
	public static String getValueByKey(String key,String defaultValue) {
		return p.getProperty(key, defaultValue);
	}
	
	//根据key修改配置，如果可以不存在 则返回使用默认值
	//参数为要修改的文件路径  以及要修改的属性名和属性值  
    public static Boolean setValueByKey(String key,String value){
    	if(p==null){
    		load();
    	}
        p.setProperty(key, value);   
        // 文件输出流   
        try {  
            FileOutputStream fos = new FileOutputStream("config/jdbc.properties");   
            // 将Properties集合保存到流中   
            p.store(fos, "Copyright (c) Boxcode Studio");
            fos.flush();
            fos.close();// 关闭流   
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
            return false;  
        } catch (IOException e) {  
            e.printStackTrace();  
            return false;  
        }  
        return true;  
    } 
	//静态的内部类
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
		load_();//初始化操作
		new LoadThread().start();//启动线程定义加载
	}
	
}
