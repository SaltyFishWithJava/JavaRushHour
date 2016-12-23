package Main;

import Abstractions.*;
import Abstractions.Action;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Scanner;
import java.util.Stack;
import java.util.regex.Pattern;

import javax.swing.*;

public class MainFrameFuncPanel extends JPanel implements ActionListener{
	public boolean gamestarted;
	public boolean gamepaused;
	public JButton btnStart;
	public JButton btnPause;
	
	private Solver s;
	private Stack<Action> prevstep;
	
	private JLabel labelmaps;
	private JLabel labelcontrols;
	private JLabel labelleaders;
	private JButton btnCreateMap;
	private JButton btnLoadMap;
	private JComboBox<MapFile> comboBoxMaps;
	private JButton btnHint;
	private JButton btnPrevStep;
	private JButton btnSolve;
	private JButton btnAutomove;
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
	private Component horizontalGlue_3;
	private Component verticalStrut_3;
	private Box horizontalBox_5;
	private JRadioButton rdbtnA;
	private JRadioButton rdbtnBFS;
	private JRadioButton rdbtnDFS;
	private Component verticalStrut_4;
	private Component verticalStrut_5;
	private Component horizontalGlue_4;
	private Component horizontalGlue_5;
	private Component horizontalGlue_6;
	private JLabel labelSteps;
	private Component horizontalGlue_7;
	private JProgressBar progressBar;
	private JSeparator separator_1;
	private Component verticalStrut_7;
	private Component verticalStrut_8;
	private JLabel lblSelectAlgorithm;
	private Component verticalStrut_6;
	private JSeparator separator;
	private Component verticalStrut_9;
	private Box horizontalBox_6;
	private JLabel lblMusic;
	private JTextPane textPane;
	private Box horizontalBox_7;
	private Component horizontalGlue_8;
	private Component verticalStrut_10;
	private JSeparator separator_2;
	private Component verticalStrut_11;
	private Box horizontalBox_8;
	private Component horizontalGlue_11;
	private Component verticalStrut_12;
	private JSeparator separator_3;
	private Component verticalStrut_13;
	private Component verticalStrut_14;
	private JSlider slider;
	private JButton btnSavegame;
	private Component horizontalGlue_9;
	private JButton btnReplay;
	private Component horizontalGlue_10;
	
	public MainFrameFuncPanel(){
		gamestarted = false;
		gamepaused = true;
		
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
		
		horizontalGlue_9 = Box.createHorizontalGlue();
		horizontalBox_8.add(horizontalGlue_9);
		
		btnSavegame = new JButton("Save Game");
		btnSavegame.setFont(new Font("Calibri", Font.PLAIN, 12));
		btnSavegame.setToolTipText("Current game process will be saved.");
		horizontalBox_8.add(btnSavegame);
		
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
		
		comboBoxMaps = new JComboBox();
		comboBoxMaps.setToolTipText("Files with *puzzles will be shown here.");
		verticalBox.add(comboBoxMaps);
	
		
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
		
		btnHint = new JButton("Hint!");
		btnHint.setEnabled(false);
		btnHint.setFont(new Font("Calibri", Font.PLAIN, 12));
		btnHint.setToolTipText("Show you the possible next step.");
		horizontalBox_1.add(btnHint);
		
		horizontalGlue_3 = Box.createHorizontalGlue();
		horizontalBox_1.add(horizontalGlue_3);
		btnPrevStep = new JButton("Prev Step");
		btnPrevStep.setEnabled(false);
		btnPrevStep.setFont(new Font("Calibri", Font.PLAIN, 12));
		btnPrevStep.setToolTipText("Go back to the previous step.");
		horizontalBox_1.add(btnPrevStep);
		
		horizontalGlue_7 = Box.createHorizontalGlue();
		horizontalBox_1.add(horizontalGlue_7);
		
		labelSteps = new JLabel("0000 Steps");
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
		
		btnReplay = new JButton("Replay");
		btnReplay.setEnabled(false);
		btnReplay.setToolTipText("Replay how you solve the map.");
		btnReplay.setFont(new Font("Calibri", Font.PLAIN, 12));
		horizontalBox_2.add(btnReplay);
		
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
		
		verticalStrut_9 = Box.createVerticalStrut(10);
		verticalBox.add(verticalStrut_9);
		
		horizontalBox_6 = Box.createHorizontalBox();
		verticalBox.add(horizontalBox_6);
		
		lblMusic = new JLabel("Volume: ");
		lblMusic.setFont(new Font("Calibri", Font.BOLD, 16));
		horizontalBox_6.add(lblMusic);
		
		slider = new JSlider();
		horizontalBox_6.add(slider);
		
		verticalStrut_10 = Box.createVerticalStrut(10);
		verticalBox.add(verticalStrut_10);
		
		separator_2 = new JSeparator();
		verticalBox.add(separator_2);
		
		verticalStrut_11 = Box.createVerticalStrut(10);
		verticalBox.add(verticalStrut_11);
		
		horizontalBox_7 = Box.createHorizontalBox();
		verticalBox.add(horizontalBox_7);
		labelleaders = new JLabel("LeaderBoard:");
		horizontalBox_7.add(labelleaders);
		labelleaders.setFont(new Font("Calibri", Font.BOLD, 16));
		
		horizontalGlue_8 = Box.createHorizontalGlue();
		horizontalBox_7.add(horizontalGlue_8);
		
		textPane = new JTextPane();
		textPane.setFont(new Font("Calibri", Font.PLAIN, 12));
		textPane.setEditable(false);
		verticalBox.add(textPane);
		
		//Add ActionListeners to various components.
		btnCreateMap.addActionListener(this);
		btnLoadMap.addActionListener(this);
		btnStart.addActionListener(this);
		btnPause.addActionListener(this);
		btnSolve.addActionListener(this);
		btnAutomove.addActionListener(this);
		ButtonGroup rbalgorithm= new ButtonGroup();
		rbalgorithm.add(rdbtnA);
		rbalgorithm.add(rdbtnBFS);
		rbalgorithm.add(rdbtnDFS);
		
		
		//Functions
		loadMaps();
		
	}

