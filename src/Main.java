//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
void main() {


    String databaseUserName = "Tlotlanang";
    String databasePassword = "Gabonewe";
    try {
        while(true) {
            ServerSocket serversocket = new ServerSocket(9000);
            System.out.println("Waiting for connection....");
            Socket socket = serversocket.accept();
            System.out.println("Connected.");
            DataInputStream inputstream = new DataInputStream(socket.getInputStream());
            DataOutputStream outputstream = new DataOutputStream(socket.getOutputStream());
            String clientUsername = inputstream.readUTF();
            String clientPassword = inputstream.readUTF();
            if(clientUsername.equals(databaseUserName) && clientPassword.equals(databasePassword)){
                outputstream.writeUTF("Welcome to your Inbox!");
            } else if (!clientUsername.equals(databaseUserName) || !clientPassword.equals(databasePassword)) {
                outputstream.writeUTF("Password and Username not linked!!");
            } else if (!clientUsername.equals(databaseUserName)) {
                outputstream.writeUTF("Wrong UserName");
            } else if (!clientPassword.equals(databasePassword)) {outputstream.writeUTF("Wrong Password!");}


            serversocket.close();
            socket.close();
            inputstream.close();
            outputstream.close();
        }

    } catch (Exception e) {
        throw new RuntimeException(e);
    }
}
