package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class HomeScreen {
    public Tab ctabFX;
    public TableView customertableFX;
    public TableColumn customeridFX;
    public TableColumn customernameFX;
    public TableColumn customerphoneFX;
    public TableView appointmenttableFX;
    public TableColumn appointmentidFX;
    public TableColumn titleFX;
    public TableColumn descriptionFX;
    public TableColumn locationFX;
    public TableColumn contactFX;
    public TableColumn typeFX;
    public TableColumn startFX;
    public TableColumn endFX;
    public TableColumn customerid_appointmentsFX;
    public TableColumn useridFX;
    public Tab atabFX;
    public RadioButton allFX;
    public RadioButton monthFX;
    public RadioButton weekFX;
    public AnchorPane anchorpaneFX;

    public void onAddCustomer(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/AddCustomer.fxml")));
        Stage stage = (Stage) (anchorpaneFX).getScene().getWindow();
        Scene scene = new Scene(root, 600, 400);
        stage.setTitle("Add Customer");
        stage.setScene(scene);
        stage.show();
    }

    public void onUpdateCustomer(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/ModifyCustomer.fxml")));
        Stage stage = (Stage) (anchorpaneFX).getScene().getWindow();
        Scene scene = new Scene(root, 600, 400);
        stage.setTitle("Modify Customer");
        stage.setScene(scene);
        stage.show();
    }

    public void onDeleteCustomer(ActionEvent actionEvent) {
    }

    public void onLogout(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/LoginScreen.fxml")));
        Stage stage = (Stage) (anchorpaneFX).getScene().getWindow();
        Scene scene = new Scene(root, 600, 400);
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
    }

    public void onToggleAll(ActionEvent actionEvent) {
    }

    public void onToggleMonth(ActionEvent actionEvent) {
    }

    public void onToggleWeek(ActionEvent actionEvent) {
    }

    public void onAddAppointment(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/AddAppointment.fxml")));
        Stage stage = (Stage) (anchorpaneFX).getScene().getWindow();
        Scene scene = new Scene(root, 600, 400);
        stage.setTitle("Add Appointment");
        stage.setScene(scene);
        stage.show();
    }

    public void onUpdateAppointment(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/ModifyAppointment.fxml")));
        Stage stage = (Stage) (anchorpaneFX).getScene().getWindow();
        Scene scene = new Scene(root, 600, 400);
        stage.setTitle("Modify Appointment");
        stage.setScene(scene);
        stage.show();
    }

    public void onDeleteAppointment(ActionEvent actionEvent) {
    }
}
