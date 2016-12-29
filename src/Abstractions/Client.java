package Abstractions;

import java.io.*;
import java.util.*;
import javax.swing.JOptionPane;

import Main.GamePanel;
import Main.MultiFrameFuncPanel;
import Main.UtilPanel;

import java.net.*;

public class Client{  
  
    public static boolean isConnected = false;  
  
    private static String name;
    private static Socket socket;  
    private static PrintWriter writer;  
    private static BufferedReader reader;  
    private static MessageThread messageThread;// 负责接收消息的线程  
    private static Map<String, UserMulti> onLineUsers = new HashMap<String, UserMulti>();// 所有在线用户  
    // 构造方法  
    public Client(int p, String ip, String n) {
    	connectServer(p,ip,n);
    }  
    // 执行发送  
    public static void send(String str) {   
    	//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1
        String message = str;  
        name = UtilPanel.getcurrentuser().getName();
        sendMessage(message);  
    }  
  
    
  
    /**  
     * 连接服务器  
     *   
     * @param port  
     * @param hostIp  
     * @param name  
     */  
    public static boolean connectServer(int port, String hostIp, String name) {  
        // 连接服务器  
        try {  
            socket = new Socket(hostIp, port);// 根据端口号和服务器ip建立连接  
            writer = new PrintWriter(socket.getOutputStream());  
            reader = new BufferedReader(new InputStreamReader(socket  
                    .getInputStream()));  
            // 发送客户端用户基本信息(用户名和ip地址)  
            sendMessage(name + "@" + socket.getLocalAddress().toString());  
            // 开启接收消息的线程  
            messageThread = new MessageThread(reader);  
            messageThread.start();  
            isConnected = true;// 已经连接上了  
            return true;  
        } catch (Exception e) {   
            isConnected = false;// 未连接上  
            JOptionPane.showMessageDialog(null, "房间号或IP地址填写错误。", "错误",  
                    JOptionPane.ERROR_MESSAGE);
            return false;  
        }  
    }  
  
    /**  
     * 发送消息  
     *   
     * @param message  
     */  
    public static void sendMessage(String message) {  
        writer.println(message);  
        writer.flush();  
    }  
  
    /**  
     * 客户端主动关闭连接  
     */  
    @SuppressWarnings("deprecation")  
    public static synchronized boolean closeConnection() {  
        try {  
            sendMessage("CLOSE");// 发送断开连接命令给服务器  
            messageThread.stop();// 停止接受消息线程  
            // 释放资源  
            if (reader != null) {  
                reader.close();  
            }  
            if (writer != null) {  
                writer.close();  
            }  
            if (socket != null) {  
                socket.close();  
            }  
            isConnected = false;  
            return true;  
        } catch (IOException e1) {  
            e1.printStackTrace();  
            isConnected = true;  
            return false;  
        }  
    }  
  
    // 不断接收消息的线程  
    static class MessageThread extends Thread {  
        private BufferedReader reader;  

        // 接收消息线程的构造方法  
        public MessageThread(BufferedReader reader) {  
            this.reader = reader;    
        }  
  
        // 被动的关闭连接  
        public synchronized void closeCon() throws Exception {  
            // 被动的关闭连接释放资源  
            if (reader != null) {  
                reader.close();  
            }  
            if (writer != null) {  
                writer.close();  
            }  
            if (socket != null) {  
                socket.close();  
            }  
            isConnected = false;// 修改状态为断开  
        }  
  
        public void run() {  
            String message = "";  
            while (true) {  
                try {  
                    message = reader.readLine();  
                    StringTokenizer stringTokenizer = new StringTokenizer(  
                            message, "/@");  
                    String command = stringTokenizer.nextToken();// 命令  
                    if (command.equals("CLOSE"))// 服务器已关闭命令  
                    {  
                    	JOptionPane.showMessageDialog(null, "房主已经终止了游戏。", "错误",  
                                JOptionPane.ERROR_MESSAGE);  
                    	UtilPanel.infoChange("Game Ended.");
                        closeCon();// 被动的关闭连接  
                        return;// 结束线程    
                    }else if(command.equals("START")){
                    	MultiFrameFuncPanel.btnStart.setEnabled(false);
            			//btnPause.setEnabled(true);
                    	MultiFrameFuncPanel.gamestarted = true;
                    	MultiFrameFuncPanel.gamepaused  = false;
                    	GamePanel.isenabled = true;
                    	UtilPanel.infoChange("Game STARTS!!!!!!!");
                    	
                    }
                    else if (command.equals("MAP")) {//地图消息传输
                        
                    	
                    } else if (command.equals("FINISHED")) {//单方游戏完成消息传输
                    	MultiFrameFuncPanel.gamestarted = true;
            			MultiFrameFuncPanel.gamepaused= true;
            			JOptionPane.showMessageDialog(null, "对手已经完成了游戏！用时：" + UtilPanel.Currenttime); 
            			GamePanel.isenabled = false;
                    } else if (command.equals("MAX")) {// 人数已达上限  
                        closeCon();// 被动的关闭连接  
                        JOptionPane.showMessageDialog(null, "该房间已开始游戏，请换一个房间。", "错误",  
                                JOptionPane.ERROR_MESSAGE);  
                        return;// 结束线程  
                    }else if(command.equals("PREV")){//对手撤回了一步
                    	
                    }
                    else {// 步骤消息  
                        System.out.println(message + "\r\n");  
                    }  
                } catch (IOException e) {  
                    e.printStackTrace();  
                } catch (Exception e) {  
                    e.printStackTrace();  
                }  
            }  
        }  
    }  
}  