
package virtualstockexchange.server;
import java.io.IOException;
import java.sql.*;

public class databaseConnection {
        static Statement statement;
        static Connection connect;
        static ResultSet results;
        static String databaseUserName = null;
        static String databasePassword = null;

        //Connection to mysql Database
    public static  void Connection(){

        try {
            System.out.println("Connecting to database...");
            Class.forName("com.mysql.cj.jdbc.Driver");
            String path = "jdbc:mysql://localhost:3306/virtualstockexchange";
            connect = DriverManager.getConnection(path, "root", "");
            statement = connect.createStatement();
        } catch (ClassNotFoundException ex) {
            System.out.println("databaseConnection-Class not found eror!!");
        } catch (SQLException e) {
            System.out.println("databaseConnection-connection method error!!");
        }
    }

    //Insert input from server into the database
    public static void userInsertTable(String name, String surName, String passWord, String dateOfBirth,
                                       String email, String phone, String address){
        
        try {
            PreparedStatement statement = connect.prepareStatement("INSERT INTO user_info(Id, Name, SurName, Password, " +
                    "DateOfBirth, emailAddress, PhoneNumber, Address)" +
                    "VALUES(null, ?, ?, ?, ?, ?, ?, ?)");
            statement.setString(1, name);
            statement.setString(2, surName);
            statement.setString(3, passWord);
            statement.setString(4, dateOfBirth);
            statement.setString(5, email);
            statement.setString(6, phone);
            statement.setString(7, address);
            statement.executeUpdate();
            System.out.println("Data inserted");
        } catch (SQLException e) {
            System.out.println("databaseConnection userInsertTable error!!");
        }
    }

    //Update user info on the database
    public static void userUpdateTable(){

    }

    //extract user info from the table
    public static void userSelectTable(String username, String password){
        try {
            Statement statement =connect.createStatement();
            //Fetch results from database
            results = statement.executeQuery("SELECT * FROM user_info WHERE Name = '" + username +"' AND Password = '" +
                    password + "' ");
            //search results
            while (results.next()){
                databaseUserName = results.getString(2);
                databasePassword = results.getString(4);
            }
        } catch (SQLException e) {
            System.out.println("databaseConnection SelectTable error!!");
        }
    }

    //Delete user info on the database
    public static void userDeleteTable(){

    }
}
