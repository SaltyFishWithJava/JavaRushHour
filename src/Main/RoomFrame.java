package Main;

import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.util.Scanner;

import javax.swing.*;
import Abstractions.*;

public class RoomFrame extends JFrame implements ActionListener{
	private int FRAME_WIDTH = 400;
	private int FRAME_HEIGHT = 200;
	private JRadioButton rdbtnserver;
	private JRadioButton rdbtnclient;
	private JButton btnPlay;
	private JTextField textFieldport;
	private JTextField textFieldip;
	public static Client client = null;
	public static Server server = null;
	public static boolean isserver;
	public static boolean isclient;
	public RoomFrame (){
		Container contentPane = getContentPane();
		this.setResizable(false);
		Box verticalBox = Box.createVerticalBox();
		getContentPane().add(verticalBox, BorderLayout.CENTER);

		Component verticalStrut = Box.createVerticalStrut(10);
		verticalBox.add(verticalStrut);

		Box horizontalBox = Box.createHorizontalBox();
		verticalBox.add(horizontalBox);

		rdbtnserver = new JRadioButton("Be Room Owner");
		rdbtnserver.setSelected(true);
		rdbtnserver.setFont(new Font("Calibri", Font.PLAIN, 12));
		horizontalBox.add(rdbtnserver);

		Component horizontalStrut = Box.createHorizontalStrut(40);
		horizontalBox.add(horizontalStrut);

		rdbtnclient = new JRadioButton("Join Room");
		rdbtnclient.setFont(new Font("Calibri", Font.PLAIN, 12));
		horizontalBox.add(rdbtnclient);

		Component verticalStrut_3 = Box.createVerticalStrut(20);
		verticalBox.add(verticalStrut_3);

		Box horizontalBox_1 = Box.createHorizontalBox();
		verticalBox.add(horizontalBox_1);

		Component horizontalStrut_1 = Box.createHorizontalStrut(30);
		horizontalBox_1.add(horizontalStrut_1);

		JLabel lblRoomno = new JLabel("Room NO. : ");
		lblRoomno.setFont(new Font("Calibri", Font.PLAIN, 12));
		horizontalBox_1.add(lblRoomno);

		textFieldport = new JTextField(10);
		textFieldport.setText("6666");
		textFieldport.setFont(new Font("Calibri", Font.BOLD, 12));
		textFieldport.setToolTipText("Enter a four digit number as your room number.");
		horizontalBox_1.add(textFieldport);
		textFieldport.setColumns(10);

		Component horizontalStrut_4 = Box.createHorizontalStrut(30);
		horizontalBox_1.add(horizontalStrut_4);

		Component verticalStrut_1 = Box.createVerticalStrut(10);
		verticalBox.add(verticalStrut_1);

		Box horizontalBox_2 = Box.createHorizontalBox();
		verticalBox.add(horizontalBox_2);

		Component horizontalStrut_2 = Box.createHorizontalStrut(30);
		horizontalBox_2.add(horizontalStrut_2);

		JLabel lblIP = new JLabel("IP Address: ");
		lblIP.setFont(new Font("Calibri", Font.PLAIN, 12));
		horizontalBox_2.add(lblIP);

		textFieldip = new JTextField();
		textFieldip.setFont(new Font("Calibri", Font.BOLD, 12));
		textFieldip.setEditable(false);
		try {
			textFieldip.setText(InetAddress.getLocalHost().getHostAddress());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		textFieldip.setToolTipText("Tell your friends your IP.");
		horizontalBox_2.add(textFieldip);
		textFieldip.setColumns(10);

		Component horizontalStrut_3 = Box.createHorizontalStrut(30);
		horizontalBox_2.add(horizontalStrut_3);

		Component verticalStrut_2 = Box.createVerticalStrut(15);
		verticalBox.add(verticalStrut_2);

		Box horizontalBox_3 = Box.createHorizontalBox();
		verticalBox.add(horizontalBox_3);

		btnPlay = new JButton("Play!");
		btnPlay.setFont(new Font("Calibri", Font.BOLD, 12));
		horizontalBox_3.add(btnPlay);

		Component verticalStrut_4 = Box.createVerticalStrut(15);
		verticalBox.add(verticalStrut_4);
		this.setTitle("Room Options.");
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();  
		int screenWidth = screenSize.width;  
		int screenHeight = screenSize.height;  
		setLocation((screenWidth - FRAME_WIDTH) / 2, (screenHeight - FRAME_HEIGHT) / 2); 
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setVisible(true);
		
		//Actions
		btnPlay.addActionListener(this);
		rdbtnserver.addActionListener(this);
		rdbtnclient.addActionListener(this);
		addWindowListener(new innerClassWindowListener());
		ButtonGroup rbroom= new ButtonGroup();
		rbroom.add(rdbtnserver);
		rbroom.add(rdbtnclient);
	}
	
	//关闭窗口事件
	class innerClassWindowListener extends WindowAdapter{
		public void windowClosing(WindowEvent e){
			//e.getWindow().setAlwaysOnTop(false);
			int result = JOptionPane.showConfirmDialog(e.getWindow(), "Do you want to exit game?", 
					"Exit?", JOptionPane.YES_NO_OPTION);
			if(result == JOptionPane.YES_OPTION)
			{
				System.exit(0);
			}
		}
	}	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new RoomFrame();
	}
	
	public static void sendMessage(){
		Scanner in = new Scanner(System.in);
		String str = in.nextLine();
		System.out.println("In RoomFrame Please send messages");
		if(isclient) client.send(str);
		else server.send(str);
	}
	
	public static void closing(){
		if(isclient) client.closeConnection();
		else Server.closeServer();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object obj = e.getActionCommand();
		if(obj == "Play!"){
			System.out.println("Play pressed!");
			this.setAlwaysOnTop(false);
			if(rdbtnserver.isSelected()){
				server = new Server(Integer.parseInt(textFieldport.getText()));
				if(Server.isStart){
					isserver = true;
					isclient = false;
					this.setVisible(false);
					UtilPanel.infoChange("Wait for an opponent to enter your room.");
				}
				else{
					this.setAlwaysOnTop(true);
				}
			}
			else{
				client = new Client(Integer.parseInt(textFieldport.getText()),textFieldip.getText(),UtilPanel.getcurrentuser().getName());
				if(Client.isConnected){
					isclient = true;
					isserver = false;
					this.setVisible(false);
					MultiFrameFuncPanel.btnCreateMap.setEnabled(false);
					MultiFrameFuncPanel.btnLoadMap.setEnabled(false);
					MultiFrameFuncPanel.comboBoxMaps.setEnabled(false);
					MultiFrameFuncPanel.btnStart.setEnabled(false);
					MultiFrameFuncPanel.btnPause.setEnabled(false);
					UtilPanel.infoChange("Waiting for your friend to choose a map and start the game.");
				}
				else{
					this.setAlwaysOnTop(true);
				}
			}
			
				
		}
		else if(obj == "Be Room Owner"){
			System.out.println("Be room owner selected.");
			textFieldip.setEditable(false);
			textFieldip.setToolTipText("Tell your friends your IP.");
			try {
				textFieldip.setText(InetAddress.getLocalHost().getHostAddress());
			} catch (UnknownHostException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		else if(obj == "Join Room"){
			System.out.println("Join selected.");
			textFieldip.setEditable(true);
			textFieldip.setText(null);
			textFieldip.setToolTipText("Your friend's IP address.");
		}
	}

}
