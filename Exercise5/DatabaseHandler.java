import java.sql.*;
 
public class DatabaseHandler {
 
    private static DatabaseHandler handler = null;
    private static Statement stmt = null;
    private static PreparedStatement pstatement = null;
 
    public static DatabaseHandler getInstance() {
        if (handler == null) {
            handler = new DatabaseHandler();
        }
        return handler;
    }
 
    public static Connection getDBConnection()
    {
        Connection connection = null;
        String dburl = "jdbc:mysql://127.0.0.1:3306/CEBPACDB?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        String userName = "root";
        String password = "Vhina05solo02_";
 
        try
        {
            connection = DriverManager.getConnection(dburl, userName, password);
 
        } catch (Exception e){
            e.printStackTrace();
        }
 
        return connection;
    }
 
    public ResultSet execQuery(String query) {
        ResultSet result;
        try {
            stmt = getDBConnection().createStatement();
            result = stmt.executeQuery(query);
        }
        catch (SQLException ex) {
            System.out.println("Exception at execQuery:dataHandler" + ex.getLocalizedMessage());
            return null;
        }
        finally {
        }
        return result;
    }
 
 
    public static boolean validateLogin(String username, String password){
 
        getInstance();
        String query = "SELECT * FROM admin WHERE UserName = '" + username + "' AND Password = '" + password + "'";
       
        System.out.println(query);
 
        ResultSet result = handler.execQuery(query);
        try {
            if (result.next()) {
                return true;
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }
}