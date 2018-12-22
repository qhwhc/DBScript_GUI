package controller;

import javax.swing.UIManager;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;
import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.FrameBorderStyle;

import controller.components.MainFrame;

public class ToolUI {
	public static void main(String[] args) {
		try {
			/*�����������벻���*/
			System.setProperty("sun.java2d.noddraw", "true");
			/*ȥ��ԭ������ð�ť*/
			UIManager.put("RootPane.setupButtonVisible", false);
			/*��������ʽ*/
			BeautyEyeLNFHelper.frameBorderStyle = FrameBorderStyle.translucencySmallShadow;
			org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		new MainFrame().setVisible(true);
	}
}
