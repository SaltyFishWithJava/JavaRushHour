package Main;

import java.awt.*;
import javax.swing.*;

import Abstractions.Client;
import Abstractions.Server;
import Abstractions.User;
import Main.MainFrame.innerClassWindowListener;

import java.awt.event.*;
import java.util.Stack;
import java.util.Timer;
import java.util.TimerTask;

public class MultiFrame extends JFrame{
	private MusicPanel mms = new MusicPanel();
	boolean gamestartedflag = false;
	boolean gamepausedflag = false;
	private int FRAME_WIDTH = 950;
	private int FRAME_HEIGHT = 600;
	public static boolean flag = true;
	private UtilPanel utilp;
	public static GamePanel carpark;
	private MultiFrameFuncPanel mffp;
	public static User currentuser;
	public static Stack<Action> prevstep;
	
	//构造函数
	public MultiFrame(){
		prevstep = new Stack<Action>();
		timer();
		this.add(mms);
		utilp = new UtilPanel();
		carpark = new GamePanel();
		mffp = new MultiFrameFuncPanel(this);
		Container contentPane = getContentPane();
		contentPane.add(utilp, BorderLayout.SOUTH);
		contentPane.add(carpark);
		mffp.setPreferredSize(new Dimension(300,680));
		contentPane.add(mffp,BorderLayout.EAST);
		setTitle("Rush Hour!" + "---Multi Play Mode---" + "Player:" + UtilPanel.getcurrentuser().getName());
		this.setResizable(false);
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();  
		int screenWidth = screenSize.width;  
		int screenHeight = screenSize.height;  
		
		setLocation((screenWidth - FRAME_WIDTH) / 2, (screenHeight - FRAME_HEIGHT) / 2); 
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new innerClassWindowListener());

		setVisible(true);
	}
	
	//关闭窗口事件
	class innerClassWindowListener extends WindowAdapter{
		public void windowClosing(WindowEvent e){
			int result = JOptionPane.showConfirmDialog(e.getWindow(), "The game process will be lost.", 
					"Exit?", JOptionPane.YES_NO_OPTION);
			if(result == JOptionPane.YES_OPTION)
			{
				mffp.closing();
				System.exit(0);
			}
			utilp.infoChange("Closing Cancelled.");
		}
	}
	
	public void timer() {  
        Timer timer = new Timer();  
        timer.schedule(new TimerTask() {  
            public void run() { 
            	
                if(mffp.gamestarted && !mffp.gamepaused){
                	//System.out.println("Game Starts");
                	if(!gamestartedflag){
                		gamestartedflag = true;
                		gamepausedflag = false;
                		utilp.timerstart();
                	}
                }
                else if(mffp.gamestarted && mffp.gamepaused){
                	//System.out.println("Game Paused");
                	if(!gamepausedflag){
                		utilp.timerstart();
                		gamepausedflag = true;
                		gamestartedflag = false;
                	}
                }
                if(carpark.IsWin()){
                	utilp.resettimer();
                	gamepausedflag = false;
                	gamestartedflag = false;
                	mffp.gamestarted = false;
                	mffp.gamepaused = false;
                	mffp.btnStart.setEnabled(true);
                	mffp.btnPause.setEnabled(false);
                	if(RoomFrame.isclient && flag) {
                		flag = false;
                		Client.send("FINISHED");
                	}
                	if(RoomFrame.isserver && flag) {
                		Server.send("FINISHED");
                		flag = false;
                	}
                }
                
            }  
        }, 1000, 1000);  
    }
	
	//For test purposed. Class will be called by GAmeModeFrame.
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MultiFrame mf = new MultiFrame();
	}

}