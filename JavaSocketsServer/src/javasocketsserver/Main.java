/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javasocketsserver;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author human
 */
public class Main {

    private static final int LISTEN_PORT = 3550;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(LISTEN_PORT);
            System.out.printf("%16s %5d\n", "SERVER LISTEN ON", LISTEN_PORT);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (serverSocket != null) {
            while (true) {
                try (Socket connectedSocket = serverSocket.accept()) { // try-with-resource
                    byte[] address = connectedSocket.getInetAddress().getAddress();
                    System.out.printf("%16s %03d.%03d.%03d.%03d\n", "Connected:",
                            Byte.toUnsignedInt(address[0]), Byte.toUnsignedInt(address[1]),
                            Byte.toUnsignedInt(address[2]), Byte.toUnsignedInt(address[3]));
                    
                    // --- NETWORK LATENCY SIMULATION ---
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    // --- NETWORK LATENCY SIMULATION ---

                    // --- DATA TRANSPORT ---
                    DataInputStream dis = new DataInputStream(connectedSocket.getInputStream());

                    // Receive command
                    String commandName = dis.readUTF();
                    System.out.printf("%16s %s\n", "Command:", commandName);

                    switch (commandName) {
                        case "PING":
                            DataOutputStream dos = new DataOutputStream(connectedSocket.getOutputStream());

                            dos.writeUTF("PONG");
                            // Flushing buffered data
                            dos.flush();
                            break;
                    }
                    // --- DATA TRANSPORT ---
                } catch (IOException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }

}
