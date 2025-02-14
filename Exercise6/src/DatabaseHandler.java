import java.sql.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class DatabaseHandler {
 
    private static DatabaseHandler handler = null;
 
    private DatabaseHandler() {
        // Private constructor to prevent instantiation
    }
 
    public static DatabaseHandler getInstance() {
        if (handler == null) {
            handler = new DatabaseHandler();
        }
        return handler;
    }
 
    public static Connection getDBConnection() {
        Connection connection = null;
        String dburl = "jdbc:mysql://127.0.0.1:3306/cebpacdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        String userName = "root";
        String password = "Vhina05solo02_";
 
        try {
            connection = DriverManager.getConnection(dburl, userName, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
 
        return connection;
    }
 
    public ResultSet execQuery(String query) {
        ResultSet result = null;
        try {
            Connection conn = getDBConnection();
            Statement stmt = conn.createStatement();
            result = stmt.executeQuery(query);
        } catch (SQLException ex) {
            System.out.println("Exception at execQuery: " + ex.getLocalizedMessage());
        }
        return result;
    }
 
    public static boolean validateLogin(String username, String password) {
        String query = "SELECT * FROM admin WHERE UserName = ? AND Password = ?";
        try (Connection conn = getDBConnection();
             PreparedStatement pstatement = conn.prepareStatement(query)) {
            pstatement.setString(1, username);
            pstatement.setString(2, password);
            ResultSet result = pstatement.executeQuery();
            return result.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
 
    public static boolean changePassword(String username, String newPassword) {
        String query = "UPDATE admin SET Password = ? WHERE Username = ?";
        try (Connection conn = getDBConnection();
             PreparedStatement pstatement = conn.prepareStatement(query)) {
            pstatement.setString(1, newPassword);
            pstatement.setString(2, username);
            return pstatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
 
    public static ResultSet getUsers() {
        String query = "SELECT * FROM admin";
        try {
            return getInstance().execQuery(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
 
    public static boolean addUser(User user) {
        if (isPhoneNumberExists(user.getPhoneNumber())) {
            showAlert("Error", "Duplicate Entry", "Phone number already exists!", AlertType.ERROR);
            return false; // Prevent insertion
        }
    
        if (isEmailExists(user.getEmail())) {
            showAlert("Error", "Duplicate Entry", "Email already exists!", AlertType.ERROR);
            return false; // Prevent insertion
        }
    
        
        String query = "INSERT INTO admin (first_name, last_name, username, password, email, phone_number) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = getDBConnection();
             PreparedStatement pstatement = conn.prepareStatement(query)) {
                pstatement.setString(1, user.getFirstName());
                pstatement.setString(2, user.getLastName());
                pstatement.setString(3, user.getUsername());
                pstatement.setString(4, user.getPassword());
                pstatement.setString(5, user.getEmail());
                pstatement.setString(6, user.getPhoneNumber());
                return pstatement.executeUpdate() > 0;
    
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
 
    public static boolean deleteUser(String id) {
        String query = "DELETE FROM admin WHERE id = ?";
        try (Connection conn = getDBConnection();
             PreparedStatement pstatement = conn.prepareStatement(query)) {
            pstatement.setString(1, id);
            return pstatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public static boolean updateUser (String oldID, User updatedID) {
        if (isPhoneNumberExists(updatedID.getPhoneNumber())) {
            showAlert("Error", "Duplicate Entry", "Phone number already exists!", AlertType.ERROR);
            return false; // Prevent insertion
        }
    
        if (isEmailExists(updatedID.getEmail())) {
            showAlert("Error", "Duplicate Entry", "Email already exists!", AlertType.ERROR);
            return false; // Prevent insertion
        }
    
        String query = "UPDATE admin SET first_name = ?, last_name = ?, username = ?, password = ?, email = ?, phone_number = ? WHERE id = ?";
   
        try (PreparedStatement pstmt = getDBConnection().prepareStatement(query)) {
            pstmt.setString(1, updatedID.getFirstName());
            pstmt.setString(2, updatedID.getLastName());
            pstmt.setString(3, updatedID.getUsername());
            pstmt.setString(4, updatedID.getPassword());
            pstmt.setString(5, updatedID.getEmail());
            pstmt.setString(6, updatedID.getPhoneNumber());
            pstmt.setString(7, oldID);

   
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }    
    
    
    public static boolean isPhoneNumberExists(String phoneNumber) {
        String query = "SELECT COUNT(*) FROM admin WHERE phone_number = ?";
        try (Connection conn = getDBConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, phoneNumber);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0; // Returns true if count > 0
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
  
    public static boolean isEmailExists(String email) {
        String query = "SELECT COUNT(*) FROM admin WHERE email = ?";
        try (Connection conn = getDBConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0; // Returns true if count > 0
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    private static void showAlert(String title, String header, String content, AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
   
}