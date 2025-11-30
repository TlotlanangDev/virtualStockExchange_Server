import java.sql.*;

public class databaseConnection {
        static Statement statement;
        static Connection connect;
    public static  void Connection(){

        try {
            System.out.println("Connecting to database...");
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver loaded");
            String path = "jdbc:mysql://localhost:3306/checking";
            //root is the username followed by password in the open quotation marks
            connect = DriverManager.getConnection(path, "root", "");
            System.out.println("Database connected..");
            statement = connect.createStatement();
            //PreparedStatement statement = connect.prepareStatement("INSERT INTO `mytable` (`Id`, `Name`, `Surname`, `Password`) " +
            //"VALUES (NULL, 'ThabSo', 'ThatSo', 'mSeme');");

        } catch (ClassNotFoundException ex) {

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void userInsertTable(){

        try {
            statement.executeUpdate("INSERT INTO `mytable` ('Id', 'Name', 'Surname','PhoneNumber', 'Password') " +
                    "VALUES (NULL, 'Cleo', 'Omar','0787041819', 'meme')");
            //PreparedStatement statement = connect.prepareStatement("INSERT INTO `mytable` (`Id`, `Name`, `Surname`,'PhoneNumber', `Password`) " +
            //"VALUES (NULL, 'Cleo', 'Omar','85663363', 'meme')");
            System.out.println("Data inserted");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void userUpdateTable(){

    }
    public static void userSelectTable(){

    }
    public static void userDeleteTable(){

    }
}
