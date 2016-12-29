package Abstractions;
import java.applet.AudioClip;  
import java.net.MalformedURLException;  
import java.net.URL;  
import javax.swing.JApplet;  
  
public class Music extends Thread{  
	public AudioClip christmas;
    public AudioClip loadSound(String filename) {  
        URL url = null;  
        try {  
            url = new URL("file:" + filename);  
        }   
        catch (MalformedURLException e) {;}  
        return JApplet.newAudioClip(url);  
    }  
    public Music(){
    	 christmas = loadSound("180.wav");  
    }
    public void run() {  
        christmas.loop();  
    }  
    public void play(){
    	christmas.loop();
    }
} 