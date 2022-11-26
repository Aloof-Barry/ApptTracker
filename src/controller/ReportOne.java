package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.AppointmentDao;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

/***
 * Controller Class for the Report One Screen
 */
public class ReportOne implements Initializable {

    /***
     * JavaFX object to represent anchor pane on the user interface
     */
    public AnchorPane anchorpaneFX;

    /***
     * JavaFX object to represent fields on the user interface
     */
    public TableColumn monthFX;

    /***
     * JavaFX object to represent fields on the user interface
     */
    public TableColumn typeFX;

    /***
     * JavaFX object to represent a button on the user interface
     */
    public Button closeFX;

    /***
     * JavaFX object to represent fields on the user interface
     */
    public TableColumn totalFX;

    /***
     * JavaFX object to represent a table on the user interface
     */
    public TableView tableFX;

    /***
     * Populates the table
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AppointmentDao.monthTypeList.clear();
        try {
            AppointmentDao.setMonthTypeList();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        tableFX.setItems(AppointmentDao.getMonthTypeList());
        monthFX.setCellValueFactory(new PropertyValueFactory<>("month"));
        typeFX.setCellValueFactory(new PropertyValueFactory<>("type"));
        totalFX.setCellValueFactory(new PropertyValueFactory<>("total"));
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
