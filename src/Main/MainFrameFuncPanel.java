package Main;

import Abstractions.*;
import Abstractions.Action;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.URI;
import java.net.URL;
import java.util.Scanner;
import java.util.Stack;
import java.util.regex.Pattern;

import javax.swing.*;

public class MainFrameFuncPanel extends JPanel implements ActionListener{
	private boolean isstoped;
	public boolean gamestarted;
	public boolean gamepaused;
	public JButton btnStart;
	public JButton btnPause;
	public MapFrame mf;
	private Solver s;
	public static Stack<Action> prevstep;
	public static Stack<Action> nextstep;
	private static int currentstep;
	public boolean ischange=false;

	private JLabel labelmaps;
	private JLabel labelcontrols;
	private JLabel labelleaders;
	public JButton btnCreateMap;
	public JButton btnLoadMap;
	public static JComboBox<MapFile> comboBoxMaps;
	public static JButton btnPrevStep;
	public JButton btnSolve;
	public JButton btnAutomove;
	private Box horizontalBox;
	private Box verticalBox;

	private Component verticalStrut;
	private Box horizontalBox_1;
	private Component verticalStrut_1;
	private Box horizontalBox_2;
	private Box horizontalBox_3;
	private Component verticalStrut_2;
	private Box horizontalBox_4;
	private Component horizontalGlue;
	private Component horizontalGlue_1;
	private Component horizontalGlue_2;
	private Component verticalStrut_3;
	private Box horizontalBox_5;
	public  JRadioButton rdbtnA;
	public  JRadioButton rdbtnBFS;
	public  JRadioButton rdbtnDFS;
	private Component verticalStrut_4;
	private Component verticalStrut_5;
	private Component horizontalGlue_4;
	private Component horizontalGlue_5;
	private Component horizontalGlue_6;
	private JLabel labelSteps;
	private JProgressBar progressBar;
	private JSeparator separator_1;
	private Component verticalStrut_7;
	private Component verticalStrut_8;
	private JLabel lblSelectAlgorithm;
	private Component verticalStrut_6;
	private JSeparator separator;
	private Box horizontalBox_6;
	private Box horizontalBox_7;
	private Component horizontalGlue_8;
	private Component verticalStrut_11;
	private Box horizontalBox_8;
	private Component horizontalGlue_11;
	private Component verticalStrut_12;
	private JSeparator separator_3;
	private Component verticalStrut_13;
	private Component verticalStrut_14;
	public  static JButton btnNextStep;
	private Component horizontalGlue_10;
	private static JTextField textFieldsteps;
	private Component horizontalStrut;
	private Component horizontalStrut_1;

	private String[] marks=new String[15];
	private JButton btnStop;
	private Component horizontalGlue_7;
	private Box horizontalBox_9;
	private JLabel lblNewLabel;

	// Music{
	//	     bgMusic;
	//  }


