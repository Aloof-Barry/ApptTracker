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
import model.Customer;
import model.CustomerDao;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class ModifyCustomer implements Initializable {
    public ChoiceBox firstdivFX;
    public ChoiceBox countryFX;
    public TextField customeridFX;
    public TextField addressFX;
    public TextField nameFX;
    public TextField postalcodeFX;
    public TextField phoneFX;
    public AnchorPane anchorpaneFX;
    public static Customer currentCustomer;



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

    public void toHome() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/HomeScreen.fxml")));
        Stage stage = (Stage) (anchorpaneFX).getScene().getWindow();
        Scene scene = new Scene(root, 600, 400);
        stage.setTitle("Home Screen");
        stage.setScene(scene);
        stage.show();
    }

    public void onSave(ActionEvent actionEvent) throws SQLException, IOException {
        int customerId = Integer.parseInt(customeridFX.getText());
        Customer newCustomer = new Customer(customerId, nameFX.getText(), addressFX.getText(), postalcodeFX.getText(),
                phoneFX.getText(), (String) countryFX.getValue(), CustomerDao.firstDivToID((String) firstdivFX.getValue()));
        CustomerDao.updateCustomer(customerId, newCustomer);

        toHome();


    }

    public void onCancel(ActionEvent actionEvent) throws IOException {
        toHome();
    }


}
