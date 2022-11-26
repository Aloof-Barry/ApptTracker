package controller;

import javafx.event.ActionEvent;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Checker;
import model.Logger;


import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

/***
 * Controller Class for the Login Screen
 */
public class LoginScreen {

    /***
     * Variable to hold the resource bundle coming from the Main Class
     */
    public static ResourceBundle rbundle;

    /***
     * JavaFX object to represent fields on the user interface
     */
    public TextField usernameFX;

    /***
     * JavaFX object to represent fields on the user interface
     */
    public TextField passwordFX;

    /***
     * JavaFX object to represent fields on the user interface
     */
    public Label countryFX;

    /***
     * JavaFX object to represent fields on the user interface
     */
    public AnchorPane anchorpaneFX;

    /***
     * JavaFX object to represent the sign in button user interface
     */
    public Button signinbuttonFX;

    /***
     * JavaFX object to represent text on the user interface
     */
    public Label headerFX;

    /***
     * JavaFX object to represent text on the user interface
     */
    public Label siteFX;

    /***
     * Sets countryFX text to reflect the systems geographic location
     */
    public void initialize() {
        System.out.println("Login Screen Init");
        countryFX.setText(ZoneId.systemDefault().getId());
    }

    /***
     * Sign in Button
     * verifies the user and checks for appointments before going to the Home Screen
     * @param actionEvent passed when save button is pressed on screen
     * @throws IOException Failed I/O operation
     * @throws SQLException For database access errors
     */
    public void onSignIn(ActionEvent actionEvent) throws IOException, SQLException {
        if(Logger.signIn(usernameFX.getText(), passwordFX.getText())){
            Alert errorB = new Alert(Alert.AlertType.ERROR);
            errorB.setTitle(rbundle.getString("ERROR"));
            errorB.setHeaderText(rbundle.getString("ERROR1"));
            errorB.setContentText(rbundle.getString("ERROR2"));
            errorB.showAndWait();
            return;
        }else {
            Logger.userFifteen();
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/HomeScreen.fxml")));
            Stage stage = (Stage) (anchorpaneFX).getScene().getWindow();
            Scene scene = new Scene(root, 750, 500);
            stage.setTitle("Home Screen");
            stage.setScene(scene);
            stage.show();
        }
    }

}