	private void loadMaps() {
		File puzzlesDir = new File(".//");
		for (File file : puzzlesDir.listFiles()) {
			String fileName = file.getName();
			System.out.println(fileName);
			if (Pattern.matches(".*(\\.maps)$", fileName))
				comboBoxMaps.addItem(new MapFile(file));
		}
		comboBoxMaps.addItemListener(new ItemListener() {  
            @Override  
            public void itemStateChanged(ItemEvent e) {
            	btnAutomove.setEnabled(false);
            	
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
    			//System.out.println(GamePanel.CARCOUNT);
    			for(int i=0;i<6;i++){
    				for(int j=0;j<6;j++){
    					System.out.print(GamePanel.testmap[i][j]+" ");
    				}
    				System.out.println("");
    			}
    			GamePanel.loadFileMap(GamePanel.testmap);
    			MainFrame.carpark.repaint();
    		}    
        });  
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//For test purpose.
		MainFrame test = new MainFrame();
		
	}

	public void pushStepToStack(Action step){
		prevstep.push(step);
		//
		//
		//
		//
		//
		//这里是用来把GamePanel里产生的步骤 传给这个prevstep的
		//
		//
		//
		//
		//
		//
		btnPrevStep.setEnabled(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object obj = e.getActionCommand();
		if(obj == "Create Map"){
			System.out.println("Create Map Pressed");
			//
			//
		}
		else if(obj == "Load Maps From Files"){
			System.out.println("Map files pressed");
			//
			//
			//
		}
		else if(obj == "Hint!"){
			System.out.println("Hint! pressed");
			//
			//
			//
			//
		}
		else if(obj == "Prev Step"){
			System.out.println("Prevstep pressed");
			Action a = prevstep.peek();
			a.toPrevStep();
			System.out.println(a.toString());
			//!!!!!!!!!!!!!!!!
			//传输给GamePanel做步骤处理
			//语法也是{A,3}这样的
			//
			//
			//
			//
			//
			//
			//
			//
			//
			
			prevstep.pop();
			if(prevstep.empty()) btnPrevStep.setEnabled(false);
		}
		else if (obj == "Solve Current Map") {
			System.out.println("Solve pressed");
			new SolverThread().start();
		}
		else if(obj == "Auto Move"){
			System.out.println("Auto Move pressed");
			new ShowThread().start();
		}
		else if(obj == "Start Game"){
			System.out.println("Start pressed.");
			btnStart.setEnabled(false);
			btnPause.setEnabled(true);
			btnSavegame.setEnabled(false);
			rdbtnA.setEnabled(false);
			rdbtnBFS.setEnabled(false);
			rdbtnDFS.setEnabled(false);
			btnSolve.setEnabled(false);
			btnAutomove.setEnabled(false);
			gamestarted = true;
			gamepaused  = false;
		}
		else if(obj == "Pause Game"){
			System.out.println("Pause pressed.");
			btnPause.setEnabled(false);
			btnStart.setEnabled(true);
			btnSavegame.setEnabled(true);
			rdbtnA.setEnabled(true);
			rdbtnBFS.setEnabled(true);
			rdbtnDFS.setEnabled(true);
			btnSolve.setEnabled(true);
			btnAutomove.setEnabled(true);
			gamestarted = true;
			gamepaused = true;
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
			
			if (s.isSolvable())
				for (Action a : s.solution()) {
					System.out.println(a.toString());
					//!!!!!!!!!!!!!!!!!!111
					//大老板这里是那种步骤出现的地方{A,3}
					//
					//
					//
					//
					//
					//
					//
					//
					//
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
			

		}
	}
	
}
