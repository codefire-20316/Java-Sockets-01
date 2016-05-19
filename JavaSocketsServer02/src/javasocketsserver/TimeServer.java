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
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author human
 */
public class TimeServer implements Runnable {

    private static final int LISTEN_PORT = 3551;
    private static final int LISTEN_TIMEOUT = 1000;

    private int port;
    private ServerSocket serverSocket;
    private boolean working;

    public TimeServer() throws IOException {
        this(LISTEN_PORT);
    }

    public TimeServer(int port) throws IOException {
        this.port = port;
        this.serverSocket = new ServerSocket(port);
        this.serverSocket.setSoTimeout(LISTEN_TIMEOUT);
    }

    @Override
    public void run() {
        working = true;
        
        System.out.println("WELCOME TO TIME SERVER " + port);
        
        while (working) {
            try (Socket acceptedSocket = serverSocket.accept()) {
                DataInputStream dis = new DataInputStream(acceptedSocket.getInputStream());
                DataOutputStream dos = new DataOutputStream(acceptedSocket.getOutputStream());
                
                printIpAddress(acceptedSocket.getInetAddress().getAddress());

                latencySimulation();

                handleCommand(dis, dos);
            } catch (SocketTimeoutException ex) {
//                Logger.getLogger(TimeServer.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(TimeServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        System.out.println("GOOD BYE!");
    }
    
    public void stop() {
        working = false;
    }

    private void handleCommand(DataInputStream dis, DataOutputStream dos) throws IOException {
        // --- DATA TRANSPORT ---
        // Receive command
        String commandName = dis.readUTF();
        System.out.printf("%16s %s\n", "Command:", commandName);
        
        switch (commandName) {
            case "PING":
                dos.writeUTF("PONG");
                // Flushing buffered data
                dos.flush();
                break;
            case "DATE":
                // TODO: Return date format (19.05.2016)
                break;
            case "TIME":
                // TODO: Return time format (08:53 PM)
                break;
            case "DATETIME":
                // TODO: Return date format (19.05.2016 08:53 PM)
                break;
        }
        // --- DATA TRANSPORT ---
    }

    private void latencySimulation() {
        // --- NETWORK LATENCY SIMULATION ---
        try {
            Thread.sleep((long)(Math.random() * 5000));
        } catch (InterruptedException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        // --- NETWORK LATENCY SIMULATION ---
    }

    private void printIpAddress(byte[] address) {
        System.out.printf("%16s %03d.%03d.%03d.%03d\n", "Connected:",
                Byte.toUnsignedInt(address[0]), Byte.toUnsignedInt(address[1]),
                Byte.toUnsignedInt(address[2]), Byte.toUnsignedInt(address[3]));
    }

}
