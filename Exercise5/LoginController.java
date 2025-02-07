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
 
    public void loginbuttonHandler(ActionEvent event) throws IOException{
 
        String uname = usernameTextfield.getText();
        String pword = passwordPasswordfield.getText();
 
        if (DatabaseHandler.validateLogin(uname, pword)) {
            //System.out.println("Login successful");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("home.fxml"));
 
            root = loader.load();
 
            // Load stage and scene
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        else
        {
            //System.out.println("Login unsuccessful :( ");
        }
    }
}