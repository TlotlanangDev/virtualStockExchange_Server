import java.sql.*;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
void main() {


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


