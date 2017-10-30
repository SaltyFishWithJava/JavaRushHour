package Main;

import Abstractions.*;
import Abstractions.Action;
import Main.GamePanel.InnerClassMouseListener2;

import java.awt.*;
import java.util.Queue;
import java.awt.event.*;
import java.awt.geom.GeneralPath;
import java.util.Arrays;
import java.util.LinkedList;

import javax.swing.*;

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.io.*;
public class MapPanel extends JPanel{
	final private int INITX = 165, INITY = 80;
	private int[][] park = { { 0, 1, 0, 1, 0, 1 }, { 1, 0, 1, 0, 1, 0 }, { 0, 1, 0, 1, 0, 1 }, { 1, 0, 1, 0, 1, 0 },
			{ 0, 1, 0, 1, 0, 1 }, { 1, 0, 1, 0, 1, 0 }, };
	private static int[][] ca = { { -1, -1, -1, -1, -1, -1 }, { -1, -1, -1, -1, -1, -1 }, { -1, -1, -1, -1, -1, -1 },
			{ -1, -1, -1, -1, -1, -1 }, { -1, -1, -1, -1, -1, -1 }, { -1, -1, -1, -1, -1, -1 }, };
	static char[][] testmap = new char[6][6];
	static public int coo=0;
	final private int WHITE = 1, GRAY = 0;
	private Image RED;
	private Image CR1;
	private Image CR2;
	private Image CR3;
	private Image CR4;
	private Image CR5;
	private Image CR6;
	private Image CR7;
	private Image CR8;
	private Image CR31;
	private Image CR32;
	private Image CR33;
	private Image CR34;
	private static Cars mc;
	private boolean ismaking=false;
	private static Cars[] CARS=new Cars[25];
	private boolean redIsEx=false;
	private static int REDCAR=0;
	
	
	public MapPanel(){
		coo=0;
		this.addMouseMotionListener(new InnerClassMouseListener2());
		this.addMouseListener(new InnerClassMouseListener());
		Cars.Mco=0;
		Cars.McC2=0;
		Cars.McC3=0;
		Cars.McR2=0;
		Cars.McR3=0;
		this.setLayout(null);
		initCa();
		initTestMap();
		//////put Cars;
		CR1 = Toolkit.getDefaultToolkit().getImage("Images\\car2c1.jpg");
		CR2 = Toolkit.getDefaultToolkit().getImage("Images\\car2c2.jpg");
		CR3 = Toolkit.getDefaultToolkit().getImage("Images\\car2c3.jpg");
		CR4 = Toolkit.getDefaultToolkit().getImage("Images\\car2c4.jpg");
		CR5 = Toolkit.getDefaultToolkit().getImage("Images\\car2c5.jpg");
		CR6 = Toolkit.getDefaultToolkit().getImage("Images\\car2c6.jpg");
		CR7 = Toolkit.getDefaultToolkit().getImage("Images\\car2c7.jpg");
		CR8 = Toolkit.getDefaultToolkit().getImage("Images\\car2c8.jpg");
		RED = Toolkit.getDefaultToolkit().getImage("Images\\Red.jpg");
		CR31 = Toolkit.getDefaultToolkit().getImage("Images\\car3r1.jpg");
		CR32 = Toolkit.getDefaultToolkit().getImage("Images\\car3r2.jpg");
		CR33 = Toolkit.getDefaultToolkit().getImage("Images\\car3r3.jpg");
		CR34 = Toolkit.getDefaultToolkit().getImage("Images\\car3r4.jpg");
	}

	public static void AddMCar(int k,int di,int le,int x,int y){
		mc=new Cars(k,di,le,x,y);
		CARS[coo++]=mc;
	}
	
	public static void initCa(){
		for(int i=0;i<6;i++){
			for(int j=0;j<6;j++){
				ca[i][j]=-1;
			}
		}
	}
	
	public void initTestMap(){
		for(int i=0;i<6;i++){
			for(int j=0;j<6;j++){
				testmap[i][j]='-';
			}
		}
	}
	
	private void DrawRect(Graphics g, int x, int y) {
		Graphics2D g2D = (Graphics2D) g;
		if (g2D == null) {
			return;
		}
		GeneralPath path = new GeneralPath();
		path.moveTo(x + 28, y);
		path.lineTo(x + 106, y + 20);
		path.lineTo(x + 74, y + 74);
		path.lineTo(x - 4, y + 52);
		path.lineTo(x + 28, y);
		g2D.fill(path); // g.draw(myPath);
	}
	
