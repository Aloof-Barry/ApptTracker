package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

/***
 * Controller Class for the Home Screen
 */
public class HomeScreen implements Initializable {

    /***
     * JavaFX object to represent the Customer Tab on the user interface
     */
    public Tab ctabFX;

    /***
     * JavaFX object to represent a table on the user interface
     */
    public TableView customertableFX;

    /***
     * JavaFX object to represent fields on the user interface
     */
    public TableColumn customeridFX;

    /***
     * JavaFX object to represent fields on the user interface
     */
    public TableColumn customernameFX;

    /***
     * JavaFX object to represent fields on the user interface
     */
    public TableColumn customerphoneFX;

    /***
     * JavaFX object to represent a table on the user interface
     */
    public TableView appointmenttableFX;

    /***
     * JavaFX object to represent fields on the user interface
     */
    public TableColumn appointmentidFX;

    /***
     * JavaFX object to represent fields on the user interface
     */
    public TableColumn titleFX;

    /***
     * JavaFX object to represent fields on the user interface
     */
    public TableColumn descriptionFX;

    /***
     * JavaFX object to represent fields on the user interface
     */
    public TableColumn locationFX;

    /***
     * JavaFX object to represent fields on the user interface
     */
    public TableColumn contactFX;

    /***
     * JavaFX object to represent fields on the user interface
     */
    public TableColumn typeFX;

    /***
     * JavaFX object to represent fields on the user interface
     */
    public TableColumn startFX;

    /***
     * JavaFX object to represent fields on the user interface
     */
    public TableColumn endFX;

    /***
     * JavaFX object to represent fields on the user interface
     */
    public TableColumn customerid_appointmentsFX;

    /***
     * JavaFX object to represent fields on the user interface
     */
    public TableColumn useridFX;

    /***
     * JavaFX object to represent the Appointment Tab on the user interface
     */
    public Tab atabFX;

    /***
     * JavaFX object to represent fields on the user interface
     */
    public RadioButton allFX;

    /***
     * JavaFX object to represent fields on the user interface
     */
    public RadioButton monthFX;

    /***
     * JavaFX object to represent fields on the user interface
     */
    public RadioButton weekFX;

    /***
     * JavaFX object to represent the anchor pane on the user interface
     */
    public AnchorPane anchorpaneFX;

    /***
     * JavaFX object to represent fields on the user interface
     */
    public TableColumn addressFX;

    /***
     * JavaFX object to represent fields on the user interface
     */
    public TableColumn postalcodeFX;

    /***
     * JavaFX object to represent fields on the user interface
     */
    public TableColumn countryFX;

    /***
     * JavaFX object to represent fields on the user interface
     */
    public TableColumn divisionFX;

    /***
     * Holds the state of the All, Month, and Week filter toggle
     */
    public int toggle = 0;


    /***
     * Populates the 2 tables
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Main Screen initialized");
        CustomerDao.setAllCustomers();
        customertableFX.setItems(CustomerDao.getAllCustomers());
        customeridFX.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        customernameFX.setCellValueFactory(new PropertyValueFactory<>("name"));
        customerphoneFX.setCellValueFactory(new PropertyValueFactory<>("phone"));
        addressFX.setCellValueFactory(new PropertyValueFactory<>("address"));
        postalcodeFX.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        countryFX.setCellValueFactory(new PropertyValueFactory<>("country"));
        divisionFX.setCellValueFactory(new PropertyValueFactory<>("division"));

        try {
            AppointmentDao.setAllAppointments(toggle);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        appointmenttableFX.setItems(AppointmentDao.getAllAppointments());
        appointmentidFX.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        titleFX.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionFX.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationFX.setCellValueFactory(new PropertyValueFactory<>("location"));
        contactFX.setCellValueFactory(new PropertyValueFactory<>("contact"));
        typeFX.setCellValueFactory(new PropertyValueFactory<>("type"));
        startFX.setCellValueFactory(new PropertyValueFactory<>("formattedStart"));
        endFX.setCellValueFactory(new PropertyValueFactory<>("formattedEnd"));
        customerid_appointmentsFX.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        useridFX.setCellValueFactory(new PropertyValueFactory<>("userId"));
    }

    /***
     * Goes to the Add Customer Screen
     * @param actionEvent passed when button is clicked
     * @throws IOException Failed I/O operation
     */
    public void onAddCustomer(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/AddCustomer.fxml")));
        Stage stage = (Stage) (anchorpaneFX).getScene().getWindow();
        Scene scene = new Scene(root, 750, 500);
        stage.setTitle("Add Customer");
        stage.setScene(scene);
        stage.show();
    }


    /***
     * Goes to the Update Customer Screen
     * @param actionEvent passed when button is clicked
     * @throws IOException Failed I/O operation
     */
    public void onUpdateCustomer(ActionEvent actionEvent) throws IOException {
        Customer customer = (Customer) customertableFX.getSelectionModel().getSelectedItem();
        if (customer == null) {
            return;
        }
        ModifyCustomer.currentCustomer = customer;

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/ModifyCustomer.fxml")));
        Stage stage = (Stage) (anchorpaneFX).getScene().getWindow();
        Scene scene = new Scene(root, 750, 500);
        stage.setTitle("Modify Customer");
        stage.setScene(scene);
        stage.show();
    }


