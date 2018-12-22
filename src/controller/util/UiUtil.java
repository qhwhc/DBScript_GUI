package controller.util;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class UiUtil {
	/*
	 * 设置图片
	 */
	public static void setFrameImage(JFrame jFrame,String imagePath) {
//		获取工具
		Toolkit toolKit = Toolkit.getDefaultToolkit();
//		获取图片路径
		Image image = toolKit.getImage(imagePath);
//		设置图片
		jFrame.setIconImage(image);
	}
	/*
	 *设置窗体居中
	 */
	public static void setFrameCenter(Container jFrame) {
		Toolkit toolKit = Toolkit.getDefaultToolkit();
//		获取屏幕宽高
		Dimension screenSize = toolKit.getScreenSize();
		double screenWidth = screenSize.getWidth();
		double screenHeight = screenSize.getHeight();
//		获取窗体宽高
		int frameWidth = jFrame.getWidth();
		int frameHeight = jFrame.getHeight();
//		屏幕-窗体(获得新宽高)
		int width = (int)(screenWidth - frameWidth)/2;
		int height = (int)(screenHeight - frameHeight)/2;
//		设置窗体位置
		jFrame.setLocation(width, height);
	}
	
}
