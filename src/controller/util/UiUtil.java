package controller.util;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class UiUtil {
	/*
	 * ����ͼƬ
	 */
	public static void setFrameImage(JFrame jFrame,String imagePath) {
//		��ȡ����
		Toolkit toolKit = Toolkit.getDefaultToolkit();
//		��ȡͼƬ·��
		Image image = toolKit.getImage(imagePath);
//		����ͼƬ
		jFrame.setIconImage(image);
	}
	/*
	 *���ô������
	 */
	public static void setFrameCenter(Container jFrame) {
		Toolkit toolKit = Toolkit.getDefaultToolkit();
//		��ȡ��Ļ���
		Dimension screenSize = toolKit.getScreenSize();
		double screenWidth = screenSize.getWidth();
		double screenHeight = screenSize.getHeight();
//		��ȡ������
		int frameWidth = jFrame.getWidth();
		int frameHeight = jFrame.getHeight();
//		��Ļ-����(����¿��)
		int width = (int)(screenWidth - frameWidth)/2;
		int height = (int)(screenHeight - frameHeight)/2;
//		���ô���λ��
		jFrame.setLocation(width, height);
	}
	
}
