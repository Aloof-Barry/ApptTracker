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

public class AddCustomer implements Initializable {

    public ChoiceBox firstdivFX =  new ChoiceBox(FXCollections.observableArrayList());
    public ChoiceBox countryFX;
    public TextField customeridFX;
    public TextField addressFX;
    public TextField nameFX;
    public TextField postalcodeFX;
    public TextField phoneFX;
    public AnchorPane anchorpaneFX;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("AddCustomer Screen Init");

        // Set contents of Country Drop Down

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
    }



    public void toHome() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/HomeScreen.fxml")));
        Stage stage = (Stage) (anchorpaneFX).getScene().getWindow();
        Scene scene = new Scene(root, 750, 500);
        stage.setTitle("Home Screen");
        stage.setScene(scene);
        stage.show();
    }

    /***
     * Saves the inputted data and inserts a customer into the database
     * @param actionEvent User clicks Save button to generate actionEvent
     * @throws IOException
     * @throws SQLException
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

    public void onCancel(ActionEvent actionEvent) throws IOException {
        toHome();
    }
}
