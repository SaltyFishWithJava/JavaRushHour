package Main;

import javax.swing.JPanel;

import Abstractions.Music;
import Abstractions.Win;

public class MusicPanel extends JPanel{
	public Music ms= new Music();
	public Win wm=new Win();
	public MusicPanel(){
		ms.start();
	}
	@SuppressWarnings("static-access")
	public void win(){
		ms.christmas.stop();

		wm.christmas.play();
		try {
			Thread.sleep(9000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		wm.christmas.stop();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ms.christmas.loop();
	}
}