	private void drawAll(Graphics g){
		g.clearRect(0, 0, this.getWidth(), getHeight());
		g.setColor(Color.BLACK);
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 6; j++) {
				int X = INITX + 72 * j - 30 * i;
				int Y = INITY + 20 * j + 50 * i;
				if (i == 2 && j == 5) {
					g.setColor(new Color(255, 1, 1, 150));
					this.DrawRect(g, X, Y);
				} else if (park[i][j] == WHITE) {
					g.setColor(new Color(255, 220, 220, 220));
					this.DrawRect(g, X, Y);
				} else if (park[i][j] == GRAY) {
					g.setColor(new Color(255, 170, 170, 170));
					this.DrawRect(g, X, Y);
				}
			}
		}
	//	initCa();
		g.drawImage(CR1, 660, 50, this);
		g.drawImage(CR2, 660+65+15, 50, this);
		g.drawImage(CR3, 660+65*2+30, 50, this);
		g.drawImage(CR4, 660+65*3+45, 50, this);
		g.drawImage(CR5, 660, 180, this);
		g.drawImage(CR6, 660+65+15, 180, this);
		g.drawImage(CR7, 660+65*2+30, 180, this);
		g.drawImage(CR8, 660+65*3+45, 180, this);
		g.drawImage(RED, 660, 300, this);
		g.drawImage(CR31, 660, 400, this);
		g.drawImage(CR32, 660+121+15, 400, this);
		g.drawImage(CR33, 660, 480, this);
		g.drawImage(CR34, 660+121+15, 480, this);
		if(SortCars()){
		for(int i=0;i<coo;i++){
	//		ChangeArrayCa(CARS[i]);
			System.out.println(CARS[i].co);
		    Image drawcar = Toolkit.getDefaultToolkit().getImage(CARS[i].image_name);
		 //   System.out.println(CARS[i].for_draw[0]+","+CARS[i].for_draw[1]+"llll");
		    g.drawImage(drawcar, CARS[i].for_draw[0], CARS[i].for_draw[1], this);
		}
		}
		if(ismaking){
			Image drawcar = Toolkit.getDefaultToolkit().getImage(mc.image_name);
			 //   System.out.println(CARS[i].for_draw[0]+","+CARS[i].for_draw[1]+"llll");
			    g.drawImage(drawcar, mc.for_draw[0], mc.for_draw[1], this);
		}
		for(int i=0;i<6;i++){
			for(int j=0;j<6;j++){
				System.out.print(ca[i][j]+" ");
			}
			System.out.println("");
		}
	}

	private static void ChangeArrayCa(Cars myc) {
		int index = myc.co;
		if (myc.dir == Cars.dirC) {
			for (int i = myc.pos[0]; i < 6 && i < myc.pos[0] + myc.movlength; i++) {
				ca[i][myc.pos[1]] = index;
			}
		} else {
			for (int i = myc.pos[1]; i < 6 && i < myc.pos[1] + myc.movlength; i++) {
				ca[myc.pos[0]][i] = index;
			}
		}
	};
	
	private double max(double d, double e) {
		return d < e ? e : d;
	}

	private double min(double d, double e) {
		return d > e ? e : d;
	}
	
	private boolean SortCars() {
		if (coo == 0) {
			return false;
		}
		System.out.println("S");
		int[][] fsort = new int[coo+1][coo+1];
		int[] deco=new int[coo+1];
		int sx,sy,ex,ey;
		System.out.println(coo);
		for(int i=1;i<=coo;i++){
			Cars now=Cars.Mallcar[i];
			sx=now.pos[0];
			sy=now.pos[1];
			if(now.dir==Cars.dirC){
				ex=(int) min(5,now.pos[0]+now.movlength);
				ey=(int) min(5,now.pos[1]+1);
			}
			else{
				ex=(int) min(5,now.pos[0]+1);
				ey=(int) min(5,now.pos[1]+now.movlength);
			}
			int[] arr=new int[coo+1];
			arr[now.co]=1;
			for(int w=sx;w<=ex;w++){
				for(int j=sy;j<=ey;j++){
					if(ca[w][j]!=-1){
						//System.out.println(Cars.Mallcar[ca[w][j]].co);
						if(arr[Cars.Mallcar[ca[w][j]].co]==0){
							fsort[now.co][++fsort[now.co][0]]=Cars.Mallcar[ca[w][j]].co;
							//System.out.println(fsort[now.co][0]);
							deco[Cars.Mallcar[ca[w][j]].co]++;
							arr[Cars.Mallcar[ca[w][j]].co]=1;
						}
					}
				}
			}
		}
		Queue<Integer> sq=new LinkedList<Integer>();
		for(int i=1;i<=coo;i++){
			if(deco[i]==0){
				sq.offer(i);
			}
		}
		int cc=0;
		while(!sq.isEmpty()){
			int ee=sq.poll();
			CARS[cc++]=Cars.Mallcar[ee];
			for(int i=1;i<=fsort[ee][0];i++){
				if(deco[fsort[ee][i]]>0){
					deco[fsort[ee][i]]--;
					if(deco[fsort[ee][i]]==0){
						sq.offer(fsort[ee][i]);
					}
				}
			}
		}
		return true;
	}

	static public void changeTestMap(){
		int cc=0;
		int[] arr = new int[25];
		for(int i=0;i<6;i++){
			for(int j=0;j<6;j++){
				if(ca[i][j]==-1){
					testmap[i][j]='-';
				}
				if(ca[i][j]!=-1){
					if(ca[i][j]==REDCAR){
						testmap[i][j]='X';
					}
					else{
						testmap[i][j]=(char) ('a'+(ca[i][j]-2));
					}
				}
			}
		}
	}
	
	
	
	
	
	
	
	
	
	class InnerClassMouseListener extends MouseAdapter {
		public void mousePressed(MouseEvent e) {
			System.out.println(e.getX()+","+e.getY());
			if(e.getX()>660&&e.getX()<660+65&&e.getY()>50&&e.getY()<133){
				MapPanel.AddMCar(1,Cars.dirC,2,e.getX(),e.getY());
				ismaking=true;
				System.out.println(mc.image_name);
				repaint();
			}
			if(e.getX()>660+65+15&&e.getX()<660+65*2+15&&e.getY()>50&&e.getY()<133){
				MapPanel.AddMCar(2,Cars.dirC,2,e.getX(),e.getY());
				ismaking=true;
				System.out.println(mc.image_name);
				repaint();
			}
			if(e.getX()>660+65*2+30&&e.getX()<660+65*3+30&&e.getY()>50&&e.getY()<133){
				MapPanel.AddMCar(3,Cars.dirC,2,e.getX(),e.getY());
				ismaking=true;
				System.out.println(mc.image_name);
				repaint();
			}
			if(e.getX()>660+65*3+45&&e.getX()<660+65*4+45&&e.getY()>50&&e.getY()<133){
				MapPanel.AddMCar(4,Cars.dirC,2,e.getX(),e.getY());
				ismaking=true;
				System.out.println(mc.image_name);
				repaint();
			}
			if(e.getX()>660&&e.getX()<660+65&&e.getY()>180&&e.getY()<263){
				MapPanel.AddMCar(5,Cars.dirC,2,e.getX(),e.getY());
				ismaking=true;
				System.out.println(mc.image_name);
				repaint();
			}
			if(e.getX()>660+65+15&&e.getX()<660+65*2+15&&e.getY()>180&&e.getY()<263){
				MapPanel.AddMCar(6,Cars.dirC,2,e.getX(),e.getY());
				ismaking=true;
				System.out.println(mc.image_name);
				repaint();
			}
			if(e.getX()>660+65*2+30&&e.getX()<660+65*3+30&&e.getY()>180&&e.getY()<263){
				MapPanel.AddMCar(7,Cars.dirC,2,e.getX(),e.getY());
				ismaking=true;
				System.out.println(mc.image_name);
				repaint();
			}
			if(e.getX()>660+65*3+45&&e.getX()<660+65*4+45&&e.getY()>180&&e.getY()<263){
				MapPanel.AddMCar(8,Cars.dirC,2,e.getX(),e.getY());
				ismaking=true;
				System.out.println(mc.image_name);
				repaint();
			}
			
			if(!redIsEx&&e.getX()>660&&e.getX()<660+86&&e.getY()>300&&e.getY()<368){
				MapPanel.AddMCar(25,Cars.dirR,2,e.getX(),e.getY());
				redIsEx=true;
				ismaking=true;
				System.out.println(mc.image_name);
				repaint();
			}
			
			if(e.getX()>660&&e.getX()<791&&e.getY()>400&&e.getY()<477){
				MapPanel.AddMCar(1,Cars.dirR,3,e.getX(),e.getY());
				ismaking=true;
				System.out.println(mc.image_name);
				repaint();
			}
			
			if(e.getX()>791+15&&e.getX()<791+15+121&&e.getY()>400&&e.getY()<477){
				MapPanel.AddMCar(2,Cars.dirR,3,e.getX(),e.getY());
				ismaking=true;
				System.out.println(mc.image_name);
				repaint();
			}
			
			if(e.getX()>660&&e.getX()<791&&e.getY()>480&&e.getY()<557){
				MapPanel.AddMCar(3,Cars.dirR,3,e.getX(),e.getY());
				ismaking=true;
				System.out.println(mc.image_name);
				repaint();
			}
			
			if(e.getX()>791+15&&e.getX()<791+121+15&&e.getY()>480&&e.getY()<557){
				MapPanel.AddMCar(4,Cars.dirR,3,e.getX(),e.getY());
				ismaking=true;
				System.out.println(mc.image_name);
				repaint();
			}
			if(e.getButton()==MouseEvent.BUTTON3){
				System.out.println("kk");
				System.out.println(mc.image_name.charAt(12));
				if(mc.image_name!="Images\\Red.png"){
			   	if(mc.image_name.charAt(11)=='r'){
					mc.image_name=mc.image_name.substring(0, 11)+'c'+mc.image_name.substring(12);
					mc.dir=Cars.dirC;
				}
				else{
					mc.image_name=mc.image_name.substring(0, 11)+'r'+mc.image_name.substring(12);
					mc.dir=Cars.dirR;
				}
				}
				repaint();
			}
			System.out.println(mc.image_name);
		}

		public void mouseReleased(MouseEvent e) {
			if(ismaking&&e.getButton()==MouseEvent.BUTTON1){
			double xx,yy;
			yy=mc.for_draw[1]+mc.offsetY[0];
			if(mc.dir==Cars.dirC&&mc.length==2){
			xx=mc.for_draw[0]+mc.offsetX[0];
			}
			else if(mc.dir==Cars.dirC&&mc.length==3){
				xx=mc.for_draw[0]+mc.offsetX[1];	
			}
			else if(mc.dir==Cars.dirR&&mc.length==2){
				xx=mc.for_draw[0]+mc.offsetX[2];
			}
			else{
				xx=mc.for_draw[0]+mc.offsetX[3];
			}
			if((18/5.0*yy-xx-123)/210-0.5<-0.5||(18/5.0*yy-xx-123)/210-0.5>5){
				Cars.Mco--;
				coo--;

				System.out.println("false");
				if(mc.image_name=="Images\\Red.png"){
					redIsEx=false;
				}
				mc=null;
			}
			else if((5/3.0*xx+5/3.0*yy-355)/140>=0&&(5/3.0*xx+yy-355)/140-0.5<=5){
				mc.pos[1]=(int)(int)(((5/3.0*xx+yy-355)/140)+0.5);
				if(mc.pos[1]>5){
					mc.pos[1]=5;
				}
				mc.pos[0]=(int)((18/5.0*yy-xx-123)/210+0.5);
				if(mc.length==2&&mc.dir==Cars.dirC){
				if(mc.pos[0]>4){
					mc.pos[0]=4;
				}
				}
				else if(mc.length==2&&mc.dir==Cars.dirR){
					if(mc.pos[1]>4){
						mc.pos[1]=4;
					}
				}
				else if(mc.length==3&&mc.dir==Cars.dirC){
					if(mc.pos[0]>3){
						mc.pos[0]=3;
					}
				}
				else if(mc.length==3&&mc.dir==Cars.dirR){
					if(mc.pos[1]>3){
						mc.pos[1]=3;
					}
				}
				boolean ok=true;
				if(mc.dir==Cars.dirC){
				for(int i=mc.pos[0];i<=mc.pos[0]+mc.length-1&&i<6;i++){
					if(ca[i][mc.pos[1]]!=-1){
						ok=false;
					}
				}
				}
				else{
					System.out.println("zz");
					for(int i=mc.pos[1];i<=mc.pos[1]+mc.length-1&&i<6;i++){
						if(ca[mc.pos[0]][i]!=-1){
							ok=false;
						}
					}
				}
				if(ok){
				mc.movpos[0]=mc.pos[0];
				mc.movpos[1]=mc.pos[1];
				mc.DrawTheCar();
				initCa();
				for(int i=0;i<coo;i++){
					ChangeArrayCa(CARS[i]);
				}
				if(mc.image_name=="Images\\Red.png"){
					REDCAR=mc.co;
				}
				}else{
					if(mc.image_name=="Images\\Red.png"){
						redIsEx=false;
					}
					Cars.Mco--;
					coo--;
					mc=null;
					System.out.println("false3");	
				}
			}
			else if(ismaking){
				Cars.Mco--;
				coo--;
				if(mc.image_name=="Images\\Red.png"){
					redIsEx=false;
				}
				mc=null;
				System.out.println("false2");
			}
	//		System.out.println(xx+","+yy);
	//		System.out.println((((18/5.0*yy-xx-123)/210)+","+(5/3.0*xx+yy-355)/140));
	//		System.out.println((int)((18/5.0*yy-xx-123)/210+0.5)+","+(int)(((5/3.0*xx+yy-355)/140)+0.5));
	//		System.out.println(mc.pos[0]+","+mc.pos[1]);
			//mc=null;
			ismaking=false;
			repaint();
			}
		}
	}

	class InnerClassMouseListener2 extends MouseMotionAdapter {
		public void mouseDragged(MouseEvent e) {
					// TODO Auto-generated method stub
					System.out.println(e.getX()+","+e.getY());
					if(ismaking)
				    mc.ChangeDraw(e.getX(), e.getY());
				//    System.out.println(mc.for_draw[0]+","+mc.for_draw[1]);
				    repaint();
		}
	}

	
	
	
	
	
	public void paintComponent(Graphics g) {
		drawAll(g);
	}
}
