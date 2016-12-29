package Main;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class IntroFrame extends JFrame implements ActionListener{
	private int FRAME_WIDTH = 300;
	private int FRAME_HEIGHT = 400;
	
	private UtilPanel utilp;
	private ImagePanel imagep;
	private JLabel labelusername;
	private JTextField textFieldusername;
	private JLabel labelpsw;
	private JPasswordField textFieldpsw;
	private JButton btnlogin;
	private JButton btnregister;
	
	public IntroFrame() {
		utilp = new UtilPanel();
		utilp.infoChange("Initialized.");
		
		imagep = new ImagePanel("Images\\intrologo.jpg");
		imagep.setPreferredSize(new Dimension(300,200));
		Container contentpane = getContentPane();
		contentpane.add(utilp, BorderLayout.SOUTH);
		contentpane.add(imagep,BorderLayout.NORTH);
		setTitle("Rush-Hour Login");
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage("Images\\roundlogo.png"));
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new innerClassWindowListener());
		
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int screenWidth = screenSize.width;
		int screenHeight = screenSize.height;
		setLocation((screenWidth - FRAME_WIDTH)/2, (screenHeight - FRAME_HEIGHT)/2);
		setSize(FRAME_WIDTH,FRAME_HEIGHT);
		
		labelusername = new JLabel("Username:");
		labelusername.setFont(new Font("Calibri", Font.BOLD, 12));
	    textFieldusername = new JTextField(10);
	    textFieldusername.setMaximumSize(textFieldusername.getPreferredSize());

	    Box hboxusername = Box.createHorizontalBox();
	    hboxusername.add(labelusername);
	    // separate with a 10-pixel strut
	    hboxusername.add(Box.createHorizontalStrut(5));
	    hboxusername.add(textFieldusername);

	    // construct the middle horizontal box

	    labelpsw = new JLabel("Password: ");
	    labelpsw.setFont(new Font("Calibri", Font.BOLD, 12));
	    textFieldpsw = new JPasswordField(10);
	    textFieldpsw.setMaximumSize(textFieldpsw.getPreferredSize());


	    Box hboxpsw = Box.createHorizontalBox();
	    hboxpsw.add(labelpsw);
	    // separate with a 10-pixel strut
	    hboxpsw.add(Box.createHorizontalStrut(5));
	    hboxpsw.add(textFieldpsw);

	    // construct the bottom horizontal box

	    btnlogin = new JButton("Login");
	    btnlogin.setFont(new Font("Calibri", Font.BOLD, 12));
	    btnregister = new JButton("New user? Register!");
	    btnregister.setFont(new Font("Calibri", Font.PLAIN, 12));

	    btnlogin.addActionListener(this);
	    btnregister.addActionListener(this);
	    
	    Box hboxbuttons = Box.createHorizontalBox();
	    hboxbuttons.add(btnlogin);
	    // use "glue" to push the two buttons apart
	    hboxbuttons.add(Box.createHorizontalStrut(5));
	    hboxbuttons.add(btnregister);


	    // add the three horizontal boxes inside a vertical box

	    Box vboxlogin = Box.createVerticalBox();
	    vboxlogin.add(hboxusername);
	    vboxlogin.add(hboxpsw);
	    vboxlogin.add(hboxbuttons);
	    
	    contentpane.add(vboxlogin, BorderLayout.CENTER);

	    this.setVisible(true);
	}

	class innerClassWindowListener extends WindowAdapter{
		public void windowClosing(WindowEvent e){
			int result = JOptionPane.showConfirmDialog(e.getWindow(), "You are going to exit Rush-Hour.", 
					"Exit?", JOptionPane.YES_NO_OPTION);
			if(result == JOptionPane.YES_OPTION)
			{
				System.exit(0);
			}
			utilp.infoChange("Closing Cancelled.");
		}
	}
	
	//程序主入口
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		IntroFrame test = new IntroFrame();
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object obj = e.getActionCommand();
		if(obj == "Login"){
			System.out.println("Login Pressed");
			utilp.infoChange("Logging in.");
			if(utilp.logincheck(textFieldusername.getText(), textFieldpsw.getText())){
				this.setVisible(false);
				//this.dispose();
				PlayModeFrame pmf = new PlayModeFrame();
				utilp.infoChange("Login Succeeded!");
			}
			else utilp.infoChange("Login Failed.");
		}
		else if(obj == "New user? Register!"){
			System.out.println("Register Pressed");
			if(utilp.registeruser(textFieldusername.getText(), textFieldpsw.getText())){
				utilp.infoChange("Reigister Succeeded!");
			}
			else utilp.infoChange("Reigister Failed.");
		}
	}

}




