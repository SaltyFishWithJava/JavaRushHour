package Main;

import java.awt.*;
import javax.swing.*;

import Main.IntroFrame.innerClassWindowListener;

import java.awt.event.*;

public class PlayModeFrame extends JFrame implements ActionListener{

	private UtilPanel utilp;
	private JButton btnsingleplay;
	private JButton btnmultiplay;
	private ImagePanel imagep;
	private int FRAME_WIDTH = 300;
	private int FRAME_HEIGHT = 400;
	private Component horizontalGlue;
	private Component horizontalGlue_1;
	private Component horizontalGlue_2;
	
	public PlayModeFrame(){
		imagep = new ImagePanel("Images\\intrologo.jpg");
		imagep.setPreferredSize(new Dimension(300,200));
		utilp = new UtilPanel();
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new innerClassWindowListener());
		setTitle("Welcome! " + utilp.getcurrentuser().getName());
		Container contentpane = getContentPane();
		contentpane.add(imagep,BorderLayout.NORTH);
		contentpane.add(utilp, BorderLayout.SOUTH);
		
		utilp.infoChange("Selecting Playmode.");
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int screenWidth = screenSize.width;
		int screenHeight = screenSize.height;
		setLocation((screenWidth - FRAME_WIDTH)/2, (screenHeight - FRAME_HEIGHT)/2);
		setSize(FRAME_WIDTH,FRAME_HEIGHT);
		setIconImage(Toolkit.getDefaultToolkit().getImage("Images\\roundlogo.png"));
		
	    Box hboxplaymode = Box.createHorizontalBox();
	    
	    horizontalGlue = Box.createHorizontalGlue();
	    hboxplaymode.add(horizontalGlue);
	    
	    btnsingleplay = new JButton("Single-Player");
	    btnsingleplay.setFont(new Font("Calibri", Font.PLAIN, 12));
	    hboxplaymode.add(btnsingleplay);
	    
	    horizontalGlue_2 = Box.createHorizontalGlue();
	    hboxplaymode.add(horizontalGlue_2);
	    btnmultiplay = new JButton("Multi-Player");
	    btnmultiplay.setFont(new Font("Calibri", Font.PLAIN, 12));
	    hboxplaymode.add(btnmultiplay);
	    
	    contentpane.add(hboxplaymode, BorderLayout.CENTER);
	    
	    horizontalGlue_1 = Box.createHorizontalGlue();
	    hboxplaymode.add(horizontalGlue_1);
	    
	    btnsingleplay.addActionListener(this);
	    btnmultiplay.addActionListener(this);
	    setVisible(true);
	    
	    
	    
	}
	
	class innerClassWindowListener extends WindowAdapter{
		public void windowClosing(WindowEvent e){
			int result = JOptionPane.showConfirmDialog(null, "You are going to exit Rush-Hour.", 
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
		PlayModeFrame test = new PlayModeFrame();
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object obj = e.getActionCommand();
		if(obj == "Single-Player"){
			System.out.println("Single-Player Pressed");
			//
			//
			//
			this.setVisible(false);
			//this.dispose();
			MainFrame mf = new MainFrame();
		}
		else if(obj == "Multi-Player"){
			System.out.println("Multi-Player Pressed");
		}
	}

}