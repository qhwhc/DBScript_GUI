package common.util;

import java.util.ArrayList;
import java.util.List;

public class Test {

	 public static void main(String[] args) {  
		    long time=System.currentTimeMillis();
	        // 创建3个线程的线程池  
	        ThreadPool t = ThreadPool.getThreadPool(300);
	        List<Runnable> taskList=new ArrayList<Runnable>();
	        for (int i = 0; i < 10000; i++) {
	        	taskList.add(new Task());
			}
	        t.execute(taskList);  
	        System.out.println(t);  
	        t.destroy();// 所有线程都执行完成才destory  
	        System.out.println(t);  
	        System.out.println(System.currentTimeMillis()-time);
	    }  
	  
	    // 任务类  
	    static class Task implements Runnable {  
	        private static volatile int i = 1;  
	  
	        @Override  
	        public void run() {// 执行任务  
	        	try {
					Thread.sleep(40);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        	i++;
	            System.out.println("任务 " + (i++) + " 完成");  
	        }  
	    }
}
