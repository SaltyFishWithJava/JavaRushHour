//Draw Maps, implements Listeners and checkers, repaint after every movement.
package Main;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.GeneralPath;
import java.util.Arrays;

import javax.swing.*;
import java.io.*;
//Maps and cars are shown here. it should be placed at BorderLayout.CENTER in MainFrame

public class GamePanel extends JPanel {
	// 地图等待贴图
	final private int INITX = 165, INITY = 80;
	private int[][] park = { { 0, 1, 0, 1, 0, 1 }, { 1, 0, 1, 0, 1, 0 }, { 0, 1, 0, 1, 0, 1 }, { 1, 0, 1, 0, 1, 0 },
			{ 0, 1, 0, 1, 0, 1 }, { 1, 0, 1, 0, 1, 0 }, };
	private int[][] ca = { { -1, -1, -1, -1, -1, -1 }, { -1, -1, -1, -1, -1, -1 }, { -1, -1, -1, -1, -1, -1 },
			{ -1, -1, -1, -1, -1, -1 }, { -1, -1, -1, -1, -1, -1 }, { -1, -1, -1, -1, -1, -1 }, };

	private int[][] mapformove = new int[650][650];
	private int cposx, cposy;
	private int CARCOUNT=7;
	private Cars[] mycars = new Cars[CARCOUNT];
	static int mousex;
	static int mousey;
	private int of, oe;
	final private int WHITE = 1, GRAY = 0;// 暂做区分稍后修改
	private int movingcar;
	int isoverhalfx, isoverhalfy;

	private void IsWin() {
		if (ca[2][5] == 1) {
			JOptionPane.showMessageDialog(this, "                  You Win!");
		}
	}

