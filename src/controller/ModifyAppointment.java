package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Appointment;
import model.AppointmentDao;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class ModifyAppointment implements Initializable {
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

    static Appointment currentAppointment;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        appointmentidFX.setText(Integer.toString(currentAppointment.getAppointmentId()));
        titleFX.setText(currentAppointment.getTitle());
        descriptionFX.setText(currentAppointment.getDescription());
        locationFX.setText(currentAppointment.getLocation());
        typeFX.setText(currentAppointment.getType());

        try {
            contactFX.setItems(AppointmentDao.selectContacts());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        contactFX.setValue(currentAppointment.getContact());

        try {
            customeridFX.setItems(AppointmentDao.selectCustomerID());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        customeridFX.setValue(currentAppointment.getCustomerId());

        try {
            useridFX.setItems(AppointmentDao.selectUserID());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        useridFX.setValue(currentAppointment.getUserId());


        starttimeFX.setItems((ObservableList) AppointmentDao.getAllTimes());
        starttimeFX.setValue(currentAppointment.getStart().toLocalTime().toString());
        endtimeFX.setItems((ObservableList) AppointmentDao.getAllTimes());
        endtimeFX.setValue(currentAppointment.getEnd().toLocalTime().toString());

        startdateFX.setValue(currentAppointment.getStart().toLocalDate());
        enddateFX.setValue(currentAppointment.getEnd().toLocalDate());

    }

    public void toHome() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/HomeScreen.fxml")));
        Stage stage = (Stage) (anchorpaneFX).getScene().getWindow();
        Scene scene = new Scene(root, 600, 400);
        stage.setTitle("Home Screen");
        stage.setScene(scene);
        stage.show();
    }

    public void onSave(ActionEvent actionEvent) {
    }

    public void onClose(ActionEvent actionEvent) throws IOException {
        toHome();

    }


}
