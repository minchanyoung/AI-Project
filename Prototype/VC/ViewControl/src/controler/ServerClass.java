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
			//올라마로 전송
			String outputMessage = "올라마로 전송";
			out.println(outputMessage);
			out.flush();
			if("quit".equalsIgnoreCase(outputMessage))
				break;
			
			// 받은걸 서버로 전송 or 저장해놓기
			String inputMessage = "올라마로 부터 수신";
			if("quit".equalsIgnoreCase(inputMessage))
				break;
			
			System.out.println("from: " + inputMessage);
			System.out.print("send>>");
		}
	}
	
	public void send(String msg) {
		//올라마로 전송
		System.out.println("전송");
		out.print(msg);
		out.flush();
	}
	
	// 받은걸 서버가 들고감
	
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
