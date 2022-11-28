package controller;

import com.mysql.cj.util.StringInspector;
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
import model.Checker;
import model.CustomerDao;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;
import java.util.ResourceBundle;

public class AddAppointment implements Initializable {
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

    /***
     * Populates the items in the Choice boxes
     * @param url
     * @param resourceBundle
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        starttimeFX.setItems((ObservableList) AppointmentDao.getAllTimes());
        endtimeFX.setItems((ObservableList) AppointmentDao.getAllTimes());

        try {
            contactFX.setItems(AppointmentDao.selectContacts());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            customeridFX.setItems(AppointmentDao.selectCustomerID());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            useridFX.setItems(AppointmentDao.selectUserID());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void toHome() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/HomeScreen.fxml")));
        Stage stage = (Stage) (anchorpaneFX).getScene().getWindow();
        Scene scene = new Scene(root, 750, 500);
        stage.setTitle("Home Screen");
        stage.setScene(scene);
        stage.show();
    }

    public void onSave(ActionEvent actionEvent) throws IOException, SQLException {
        int appointmentID = 222;
        String title = titleFX.getText();
        String description = descriptionFX.getText();
        String location = locationFX.getText();
        String type = typeFX.getText();
        String contact = (String) contactFX.getValue();

        if (customeridFX.getValue() ==null){
            Checker.apptInputAlert();
            return;
        }

        int customerID = Integer.parseInt((String) customeridFX.getValue());

        if (starttimeFX.getValue() ==null){
            Checker.apptInputAlert();
            return;
        }

        LocalTime startTime = LocalTime.parse((String) starttimeFX.getValue());

        if (endtimeFX.getValue() ==null){
            Checker.apptInputAlert();
            return;
        }

        LocalTime endTime = LocalTime.parse((String) endtimeFX.getValue());

        if (useridFX.getValue() ==null){
            Checker.apptInputAlert();
            return;
        }

        int userID = Integer.parseInt((String)useridFX.getValue());

        if (startdateFX.getValue() ==null){
            Checker.apptInputAlert();
            return;
        }

        LocalDate startDate = startdateFX.getValue();

        if (enddateFX.getValue() ==null){
            Checker.apptInputAlert();
            return;
        }
        LocalDate endDate = enddateFX.getValue();

        LocalDateTime start = LocalDateTime.of(startDate, startTime);
        LocalDateTime end = LocalDateTime.of( endDate, endTime);

        if(Checker.apptInputVal(title, description, location, type, contact, customerID, startTime, endTime, userID, startDate, endDate)){
            return;
        }

        Appointment appointment = new Appointment(appointmentID, title, description, location, contact, type, start, end,
                customerID, userID);

        if(Checker.workDays(appointment)){
            return;
        }
        if(Checker.overlap(appointment)){
            return;
        }



        AppointmentDao.insertAppointment(appointment);
        toHome();


    }

    public void onClose(ActionEvent actionEvent) throws IOException {
        toHome();
    }


}