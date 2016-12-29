package Main;

import java.awt.*;
import javax.swing.*;

//专门处理在JFrame中放置图片

class ImagePanel extends JPanel{
	private Image image;
	
	public ImagePanel(String filename){
		image = Toolkit.getDefaultToolkit().getImage(filename);
		MediaTracker mt = new MediaTracker(this);
		mt.addImage(image, 0);
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		int imageWidth = image.getWidth(this);
		int imageHeight = image.getHeight(this);
		
		int panelWidth = this.getWidth();
		int panelHeight = this.getHeight();
		
		//System.out.println(panelWidth);
		//System.out.println(panelHeight);
		
		int x = (panelWidth - imageWidth)/2;
		int y = (panelHeight - imageHeight)/2;
		
		g.drawImage(image, x, y, null);
	}
	
	public static void main(String[] args){
		JFrame test = new JFrame();
		test.setSize(400,500);
		
		Container contentpane = test.getContentPane();
		ImagePanel iptest = new ImagePanel("Images\\intrologo.jpg");
		iptest.setPreferredSize(new Dimension(300,400));
		contentpane.add(iptest,BorderLayout.NORTH);
		test.setVisible(true);
	}
}
