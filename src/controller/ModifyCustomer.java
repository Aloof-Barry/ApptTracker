package controller;

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
 * Controller Class for the Modify Customer Screen
 */
public class ModifyCustomer implements Initializable {

    /***
     * JavaFX object to represent fields on the user interface
     */
    public ChoiceBox firstdivFX;

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
     * JavaFX object to represent fields on the user interface
     */
    public AnchorPane anchorpaneFX;

    /***
     * JavaFX object to represent fields on the user interface
     */
    public static Customer currentCustomer;

    /***
     * Populates the items in the fields and choice boxes
     * The Lambda expression is a function that triggers when the Country choice box is changed. The function changes the contents of the Division choice box
     * The reason for using a LAMBDA expression here is to execute a line of code on action of the choice box.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customeridFX.setText(Integer.toString(currentCustomer.getCustomerId()));
        addressFX.setText(currentCustomer.getAddress());
        nameFX.setText((currentCustomer.getName()));
        postalcodeFX.setText(currentCustomer.getPostalCode());
        phoneFX.setText(currentCustomer.getPhone());
        try {
            countryFX.setItems(CustomerDao.selectCountries());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        /***Set Contents of Division drop down
         *LAMDA block of code to set an action event on Country drop down to change contents of Division ID drop
         * down based on the selected value in Country drop down
         */
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

        // Set default values for Choiceboxes

        countryFX.setValue(currentCustomer.getCountry());
        try {
            firstdivFX.setValue((currentCustomer.getDivision()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
    public void onSave(ActionEvent actionEvent) throws SQLException, IOException {
        int customerId = Integer.parseInt(customeridFX.getText());
        if(firstdivFX.getValue() == null){
            Checker.apptInputAlert();
            return;
        }


        Customer newCustomer = new Customer(customerId, nameFX.getText(), addressFX.getText(), postalcodeFX.getText(),
                phoneFX.getText(), (String) countryFX.getValue(), CustomerDao.firstDivToID((String) firstdivFX.getValue()));
        if (Checker.customerInputVal(newCustomer)){
            return;
        }

        CustomerDao.updateCustomer(customerId, newCustomer);
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
