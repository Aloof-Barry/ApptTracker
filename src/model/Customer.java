package model;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/***
 * Abstract Class to represent Customers
 */
public class Customer {

    /***
     * Class member variable
     */
    private int customerId;

    /***
     * Class member variable
     */
    private String name;

    /***
     * Class member variable
     */
    private String address;

    /***
     * Class member variable
     */
    private String postalCode;

    /***
     * Class member variable
     */
    private String phone;

    /***
     * Class member variable
     */
    private String country;

    /***
     * Class member variable
     */
    private int divisionId;

    /***
     * Class member variable
     */
    private String division;



    /***
     * Constructor Method
     * @param customerId Customer ID
     * @param name Customer Name
     * @param address Customer address
     * @param postalCode Customer postal code
     * @param phone Customer phone
     * @param country Customer country
     * @param divisionId Customer division ID
     * @throws SQLException Database error
     */
    public Customer(int customerId, String name, String address, String postalCode, String phone,
                    String country, int divisionId) throws SQLException {
        this.customerId = customerId;
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.country = country;
        this.divisionId = divisionId;

    }



    /***
     * Getter Method
     * @return
     */
    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /***
     * Getter Method
     * @return name
     */
    public String getName() {
        return name;
    }

    /***
     * Setter Method
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /***
     * Getter Method
     * @return
     */
    public String getAddress() {
        return address;
    }

    /***
     * Setter Method
     * @param address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /***
     * Getter Method
     * @return postalCode
     */
    public String getPostalCode() {
        return postalCode;
    }

    /***
     * Setter Method
     * @param postalCode
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /***
     * Getter Method
     * @return phone
     */
    public String getPhone() {
        return phone;
    }

    /***
     * Setter Method
     * @param phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /***
     * Getter Method
     * @return country
     */
    public String getCountry(){
        return country;
    }

    /***
     * Setter Method
     * @param country
     */
    public void setCountry(String country){
        this.country = country;
    }

    /***
     * Getter Method
     * @return divisionId
     */
    public int getDivisionId(){
        return divisionId;
    }

    /***
     * Setter Method
     * @param divisionId
     */
    public void setDivisionId(int divisionId){
        this.divisionId = divisionId;
    }

    /***
     * Setter Method
     * Sets the Division by using the Division ID
     * @throws SQLException
     */
    public void setDivision() throws SQLException {
        String sql = "SELECT Division FROM client_schedule.first_level_divisions WHERE Division_ID =?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setString(1, Integer.toString(divisionId));
        ResultSet rs = ps.executeQuery();
        ObservableList<String> divisionList = FXCollections.observableArrayList();
        while(rs.next()){
            String divisionSet =  rs.getString("Division");

            divisionList.add(divisionSet);
        }
        this.division = divisionList.get(0);
    }

    /***
     * Getter Method
     * @return getDivision
     */
    public String getDivision() throws SQLException {
        setDivision();
        return division;
    }
}
