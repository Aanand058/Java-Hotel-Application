/**********************************************
Project - Hotel ABC
Course: APD545 - 5th Semester
Last Name: Aman
First Name: Aanand
ID: 166125211
Section: ZAA
This assignment represents my own work in accordance with Seneca Academic Policy.
Signature
Date: 2024/04/13
**********************************************/

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