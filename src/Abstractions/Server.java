package Abstractions;

import java.io.*;
import java.net.*;  
import java.util.*;
import javax.swing.*;

import Main.GamePanel;
import Main.MultiFrameFuncPanel;
import Main.UtilPanel;

public class Server{
	private static ServerSocket serverSocket;  
	private static ServerThread serverThread;  
	private static ArrayList<ClientThread> clients; 
	public static boolean isStart = false; 
	private int max = 1;
	// ����
	public Server(int port) {  
		try {
			serverStart(max, port);
		} catch (BindException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    
	} 

	// ����������  
	public static void serverStart(int max, int port) throws java.net.BindException {  
		try {  
			clients = new ArrayList<ClientThread>();  
			serverSocket = new ServerSocket(port);  
			serverThread = new ServerThread(serverSocket, max);  
			serverThread.start();  
			JOptionPane.showMessageDialog(null, "���䴴���ɹ�!");   
			isStart = true;  
		} catch (BindException e) {  
			isStart = false;  
			JOptionPane.showMessageDialog(null,"�÷�����ѱ�ʹ�ã��뻻һ������š�",  
					"����", JOptionPane.ERROR_MESSAGE); 
		} catch (Exception e1) {  
			e1.printStackTrace();  
			isStart = false;  
			throw new BindException("�����������쳣��");  
		}  

	}  
	// ִ����Ϣ����  
	public static void send(String str) {  
		String message = str;
		sendServerMessage(message);
	}  

	// Ⱥ����������Ϣ  
	public static void sendServerMessage(String message) {  
		for (int i = clients.size() - 1; i >= 0; i--) {  
			clients.get(i).getWriter().println(message);  
			clients.get(i).getWriter().flush();  
		}  
	}  

	// �رշ�����  
	@SuppressWarnings("deprecation")  
	public static void closeServer() {  
		try {  
			if (serverThread != null)  
				serverThread.stop();// ֹͣ�������߳�  

			for (int i = clients.size() - 1; i >= 0; i--) {  
				// �����������û����͹ر�����  
				clients.get(i).getWriter().println("CLOSE");  
				clients.get(i).getWriter().flush();  
				// �ͷ���Դ  
				clients.get(i).stop();// ֹͣ����Ϊ�ͻ��˷�����߳�  
				clients.get(i).reader.close();  
				clients.get(i).writer.close();  
				clients.get(i).socket.close();  
				clients.remove(i);  
			}  
			if (serverSocket != null) {  
				serverSocket.close();// �رշ�����������  
			}  
			isStart = false;  
		} catch (IOException e) {  
			e.printStackTrace();  
			isStart = true;  
		}  
	}  

	// �������߳�  
	static class ServerThread extends Thread {  
		private ServerSocket serverSocket;  
		private int max = 1;// ��������  

		// �������̵߳Ĺ��췽��  
		public ServerThread(ServerSocket serverSocket, int max) {  
			this.serverSocket = serverSocket;  
			this.max = max;  
		}  
		public void run() {  
			while (true) {// ��ͣ�ĵȴ��ͻ��˵�����  
				try {  
					Socket socket = serverSocket.accept();  
					if (clients.size() == max) {// ����Ѵ���������  
						BufferedReader r = new BufferedReader(  
								new InputStreamReader(socket.getInputStream()));  
						PrintWriter w = new PrintWriter(socket  
								.getOutputStream());  
						// ���տͻ��˵Ļ����û���Ϣ  
						String inf = r.readLine();  
						StringTokenizer st = new StringTokenizer(inf, "@");  
						UserMulti user = new UserMulti(st.nextToken(), st.nextToken());  
						// �������ӳɹ���Ϣ  
						w.println("MAX@���������Բ���" + user.getName()  
						+ user.getIp() + "�����������������Ѵ����ޣ����Ժ������ӣ�");  
						w.flush();  
						// �ͷ���Դ  
						r.close();  
						w.close();  
						socket.close();  
						continue;  
					}  
					ClientThread client = new ClientThread(socket);  
					client.start();// �����Դ˿ͻ��˷�����߳�  
					clients.add(client);  
				} catch (IOException e) {  
					e.printStackTrace();  
				}  
			}  
		}  
	}  

	// Ϊһ���ͻ��˷�����߳�  
	static class ClientThread extends Thread {  
		private Socket socket;  
		private BufferedReader reader;  
		private PrintWriter writer;  
		private UserMulti user;  

		public BufferedReader getReader() {  
			return reader;  
		}  

		public PrintWriter getWriter() {  
			return writer;  
		}  

		public UserMulti getUser() {  
			return user;  
		}  

		// �ͻ����̵߳Ĺ��췽��  
		public ClientThread(Socket socket) {  
			try {  
				this.socket = socket;  
				reader = new BufferedReader(new InputStreamReader(socket  
						.getInputStream()));  
				writer = new PrintWriter(socket.getOutputStream());  
				// ���տͻ��˵Ļ����û���Ϣ  
				String inf = reader.readLine();  
				StringTokenizer st = new StringTokenizer(inf, "@");  
				user = new UserMulti(st.nextToken(), st.nextToken());  
				// �������ӳɹ���Ϣ  
				writer.println(user.getName() + user.getIp() + "����������ӳɹ�!");  
				writer.flush();  
				JOptionPane.showMessageDialog(null, user.getName() + "�Ѽ����˷��䡣");
				MultiFrameFuncPanel.btnStart.setEnabled(true);
				UtilPanel.infoChange("Please select a map and start the game!");
			} catch (IOException e) {  
				e.printStackTrace();  
			}  
		}  

		@SuppressWarnings("deprecation")  
		public void run() {// ���Ͻ��տͻ��˵���Ϣ�����д���  
			String message = null;  
			while (true) {  
				try { 
					message = reader.readLine();// ���տͻ�����Ϣ  
					StringTokenizer stringTokenizer = new StringTokenizer(  
                            message, "/@");  
                    String command = stringTokenizer.nextToken();// ����  
                    System.out.println(command);
                    if (command.equals("CLOSE"))//�߳��������� 
                    {  
                    	JOptionPane.showMessageDialog(null, user.getName() + "�Ѿ��˳��˷��䡣��Ϸ�жϡ�", "����",  
								JOptionPane.ERROR_MESSAGE);
						reader.close();  
						writer.close();  
						socket.close();  

						// ɾ�������ͻ��˷����߳�  
						for (int i = clients.size() - 1; i >= 0; i--) {  
							if (clients.get(i).getUser() == user) {  
								ClientThread temp = clients.get(i);  
								clients.remove(i);// ɾ�����û��ķ����߳�  
								temp.stop();// ֹͣ���������߳�  
								return;  
							}  
						}
						
                    } else if (command.equals("MAP")) {//��ͼ��Ϣ����
                        
                    } else if(command.equals("PREV")){
                    	
                    }
                     else if (command.equals("FINISHED")) {//������Ϸ�����Ϣ����
                    	MultiFrameFuncPanel.gamestarted = true;
             			MultiFrameFuncPanel.gamepaused= true;
             			JOptionPane.showMessageDialog(null, "�����Ѿ��������Ϸ����ʱ��" + UtilPanel.Currenttime);
             			GamePanel.isenabled = false;
                    } else {// ������Ϣ  
                        System.out.println(message + "\r\n");  
                    }  
				} catch (IOException e) {  
					e.printStackTrace();  
				}  
			}  
		}  
	}
}