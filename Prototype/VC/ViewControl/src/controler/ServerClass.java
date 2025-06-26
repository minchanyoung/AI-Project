package controler;

import java.io.PrintWriter;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerClass{
	
	private ServerSocket serverSocket = null;
	
	private Socket socket = null;
	private PrintWriter out = null;
	
	public ServerClass() {
		try {
			serverSocket = new ServerSocket(8989);
			socket = serverSocket.accept();
			
			out = new PrintWriter(socket.getOutputStream());
			
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void run() {
		while(true) {
			//�ö󸶷� ����
			String outputMessage = "�ö󸶷� ����";
			out.println(outputMessage);
			out.flush();
			if("quit".equalsIgnoreCase(outputMessage))
				break;
			
			// ������ ������ ���� or �����س���
			String inputMessage = "�ö󸶷� ���� ����";
			if("quit".equalsIgnoreCase(inputMessage))
				break;
			
			System.out.println("from: " + inputMessage);
			System.out.print("send>>");
		}
	}
	
	public void send(String msg) {
		//�ö󸶷� ����
		System.out.println("����");
		out.print(msg);
		out.flush();
	}
	
	// ������ ������ ���
	
	public void interrupt() {
		try {
			socket.close();
			serverSocket.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public Socket getSocket() {
		return socket;
	}

}
