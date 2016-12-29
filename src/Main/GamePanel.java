//Draw Maps, implements Listeners and checkers, repaint after every movement.
package Main;

import Abstractions.*;
import Abstractions.Action;

import java.awt.*;
import java.util.Queue;
import java.util.Stack;
import java.awt.event.*;
import java.awt.geom.GeneralPath;
import java.util.Arrays;
import java.util.LinkedList;

import javax.swing.*;

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.io.*;
//Maps and cars are shown here. it should be placed at BorderLayout.CENTER in MainFrame
//For Drawing purposes. Solving Board are located in Abstractions.Board.
public class GamePanel extends JPanel {
	final private int INITX = 165, INITY = 80;
	private int[][] park = { { 0, 1, 0, 1, 0, 1 }, { 1, 0, 1, 0, 1, 0 }, { 0, 1, 0, 1, 0, 1 }, { 1, 0, 1, 0, 1, 0 },
			{ 0, 1, 0, 1, 0, 1 }, { 1, 0, 1, 0, 1, 0 }, };
	private static int[][] ca = { { -1, -1, -1, -1, -1, -1 }, { -1, -1, -1, -1, -1, -1 }, { -1, -1, -1, -1, -1, -1 },
			{ -1, -1, -1, -1, -1, -1 }, { -1, -1, -1, -1, -1, -1 }, { -1, -1, -1, -1, -1, -1 }, };

	public static boolean isenabled;
	private int[][] mapformove = new int[650][650];
	private int cposx, cposy;
	static public int CARCOUNT=7;
	private static Cars[] mycars = new Cars[21];
	static int mousex;
	static int mousey;
	private int of, oe;
	final private int WHITE = 1, GRAY = 0;
	private int movingcar;
	int isoverhalfx, isoverhalfy;
	static char[][] testmap = { { 'a', 'a', 'b', 'c', '-', '-' }, { 'd', '-', 'b', 'c', '-', '-' },
			{ 'd', 'X', 'X', 'c', '-', '-' }, { 'd', 'e', 'e', 'e', '-', '-' }, { '-', '-', '-', '-', '-', '-' },
			{ '-', '-', '-', 'f', 'f', 'f' }, };
	static int[] link={2,3,4,5,6,7,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}; 
	// CYJ 2016/12/18 changed "Iswin" "Drawpark" functions, member of the class
	public boolean IsWin() {
		boolean isWin = false;
		if (ca[2][5] == 1) {
			isWin = true;
			Cars.allcar[1].pos[0]=2;
			Cars.allcar[1].pos[1]=4;
			Cars.allcar[1].movpos[0]=2;
			Cars.allcar[1].movpos[1]=4;
		}
		return isWin;
	}

