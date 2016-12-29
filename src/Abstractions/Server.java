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
	// 构造
	public Server(int port) {  
		try {
			serverStart(max, port);
		} catch (BindException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    
	} 

	// 启动服务器  
	public static void serverStart(int max, int port) throws java.net.BindException {  
		try {  
			clients = new ArrayList<ClientThread>();  
			serverSocket = new ServerSocket(port);  
			serverThread = new ServerThread(serverSocket, max);  
			serverThread.start();  
			JOptionPane.showMessageDialog(null, "房间创建成功!");   
			isStart = true;  
		} catch (BindException e) {  
			isStart = false;  
			JOptionPane.showMessageDialog(null,"该房间号已被使用，请换一个房间号。",  
					"错误", JOptionPane.ERROR_MESSAGE); 
		} catch (Exception e1) {  
			e1.printStackTrace();  
			isStart = false;  
			throw new BindException("启动服务器异常！");  
		}  

	}  
	// 执行消息发送  
	public static void send(String str) {  
		String message = str;
		sendServerMessage(message);
	}  

	// 群发服务器消息  
	public static void sendServerMessage(String message) {  
		for (int i = clients.size() - 1; i >= 0; i--) {  
			clients.get(i).getWriter().println(message);  
			clients.get(i).getWriter().flush();  
		}  
	}  

	// 关闭服务器  
	@SuppressWarnings("deprecation")  
	public static void closeServer() {  
		try {  
			if (serverThread != null)  
				serverThread.stop();// 停止服务器线程  

			for (int i = clients.size() - 1; i >= 0; i--) {  
				// 给所有在线用户发送关闭命令  
				clients.get(i).getWriter().println("CLOSE");  
				clients.get(i).getWriter().flush();  
				// 释放资源  
				clients.get(i).stop();// 停止此条为客户端服务的线程  
				clients.get(i).reader.close();  
				clients.get(i).writer.close();  
				clients.get(i).socket.close();  
				clients.remove(i);  
			}  
			if (serverSocket != null) {  
				serverSocket.close();// 关闭服务器端连接  
			}  
			isStart = false;  
		} catch (IOException e) {  
			e.printStackTrace();  
			isStart = true;  
		}  
	}  

	// 服务器线程  
	static class ServerThread extends Thread {  
		private ServerSocket serverSocket;  
		private int max = 1;// 人数上限  

		// 服务器线程的构造方法  
		public ServerThread(ServerSocket serverSocket, int max) {  
			this.serverSocket = serverSocket;  
			this.max = max;  
		}  
		public void run() {  
			while (true) {// 不停的等待客户端的链接  
				try {  
					Socket socket = serverSocket.accept();  
					if (clients.size() == max) {// 如果已达人数上限  
						BufferedReader r = new BufferedReader(  
								new InputStreamReader(socket.getInputStream()));  
						PrintWriter w = new PrintWriter(socket  
								.getOutputStream());  
						// 接收客户端的基本用户信息  
						String inf = r.readLine();  
						StringTokenizer st = new StringTokenizer(inf, "@");  
						UserMulti user = new UserMulti(st.nextToken(), st.nextToken());  
						// 反馈连接成功信息  
						w.println("MAX@服务器：对不起，" + user.getName()  
						+ user.getIp() + "，服务器在线人数已达上限，请稍后尝试连接！");  
						w.flush();  
						// 释放资源  
						r.close();  
						w.close();  
						socket.close();  
						continue;  
					}  
					ClientThread client = new ClientThread(socket);  
					client.start();// 开启对此客户端服务的线程  
					clients.add(client);  
				} catch (IOException e) {  
					e.printStackTrace();  
				}  
			}  
		}  
	}  

	// 为一个客户端服务的线程  
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

		// 客户端线程的构造方法  
		public ClientThread(Socket socket) {  
			try {  
				this.socket = socket;  
				reader = new BufferedReader(new InputStreamReader(socket  
						.getInputStream()));  
				writer = new PrintWriter(socket.getOutputStream());  
				// 接收客户端的基本用户信息  
				String inf = reader.readLine();  
				StringTokenizer st = new StringTokenizer(inf, "@");  
				user = new UserMulti(st.nextToken(), st.nextToken());  
				// 反馈连接成功信息  
				writer.println(user.getName() + user.getIp() + "与服务器连接成功!");  
				writer.flush();  
				JOptionPane.showMessageDialog(null, user.getName() + "已加入了房间。");
				MultiFrameFuncPanel.btnStart.setEnabled(true);
				UtilPanel.infoChange("Please select a map and start the game!");
			} catch (IOException e) {  
				e.printStackTrace();  
			}  
		}  

		@SuppressWarnings("deprecation")  
		public void run() {// 不断接收客户端的消息，进行处理。  
			String message = null;  
			while (true) {  
				try { 
					message = reader.readLine();// 接收客户端消息  
					StringTokenizer stringTokenizer = new StringTokenizer(  
                            message, "/@");  
                    String command = stringTokenizer.nextToken();// 命令  
                    System.out.println(command);
                    if (command.equals("CLOSE"))//线程下线命令 
                    {  
                    	JOptionPane.showMessageDialog(null, user.getName() + "已经退出了房间。游戏中断。", "错误",  
								JOptionPane.ERROR_MESSAGE);
						reader.close();  
						writer.close();  
						socket.close();  

						// 删除此条客户端服务线程  
						for (int i = clients.size() - 1; i >= 0; i--) {  
							if (clients.get(i).getUser() == user) {  
								ClientThread temp = clients.get(i);  
								clients.remove(i);// 删除此用户的服务线程  
								temp.stop();// 停止这条服务线程  
								return;  
							}  
						}
						
                    } else if (command.equals("MAP")) {//地图消息传输
                        
                    } else if(command.equals("PREV")){
                    	
                    }
                     else if (command.equals("FINISHED")) {//单方游戏完成消息传输
                    	MultiFrameFuncPanel.gamestarted = true;
             			MultiFrameFuncPanel.gamepaused= true;
             			JOptionPane.showMessageDialog(null, "对手已经完成了游戏！用时：" + UtilPanel.Currenttime);
             			GamePanel.isenabled = false;
                    } else {// 步骤消息  
                        System.out.println(message + "\r\n");  
                    }  
				} catch (IOException e) {  
					e.printStackTrace();  
				}  
			}  
		}  
	}
}