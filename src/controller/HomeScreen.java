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
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

public class HomeScreen implements Initializable {
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
    public TableColumn addressFX;
    public TableColumn postalcodeFX;
    public TableColumn countryFX;
    public TableColumn divisionFX;


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

        AppointmentDao.setAllAppointments();
        appointmenttableFX.setItems(AppointmentDao.getAllAppointments());
        appointmentidFX.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        titleFX.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionFX.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationFX.setCellValueFactory(new PropertyValueFactory<>("location"));
        contactFX.setCellValueFactory(new PropertyValueFactory<>("contact"));
        typeFX.setCellValueFactory(new PropertyValueFactory<>("type"));
        startFX.setCellValueFactory(new PropertyValueFactory<>("start"));
        endFX.setCellValueFactory(new PropertyValueFactory<>("end"));
        customerid_appointmentsFX.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        useridFX.setCellValueFactory(new PropertyValueFactory<>("userId"));






    }

    public void onAddCustomer(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/AddCustomer.fxml")));
        Stage stage = (Stage) (anchorpaneFX).getScene().getWindow();
        Scene scene = new Scene(root, 600, 400);
        stage.setTitle("Add Customer");
        stage.setScene(scene);
        stage.show();
    }

    public void onUpdateCustomer(ActionEvent actionEvent) throws IOException {
        Customer customer = (Customer) customertableFX.getSelectionModel().getSelectedItem();
        if (customer == null) {
            return;
        }
        ModifyCustomer.currentCustomer = customer;

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
        Locale myLocale = Locale.getDefault();
        ResourceBundle rBundle = ResourceBundle.getBundle("helper.lang", myLocale);
        Parent root = FXMLLoader.load(getClass().getResource("/view/LoginScreen.fxml"), rBundle);
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
        Appointment appointment = (Appointment) appointmenttableFX.getSelectionModel().getSelectedItem();
        if (appointment == null) {
            return;
        }
        ModifyAppointment.currentAppointment = appointment;

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/ModifyAppointment.fxml")));
        Stage stage = (Stage) (anchorpaneFX).getScene().getWindow();
        Scene scene = new Scene(root, 600, 400);
        stage.setTitle("Modify Appointment");
        stage.setScene(scene);
        stage.show();
    }

    public void onDeleteAppointment(ActionEvent actionEvent) {
    }

    public void onResetData(ActionEvent actionEvent) {
        Report.resetData();
    }
}
