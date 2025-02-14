import javafx.event.ActionEvent;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
 
 
public class LoginController {
   
    @FXML
    Label usernameLabel;
 
    @FXML
    Label passwordLabel;
 
    @FXML
    TextField usernameTextfield;
 
    @FXML
    PasswordField passwordPasswordfield;
 
    @FXML
    Button loginButton;
 
    private Stage stage;
    private Scene scene;
    private Parent root;
 

public void loginbuttonHandler(ActionEvent event) throws IOException {
    String uname = usernameTextfield.getText();
    String pword = passwordPasswordfield.getText();

    DatabaseHandler dbHandler = DatabaseHandler.getInstance();


    if (dbHandler.validateLogin(uname, pword)) { // Use the instance to call validateLogin
        FXMLLoader loader = new FXMLLoader(getClass().getResource("home.fxml"));
        root = loader.load();

        HomeController homeController = loader.getController();
        homeController.displayName(uname);

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    } else {
        // Show an alert box
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Login Failed");
        alert.setHeaderText("Invalid Credentials");
        alert.setContentText("Please check your username and password.");
        alert.showAndWait();
    }
}
}