	private void InitMoveMap() {
		for (int i = 0; i < 650; i++) {
			for (int j = 0; j < 650; j++) {
				mapformove[i][j] = 0;
			}
		}
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
					if(ca[i][j]==1){
						testmap[i][j]='X';
					}
					else if(arr[ca[i][j]]==0){
						arr[ca[i][j]]=++cc;
						testmap[i][j]=(char) ('a'+(cc-1));
					}
					else{
						testmap[i][j]=(char) ('a'+(cc-1));
					}
				}
			}
		}
	}
	
	
	private void DrawPark(Graphics g) {
		IsWin();
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

		//if(MainFrameFuncPanel.isReplay)
	    // 	reply();
		if (SortCars() == true) {
			InitMoveMap();
			for (int i = 0; i < CARCOUNT; i++) {
				ChangeMap(mycars[i]);
				String im_name = mycars[i].image_name;
				Image drawcar = Toolkit.getDefaultToolkit().getImage(im_name);
				g.drawImage(drawcar, mycars[i].for_draw[0], mycars[i].for_draw[1], this);
				mycars[i].DrawTheCar();
			}
		}
	}

	private static void InitCa() {
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 6; j++) {
				ca[i][j] = -1;
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

	public GamePanel() {
		loadFileMap(testmap);
		this.addMouseListener(new InnerClassMouseListener());
		this.addMouseMotionListener(new InnerClassMouseListener2());
		repaint();
	}

	private boolean SortCars() {
		if (mycars.length == 0) {
			return false;
		}
		int[][] fsort = new int[CARCOUNT+1][CARCOUNT+1];
		int[] deco=new int[CARCOUNT+1];
		int sx,sy,ex,ey;
		for(int i=1;i<=CARCOUNT;i++){
			Cars now=Cars.allcar[i];
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
			int[] arr=new int[CARCOUNT+1];
			arr[now.co]=1;
			for(int w=sx;w<=ex;w++){
				for(int j=sy;j<=ey;j++){
					if(ca[w][j]!=-1){
						//System.out.println(Cars.allcar[ca[w][j]].co);
						if(arr[Cars.allcar[ca[w][j]].co]==0){
							fsort[now.co][++fsort[now.co][0]]=Cars.allcar[ca[w][j]].co;
							//System.out.println(fsort[now.co][0]);
							deco[Cars.allcar[ca[w][j]].co]++;
							arr[Cars.allcar[ca[w][j]].co]=1;
						}
					}
				}
			}
		}
		Queue<Integer> sq=new LinkedList<Integer>();
		for(int i=1;i<=CARCOUNT;i++){
			if(deco[i]==0){
				sq.offer(i);
			}
		}
		int cc=0;
		while(!sq.isEmpty()){
			int ee=sq.poll();
			mycars[cc++]=Cars.allcar[ee];
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

	private static boolean AddCar(Cars a) {
		if (mycars.length == 0) {
			return false;
		}
		int index = Cars.count - 1;
		mycars[index] = a;
		ChangeArrayCa(a);
		return true;
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

	/*
	private void reply(){
		MainFrameFuncPanel.replay = new Stack<Action>();
		while(!MainFrameFuncPanel.prevstep.empty()){
			MainFrameFuncPanel.replay.push(MainFrameFuncPanel.prevstep.peek());
			Action a = MainFrameFuncPanel.prevstep.peek();
			a.toPrevStep();
			MainFrame.carpark.MoveCar(a);
			System.out.println(a.toString());
			MainFrameFuncPanel.prevstep.pop();
		}
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		while(!MainFrameFuncPanel.replay.empty()){
			Action a = MainFrameFuncPanel.replay.peek();
			a.toPrevStep();
			System.out.println(a.toString());
			
			MainFrame.carpark.MoveCar(a);
		//	try {
	//			Thread.sleep(1000);
		//	} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
	//			e1.printStackTrace();
	//		}
			MainFrameFuncPanel.replay.pop();
			MainFrameFuncPanel.prevstep.push(a);
		}
		MainFrameFuncPanel.isReplay=false;
	}
	*/
	//////////////////////////////////////////////////////////
	// functions for car_movement
	public void MoveCar(Action a){
		if(a.getBlock()!='?'){
		int index=0;
		if(a.getBlock()=='X'){
			index=CARCOUNT-1;
		}
		else{
			index=(int)(a.getBlock()-'a');
		}
		System.out.println(a.getBlock()+","+a.getMoves());
		Cars ne=Cars.allcar[link[index]];
		System.out.println(ne.pos[0]+","+ne.pos[1]);
		if(ne.dir==Cars.dirR){
			ne.pos[1]+=a.getMoves();
			ne.movpos[1]=ne.pos[1];
		}
		else{
			ne.pos[0]+=a.getMoves();
			ne.movpos[0]=ne.pos[0];
		}
		System.out.println(ne.pos[0]+","+ne.pos[1]);
		ne.DrawTheCar();
		}
		InitCa();
		for (int i = 1; i <= Cars.count; i++) {
			ChangeArrayCa(Cars.allcar[i]);
		}
		for(int i=0;i<6;i++){
			for(int j=0;j<6;j++){
				System.out.print(ca[i][j]+" ");
			}
			System.out.println("");
		}
		//if(!MainFrameFuncPanel.isReplay)
		    repaint();
	}
	static void loadFileMap(char[][] filemap) {
		Cars.count=0;
		Cars.cC2=0;
		Cars.cR2=0;
		Cars.cC3=0;
		Cars.cR3=0;
		InitCa();
		System.out.println(CARCOUNT);
		int[][] isarr = new int[6][6];
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 6; j++) {
				if (filemap[i][j] == 'X') {
					isarr[i][j] = 1;
					isarr[i][j + 1] = 1;
					AddCar(new Cars(2, Cars.dirR, i, j));
					link[CARCOUNT-1]=1;
					break;
				}
			}
		}
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 6; j++) {
				if (filemap[i][j] != '-' && isarr[i][j] == 0) {
					if (j + 1 < 6 && filemap[i][j + 1] == filemap[i][j]) {
						isarr[i][j] = 1;
						isarr[i][j + 1] = 1;
						if (j + 2 < 6 && filemap[i][j + 2] == filemap[i][j]) {
							isarr[i][j + 2] = 1;
							AddCar(new Cars(3, Cars.dirR, i, j));
							link[(int)(filemap[i][j]-'a')]=Cars.count;
						} else {
							AddCar(new Cars(2, Cars.dirR, i, j));
							link[(int)(filemap[i][j]-'a')]=Cars.count;
						}
					} else if (i + 1 < 6 && filemap[i][j] == filemap[i + 1][j]) {
						isarr[i + 1][j] = 1;
						isarr[i][j] = 1;
						if (i + 2 < 6 && filemap[i + 2][j] == filemap[i][j]) {
							isarr[i + 2][j] = 1;
							AddCar(new Cars(3, Cars.dirC, i, j));
							link[(int)(filemap[i][j]-'a')]=Cars.count;
						} else {
							AddCar(new Cars(2, Cars.dirC, i, j));
							link[(int)(filemap[i][j]-'a')]=Cars.count;
						}
					}
				}
			}
		}
		for(int i=0;i<6;i++){
			for(int j=0;j<6;j++){
				System.out.print(filemap[i][j]+" ");
			}
			System.out.println("");
		}
	}

	private void ChangeMap(Cars mc) {
		int x = 0, y = 0;
		for (int i = 0; i < mc.length; i++) {
			if (mc.dir == Cars.dirC) {
				x = 165 + mc.pos[1] * 72 - (mc.pos[0] + i) * 30;
				y = 80 + mc.pos[1] * 20 + (mc.pos[0] + i) * 50;
			} else {
				x = 165 + (mc.pos[1] + i) * 72 - mc.pos[0] * 30;
				y = 80 + (mc.pos[1] + i) * 20 + mc.pos[0] * 50;
			}
			for (double j = max(x - 4, 0); j <= x + 106; j++) {
				for (double k = max(y - 50, 0); k <= y + 120; k++) {
					if (k <= min((j - x) * 0.256 + 53.26 + y, (j - x) * (-1.6875) + 176.875 + y)
							&& k >= max((j - x) * 0.256 - 3.59 - 43 + y, (j - x) * (-1.6875) + 4.25 + y)) {
						if ((int) (j) < 650 && (int) (j) > 0 && (int) (k) < 650 && (int) (k) > 0)
							mapformove[(int) (j)][(int) (k)] = mc.co;
					}
				}
			}
		}
	}

	private double max(double d, double e) {
		return d < e ? e : d;
	}

	private double min(double d, double e) {
		return d > e ? e : d;
	}

	class InnerClassMouseListener extends MouseAdapter {
		public void mousePressed(MouseEvent e) {
			if(!isenabled)return; 
			if (e.getX() < 650 && e.getY() < 650) {
				movingcar = mapformove[e.getX()][e.getY()];
			}
			if (movingcar > 0) {
				mousex = e.getX();
				mousey = e.getY();
				cposx = Cars.allcar[movingcar].pos[0];
				cposy = Cars.allcar[movingcar].pos[1];
				isoverhalfx = 0;
				isoverhalfy = 0;
				of = -1;
				oe = 6;
				if (Cars.allcar[movingcar].dir == Cars.dirC) {
					for (int i = cposx - 1; i >= 0; i--) {
						if (ca[i][cposy] != -1) {
							of = i;
							break;
						}
					}
					for (int i = cposx + Cars.allcar[movingcar].length; i < 6; i++) {
						if (ca[i][cposy] != -1) {
							oe = i;
							break;
						}
					}
				} else {
					for (int i = cposy - 1; i >= 0; i--) {
						if (ca[cposx][i] != -1) {
							of = i;
							break;
						}
					}
					for (int i = cposy + Cars.allcar[movingcar].length; i < 6; i++) {
						if (ca[cposx][i] != -1) {
							oe = i;
							break;
						}
					}
				}
				Cars.allcar[movingcar].movlength = Cars.allcar[movingcar].length + 1;
			}
		}

		public void mouseReleased(MouseEvent e) {
			if(!isenabled) return;
			if (movingcar > 0) {
				Cars.allcar[movingcar].movpos[0] += isoverhalfx;
				Cars.allcar[movingcar].movpos[1] += isoverhalfy;
				Cars.allcar[movingcar].pos[0] = Cars.allcar[movingcar].movpos[0];
				Cars.allcar[movingcar].pos[1] = Cars.allcar[movingcar].movpos[1];
				Cars.allcar[movingcar].movlength = Cars.allcar[movingcar].length;
				Cars.allcar[movingcar].DrawTheCar();
				InitCa();
				for (int i = 1; i <= Cars.count; i++) {
					ChangeArrayCa(Cars.allcar[i]);
				}
				char tt=' ';
				for(int i=0;i<CARCOUNT;i++){
					if(link[i]==movingcar){
						tt=(char) ('a'+i);
						if(i==CARCOUNT-1){
							tt='X';
						}
					}
				}
				if(Cars.allcar[movingcar].dir==Cars.dirC){
					MainFrameFuncPanel.pushStepToStack(new Action(tt,Cars.allcar[movingcar].pos[0]-cposx));
					System.out.println(Cars.allcar[movingcar].pos[0]+","+Cars.allcar[movingcar].pos[1]);
				}
				else{
					MainFrameFuncPanel.pushStepToStack(new Action(tt,Cars.allcar[movingcar].pos[1]-cposy));
					System.out.println(Cars.allcar[movingcar].pos[0]+","+Cars.allcar[movingcar].pos[1]);
				}
				
				repaint();
			}
		}
	}

	class InnerClassMouseListener2 extends MouseMotionAdapter {
		public void mouseDragged(MouseEvent e) {
			if(!isenabled)return;
			if (movingcar > 0) {
				Cars moving = Cars.allcar[movingcar];
				double cx = 0, cy = 0;
				int ex = e.getX(), ey = e.getY();
				cx = ex - mousex;
				cy = ey - mousey;
				Cars.allcar[movingcar].movlength = Cars.allcar[movingcar].length + 1;
				if (moving.dir == Cars.dirR) {
					if (cx < 0 && Math.abs(cx) <= (moving.movpos[1]) * 75
							|| cx > 0 && cx <= (6 - moving.movpos[1] - moving.length) * 75) {
						if (cx > 0 && moving.movpos[1] + (int) (cx / 75) + moving.length >= oe) {
							Cars.allcar[movingcar].movlength = Cars.allcar[movingcar].length;
							moving.GetDrawPos(moving.movpos[0], oe - moving.length);
							moving.pos[1] = oe - moving.length;
							isoverhalfy = oe - moving.length - moving.movpos[1];
						} else if (cx < 0 && moving.movpos[1] + (int) (cx / 75) - 1 <= of) {
							Cars.allcar[movingcar].movlength = Cars.allcar[movingcar].length;
							moving.GetDrawPos(moving.movpos[0], of + 1);
							moving.pos[1] = of + 1;
							isoverhalfy = of + 1 - moving.movpos[1];
						} else {
							Cars.allcar[movingcar].movlength = Cars.allcar[movingcar].length + 1;
							moving.GetDrawPos(moving.movpos[0], moving.movpos[1]);
							moving.for_draw[0] += cx;
							moving.for_draw[1] += cx * 0.275;
							if (cx > 0) {
								moving.pos[1] = (int) (cx / 75) + cposy;
							} else {
								if (cx / 75 - (int) (cx / 75) < 0) {
									moving.pos[1] = (int) (cx / 75) + cposy - 1;
								} else {
									moving.pos[1] = (int) (cx / 75) + cposy;
								}
							}
							isoverhalfy = (int) (cx / 75);
							if (cx < 0 && Math.abs((cx / 75) - (double) (int) (cx / 75)) > 0.5) {
								isoverhalfy -= 1;
							} else if (cx > 0 && Math.abs((cx / 75) - (double) (int) (cx / 75)) > 0.5) {
								isoverhalfy += 1;
							}
						}
					} else {
						Cars.allcar[movingcar].movlength = Cars.allcar[movingcar].length;
						if (cx > 0) {
							moving.GetDrawPos(moving.movpos[0], oe - moving.length);
							moving.pos[1] = oe - moving.length;
							isoverhalfy = oe - moving.length - moving.movpos[1];
						} else if (cx < 0) {
							moving.GetDrawPos(moving.movpos[0], of + 1);
							moving.pos[1] = of + 1;
							isoverhalfy = of + 1 - moving.movpos[1];
						}
					}
				} else {
					if (cy < 0 && Math.abs(cy) <= (moving.movpos[0]) * 52
							|| cy > 0 && cy <= (6 - moving.movpos[0] - moving.length) * 52) {
						if (cy > 0 && moving.movpos[0] + (int) (cy / 52) + moving.length >= oe) {
							Cars.allcar[movingcar].movlength = Cars.allcar[movingcar].length;
							moving.GetDrawPos(oe - moving.length, moving.movpos[1]);
							moving.pos[0] = oe - moving.length;
							isoverhalfx = oe - moving.length - moving.movpos[0];
						} else if (cy < 0 && moving.movpos[0] + (int) (cy / 52) - 1 <= of) {
							Cars.allcar[movingcar].movlength = Cars.allcar[movingcar].length;
							moving.GetDrawPos(of + 1, moving.movpos[1]);
							moving.pos[0] = of + 1;
							isoverhalfx = of + 1 - moving.movpos[0];
						} else {
							moving.GetDrawPos(moving.movpos[0], moving.movpos[1]);
							moving.for_draw[1] += cy;
							moving.for_draw[0] += -cy / 1.6875;
							if (cy > 0) {
								moving.pos[0] = (int) (cy / 52) + cposx;
							} else {
								if (cy / 52 - (int) (cy / 52) < 0) {
									moving.pos[0] = (int) (cy / 52) + cposx - 1;
								} else {
									moving.pos[0] = (int) (cy / 52) + cposx;
								}
							}
							isoverhalfx = (int) (cy / 52);
							if (cy < 0 && Math.abs((cy / 52) - (double) (int) (cy / 52)) > 0.5) {
								isoverhalfx -= 1;
							} else if (cy > 0 && Math.abs((cy / 52) - (double) (int) (cy / 52)) > 0.5) {
								isoverhalfx += 1;
							}
						}
					} else {
						Cars.allcar[movingcar].movlength = Cars.allcar[movingcar].length;
						if (cy > 0) {
							moving.GetDrawPos(oe - moving.length, moving.movpos[1]);
							moving.pos[0] = oe - moving.length;
							isoverhalfx = oe - moving.length - moving.movpos[0];
						} else if (cy < 0) {
							moving.GetDrawPos(of + 1, moving.movpos[1]);
							moving.pos[0] = of + 1;
							isoverhalfx = of + 1 - moving.movpos[0];
						}
					}
				}
				InitCa();
				for (int i = 1; i <= Cars.count; i++) {
					ChangeArrayCa(Cars.allcar[i]);
				}
				repaint();
			}
		}
	}

	public void paintComponent(Graphics g) {
		DrawPark(g);
	}

}
