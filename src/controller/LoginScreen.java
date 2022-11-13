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

/**
 *
 */
public class LoginScreen {


    public static ResourceBundle rbundle;
    public TextField usernameFX;
    public TextField passwordFX;
    public Label countryFX;
    public AnchorPane anchorpaneFX;
    public Button signinbuttonFX;
    public Label headerFX;
    public Label siteFX;









    public void initialize() {

        System.out.println("Login Screen Init");
        countryFX.setText(ZoneId.systemDefault().getId());



    }

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
