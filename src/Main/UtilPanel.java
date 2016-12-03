package Main;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class UtilPanel extends JPanel{

	private JPanel jpInfo;
	private JLabel jlInfo;
	private JPanel jpButtons;
	private JButton jbAbout;
	private JButton jbHelp;
	
	public UtilPanel(){
		setBackground(Color.gray);
		
		jpInfo = new JPanel();
		jlInfo = new JLabel("Initialized.");
		jpInfo.add(jlInfo);
		jpInfo.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		jpButtons = new JPanel();
		jbAbout = new JButton("About");
		jbHelp = new JButton("Help");
		jpButtons.add(jbAbout);
		jpButtons.add(jbHelp);
		jpButtons.setLayout(new FlowLayout(FlowLayout.RIGHT,5,0));
		
		add(jpInfo);
		add(jpButtons);
		setLayout(new GridLayout(1,2,0,0));
	}
	
	public void infoChange(String s){
		jlInfo.setText(s);
	}
	
	public void actionPerformed(ActionEvent evt) {
		
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//Testing
		JFrame jf = new JFrame("Test");
		UtilPanel up = new UtilPanel();
		Container contentpane = jf.getContentPane();
		contentpane.add(up,BorderLayout.SOUTH);
		jf.setSize(500, 500);
		jf.setVisible(true);
	}

}