	private void InitMoveMap() {
		for (int i = 0; i < 650; i++) {
			for (int j = 0; j < 650; j++) {
				mapformove[i][j] = 0;
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
				} else if (park[i][j] == WHITE) {// 白色空地
					g.setColor(new Color(255, 220, 220, 220));
					this.DrawRect(g, X, Y);
				} else if (park[i][j] == GRAY) {// 灰色空地
					g.setColor(new Color(255, 170, 170, 170));
					this.DrawRect(g, X, Y);
				}
			}
		}
		if (SortCars() == true) {
			InitMoveMap();
			for (int i = 0; i < mycars.length; i++) {
				ChangeMap(mycars[i]);
				String im_name = mycars[i].image_name;
				Image drawcar = Toolkit.getDefaultToolkit().getImage(im_name);
				g.drawImage(drawcar, mycars[i].for_draw[0], mycars[i].for_draw[1], this);
				mycars[i].DrawTheCar();
			}
		}
	}

	private void InitCa() {
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

	// GamePanel构造函数
	public GamePanel() {
		AddMap();
		this.addMouseListener(new InnerClassMouseListener());
		this.addMouseMotionListener(new InnerClassMouseListener2());
		repaint();
	}

	private boolean SortCars() {
		if (mycars.length == 0) {
			return false;
		}
		Arrays.sort(mycars, new CarsComparators());
		SortSort();
		return true;
	}

	private boolean AddCar(Cars a) {
		if (mycars.length == 0) {
			return false;
		}
		int index = Cars.count - 1;
		mycars[index] = a;
		// 修改数组ca
		ChangeArrayCa(a);
		return true;
	}

	private void ChangeArrayCa(Cars myc) {
		int index = myc.co;
		if (myc.dir == Cars.dirC) {
			for (int i = myc.pos[0]; i < 6 && i < myc.pos[0] + myc.length; i++) {
				ca[i][myc.pos[1]] = index;
			}
		} else {
			for (int i = myc.pos[1]; i < 6 && i < myc.pos[1] + myc.length; i++) {
				ca[myc.pos[0]][i] = index;
			}
		}
	};

	private void SortSort() {
		for (int i = 0; i < mycars.length; i++) {
			int endy, sty = mycars[i].pos[1];
			if (mycars[i].dir == Cars.dirC) {
				continue;
			} else {
				endy = sty + mycars[i].length - 1;
			}
			for (int j = i + 1; j < mycars.length; j++) {
				int xx, yy, sx = mycars[j].pos[0], sy = mycars[j].pos[1];
				if (mycars[j].dir == Cars.dirC) {
					xx = mycars[j].pos[0] + mycars[j].length - 1;
					yy = mycars[j].pos[1];
				} else {
					xx = mycars[j].pos[0];
					yy = mycars[j].pos[1] + mycars[j].length - 1;
				}
				if (xx == mycars[i].pos[0] - 1 && yy >= sty && yy <= endy
						|| ((sx == mycars[i].pos[0] - 1) && (sy >= sty) && (sy <= endy))) {
					Cars tem = mycars[i];
					for (int w = i + 1; w <= j; w++) {
						mycars[w - 1] = mycars[w];
					}
					mycars[j] = tem;
					break;
				}
			}
		}
	}

	private void AddMap() {
		// 第一个是红车
		AddCar(new Cars(2, Cars.dirR, 2, 1));// 1
		AddCar(new Cars(2, Cars.dirR, 0, 0));// 2
		AddCar(new Cars(3, Cars.dirR, 5, 2));// 3
		AddCar(new Cars(2, Cars.dirC, 4, 0));// 4
		AddCar(new Cars(3, Cars.dirC, 1, 0));// 5
		AddCar(new Cars(3, Cars.dirC, 1, 3));// 6
		AddCar(new Cars(3, Cars.dirC, 0, 5));// 7
	}

	//////////////////////////////////////////////////////////
	// functions for car_movement

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
			}
		}

		public void mouseReleased(MouseEvent e) {
			if (movingcar > 0) {
				Cars.allcar[movingcar].movpos[0] += isoverhalfx;
				Cars.allcar[movingcar].movpos[1] += isoverhalfy;
				Cars.allcar[movingcar].pos[0] = Cars.allcar[movingcar].movpos[0];
				Cars.allcar[movingcar].pos[1] = Cars.allcar[movingcar].movpos[1];
				Cars.allcar[movingcar].DrawTheCar();
				InitCa();
				for (int i = 1; i <= Cars.count; i++) {
					ChangeArrayCa(Cars.allcar[i]);
				}
				repaint();
			}
		}
	}

	class InnerClassMouseListener2 extends MouseMotionAdapter {
		public void mouseDragged(MouseEvent e) {
			if (movingcar > 0) {
				Cars moving = Cars.allcar[movingcar];
				double cx = 0, cy = 0;
				int ex = e.getX(), ey = e.getY();
				cx = ex - mousex;
				cy = ey - mousey;
				if (moving.dir == Cars.dirR) {
					if (cx < 0 && Math.abs(cx) <= (moving.movpos[1]) * 75
							|| cx > 0 && cx <= (6 - moving.movpos[1] - moving.length) * 75) {
						if (cx > 0 && moving.movpos[1] + (int) (cx / 75) + moving.length >= oe) {
							moving.GetDrawPos(moving.movpos[0], oe - moving.length);
							isoverhalfy = oe - moving.length - moving.movpos[1];
						} else if (cx < 0 && moving.movpos[1] + (int) (cx / 75) - 1 <= of) {
							moving.GetDrawPos(moving.movpos[0], of + 1);
							isoverhalfy = of + 1 - moving.movpos[1];
						} else {
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
						if (cx > 0) {
							moving.GetDrawPos(moving.movpos[0], oe - moving.length);
							isoverhalfy = oe - moving.length - moving.movpos[1];
						} else if (cx < 0) {
							moving.GetDrawPos(moving.movpos[0], of + 1);
							isoverhalfy = of + 1 - moving.movpos[1];
						}
					}
				} else {
					if (cy < 0 && Math.abs(cy) <= (moving.movpos[0]) * 52
							|| cy > 0 && cy <= (6 - moving.movpos[0] - moving.length) * 52) {
						if (cy > 0 && moving.movpos[0] + (int) (cy / 52) + moving.length >= oe) {
							moving.GetDrawPos(oe - moving.length, moving.movpos[1]);
							isoverhalfx = oe - moving.length - moving.movpos[0];
						} else if (cy < 0 && moving.movpos[0] + (int) (cy / 52) - 1 <= of) {
							moving.GetDrawPos(of + 1, moving.movpos[1]);
							isoverhalfx = of + 1 - moving.movpos[0];
						} else {
							moving.GetDrawPos(moving.movpos[0], moving.movpos[1]);
							moving.for_draw[1] += cy;
							moving.for_draw[0] += -cy / 1.6875;
							if (cy < 0) {
								moving.pos[0] = (int) (cy / 52) + cposx;
							} else {
								if (cy / 52 - (int) (cy / 52) > 0) {
									moving.pos[0] = (int) (cy / 52) + cposx + 1;
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
						if (cy > 0) {
							moving.GetDrawPos(oe - moving.length, moving.movpos[1]);
							isoverhalfx = oe - moving.length - moving.movpos[0];
						} else if (cy < 0) {
							moving.GetDrawPos(of + 1, moving.movpos[1]);
							isoverhalfx = of + 1 - moving.movpos[0];
						}
					}
				}
				repaint();
			}
		}
	}

	class InnerClassWindowListener extends WindowAdapter {

	}

	class InnerClassKeyListener extends KeyAdapter {

	}

	public void paintComponent(Graphics g) {
		DrawPark(g);
	}

}
