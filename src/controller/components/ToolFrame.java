package controller.components;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

public class ToolFrame extends JFrame{
	private static final long serialVersionUID = 1L;
	public static final int WIDTH =400;
	public static final int HEIGHT = 200;
	public ToolFrame(){
		setLocation(WindowXY.getXY(WIDTH, HEIGHT));
		setVisible(true);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
	
	public void launchView() {
		setSize(WIDTH, HEIGHT);
		add(loadMenuBar());
	}
	
	public ToolFrame getToolFrame() {
		return this;
	}
	 /**
	  * 菜单管理
	 * @return 
	  */
	    public JMenuBar loadMenuBar() {
	    	JMenuBar  br=new  JMenuBar() ;  //创建菜单工具栏
	    	JMenu menu = new JMenu("DB");
	    	JMenuItem jm1 = new JMenuItem("导出模版") ;  //菜单项
	    	jm1.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					String str = JOptionPane.showInputDialog(getToolFrame(), "请输入表名:");
					System.out.println(str);
				}
			});
	   	    JMenuItem jm2 = new JMenuItem("导入数据") ;//菜单项
	   	    menu.add(jm1);
	   	    menu.add(jm2);
	   	    br.add(menu) ;      //将菜单增加到菜单工具栏
	    	return br;
	    }
}
