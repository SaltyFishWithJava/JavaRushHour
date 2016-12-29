package Main;

import java.awt.*;
import java.awt.event.*;
import java.util.Stack;

import javax.swing.*;

import Abstractions.Action;
import Abstractions.MapFile;
import Abstractions.Server;
import Abstractions.Solver;
import Abstractions.User;


public class MultiFrameFuncPanel extends JPanel implements ActionListener{
	public static boolean gamestarted;
	public static boolean gamepaused;
	public static JButton btnStart;
	public MiniMap mp;
	private static RoomFrame rf;
	private User currentuser;
	
	private Solver s;
	private static Stack<Action> prevstep;
	private static Stack<Action> opprevstep;
	
	private JLabel labelmaps;
	private JLabel labelcontrols;
	public static JButton btnCreateMap;
	public static JButton btnLoadMap;
	public static JComboBox<MapFile> comboBoxMaps;
	private JButton btnPrevStep;
	private Box horizontalBox;
	private Box verticalBox;
	
	private Component verticalStrut;
	private Box horizontalBox_1;
	private Component verticalStrut_1;
	private Box horizontalBox_3;
	private Component verticalStrut_2;
	private Box horizontalBox_4;
	private Component horizontalGlue_1;
	private Component horizontalGlue_2;
	private Component verticalStrut_3;
	private JLabel labelSteps;
	private Component horizontalGlue_7;
	private JSeparator separator_1;
	private JSeparator separator;
	private Box horizontalBox_6;
	private JLabel lblMusic;
	private Box horizontalBox_7;
	private Component verticalStrut_10;
	private JSeparator separator_2;
	private Box horizontalBox_8;
	private JSeparator separator_3;
	private Component verticalStrut_13;
	private Component verticalStrut_14;
	private JSlider slider;
	private Box horizontalBox_2;
	private JLabel lblopponentmove;
	private Component horizontalGlue_3;
	private Component verticalStrut_4;
	private JLabel lbOpponentStep;
	private JLabel lblVS;
	private Component verticalStrut_5;
	public static JButton btnPause;
	private Component horizontalGlue_4;
	private Component verticalStrut_6;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	private Component horizontalStrut;
	private Component horizontalStrut_1;
	
