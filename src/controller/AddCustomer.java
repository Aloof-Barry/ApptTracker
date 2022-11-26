package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Checker;
import model.Customer;
import model.CustomerDao;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

/***
 *Controller Class for the Add Customer Screen
 */
public class AddCustomer implements Initializable {

    /***
     * JavaFX object to represent fields on the user interface
     */
    public ChoiceBox firstdivFX =  new ChoiceBox(FXCollections.observableArrayList());

    /***
     * JavaFX object to represent fields on the user interface
     */
    public ChoiceBox countryFX;

    /***
     * JavaFX object to represent fields on the user interface
     */
    public TextField customeridFX;

    /***
     * JavaFX object to represent fields on the user interface
     */
    public TextField addressFX;

    /***
     * JavaFX object to represent fields on the user interface
     */
    public TextField nameFX;

    /***
     * JavaFX object to represent fields on the user interface
     */
    public TextField postalcodeFX;

    /***
     * JavaFX object to represent fields on the user interface
     */
    public TextField phoneFX;

    /***
     * JavaFX object to represent the anchor pane. Used for scene navigation
     */
    public AnchorPane anchorpaneFX;

    /***
     * LAMDA
     * Populates the items in the Choice boxes.
     * LAMDA block of code to set an action event on Country drop down to change contents of Division ID drop
     * down based on the selected value in Country drop down. Changing the Country will execute the LAMDA function.
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("AddCustomer Screen Init");

        // Set contents of Country Drop Down
        try {
            countryFX.setItems(CustomerDao.selectCountries());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        countryFX.setOnAction(event -> {
            System.out.println(countryFX.getValue() + " selected from drop down");

            ObservableList divisionList = null;
            try {
                divisionList = CustomerDao.selectDivision((countryFX.getValue()).toString());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            firstdivFX.setItems((divisionList));
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
     * Save button
     * Saves the customer, triggers relevant alerts, returns to Home Screen
     * @param actionEvent passed when save button is pressed on screen
     * @throws IOException Failed I/O operation
     * @throws SQLException For database access errors
     */
    public void onSave(ActionEvent actionEvent) throws IOException, SQLException {
        int customerId = 222;
        String name = nameFX.getText();
        String address = addressFX.getText();
        String postalCode = postalcodeFX.getText();
        String phone = phoneFX.getText();
        String country = (String) countryFX.getValue();
        int divisionId = CustomerDao.firstDivToID((String) firstdivFX.getValue());

        Customer customer = new Customer(customerId, name, address, postalCode, phone, country, divisionId);
        if (Checker.customerInputVal(customer)){
            return;
        }
        CustomerDao.insertCustomer(customer);
        toHome();
    }

    /***
     * Close button
     * @param actionEvent passed when closed button is pressed on the screen
     * @throws IOException Failed I/O operation
     */
    public void onCancel(ActionEvent actionEvent) throws IOException {
        toHome();
    }
}
