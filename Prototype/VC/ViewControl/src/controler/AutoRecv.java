package controler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class AutoRecv extends Thread {
	private Socket socket;
    @Override
    public void run() {
        super.run();

        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String receiveString = "";

            while(true) {
                receiveString = bufferedReader.readLine();
                System.out.println(receiveString);
                if (receiveString == null) {
                    System.out.println("disconnect");
                    socket.close();
                    break;
                } else {
                    System.out.println(receiveString);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void setSocket(Socket socket) {
        this.socket = socket;
    }
}
