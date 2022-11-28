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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;
import java.util.ResourceBundle;

/***
 * Controller Class for the Add Appointments Screen
 * */
public class AddAppointment implements Initializable {

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

    /***
     * Method for going to the Home Screen
     * @throws IOException Failed I/O operation FXMLoader
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
     * @throws IOException Failed I/O operation FXMLoader
     * @throws SQLException For database access errors
     */
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

    /***
     * Close button
     * @param actionEvent passed when closed button is pressed on the screen
     * @throws IOException Failed I/O operation FXMLoader
     */
    public void onClose(ActionEvent actionEvent) throws IOException {
        toHome();
    }


}