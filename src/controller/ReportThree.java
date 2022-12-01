package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.AppointmentDao;
import model.CustomerDao;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

/***
 * Controller Class for the Report 3 Screen
 */
public class ReportThree implements Initializable {


    public AnchorPane anchorpaneFX;
    public ChoiceBox locationFX;
    public TableView tableFX;
    public TableColumn customeridFX;
    public TableColumn nameFX;
    public TableColumn addressFX;
    public TableColumn divisionFX;
    public TableColumn postalcodeFX;
    public TableColumn futureFX;
    public TableColumn pastFX;

    /***
     * Populates the items in the table
     * Lambda expression is set to trigger when the Customer choice box is changed. The function populates the table with the chosen customer's information
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            locationFX.setItems(CustomerDao.selectCountries());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        locationFX.setOnAction(event -> {
            System.out.println(locationFX.getValue() + " selected from drop down");

            ObservableList customerList = null;
            try {
                customerList = CustomerDao.selectLocals((locationFX.getValue()).toString());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            tableFX.setItems(customerList);
            customeridFX.setCellValueFactory(new PropertyValueFactory<>("customerId"));
            nameFX.setCellValueFactory(new PropertyValueFactory<>("name"));
            addressFX.setCellValueFactory(new PropertyValueFactory<>("address"));
            divisionFX.setCellValueFactory(new PropertyValueFactory<>("division"));
            postalcodeFX.setCellValueFactory(new PropertyValueFactory<>("postalCode"));

        });

        
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
     * Close button
     * @param actionEvent passed when closed button is pressed on the screen
     * @throws IOException Failed I/O operation
     */
    public void onClose(ActionEvent actionEvent) throws IOException {
        toHome();
    }
}
