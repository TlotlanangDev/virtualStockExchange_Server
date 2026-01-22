
package tlotlanang.virtualstockexchange_server;

/**
 *
 * @author Tlotlanang
 */
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ServerThread implements Runnable{
    Socket socket;


    public ServerThread(Socket socket) {

        this.socket = socket;
    }
    
    @Override
    public void run() {
        DataInputStream inputstream;
        DataOutputStream outputstream;
        String registerIndicator = "Register";
        String loginIndicator = "Login";
        System.out.println("Connected.");
        try {
            inputstream = new DataInputStream(socket.getInputStream());
            outputstream = new DataOutputStream(socket.getOutputStream());
           
            String initialmessage = inputstream.readUTF();
            System.out.println("First message:" + initialmessage);
            //An initial message from a client will be login or register to determine which method must be executed.
            if(initialmessage.equals(registerIndicator)){
                /*if initial message from client is register, send another message to confirm register, then client will send 
                register information which will be recieved and executed in the registerMethod*/
                outputstream.writeUTF("Register");
                registerMethod(socket, inputstream, outputstream);
            } else if (initialmessage.equals(loginIndicator)) {
                //the same explanation as register but for login method.
                outputstream.writeUTF("Login");
                loginMethod(socket, inputstream,outputstream);
            }
            else {
                outputstream.writeUTF("Error!!");
            }
        } catch (Exception e) {
            System.out.println("ServerThread run Streaming input/output error!!");
        }
    }

//Read info from client and send it to mysql database
    private void registerMethod(Socket socket, DataInputStream inputstream, DataOutputStream outputstream) {
        try {
            String name = inputstream.readUTF();
            String surName = inputstream.readUTF();
            String passWord = inputstream.readUTF();
            String dateOfBirth = inputstream.readUTF();
            String email = inputstream.readUTF();
            String phone = inputstream.readUTF();
            String address = inputstream.readUTF();
            outputstream.writeUTF("Registration Successful.");
            databaseConnection.Connection();
            //parse info to database
            databaseConnection.userInsertTable(name, surName, passWord, dateOfBirth, email, phone, address);
        } catch (IOException e) {
            System.out.println("ServerThread RegisterMethod Input stream error!!");
        }
    }

    //compare input from client and stored info in the database to check if account is available to login
    public static void loginMethod(Socket socket, DataInputStream inputstream, DataOutputStream outputstream){
        System.out.println("Now in the login method..");
        try {
            String username = inputstream.readUTF();
            String password = inputstream.readUTF();
            //System.out.println(username + " " + password);
            //outputstream.writeUTF("logged In");
            
            databaseConnection.Connection();
            System.out.println("Connected to database in login method..");
            databaseConnection.userSelectTable(username, password);
            String databaseUserName = databaseConnection.databaseUserName;
            String databasePassword = databaseConnection.databasePassword;
                //validate client input and database
                if (username.equals(databaseUserName) && password.equals(databasePassword)) {
                    outputstream.writeUTF("You have loggen in..");
                } else {
                    outputstream.writeUTF("Wrong username or Password...");
                }
              
        } catch (IOException e) {
            System.out.println("ServerThread loginmethod stream error!");
        }
    }

}