	public MultiFrameFuncPanel(JFrame m){
		
		gamestarted = false;
		gamepaused = true;
		//Layouts and Listeners
		verticalBox = Box.createVerticalBox();
		add(verticalBox);
		
		verticalStrut_14 = Box.createVerticalStrut(5);
		verticalBox.add(verticalStrut_14);
		
		horizontalBox_8 = Box.createHorizontalBox();
		verticalBox.add(horizontalBox_8);
		
		btnStart = new JButton("Start Game");
		btnStart.setEnabled(false);
		btnStart.setToolTipText("Timer will be started.");
		btnStart.setFont(new Font("Calibri", Font.PLAIN, 12));
		horizontalBox_8.add(btnStart);
		
		horizontalGlue_4 = Box.createHorizontalGlue();
		horizontalBox_8.add(horizontalGlue_4);
		
		btnPause = new JButton("Pause Game");
		btnPause.setEnabled(false);
		btnPause.setFont(new Font("Calibri", Font.PLAIN, 12));
		horizontalBox_8.add(btnPause);
		
		verticalStrut_6 = Box.createVerticalStrut(5);
		verticalBox.add(verticalStrut_6);
		
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
		btnCreateMap.setEnabled(false);
		btnCreateMap.setFont(new Font("Calibri", Font.PLAIN, 12));
		btnCreateMap.setToolTipText("Start MapCreator");
		horizontalBox.add(btnCreateMap);
		
		horizontalGlue_2 = Box.createHorizontalGlue();
		horizontalBox.add(horizontalGlue_2);
		btnLoadMap = new JButton("Load Maps From Files");
		btnLoadMap.setEnabled(false);
		btnLoadMap.setFont(new Font("Calibri", Font.PLAIN, 12));
		btnLoadMap.setToolTipText("Load maps from file.");
		horizontalBox.add(btnLoadMap);
		
		verticalStrut = Box.createVerticalStrut(10);
		verticalBox.add(verticalStrut);
		
		comboBoxMaps = new JComboBox();
		comboBoxMaps.setEnabled(false);
		comboBoxMaps.setToolTipText("Files with *puzzles will be shown here.");
		verticalBox.add(comboBoxMaps);
	
		
		verticalStrut_1 = Box.createVerticalStrut(15);
		verticalBox.add(verticalStrut_1);
		
		separator_1 = new JSeparator();
		verticalBox.add(separator_1);
		
		horizontalBox_4 = Box.createHorizontalBox();
		verticalBox.add(horizontalBox_4);
		labelcontrols = new JLabel("Controls:");
		horizontalBox_4.add(labelcontrols);
		labelcontrols.setFont(new Font("Calibri", Font.BOLD, 16));
		
		horizontalStrut = Box.createHorizontalStrut(80);
		horizontalBox_4.add(horizontalStrut);
		
		lblNewLabel = new JLabel("Yours");
		lblNewLabel.setFont(new Font("Calibri", Font.PLAIN, 12));
		horizontalBox_4.add(lblNewLabel);
		
		horizontalStrut_1 = Box.createHorizontalStrut(35);
		horizontalBox_4.add(horizontalStrut_1);
		
		lblNewLabel_1 = new JLabel("Opponent's");
		lblNewLabel_1.setFont(new Font("Calibri", Font.PLAIN, 12));
		horizontalBox_4.add(lblNewLabel_1);
		
		verticalStrut_3 = Box.createVerticalStrut(5);
		verticalBox.add(verticalStrut_3);
		
		horizontalBox_1 = Box.createHorizontalBox();
		verticalBox.add(horizontalBox_1);
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
		
		lblVS = new JLabel("   VS   ");
		lblVS.setFont(new Font("Calibri", Font.PLAIN, 12));
		horizontalBox_1.add(lblVS);
		
		lbOpponentStep = new JLabel("0000 Steps");
		horizontalBox_1.add(lbOpponentStep);
		lbOpponentStep.setToolTipText("The steps your opponets have made.");
		lbOpponentStep.setFont(new Font("Calibri", Font.PLAIN, 12));
		
		verticalStrut_5 = Box.createVerticalStrut(5);
		verticalBox.add(verticalStrut_5);
		
		separator = new JSeparator();
		verticalBox.add(separator);
		
		verticalStrut_4 = Box.createVerticalStrut(10);
		verticalBox.add(verticalStrut_4);
		
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
		
		horizontalBox_2 = Box.createHorizontalBox();
		verticalBox.add(horizontalBox_2);
		
		lblopponentmove = new JLabel("Opponent's Movements:");
		lblopponentmove.setFont(new Font("Calibri", Font.BOLD, 16));
		horizontalBox_2.add(lblopponentmove);
		
		horizontalGlue_3 = Box.createHorizontalGlue();
		horizontalBox_2.add(horizontalGlue_3);
		
		horizontalBox_7 = Box.createHorizontalBox();
		verticalBox.add(horizontalBox_7);
		mp = new MiniMap();
		mp.setToolTipText("A peek of what your opponent is doing.");
		mp.setPreferredSize(new Dimension(270,270));
		verticalBox.add(mp);
		
		//Add ActionListeners to various components.
		btnCreateMap.addActionListener(this);
		btnLoadMap.addActionListener(this);
		btnStart.addActionListener(this);
		
		this.setEnabled(false);
		//Functions
		rf = new RoomFrame();
		rf.setAlwaysOnTop(true);
	}
	
	public void closing(){
		rf.closing();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//For testing purposes.
		MultiFrame mf = new MultiFrame();
		//rf.sendMessage();
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
		else if(obj == "Prev Step"){
			System.out.println("Prevstep pressed");
			Action a = prevstep.peek();
			a.toPrevStep();
			System.out.println(a.toString());
			//
			prevstep.pop();
			if(prevstep.empty()) btnPrevStep.setEnabled(false);
		}
		else if(obj == "Start Game"){
			System.out.println("Start pressed.");
			Server.send("START");
			btnStart.setEnabled(false);
			GamePanel.isenabled = true;
			//btnPause.setEnabled(true);
			gamestarted = true;
			gamepaused  = false;
		}
		else if(obj == "Pause Game"){
			System.out.println("Pause pressed.");
			btnPause.setEnabled(false);
			btnStart.setEnabled(true);
			gamestarted = true;
			gamepaused = true;
		}
	}

}