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
            System.out.println("read the message");
            if(initialmessage.equals(registerIndicator)){
                outputstream.writeUTF("Register");
                registerMethod(socket, inputstream, outputstream);
            } else if (initialmessage.equals(loginIndicator)) {
                System.out.println("login");
                outputstream.writeUTF("Login");
                loginMethod(socket, inputstream,outputstream);
            }
            else {

                outputstream.writeUTF("Error!!");
            }

        } catch (Exception e) {
            throw new RuntimeException(e);

        }


    }

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
            databaseConnection.userInsertTable(name, surName, passWord, dateOfBirth, email, phone, address);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void loginMethod(Socket socket, DataInputStream inputstream, DataOutputStream outputstream){



        try {
            String username = inputstream.readUTF();
            String password = inputstream.readUTF();
            databaseConnection.Connection();
            databaseConnection.userSelectTable(username, password);
            String databaseUserName = databaseConnection.databaseUserName;
            String databasePassword = databaseConnection.databasePassword;


                if (username.equals(databaseUserName) && password.equals(databasePassword)) {
                    outputstream.writeUTF("You have loggen in..");
                } else {
                    outputstream.writeUTF("Wrong username or Password...");

                }





        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }


}
