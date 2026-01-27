
package virtualstockexchange.server;

import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Tlotlanang
 */
public class VirtualStockExchangeServer {

    public static void main(String[] args) {
        ServerSocket serversocket;
        Socket socket;
        ServerThread serverthread;
        Thread threading;

    try {

        while(true) {

            serversocket = new ServerSocket(9000);
            System.out.println("Waiting for connection....");
            socket = serversocket.accept();
            serverthread = new ServerThread(socket);
            threading = new Thread(serverthread);
            threading.start();
            serversocket.close();
        }
    } catch (Exception e) {
        System.out.println("Failed to connect Thread.");
    }
    }
}
