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

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
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
            //System.out.println("reading message");
            String initialmessage = inputstream.readUTF();
            System.out.println("read the message");
            if(initialmessage.equals(registerIndicator)){
                //System.out.println(initialmessage);
                outputstream.writeUTF("Register");
                registerMethod(socket, inputstream, outputstream);
            } else if (initialmessage.equals(loginIndicator)) {
                System.out.println("login");
                outputstream.writeUTF("Login");
                loginMethod(socket, inputstream,outputstream);
            }
            else {
                //System.out.println("error, no initial message");
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
        System.out.println("welcome to login");
        String databaseUserName = "Tlotlanang";
        String databasePassword = "Gabonewe";
        try {
            String username = inputstream.readUTF();
            String password = inputstream.readUTF();
            if(username.equals(databaseUserName) && password.equals(databasePassword)){
                outputstream.writeUTF("You have loggen in..");
            }else{
                outputstream.writeUTF("Wrong username or Password..");
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        /*
        String databaseUserName = "Tlotlanang";
        String databasePassword = "Gabonewe";
        try {
            inputstream = new DataInputStream(socket.getInputStream());
            outputstream = new DataOutputStream(socket.getOutputStream());
            String clientUsername = inputstream.readUTF();
            String clientPassword = inputstream.readUTF();

            if (clientUsername.equals(databaseUserName) && clientPassword.equals(databasePassword)) {
                outputstream.writeUTF("Welcome to your Inbox!");
            } else if (!clientUsername.equals(databaseUserName) || !clientPassword.equals(databasePassword)) {
                outputstream.writeUTF("Password and Username not linked!!");
            } else if (!clientUsername.equals(databaseUserName)) {
                outputstream.writeUTF("Wrong UserName");
            } else if (!clientPassword.equals(databasePassword)) {
                outputstream.writeUTF("Wrong Password!");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }*/
    }


}
