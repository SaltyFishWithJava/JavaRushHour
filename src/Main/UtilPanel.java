package Main;

import java.awt.*;
import javax.swing.*;

import Abstractions.User;

import java.awt.event.*;
import java.io.*;
import java.util.Scanner;
import java.util.regex.Pattern;

public class UtilPanel extends JPanel implements ActionListener{

	private static String info;
	private JPanel jpInfo;
	private JLabel jlInfo;
	private JPanel jpButtons;
	private JButton btnAbout;
	private JButton btnHelp;
	
	private static final String INITIAL_LABEL_TEXT = "00:00:00 000";   
	private String Currenttime;
    private CountingThread thread = new CountingThread();    
    private long programStart = System.currentTimeMillis();    
    private long pauseStart = programStart;    
    private long pauseCount = 0;    
	
	//Load users' information for the whole game.
	private static User currentuser;
	private User[] allusers = new User[1000];
	private int numberofusers;
	
	//构造函数
	public UtilPanel(){
		
		//Layouts
		setBackground(Color.gray);
		
		jpInfo = new JPanel();
		jlInfo = new JLabel(info);
		jpInfo.add(jlInfo);
		jpInfo.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		jpButtons = new JPanel();
		btnAbout = new JButton("About");
		btnAbout.setFont(new Font("Calibri", Font.PLAIN, 12));
		btnHelp = new JButton("Help");
		btnHelp.setFont(new Font("Calibri", Font.PLAIN, 12));
		jpButtons.add(btnAbout);
		jpButtons.add(btnHelp);
		jpButtons.setLayout(new FlowLayout(FlowLayout.RIGHT,5,0));
		
		btnAbout.addActionListener(this);
		btnHelp.addActionListener(this);
		
		add(jpInfo);
		add(jpButtons);
		setLayout(new GridLayout(1,2,0,0));
		
		//Users
		loadUsers();
		thread.start();
	}
	
	//对于计时器线程的控制
	public void timerstart(){
		if (thread.stopped) {
            pauseCount += (System.currentTimeMillis() - pauseStart);    
            thread.stopped = false;    
        } else {    
            pauseStart = System.currentTimeMillis();    
            thread.stopped = true;   
        }  
	}
	
	public void resettimer(){
		pauseStart = programStart;    
        pauseCount = 0;    
        thread.stopped = true;    
	}
	
	//对于用户的处理
	private void loadUsers(){
		File puzzlesDir = new File(".//");
		numberofusers = 0;
		for (File file : puzzlesDir.listFiles()) {
			String fileName = file.getName();
			System.out.println(fileName);
			if (Pattern.matches(".*(\\.users)$", fileName)){
				try {
					Scanner in = new Scanner(new File(fileName));
					String uname = in.next();
					String upassword = in.next();
					String uid = in.next();
					//System.out.println(uname+upassword+uid);
					allusers[numberofusers++] = new User(uname,upassword,uid);
					//System.out.println(allusers[0].getName()+allusers[0].getPassword()+allusers[0].getId());
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
				
		}
	}
	
	public boolean logincheck(String _name, String _password){
		boolean result = false;
		boolean hasuser = false;
		System.out.println(_name);
		System.out.println(_password);
		for(int i = 0 ; i < numberofusers; i++){
			if(allusers[i].getName().equals(_name)){
				if(allusers[i].getPassword().equals(_password)){
					hasuser = true;
					result = true;
					currentuser = new User(allusers[i].getName(),allusers[i].getPassword(),allusers[i].getId());
					break;
				}
				else{
					JOptionPane.showMessageDialog(null, "Invalid Password.");
					hasuser = true;
					break;
				}
			}
		}
		if(!hasuser) JOptionPane.showMessageDialog(null, "Invalid Username. Please register!.");
		return result;
	}
	
	public boolean registeruser(String _name, String _password){
		boolean result = false;
		String filename = "user" + numberofusers + ".users";
		if(_name.length()<3 || _password.length()<3) return false;
		for(int i = 0; i < numberofusers; i++){
			if(allusers[i].getName().equals(_name)){
				return false;
			}
		}
		//System.out.println(numberofusers + filename);
		File file = new File(filename);
		FileWriter fw = null;
		BufferedWriter bw = null;
		try {
			fw = new FileWriter(file);
			bw = new BufferedWriter(fw);
			bw.write(_name);
			bw.newLine();
			bw.write(_password);
			bw.newLine();
			bw.write(Integer.toString(numberofusers));
			bw.flush();
			result = true;
			allusers[numberofusers++] = new User(_name,_password,Integer.toString(numberofusers));
			fw.close();
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	public User getcurrentuser(){
		return currentuser;
	}
	
	//功能栏信息改变
	public void infoChange(String s){
		info = s;
		jlInfo.setText(s);
	}
	
	//按键监听
	public void actionPerformed(ActionEvent evt) {
		Object obj = evt.getActionCommand();
		if(obj == "About"){
			System.out.println("About Pressed");
			JOptionPane.showMessageDialog(null,"这是一个令人魂牵梦绕的游戏。", "About",  JOptionPane.PLAIN_MESSAGE);
			
		}
		else if(obj == "Help"){
			System.out.println("Help Pressed");
		}
		
	}
	
	//测试用
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
	
	//计时器线程
	private class CountingThread extends Thread {    
	     
        public boolean stopped = true;    
     
        private CountingThread() {    
            setDaemon(true);    
        }    
     
        @Override    
        public void run() {    
            while (true) {    
                if (!stopped) {    
                    long elapsed = System.currentTimeMillis() - programStart - pauseCount;
                    jlInfo.setText(format(elapsed));
                }    
     
                try {    
                    sleep(1);  // 1毫秒更新一次显示  
                } catch (InterruptedException e) {    
                    e.printStackTrace();    
                    System.exit(1);    
                }    
            }    
        }    
     
        // 将毫秒数格式化    
        private String format(long elapsed) {    
            int hour, minute, second, milli;    
     
            milli = (int) (elapsed % 1000);    
            elapsed = elapsed / 1000;    
     
            second = (int) (elapsed % 60);    
            elapsed = elapsed / 60;    
     
            minute = (int) (elapsed % 60);    
            elapsed = elapsed / 60;    
     
            hour = (int) (elapsed % 60);    
     
            //String.format("%02d:%02d:%02d %03d", hour, minute, second, milli);
            return Currenttime = String.format("%02d:%02d:%02d", hour, minute, second, milli);    
        }    
    }    

}