	public MainFrameFuncPanel(){
		gamestarted = false;
		gamepaused = true;
		prevstep = new Stack<Action>();
		nextstep = new Stack<Action>();
		currentstep = 0;

		//Layouts and Listeners
		verticalBox = Box.createVerticalBox();
		add(verticalBox);

		verticalStrut_14 = Box.createVerticalStrut(10);
		verticalBox.add(verticalStrut_14);

		horizontalBox_8 = Box.createHorizontalBox();
		verticalBox.add(horizontalBox_8);

		btnStart = new JButton("Start Game");
		btnStart.setToolTipText("Timer will be started.");
		btnStart.setFont(new Font("Calibri", Font.PLAIN, 12));
		horizontalBox_8.add(btnStart);

		horizontalGlue_11 = Box.createHorizontalGlue();
		horizontalBox_8.add(horizontalGlue_11);

		btnPause = new JButton("Pause Game");
		btnPause.setEnabled(false);
		btnPause.setFont(new Font("Calibri", Font.PLAIN, 12));
		btnPause.setToolTipText("Timer will be paused.");
		horizontalBox_8.add(btnPause);

		horizontalGlue_7 = Box.createHorizontalGlue();
		horizontalBox_8.add(horizontalGlue_7);

		btnStop = new JButton("Stop!");
		btnStop.setEnabled(false);
		btnStop.setFont(new Font("Calibri", Font.PLAIN, 12));
		horizontalBox_8.add(btnStop);

		verticalStrut_12 = Box.createVerticalStrut(10);
		verticalBox.add(verticalStrut_12);

		separator_3 = new JSeparator();
		verticalBox.add(separator_3);

		verticalStrut_13 = Box.createVerticalStrut(5);
		verticalBox.add(verticalStrut_13);

		horizontalBox_3 = Box.createHorizontalBox();
		verticalBox.add(horizontalBox_3);
		labelmaps = new JLabel("Maps:");
		horizontalBox_3.add(labelmaps);
		labelmaps.setFont(new Font("Calibri", Font.BOLD, 16));

		horizontalGlue_1 = Box.createHorizontalGlue();
		horizontalBox_3.add(horizontalGlue_1);

		verticalStrut_2 = Box.createVerticalStrut(5);
		verticalBox.add(verticalStrut_2);

		horizontalBox = Box.createHorizontalBox();
		verticalBox.add(horizontalBox);

		btnCreateMap = new JButton("Create Map");
		btnCreateMap.setFont(new Font("Calibri", Font.PLAIN, 12));
		btnCreateMap.setToolTipText("Start MapCreator");
		horizontalBox.add(btnCreateMap);

		horizontalGlue_2 = Box.createHorizontalGlue();
		horizontalBox.add(horizontalGlue_2);
		btnLoadMap = new JButton("Load Maps From Files");
		btnLoadMap.setFont(new Font("Calibri", Font.PLAIN, 12));
		btnLoadMap.setToolTipText("Load maps from file.");
		horizontalBox.add(btnLoadMap);

		verticalStrut = Box.createVerticalStrut(10);
		verticalBox.add(verticalStrut);
		
		horizontalBox_9 = Box.createHorizontalBox();
		verticalBox.add(horizontalBox_9);
		
		lblNewLabel = new JLabel("Map Selector:   ");
		lblNewLabel.setFont(new Font("Calibri", Font.PLAIN, 12));
		horizontalBox_9.add(lblNewLabel);

		comboBoxMaps = new JComboBox();
		horizontalBox_9.add(comboBoxMaps);
		comboBoxMaps.setToolTipText("Files with *puzzles will be shown here.");


		verticalStrut_1 = Box.createVerticalStrut(15);
		verticalBox.add(verticalStrut_1);

		separator_1 = new JSeparator();
		verticalBox.add(separator_1);

		verticalStrut_7 = Box.createVerticalStrut(5);
		verticalBox.add(verticalStrut_7);

		horizontalBox_4 = Box.createHorizontalBox();
		verticalBox.add(horizontalBox_4);
		labelcontrols = new JLabel("Controls:");
		horizontalBox_4.add(labelcontrols);
		labelcontrols.setFont(new Font("Calibri", Font.BOLD, 16));

		horizontalGlue = Box.createHorizontalGlue();
		horizontalBox_4.add(horizontalGlue);

		verticalStrut_3 = Box.createVerticalStrut(5);
		verticalBox.add(verticalStrut_3);

		horizontalBox_1 = Box.createHorizontalBox();
		verticalBox.add(horizontalBox_1);

		btnNextStep = new JButton("Next Step");
		btnNextStep.setEnabled(false);
		btnNextStep.setFont(new Font("Calibri", Font.PLAIN, 12));
		btnNextStep.setToolTipText("See how you solve this map.");
		horizontalBox_1.add(btnNextStep);

		horizontalStrut_1 = Box.createHorizontalStrut(20);
		horizontalBox_1.add(horizontalStrut_1);
		btnPrevStep = new JButton("Prev Step");
		btnPrevStep.setEnabled(false);
		btnPrevStep.setFont(new Font("Calibri", Font.PLAIN, 12));
		btnPrevStep.setToolTipText("Go back to the previous step.");
		horizontalBox_1.add(btnPrevStep);

		horizontalStrut = Box.createHorizontalStrut(30);
		horizontalBox_1.add(horizontalStrut);

		textFieldsteps = new JTextField(2);
		textFieldsteps.setFont(new Font("Calibri", Font.BOLD, 13));
		textFieldsteps.setEditable(false);
		horizontalBox_1.add(textFieldsteps);

		labelSteps = new JLabel("Steps");
		labelSteps.setFont(new Font("Calibri", Font.PLAIN, 12));
		labelSteps.setToolTipText("Current step count.");
		horizontalBox_1.add(labelSteps);

		verticalStrut_5 = Box.createVerticalStrut(15);
		verticalBox.add(verticalStrut_5);

		horizontalBox_5 = Box.createHorizontalBox();
		verticalBox.add(horizontalBox_5);

		lblSelectAlgorithm = new JLabel("Select Algorithm:");
		lblSelectAlgorithm.setFont(new Font("Calibri", Font.PLAIN, 14));
		horizontalBox_5.add(lblSelectAlgorithm);

		rdbtnA = new JRadioButton("A*");
		rdbtnA.setFont(new Font("Calibri", Font.PLAIN, 12));
		horizontalBox_5.add(rdbtnA);

		horizontalGlue_5 = Box.createHorizontalGlue();
		horizontalBox_5.add(horizontalGlue_5);

		rdbtnBFS = new JRadioButton("BFS");
		rdbtnBFS.setFont(new Font("Calibri", Font.PLAIN, 12));
		horizontalBox_5.add(rdbtnBFS);

		horizontalGlue_6 = Box.createHorizontalGlue();
		horizontalBox_5.add(horizontalGlue_6);

		rdbtnDFS = new JRadioButton("DFS");
		rdbtnDFS.setFont(new Font("Calibri", Font.PLAIN, 12));
		horizontalBox_5.add(rdbtnDFS);

		verticalStrut_8 = Box.createVerticalStrut(5);
		verticalBox.add(verticalStrut_8);

		progressBar = new JProgressBar();
		progressBar.setToolTipText("Process of solving.");
		progressBar.setStringPainted(true);
		verticalBox.add(progressBar);

		verticalStrut_4 = Box.createVerticalStrut(10);
		verticalBox.add(verticalStrut_4);

		horizontalBox_2 = Box.createHorizontalBox();
		verticalBox.add(horizontalBox_2);
		btnSolve = new JButton("Solve Map");
		btnSolve.setToolTipText("Application will give solutions to the current map.");
		btnSolve.setFont(new Font("Calibri", Font.PLAIN, 12));
		horizontalBox_2.add(btnSolve);

		horizontalGlue_4 = Box.createHorizontalGlue();
		horizontalBox_2.add(horizontalGlue_4);

		horizontalGlue_10 = Box.createHorizontalGlue();
		horizontalBox_2.add(horizontalGlue_10);
		btnAutomove = new JButton("Auto Move");
		btnAutomove.setFont(new Font("Calibri", Font.PLAIN, 12));
		btnAutomove.setEnabled(false);
		horizontalBox_2.add(btnAutomove);

		verticalStrut_6 = Box.createVerticalStrut(20);
		verticalBox.add(verticalStrut_6);

		separator = new JSeparator();
		verticalBox.add(separator);

		horizontalBox_6 = Box.createHorizontalBox();
		verticalBox.add(horizontalBox_6);

		verticalStrut_11 = Box.createVerticalStrut(10);
		verticalBox.add(verticalStrut_11);

		horizontalBox_7 = Box.createHorizontalBox();
		verticalBox.add(horizontalBox_7);
		labelleaders = new JLabel("LeaderBoard:");
		horizontalBox_7.add(labelleaders);
		labelleaders.setFont(new Font("Calibri", Font.BOLD, 16));

		horizontalGlue_8 = Box.createHorizontalGlue();
		horizontalBox_7.add(horizontalGlue_8);

		//Add ActionListeners to various components.
		btnCreateMap.addActionListener(this);
		btnLoadMap.addActionListener(this);
		btnStart.addActionListener(this);
		btnPause.addActionListener(this);
		btnSolve.addActionListener(this);
		btnAutomove.addActionListener(this);
		btnPrevStep.addActionListener(this);
		btnNextStep.addActionListener(this);
		btnStop.addActionListener(this);
		ButtonGroup rbalgorithm= new ButtonGroup();
		rbalgorithm.add(rdbtnA);
		rbalgorithm.add(rdbtnBFS);
		rbalgorithm.add(rdbtnDFS);

		UtilPanel.infoChange("Please select a map and start the game!");

		//Functions
		loadMaps();
		for(int i=0;i<15;i++){
			marks[i]=null;
		}
		FileReader fi = null;
		try {
			fi = new FileReader("Advanced-01.maps");
		} catch (FileNotFoundException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
		BufferedReader in=new BufferedReader(fi);
		try {
			GamePanel.CARCOUNT=Integer.valueOf(in.readLine()).intValue();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		char tem;
		for(int i=0;i<6;i++){
			for(int j=0;j<6;j++){
				try {
					GamePanel.testmap[i][j]=(char) in.read();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					tem=(char) in.read();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		int oo=0;
		while(true&&oo<10){
			try {
				System.out.println(oo);
				marks[oo++] = in.readLine();
				if(marks[oo-1]==null){
					System.out.println("end");
					oo--;
					break;
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				System.out.println("end");
				break;
			}
		}
		try {
			fi.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String ttt="<html><body>"+"LeaderBoard:"+"<br>";
		for(int i=0;i<oo&&i<5;i++){
			ttt+=(i+1)+". "+marks[i]+"<br>";
		}
		ttt+="<html><body>";
		labelleaders.setText(ttt);
		textFieldsteps.setText(Integer.toString(currentstep));

	}

	public void Win(){
		if(!ischange){
			JOptionPane.showMessageDialog(null, "你已经完成了游戏！用时：" + UtilPanel.Currenttime);
			System.out.println(UtilPanel.getcurrentuser().getName());
			System.out.println(UtilPanel.te);
			if(UtilPanel.te!=null){
			String res=UtilPanel.getcurrentuser().getName()+" "+UtilPanel.te;
			int cc=0;
			MapFile te=(MapFile) comboBoxMaps.getSelectedItem();
			System.out.println(((MapFile) comboBoxMaps.getSelectedItem()).getMapName());
			String filename=((MapFile) comboBoxMaps.getSelectedItem()).getMapName();
			FileReader fi = null;
			try {
				fi = new FileReader(filename);
			} catch (FileNotFoundException e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			}
			BufferedReader in=new BufferedReader(fi);
			try {
				cc=Integer.valueOf(in.readLine()).intValue();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			for(int i=0;i<15;i++){
				marks[i]=null;
			}
			for(int i=0;i<6;i++){
				for(int j=0;j<6;j++){
					try {
						in.read();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					try {
						in.read();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
			int oo=0;
			while(true){
				try {
					System.out.println(oo);
					marks[oo++] = in.readLine();
					if(marks[oo-1]==null){
						oo--;
						break;
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.out.println("end");
					break;
				}
			}
			System.out.println(oo);
			System.out.println(marks[0]);
			try {
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(res);
			String tem = "00:00:00";
			for(int i=0;i<=oo;i++){
				if(marks[0]==null){
					System.out.println("ok");
					marks[0]=res;
					break;
				}
				if(marks[i]==null)
					tem = "99:99:99";
				else{
					for(int j=0;j<marks[i].length();j++){
						if(marks[i].charAt(j)==' '){
							tem=marks[i].substring(j+1);

							break;
						}
					}
				}
				System.out.println(tem);
				if(UtilPanel.te.compareTo(tem)<=0){
					System.out.println("s");
					for(int j=10;j>i;j--){
						marks[j]=marks[j-1];
					}
					marks[i]=res;
					break;
				}
			}
			oo++;
			FileWriter writer = null;
			try {
				writer = new FileWriter(filename, false);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				writer.write(""+cc);
				writer.write("\n");
				for(int i=0;i<6;i++){
					for(int j=0;j<6;j++){
						writer.write(""+GamePanel.testmap[i][j]);
						if(j==5){
							writer.write("\n");
						}
						else{
							writer.write(" ");
						}
					}
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			for(int i=0;i<=oo&&i<10;i++){
				try {
					if(marks[i]!=null)
						writer.write(marks[i]+'\n');
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			try {
				writer.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			ischange=true;
			String ttt="<html><body>"+"LeaderBoard:"+"<br>";
			for(int i=0;i<oo&&i<5;i++){
				ttt+=(i+1)+". "+marks[i]+"<br>";
			}
			ttt+="<html><body>";
			labelleaders.setText(ttt);
			repaint();
		}
		}
	}

	private void loadMaps() {
		File puzzlesDir = new File(".//");
		for (File file : puzzlesDir.listFiles()) {
			String fileName = file.getName();
			System.out.println(fileName);
			if (Pattern.matches(".*(\\.maps)$", fileName)){
				comboBoxMaps.addItem(new MapFile(file));
			String tem=fileName.substring(0, 3);
			if(tem.equals("New")){
				System.out.println("ok");
				MapFrame.count++;
			}
			}
		}
		comboBoxMaps.addItemListener(new ItemListener() {  
			@Override  
			public void itemStateChanged(ItemEvent e) {
				while(!prevstep.empty())prevstep.pop();
				while(!nextstep.empty())nextstep.pop();
				btnPrevStep.setEnabled(false);
				btnPrevStep.setEnabled(false);
				currentstep = 0;
				textFieldsteps.setText(Integer.toString(currentstep));
				btnAutomove.setEnabled(false);

				MapFile te=(MapFile) comboBoxMaps.getSelectedItem();
				System.out.println(((MapFile) comboBoxMaps.getSelectedItem()).getMapName());
				String filename=((MapFile) comboBoxMaps.getSelectedItem()).getMapName();
				for(int i=0;i<15;i++){
					marks[i]=null;
				}
				FileReader fi = null;
				try {
					fi = new FileReader(filename);
				} catch (FileNotFoundException e3) {
					// TODO Auto-generated catch block
					e3.printStackTrace();
				}
				BufferedReader in=new BufferedReader(fi);
				try {
					GamePanel.CARCOUNT=Integer.valueOf(in.readLine()).intValue();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				char tem;
				for(int i=0;i<6;i++){
					for(int j=0;j<6;j++){
						try {
							GamePanel.testmap[i][j]=(char) in.read();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						try {
							tem=(char) in.read();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
				int oo=0;
				while(true&&oo<10){
					try {
						System.out.println(oo);
						marks[oo++] = in.readLine();
						if(marks[oo-1]==null){
							System.out.println("end");
							oo--;
							break;
						}
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						System.out.println("end");
						break;
					}
				}
				String ttt="<html><body>"+"LeaderBoard:"+"<br>";
				for(int i=0;i<oo&&i<5;i++){
					ttt+=(i+1)+". "+marks[i]+"<br>";
				}
				ttt+="<html><body>";
				labelleaders.setText(ttt);
				repaint();
				//System.out.println(GamePanel.CARCOUNT);
				for(int i=0;i<6;i++){
					for(int j=0;j<6;j++){
						System.out.print(GamePanel.testmap[i][j]+" ");
					}     
					System.out.println("");
				}
				GamePanel.loadFileMap(GamePanel.testmap);
				MainFrame.carpark.repaint();

				try {
					in.close();
					fi.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}    
		});  
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//For test purpose.
		MainFrame test = new MainFrame();

	}

	public static void pushStepToStack(Action step){
		prevstep.push(step);
		while(!nextstep.empty())nextstep.pop();
		btnPrevStep.setEnabled(true);
		currentstep++;
		textFieldsteps.setText(Integer.toString(currentstep));
		if(prevstep.empty()) btnPrevStep.setEnabled(false);
		else btnPrevStep.setEnabled(true);
		if(!nextstep.empty()) btnNextStep.setEnabled(true);
		else btnNextStep.setEnabled(false);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object obj = e.getActionCommand();
		if(obj == "Create Map"){
			System.out.println("Create Map Pressed");
			mf = new MapFrame();
		}
		else if(obj == "Load Maps From Files"){
			System.out.println("Map files pressed");
			JFileChooser jfc=new JFileChooser();  
	        jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);  
	        jfc.showDialog(new JLabel(), "选择");  
	        jfc.setCurrentDirectory(new File(".//"));
	        jfc.setAcceptAllFileFilterUsed(false);
	        File file = jfc.getSelectedFile();  
	        if(file.isFile()) System.out.println("文件:"+file.getAbsolutePath());
	        else{
	        	JOptionPane.showMessageDialog(null,"文件选择出错",  
						"错误", JOptionPane.ERROR_MESSAGE); 
	        }
	        String fileName = file.getName();
	        System.out.println(fileName);  
	        if (Pattern.matches(".*(\\.maps)$", fileName))
				comboBoxMaps.addItem(new MapFile(file));
		}
		else if(obj == "Next Step"){
			System.out.println("Replay pressed");
			Action a = new Action(nextstep.peek().getBlock(),nextstep.peek().getMoves());
			System.out.println(a.toString());
			MainFrame.carpark.MoveCar(a);
			prevstep.push(nextstep.peek());
			nextstep.pop();
			currentstep++;
			textFieldsteps.setText(Integer.toString(currentstep));
			if(prevstep.empty()) btnPrevStep.setEnabled(false);
			else btnPrevStep.setEnabled(true);
			if(!nextstep.empty()) btnNextStep.setEnabled(true);
			else btnNextStep.setEnabled(false);
		}
		else if(obj == "Prev Step"){
			System.out.println("Prevstep pressed");
			Action a = new Action(prevstep.peek().getBlock(),-prevstep.peek().getMoves());
			System.out.println(a.toString());
			MainFrame.carpark.MoveCar(a);
			nextstep.push(prevstep.peek());
			prevstep.pop();
			currentstep--;
			textFieldsteps.setText(Integer.toString(currentstep));
			if(prevstep.empty()) btnPrevStep.setEnabled(false);
			else btnPrevStep.setEnabled(true);
			if(!nextstep.empty()) btnNextStep.setEnabled(true);
			else btnNextStep.setEnabled(false);
		}
		else if (obj == "Solve Map") {
			System.out.println("Solve pressed");
			btnPrevStep.setEnabled(false);
			btnNextStep.setEnabled(false);
			while(!nextstep.empty()) nextstep.pop();
			while(!prevstep.empty()){
				Action a = new Action(prevstep.peek().getBlock(),-prevstep.peek().getMoves());
				System.out.println(a.toString());
				MainFrame.carpark.MoveCar(a);
				prevstep.pop();
			}
			currentstep = 0;
			textFieldsteps.setText(Integer.toString(currentstep));
			new SolverThread().start();
		}
		else if(obj == "Auto Move"){
			System.out.println("Auto Move pressed");
			new ShowThread().start();
			btnAutomove.setEnabled(false);
			
		}
		else if(obj == "Start Game"){
			System.out.println("Start pressed.");
			UtilPanel.infoChange("Game STARTS!!!");
			btnStart.setEnabled(false);
			btnPause.setEnabled(true);
			rdbtnA.setEnabled(false);
			rdbtnBFS.setEnabled(false);
			rdbtnDFS.setEnabled(false);
			btnSolve.setEnabled(false);
			btnCreateMap.setEnabled(false);
			comboBoxMaps.setEnabled(false);
			btnLoadMap.setEnabled(false);
			btnAutomove.setEnabled(false);
			gamestarted = true;
			gamepaused  = false;
			ischange=false;
		}
		else if(obj == "Pause Game"){
			System.out.println("Pause pressed.");
			gamestarted = true;
			gamepaused = true;
			btnStart.setEnabled(true);
			btnStop.setEnabled(true);
			btnPause.setEnabled(false);
			btnPrevStep.setEnabled(false);
			btnNextStep.setEnabled(false);
		}
		else if(obj == "Stop!"){
			gamestarted = false;
			gamepaused = false;
			btnPause.setEnabled(false);
			btnStart.setEnabled(true);
			btnStop.setEnabled(false);
			rdbtnA.setEnabled(true);
			rdbtnBFS.setEnabled(true);
			rdbtnDFS.setEnabled(true);
			btnSolve.setEnabled(true);
			btnLoadMap.setEnabled(true);
			comboBoxMaps.setEnabled(true);
			btnCreateMap.setEnabled(true);
			if(prevstep.empty()) btnPrevStep.setEnabled(false);
			else btnPrevStep.setEnabled(true);
			if(!nextstep.empty()) btnNextStep.setEnabled(true);
			else btnNextStep.setEnabled(false);
		}
	}

	private class SolverThread extends Thread {

		public void run() {
			btnSolve.setEnabled(false);
			btnAutomove.setEnabled(false);
			comboBoxMaps.setEnabled(false);
			rdbtnA.setEnabled(false);
			rdbtnBFS.setEnabled(false);
			rdbtnDFS.setEnabled(false);

			MapFile mFile= (MapFile)comboBoxMaps.getSelectedItem();
			Scanner in = null;
			try {
				in = new Scanner(mFile.file());
			} catch (FileNotFoundException e1) {}

			in.next();
			char[][] blocks = new char[6][6];
			for (int i = 0; i < 6; i++)
				for (int j = 0; j < 6; j++)
					blocks[i][j] = in.next().charAt(0);

			Board initial = new Board(blocks);
			in.close();
			if (rdbtnA.isSelected())
				s = new AStarSolver(initial);
			else if (rdbtnBFS.isSelected())
				s = new BFSSolver(initial);
			else if (rdbtnDFS.isSelected())
				s = new DFSSolver(initial);
			else {
				JOptionPane.showMessageDialog(null,
						"You should select an algorithm.");

				btnSolve.setEnabled(true);
				btnAutomove.setEnabled(true);
				comboBoxMaps.setEnabled(true);
				rdbtnA.setEnabled(true);
				rdbtnBFS.setEnabled(true);
				rdbtnDFS.setEnabled(true);
				return;
			}

			//For testing purpose.
			System.out.println("Number of Moves: " + s.moves());
			System.out.println("Number of Expanded Nodes " + s.expandedNodes());
			System.out.println("Running time: " + s.getRunningTime());
			
			UtilPanel.infoChange("Number of Moves: " + s.moves()+ "   Running time: " + s.getRunningTime());
			
			btnSolve.setEnabled(true);
			btnAutomove.setEnabled(true);
			comboBoxMaps.setEnabled(true);
			rdbtnA.setEnabled(true);
			rdbtnBFS.setEnabled(true);
			rdbtnDFS.setEnabled(true);
		}
	}


	private class ShowThread extends Thread {

		public void run() {
			btnSolve.setEnabled(false);
			btnAutomove.setEnabled(false);
			comboBoxMaps.setEnabled(false);
			rdbtnA.setEnabled(false);
			rdbtnBFS.setEnabled(false);
			rdbtnDFS.setEnabled(false);

			if (s.isSolvable()){
				double havedonesteps = 0;
				double totalsteps = s.moves();
				for (Action a : s.solution()) {
					System.out.println(a.toString());
					MainFrame.carpark.MoveCar(a);
					prevstep.push(a);
					currentstep++;
					textFieldsteps.setText(Integer.toString(currentstep));
					progressBar.setValue((int)(havedonesteps++/totalsteps*100));
					//progressBar.setString(Double.toString(havedonesteps) + " / " + Double.toString(totalsteps));
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					repaint();
				}
				//progressBar.setValue(100);
			}
			else
				JOptionPane.showMessageDialog(null,
						"The board you selected has no solution.");
			btnSolve.setEnabled(true);
			btnAutomove.setEnabled(true);
			comboBoxMaps.setEnabled(true);
			rdbtnA.setEnabled(true);
			rdbtnBFS.setEnabled(true);
			rdbtnDFS.setEnabled(true);
			if(prevstep.empty()) btnPrevStep.setEnabled(false);
			else btnPrevStep.setEnabled(true);
			if(!nextstep.empty()) btnNextStep.setEnabled(true);
			else btnNextStep.setEnabled(false);
		}
	}
}