package Main;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class FunctionPanel extends JPanel{
	private JTextField txtMaptext;
	public FunctionPanel() {
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		Box verticalBoxFunc = Box.createVerticalBox();
		add(verticalBoxFunc);
		
		Box horizontalBoxGame = Box.createHorizontalBox();
		verticalBoxFunc.add(horizontalBoxGame);
		
		JLabel Labelgame = new JLabel("Game: ");
		Labelgame.setFont(new Font("LiSong Pro", Font.BOLD, 15));
		horizontalBoxGame.add(Labelgame);
		Labelgame.setHorizontalAlignment(SwingConstants.CENTER);
		
		Component horizontalGlue = Box.createHorizontalGlue();
		horizontalBoxGame.add(horizontalGlue);
		
		JButton jbgameStart = new JButton("Start Game");
		horizontalBoxGame.add(jbgameStart);
		
		JButton jbgameSave = new JButton("Save Game");
		jbgameSave.setEnabled(false);
		horizontalBoxGame.add(jbgameSave);
		
		Box verticalBoxMap = Box.createVerticalBox();
		verticalBoxFunc.add(verticalBoxMap);
		
		Box horizontalMap = Box.createHorizontalBox();
		verticalBoxMap.add(horizontalMap);
		
		JLabel jlmap = new JLabel("Maps:");
		jlmap.setHorizontalAlignment(SwingConstants.CENTER);
		jlmap.setFont(new Font("LiSong Pro", Font.BOLD, 15));
		horizontalMap.add(jlmap);
		
		Component horizontalGlue_1 = Box.createHorizontalGlue();
		horizontalMap.add(horizontalGlue_1);
		
		Box horizontalBoxMapCreater = Box.createHorizontalBox();
		verticalBoxMap.add(horizontalBoxMapCreater);
		
		JButton jbLoadMap = new JButton("Load Map");
		horizontalBoxMapCreater.add(jbLoadMap);
		
		JButton btnNewButton_1 = new JButton("Save Map");
		horizontalBoxMapCreater.add(btnNewButton_1);
		btnNewButton_1.setEnabled(false);
		
		JButton jbCreateMap = new JButton("Create Map");
		horizontalBoxMapCreater.add(jbCreateMap);
		
		Box horizontalBoxMapSelect = Box.createHorizontalBox();
		verticalBoxMap.add(horizontalBoxMapSelect);
		
		txtMaptext = new JTextField();
		txtMaptext.setText("MapText");
		verticalBoxMap.add(txtMaptext);
		txtMaptext.setColumns(10);
		
		JComboBox comboBox = new JComboBox();
		verticalBoxMap.add(comboBox);
		
		Box verticalBoxSolver = Box.createVerticalBox();
		verticalBoxFunc.add(verticalBoxSolver);
		
		Box horizontalBox = Box.createHorizontalBox();
		verticalBoxSolver.add(horizontalBox);
		
		JLabel lblSolver = new JLabel("Solver:");
		lblSolver.setFont(new Font("LiSong Pro", Font.BOLD, 15));
		lblSolver.setHorizontalAlignment(SwingConstants.CENTER);
		horizontalBox.add(lblSolver);
		
		Component horizontalGlue_2 = Box.createHorizontalGlue();
		horizontalBox.add(horizontalGlue_2);
		
		Box horizontalBox_1 = Box.createHorizontalBox();
		verticalBoxSolver.add(horizontalBox_1);
		
		JButton jbStartSolver = new JButton("Start Solver");
		jbStartSolver.setEnabled(false);
		horizontalBox_1.add(jbStartSolver);
		
		JButton jbSaveSolution = new JButton("Save Solutions");
		jbSaveSolution.setEnabled(false);
		horizontalBox_1.add(jbSaveSolution);
		
		JButton jbHint = new JButton("Hint!");
		jbHint.setEnabled(false);
		horizontalBox_1.add(jbHint);
		
		Box verticalBox_1 = Box.createVerticalBox();
		verticalBoxSolver.add(verticalBox_1);
		
		JProgressBar progressBar = new JProgressBar();
		verticalBox_1.add(progressBar);
		
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setEnabled(false);
		verticalBox_1.add(comboBox_1);
		
		JTextPane txtpnSteps = new JTextPane();
		txtpnSteps.setText("Steps:");
		verticalBox_1.add(txtpnSteps);
		
		Box horizontalBox_2 = Box.createHorizontalBox();
		verticalBoxSolver.add(horizontalBox_2);
		
		JButton jbautomove = new JButton("Auto Move");
		jbautomove.setEnabled(false);
		horizontalBox_2.add(jbautomove);
		
		JButton jbstepbystep = new JButton("Step By Step");
		jbstepbystep.setEnabled(false);
		horizontalBox_2.add(jbstepbystep);
		
		Box verticalBox = Box.createVerticalBox();
		verticalBoxFunc.add(verticalBox);
		
		Box horizontalBox_4 = Box.createHorizontalBox();
		verticalBox.add(horizontalBox_4);
		
		JLabel lblPlayWithFriends = new JLabel("Play With Friends");
		lblPlayWithFriends.setHorizontalAlignment(SwingConstants.CENTER);
		lblPlayWithFriends.setFont(new Font("LiSong Pro", Font.BOLD, 15));
		horizontalBox_4.add(lblPlayWithFriends);
		
		JButton btnMultiplayer = new JButton("Multi-Player");
		horizontalBox_4.add(btnMultiplayer);
		
		JTextPane txtpnLeaderboard = new JTextPane();
		txtpnLeaderboard.setText("LeaderBoard:");
		verticalBox.add(txtpnLeaderboard);
	}

}
