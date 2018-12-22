package common.util;

import java.util.ArrayList;
import java.util.List;

public class Test {

	 public static void main(String[] args) {  
		    long time=System.currentTimeMillis();
	        // ����3���̵߳��̳߳�  
	        ThreadPool t = ThreadPool.getThreadPool(300);
	        List<Runnable> taskList=new ArrayList<Runnable>();
	        for (int i = 0; i < 10000; i++) {
	        	taskList.add(new Task());
			}
	        t.execute(taskList);  
	        System.out.println(t);  
	        t.destroy();// �����̶߳�ִ����ɲ�destory  
	        System.out.println(t);  
	        System.out.println(System.currentTimeMillis()-time);
	    }  
	  
	    // ������  
	    static class Task implements Runnable {  
	        private static volatile int i = 1;  
	  
	        @Override  
	        public void run() {// ִ������  
	        	try {
					Thread.sleep(40);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        	i++;
	            System.out.println("���� " + (i++) + " ���");  
	        }  
	    }
}
