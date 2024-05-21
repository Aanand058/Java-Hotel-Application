
package application;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;



public class ServerCode {
    public static void main(String[] args) {
        try (ServerSocket ss = new ServerSocket(6000)) {
            System.out.println("MultiThreader Server Started at "+ new Date() + "\n");

            while (true) {
                Socket clientSocket = ss.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress().getHostAddress() + " at " +  new Date() + "\n");

    
                Thread clientHandlerThread = new Thread(new ClientHandler(clientSocket));
                clientHandlerThread.start();
            }
        } catch (IOException e) {
        	e.printStackTrace();
           
        }
    }
}
