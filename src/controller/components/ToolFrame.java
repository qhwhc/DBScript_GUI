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
	  * �˵�����
	 * @return 
	  */
	    public JMenuBar loadMenuBar() {
	    	JMenuBar  br=new  JMenuBar() ;  //�����˵�������
	    	JMenu menu = new JMenu("DB");
	    	JMenuItem jm1 = new JMenuItem("����ģ��") ;  //�˵���
	    	jm1.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					String str = JOptionPane.showInputDialog(getToolFrame(), "���������:");
					System.out.println(str);
				}
			});
	   	    JMenuItem jm2 = new JMenuItem("��������") ;//�˵���
	   	    menu.add(jm1);
	   	    menu.add(jm2);
	   	    br.add(menu) ;      //���˵����ӵ��˵�������
	    	return br;
	    }
}