    /***
     * Goes to the Login Screen
     * @param actionEvent passed when button is clicked
     * @throws IOException Failed I/O operation
     */
    public void onLogout(ActionEvent actionEvent) throws IOException {
        Locale myLocale = Locale.getDefault();
        ResourceBundle rBundle = ResourceBundle.getBundle("helper.lang", myLocale);
        Parent root = FXMLLoader.load(getClass().getResource("/view/LoginScreen.fxml"), rBundle);
        Stage stage = (Stage) (anchorpaneFX).getScene().getWindow();
        Scene scene = new Scene(root, 750, 500);
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
    }

    /***
     * Sets toggle to 0
     * @param actionEvent passed when the "All" toggle is clicked in the UI
     * @throws SQLException
     */
    public void onToggleAll(ActionEvent actionEvent) throws SQLException {
        toggle = 0;
        AppointmentDao.setAllAppointments(toggle);
    }

    /***
     * Sets toggle to 1
     * @param actionEvent passed when the "Month" toggle is clicked in the UI
     * @throws SQLException
     */
    public void onToggleMonth(ActionEvent actionEvent) throws SQLException {
        toggle = 1;
        AppointmentDao.setAllAppointments(toggle);
    }

    /***
     * Sets toggle to 2
     * @param actionEvent passed when the "Week" toggle is clicked in the UI
     * @throws SQLException
     */
    public void onToggleWeek(ActionEvent actionEvent) throws SQLException {
        toggle = 2;
        AppointmentDao.setAllAppointments(toggle);
    }

    /***
     * Goes to the Add Appointment Screen
     * @param actionEvent passed when button is clicked
     * @throws IOException Failed I/O operation
     */
    public void onAddAppointment(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/AddAppointment.fxml")));
        Stage stage = (Stage) (anchorpaneFX).getScene().getWindow();
        Scene scene = new Scene(root, 750, 500);
        stage.setTitle("Add Appointment");
        stage.setScene(scene);
        stage.show();
    }

    /***
     * Goes to the Add Appointment Screen
     * @param actionEvent passed when button is clicked
     * @throws IOException Failed I/O operation
     */
    public void onUpdateAppointment(ActionEvent actionEvent) throws IOException {
        Appointment appointment = (Appointment) appointmenttableFX.getSelectionModel().getSelectedItem();
        if (appointment == null) {
            return;
        }
        ModifyAppointment.currentAppointment = appointment;

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/ModifyAppointment.fxml")));
        Stage stage = (Stage) (anchorpaneFX).getScene().getWindow();
        Scene scene = new Scene(root, 750, 500);
        stage.setTitle("Modify Appointment");
        stage.setScene(scene);
        stage.show();
    }

    /***
     * removes an Appointment from the database
     * @param actionEvent passed when button is clicked
     * @throws IOException Failed I/O operation
     */
    public void onDeleteAppointment(ActionEvent actionEvent) throws SQLException {
        Appointment appointment = (Appointment) appointmenttableFX.getSelectionModel().getSelectedItem();
        if (appointment == null) {
            return;
        }
        AppointmentDao.deleteAppointment(appointment);
        AppointmentDao.setAllAppointments(toggle);
        Checker.deleteApptNotification(appointment);
    }

    /***
     * removes a Customer from the database
     * @param actionEvent passed when button is clicked
     * @throws IOException Failed I/O operation
     */
    public void onDeleteCustomer(ActionEvent actionEvent) throws SQLException {
        Customer customer = (Customer) customertableFX.getSelectionModel().getSelectedItem();
        if (customer == null) {
            return;
        }

        if (Checker.checkCustKeyRes(customer)){
            System.out.println("TRUE Foriegn Key Restraint Present");

            AppointmentDao.deleteCustAppts(customer);
            AppointmentDao.setAllAppointments(toggle);
            System.out.println("deleted appts with Customer_ID: " + customer.getCustomerId());

        } else {System.out.println("FALSE: FKR NOT present");}

        CustomerDao.deleteCustomer(customer);
        CustomerDao.setAllCustomers();
        Checker.deleteCustNotification();


    }

    /***
     * Goes to 1st report screen
     * @param actionEvent passed when button is clicked
     * @throws IOException Failed I/O operation
     */
    public void onReportOne(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/ReportOne.fxml")));
        Stage stage = (Stage) (anchorpaneFX).getScene().getWindow();
        Scene scene = new Scene(root, 750, 500);
        stage.setTitle("Report One");
        stage.setScene(scene);
        stage.show();
    }

    /***
     * this method does not do anything right now
     * @param actionEvent passed when button is clicked
     */
    public void onResetData(ActionEvent actionEvent) {

    }

    /***
     * Goes to 2nd report screen
     * @param actionEvent passed when button is clicked
     * @throws IOException Failed I/O operation
     */
    public void onReportTwo(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/ReportTwo.fxml")));
        Stage stage = (Stage) (anchorpaneFX).getScene().getWindow();
        Scene scene = new Scene(root, 750, 500);
        stage.setTitle("Report Two");
        stage.setScene(scene);
        stage.show();
    }

    /***
     * Goes to 3rd report screen
     * @param actionEvent passed when button is clicked
     * @throws IOException Failed I/O operation
     */
    public void onReportThree(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/ReportThree.fxml")));
        Stage stage = (Stage) (anchorpaneFX).getScene().getWindow();
        Scene scene = new Scene(root, 750, 500);
        stage.setTitle("Report Three");
        stage.setScene(scene);
        stage.show();
    }
}
