package Main;

import java.awt.*;
import javax.swing.*;
import java.util.Arrays;
import java.util.Comparator;;

public class Cars{

    final public int []offsetY={42,42};
    final public int []offsetX={30,60,2,0};//c2,c3,r2,r3
	final static int dirC=1,dirR=0;
	final static int maxn=21;
    static final Cars[] MCARS = null;
	static Cars[] allcar=new Cars[maxn];
	static Cars[] Mallcar=new Cars[maxn];
	public int length;//车长
	public int movlength;//扩展车长
	public int dir;//车的方向
	public int id;
	public int co;
	static public int count = 0,cC2=0,cR2=0,cC3=0,cR3=0;
	static public int Mco=0,McC2=0,McR2=0,McC3=0,McR3=0;
	public int []pos={0,0};
	public int []movpos={0,0};
	public int []for_draw={0,0};
	public String image_name;
	public Cars(int le,int di,int x,int y){
		image_name="x";
		length=le;
		movlength=le;
		dir=di;
		pos[0]=x;
		pos[1]=y;
		movpos[0]=x;
		movpos[1]=y;
		for_draw[0]=0;
		for_draw[1]=0;
		co=++count;
		allcar[count]=this;
		if(di==dirC&&le==2){
			id=++cC2;
		}
		else if(di==dirR&&le==2){
			id=++cR2;
		}
		else if(di==dirC&&le==3){
			id=++cC3;
		}
		else if(di==dirR&&le==3){
			id=++cR3;
		}
		DrawTheCar();
	}
	public Cars(){
		length=0;
		movlength=0;
		dir=0;
		co=0;
		id=0;
	}
	public Cars(int iid,int dirr,int le,int xx,int yy){
		image_name="Images\\car"+le;
		length=le;
		movlength=le;
		dir=dirr;
		pos[0]=0;
		pos[1]=0;
		movpos[0]=0;
		movpos[1]=0;
		for_draw[0]=xx-40;
		for_draw[1]=yy-40;
		co=++Mco;
		Mallcar[Mco]=this;
		if(dirr==dirC&&le==2){
			id=++McC2;
			image_name+='c';
			image_name+=iid;
		}
		else if(dirr==dirR&&le==2){
			id=++McR2;
			image_name+='r';
			image_name+=iid;
		}
		else if(dirr==dirC&&le==3){
			id=++McC3;
			image_name+='c';
			image_name+=iid;
		}
		else if(dirr==dirR&&le==3){
			id=++McR3;
			image_name+='r';
			image_name+=iid;
		}
		image_name+=".png";
		if(iid==25){
			image_name="Images\\Red.png";
		}
	}
	
	public void ChangeDraw(int xx, int yy){
		for_draw[0]=xx-40;
		for_draw[1]=yy-40;
	}
	
	public void DrawTheCar(){
		if(Mco==0&&co==1){
			image_name="Images\\Red.png";
		}
	    if(image_name=="x"){
			image_name="Images\\car";
			image_name+=length;
			if(dir==dirC){
				image_name+='c';
			}																																		
			else if(dir==dirR){
				image_name+='r';
			}
			if(length==2){
				image_name+=(id%8)+1;
			}
			else{
				image_name+=(id%4)+1;
			}
			image_name+=".png";
		}
		GetDrawPos(movpos[0],movpos[1]);
	}
	
	public void GetDrawPos(int px,int py){
		int X=165+py*72-px*30;
		int Y=80+py*20+px*50;
		if(dir==dirC&&length==2){
			X-=offsetX[0];
			Y-=offsetY[0];
		}
		else if(dir==dirC&&length==3){
			X-=offsetX[1];
			Y-=offsetY[0];
		}
		else if(dir==dirR&&length==2){
			X-=offsetX[2];
			Y-=offsetY[1];
		}
		else{
			X-=offsetX[3];
			Y-=offsetY[1];
		}
		for_draw[0]=X;
		for_draw[1]=Y;
		//修改数组
	}
}