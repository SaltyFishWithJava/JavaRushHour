package Main;

import java.awt.*;
import javax.swing.*;

import Abstractions.MapFile;
import Main.MainFrame.innerClassWindowListener;

import java.awt.event.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException; 

public class MapFrame extends JFrame{
	private final static int FRAME_WIDTH = 1000;
	private final static int FRAME_HEIGHT = 650;
	static public int count=0;
	public MapPanel mappark;
	////buttons

	
	public MapFrame(){
		mappark=new MapPanel();
		Container contentPane = getContentPane();
		contentPane.add(mappark);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();  
		int screenWidth = screenSize.width;  
		int screenHeight = screenSize.height;  
		
		setLocation((screenWidth - FRAME_WIDTH) / 2, (screenHeight - FRAME_HEIGHT) / 2); 
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setBackground(Color.white);
		this.addWindowListener(new innerClassWindowListener());
		setVisible(true);
	}
	public static void main(String[] args){
		MapFrame mff = new MapFrame();

	}
	class innerClassWindowListener extends WindowAdapter{
		public void windowClosing(WindowEvent e){
			MapPanel.changeTestMap();
			boolean isch=false;
			for(int i=0;i<6;i++){
				for(int j=0;j<6;j++){
					System.out.print(MapPanel.testmap[i][j]+" ");
					if(MapPanel.testmap[i][j]!='-'){
						isch=true;
					}
				}
				System.out.println("");
			}
			if(isch){
			FileWriter writer = null;
			try {
				writer = new FileWriter("NewMap-0"+(++count)+".maps", false);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
            try {
				writer.write(""+MapPanel.coo);
				writer.write('\n');
				for(int i=0;i<6;i++){
					for(int j=0;j<6;j++){
						writer.write(""+MapPanel.testmap[i][j]);
						if(j==5){
							writer.write('\n');
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
            
            try {
				writer.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
            MapPanel.initCa();
            File f=new File("NewMap-0"+(count)+".maps");
            MainFrameFuncPanel.comboBoxMaps.addItem(new MapFile(f));
			}
            Cars.Mco=0;
		}
	}
}
