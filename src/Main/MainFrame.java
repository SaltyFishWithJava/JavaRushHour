//Call main window, require panel to be put on the content pane of the MainFrame
package Main;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.Timer;
import java.util.TimerTask;  

public class MainFrame extends JFrame{
	
	boolean gamestartedflag = false;
	boolean gamepausedflag = false;
	private int FRAME_WIDTH = 950;
	private int FRAME_HEIGHT = 600;

	private UtilPanel utilp;
	private GamePanel carpark;
	private MainFrameFuncPanel mffp;
	//构造函数
	public MainFrame(){
		timer();
		utilp = new UtilPanel();
		carpark = new GamePanel();
		mffp = new MainFrameFuncPanel();
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
	
	//定时器函数，用来处理mffp与carpark与utilpane之间的联动，设定时间间隔为1s
	public void timer() {  
        Timer timer = new Timer();  
        timer.schedule(new TimerTask() {  
            public void run() {  
                if(mffp.gamestarted && !mffp.gamepaused){
                	System.out.println("Game Starts");
                	if(!gamestartedflag){
                		gamestartedflag = true;
                		gamepausedflag = false;
                		utilp.timerstart();
                	}
                }
                else if(mffp.gamestarted && mffp.gamepaused){
                	System.out.println("Game Paused");
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
                }
                
            }  
        }, 1000, 1000);  
    }
	
	//测试用
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MainFrame mf = new MainFrame();
	}
}

