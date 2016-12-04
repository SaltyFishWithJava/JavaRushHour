//Call main window, require panel to be put on the content pane of the MainFrame
package Main;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class MainFrame extends JFrame{
	
	private int FRAME_WIDTH = 1000;
	private int FRAME_HEIGHT = 700;

	public UtilPanel utilp;
	public FunctionPanel funcp;
	
	public MainFrame(){
		utilp = new UtilPanel();
		funcp = new FunctionPanel();
		Container contentPane = getContentPane();
		contentPane.add(utilp, BorderLayout.SOUTH);
		contentPane.add(funcp, BorderLayout.EAST);
		setTitle("Rush Hour!");
		
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

	
	class innerClassWindowListener extends WindowAdapter{
		public void windowClosing(WindowEvent e){
			int result = JOptionPane.showConfirmDialog(null, "The unsaved content will be lost.", 
					"Exit?", JOptionPane.YES_NO_OPTION);
			if(result == JOptionPane.YES_OPTION)
			{
				System.exit(0);
			}
			utilp.infoChange("Closing Cancelled.");
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//Start point of the program.
		MainFrame mf = new MainFrame();
	}
}

