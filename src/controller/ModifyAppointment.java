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
import model.Checker;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;
import java.util.ResourceBundle;

/***
 * Controller Class for the Modify Appointments Screen
 * */
public class ModifyAppointment implements Initializable {
    /***
     * JavaFX object to represent fields on the user interface
     */
    public TextField appointmentidFX;

    /***
     * JavaFX object to represent fields on the user interface
     */
    public TextField titleFX;

    /***
     * JavaFX object to represent fields on the user interface
     */
    public TextField descriptionFX;

    /***
     * JavaFX object to represent fields on the user interface
     */
    public TextField locationFX;

    /***
     * JavaFX object to represent fields on the user interface
     */
    public TextField typeFX;

    /***
     * JavaFX object to represent fields on the user interface
     */
    public ChoiceBox contactFX;

    /***
     * JavaFX object to represent fields on the user interface
     */
    public ChoiceBox customeridFX;

    /***
     * JavaFX object to represent fields on the user interface
     */
    public ChoiceBox starttimeFX;

    /***
     * JavaFX object to represent fields on the user interface
     */
    public ChoiceBox endtimeFX;

    /***
     * JavaFX object to represent fields on the user interface
     */
    public ChoiceBox useridFX;

    /***
     * JavaFX object to represent fields on the user interface
     */
    public DatePicker startdateFX;

    /***
     * JavaFX object to represent fields on the user interface
     */
    public DatePicker enddateFX;

    /***
     * JavaFX object to represent the anchor pane on the user interface
     */
    public AnchorPane anchorpaneFX;

    /***
     * Variable holding the appointment selected on the home screen to be modified
     */
    static Appointment currentAppointment;

    /***
     * Populates the items in the Choice boxes
     * @param url
     * @param resourceBundle
     */
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

    /***
     * Method for going to the Home Screen
     * @throws IOException Failed I/O operation
     */
    public void toHome() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/HomeScreen.fxml")));
        Stage stage = (Stage) (anchorpaneFX).getScene().getWindow();
        Scene scene = new Scene(root, 750, 500);
        stage.setTitle("Home Screen");
        stage.setScene(scene);
        stage.show();
    }

    /***
     * Save button
     * Saves the appointment, triggers relevant alerts, returns to Home Screen
     * @param actionEvent passed when save button is pressed on screen
     * @throws IOException Failed I/O operation
     * @throws SQLException For database access errors
     */
    public void onSave(ActionEvent actionEvent) throws SQLException, IOException {
        int appointmentID = Integer.parseInt(appointmentidFX.getText());
        LocalDateTime start = LocalDateTime.of(startdateFX.getValue(), LocalTime.parse(starttimeFX.getValue().toString()));
        LocalDateTime end = LocalDateTime.of(enddateFX.getValue(), LocalTime.parse(endtimeFX.getValue().toString()));

        Appointment newAppointment = new Appointment(appointmentID, titleFX.getText(), descriptionFX.getText(), locationFX.getText(),
                (String) contactFX.getValue(), typeFX.getText(), start, end, Integer.parseInt(customeridFX.getValue().toString()), Integer.parseInt(useridFX.getValue().toString()));

        if(Checker.workDays(newAppointment)){
            return;
        }
        if(Checker.overlap(newAppointment)){
            return;
        }
        AppointmentDao.updateAppointment(newAppointment);
        toHome();
    }

    /***
     * Close button
     * @param actionEvent passed when closed button is pressed on the screen
     * @throws IOException Failed I/O operation
     */
    public void onClose(ActionEvent actionEvent) throws IOException {
        toHome();
    }
}
