/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javasockets;

import java.io.IOException;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author human
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Socket's
        Socket socket = null;

        try {
            socket = new Socket();

            // Describe remote "ip address" and "port"
            InetSocketAddress isa = new InetSocketAddress("192.168.1.99", 3550);

            System.out.println("Try Connect...");
            socket.connect(isa);

            if (socket.isConnected()) {
                System.out.println("Connected");
            }
        } catch (ConnectException ex) {
            System.out.println("Not Connected");
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

}
