package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class AddAppointment {
    public TextField appointmentidFX;
    public TextField titleFX;
    public TextField descriptionFX;
    public TextField locationFX;
    public TextField typeFX;
    public ChoiceBox contactFX;
    public ChoiceBox customeridFX;
    public ChoiceBox starttimeFX;
    public ChoiceBox endtimeFX;
    public ChoiceBox useridFX;
    public DatePicker startdateFX;
    public DatePicker enddateFX;
    public AnchorPane anchorpaneFX;

    public void onSave(ActionEvent actionEvent) {
    }

    public void onClose(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/HomeScreen.fxml")));
        Stage stage = (Stage) (anchorpaneFX).getScene().getWindow();
        Scene scene = new Scene(root, 600, 400);
        stage.setTitle("Home Screen");
        stage.setScene(scene);
        stage.show();
    }
}
