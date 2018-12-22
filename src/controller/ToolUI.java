package controller;

import javax.swing.UIManager;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;
import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.FrameBorderStyle;

import controller.components.MainFrame;

public class ToolUI {
	public static void main(String[] args) {
		try {
			/*设置中文输入不变白*/
			System.setProperty("sun.java2d.noddraw", "true");
			/*去除原面板设置按钮*/
			UIManager.put("RootPane.setupButtonVisible", false);
			/*设置面板格式*/
			BeautyEyeLNFHelper.frameBorderStyle = FrameBorderStyle.translucencySmallShadow;
			org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		new MainFrame().setVisible(true);
	}
}
