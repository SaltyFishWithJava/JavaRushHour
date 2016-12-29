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
    private static MessageThread messageThread;// ���������Ϣ���߳�  
    private static Map<String, UserMulti> onLineUsers = new HashMap<String, UserMulti>();// ���������û�  
    // ���췽��  
    public Client(int p, String ip, String n) {
    	connectServer(p,ip,n);
    }  
    // ִ�з���  
    public static void send(String str) {   
    	//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1
        String message = str;  
        name = UtilPanel.getcurrentuser().getName();
        sendMessage(message);  
    }  
  
    
  
    /**  
     * ���ӷ�����  
     *   
     * @param port  
     * @param hostIp  
     * @param name  
     */  
    public static boolean connectServer(int port, String hostIp, String name) {  
        // ���ӷ�����  
        try {  
            socket = new Socket(hostIp, port);// ���ݶ˿ںźͷ�����ip��������  
            writer = new PrintWriter(socket.getOutputStream());  
            reader = new BufferedReader(new InputStreamReader(socket  
                    .getInputStream()));  
            // ���Ϳͻ����û�������Ϣ(�û�����ip��ַ)  
            sendMessage(name + "@" + socket.getLocalAddress().toString());  
            // ����������Ϣ���߳�  
            messageThread = new MessageThread(reader);  
            messageThread.start();  
            isConnected = true;// �Ѿ���������  
            return true;  
        } catch (Exception e) {   
            isConnected = false;// δ������  
            JOptionPane.showMessageDialog(null, "����Ż�IP��ַ��д����", "����",  
                    JOptionPane.ERROR_MESSAGE);
            return false;  
        }  
    }  
  
    /**  
     * ������Ϣ  
     *   
     * @param message  
     */  
    public static void sendMessage(String message) {  
        writer.println(message);  
        writer.flush();  
    }  
  
    /**  
     * �ͻ��������ر�����  
     */  
    @SuppressWarnings("deprecation")  
    public static synchronized boolean closeConnection() {  
        try {  
            sendMessage("CLOSE");// ���ͶϿ����������������  
            messageThread.stop();// ֹͣ������Ϣ�߳�  
            // �ͷ���Դ  
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
  
    // ���Ͻ�����Ϣ���߳�  
    static class MessageThread extends Thread {  
        private BufferedReader reader;  

        // ������Ϣ�̵߳Ĺ��췽��  
        public MessageThread(BufferedReader reader) {  
            this.reader = reader;    
        }  
  
        // �����Ĺر�����  
        public synchronized void closeCon() throws Exception {  
            // �����Ĺر������ͷ���Դ  
            if (reader != null) {  
                reader.close();  
            }  
            if (writer != null) {  
                writer.close();  
            }  
            if (socket != null) {  
                socket.close();  
            }  
            isConnected = false;// �޸�״̬Ϊ�Ͽ�  
        }  
  
        public void run() {  
            String message = "";  
            while (true) {  
                try {  
                    message = reader.readLine();  
                    StringTokenizer stringTokenizer = new StringTokenizer(  
                            message, "/@");  
                    String command = stringTokenizer.nextToken();// ����  
                    if (command.equals("CLOSE"))// �������ѹر�����  
                    {  
                    	JOptionPane.showMessageDialog(null, "�����Ѿ���ֹ����Ϸ��", "����",  
                                JOptionPane.ERROR_MESSAGE);  
                    	UtilPanel.infoChange("Game Ended.");
                        closeCon();// �����Ĺر�����  
                        return;// �����߳�    
                    }else if(command.equals("START")){
                    	MultiFrameFuncPanel.btnStart.setEnabled(false);
            			//btnPause.setEnabled(true);
                    	MultiFrameFuncPanel.gamestarted = true;
                    	MultiFrameFuncPanel.gamepaused  = false;
                    	GamePanel.isenabled = true;
                    	UtilPanel.infoChange("Game STARTS!!!!!!!");
                    	
                    }
                    else if (command.equals("MAP")) {//��ͼ��Ϣ����
                        
                    	
                    } else if (command.equals("FINISHED")) {//������Ϸ�����Ϣ����
                    	MultiFrameFuncPanel.gamestarted = true;
            			MultiFrameFuncPanel.gamepaused= true;
            			JOptionPane.showMessageDialog(null, "�����Ѿ��������Ϸ����ʱ��" + UtilPanel.Currenttime); 
            			GamePanel.isenabled = false;
                    } else if (command.equals("MAX")) {// �����Ѵ�����  
                        closeCon();// �����Ĺر�����  
                        JOptionPane.showMessageDialog(null, "�÷����ѿ�ʼ��Ϸ���뻻һ�����䡣", "����",  
                                JOptionPane.ERROR_MESSAGE);  
                        return;// �����߳�  
                    }else if(command.equals("PREV")){//���ֳ�����һ��
                    	
                    }
                    else {// ������Ϣ  
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