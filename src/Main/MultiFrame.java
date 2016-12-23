package Main;

import java.awt.*;
import javax.swing.*;

import Main.MainFrame.innerClassWindowListener;

import java.awt.event.*;

public class MultiFrame extends JFrame{

	private int FRAME_WIDTH = 950;
	private int FRAME_HEIGHT = 600;

	private UtilPanel utilp;
	public static GamePanel carpark;
	private MultiFrameFuncPanel mffp;
	
	//构造函数
	public MultiFrame(){
		//timer();
		utilp = new UtilPanel();
		carpark = new GamePanel();
		mffp = new MultiFrameFuncPanel();
		Container contentPane = getContentPane();
		contentPane.add(utilp, BorderLayout.SOUTH);
		contentPane.add(carpark);
		mffp.setPreferredSize(new Dimension(300,680));
		contentPane.add(mffp,BorderLayout.EAST);
		setTitle("Rush Hour!");
		this.setResizable(false);
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();  
		int screenWidth = screenSize.width;  
		int screenHeight = screenSize.height;  
		
		setLocation((screenWidth - FRAME_WIDTH) / 2, (screenHeight - FRAME_HEIGHT) / 2); 
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
		//Rectangle r = this.getBounds();
		//System.out.println(r.getWidth() + " " + r.getHeight());
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new innerClassWindowListener());
		this.setBackground(Color.BLACK);
		setVisible(true);
	}
	
	//关闭窗口事件
	class innerClassWindowListener extends WindowAdapter{
		public void windowClosing(WindowEvent e){
			int result = JOptionPane.showConfirmDialog(e.getWindow(), "The game process will be lost.", 
					"Exit?", JOptionPane.YES_NO_OPTION);
			if(result == JOptionPane.YES_OPTION)
			{
				System.exit(0);
			}
			utilp.infoChange("Closing Cancelled.");
		}
	}
	
	//For test purposed. Class will be called by GAmeModeFrame.
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MultiFrame mf = new MultiFrame();
	}

}
