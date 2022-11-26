package model;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/***
 * Data Access Object Class for customer data
 */
public class CustomerDao implements DAOInterface{

    /***
     * Creates the Singleton class instance
     */
    private static final CustomerDao customerDao = new CustomerDao();

    /***
     * Constructor
     */
    private CustomerDao(){

    }

    /***
     * Gets the class instance
     * @return final customerDao
     */
    public static CustomerDao getCustomerDao(){
        System.out.println("All Singletons instantiated presumably");
        return customerDao;
    }

    /***
     * List containing all the customers in the database. used to populate UI tables.
     */
    public static ObservableList<Customer> allCustomers = FXCollections.observableArrayList();

    /***
     * getter for the allCustomers Observablelist
     * @return OList allCustomers
     */
    public static ObservableList<Customer> getAllCustomers(){
        return allCustomers;
    }

    /***
     * Appends a Customer object to the allCustomers list
     * @param customer Customer Object
     */
    public static void addCustomer(Customer customer){
        allCustomers.add(customer);
    }

    /***
     * Clears the current list of all the customers in the Java Program and reloads it with a fresh list of all the customers
     * from the database.
     */
    public static void setAllCustomers() {

        try{
            allCustomers.clear();

            String sql = "SELECT c.Customer_ID, c.Customer_Name, c.Address, c.Postal_Code, c.Phone, c.Division_ID, n.Country\n" +
                    "FROM client_schedule.customers AS c\n" +
                    "JOIN client_schedule.first_level_divisions AS f ON f.Division_ID = c.Division_ID\n" +
                    "JOIN client_schedule.countries AS n ON n.Country_ID = f.Country_ID";
            System.out.println(sql);
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int customerId = rs.getInt("Customer_ID");
                String name =  rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String postalCode = rs.getString("Postal_Code");
                String phone = rs.getString("Phone");
                String country = rs.getString("Country");
                int divisionId = rs.getInt("Division_ID");
                Customer customer = new Customer(customerId, name, address, postalCode, phone, country, divisionId);
                addCustomer(customer);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    /***
     * Inserts a given Customer object into customer database table
     * turns Customer object into a List of String Values representing each field.
     * Executes SQL Insert statement for Customer record
     * @param customer The customer to be inserted into the database. Usually received from AddCustomer Controller.
     */
    public static void insertCustomer(Customer customer){
        List<String> customerData = DAOInterface.customerToString(customer);
        try{

            String sql = "INSERT INTO client_schedule.customers (Customer_Name, Address, Postal_Code, Phone, Division_ID)\n" +
                "VALUES (\'" + customerData.get(1) + "\',\'" + customerData.get(2) + "\',\'" + customerData.get(3) + "\',\'"
                    + customerData.get(4) + "\'," + customerData.get(5) + ")";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.execute();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    /***
     * UPDATE query to update a customer's data
     * @param customerId The customer's ID
     * @param newCustomer Customer Object
     * @throws SQLException Database error
     */
    public static void updateCustomer(int customerId, Customer newCustomer) throws SQLException {
        String sql = "UPDATE client_schedule.customers\n" +
                "SET Customer_ID = " + customerId + ", Customer_Name = \'" + newCustomer.getName() +
                "\', Address = \'" + newCustomer.getAddress() + "\', Postal_Code = \'" + newCustomer.getPostalCode() +
                "\', Phone = \'" + newCustomer.getPhone() + "\', Division_ID = " + newCustomer.getDivisionId() + "\n" +
                "WHERE Customer_ID = " + customerId;

        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.execute();
    }

    /***
     * Returns the 3 countries as Strings by using a SELECT STATEMENT
     * @return A list of the Countries in the database
     * @throws SQLException for possible database access error
     */
    public static ObservableList selectCountries() throws SQLException {

        ObservableList<String> countryList = FXCollections.observableArrayList();

        String sql = "SELECT Country\n" +
                "FROM client_schedule.countries";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while(rs.next()){
            String country =  rs.getString("Country");
            System.out.println("append " + country + " to list");
            countryList.add(country);
        }
        return countryList;
    }

    /***
     * Runs 1 of 3 possible SELECT statements to return the correct divisions for that country
     * @param country The country to be considered when selecting the relevant divisions
     * @return the divisions as a list of Strings
     * @throws SQLException for possible database access error
     */
    public static ObservableList selectDivision(String country) throws SQLException {
        ObservableList<String> divisionList = FXCollections.observableArrayList();

        String countryID = null;
        switch (country) {
            case "U.S":
                countryID = "1";
                break;
            case "UK":
                countryID = "2";
                break;
            case "Canada":
                countryID = "3";
                break;
        }
        String sql =
                "SELECT Division\n" +
                "FROM client_schedule.first_level_divisions\n" +
                "WHERE Country_ID = " + countryID;
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            String division =  rs.getString("Division");
            System.out.println("append " + division + " to list");
            divisionList.add(division);
        }
        return divisionList;
    }

    /***
     * Converts the First Level Division name into the division ID
     * @param firstDiv The First Level Division as a String
     * @return int divisionID
     * @throws SQLException Database Error
     */
    public static int firstDivToID(String firstDiv) throws SQLException {
        String sql = "SELECT Division_ID FROM client_schedule.first_level_divisions WHERE Division =?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setString(1, firstDiv);
        ResultSet rs = ps.executeQuery();
        ObservableList<Integer> divisionIDList = FXCollections.observableArrayList();
        while(rs.next()){
            int divID =  rs.getInt("Division_ID");
            System.out.println("Selecting Div ID" + divID);
            divisionIDList.add(divID);
        }
        return divisionIDList.get(0);
    }

    /***
     * DELETE query for a single customer
     * @param customer Customer Object to be deleted
     * @throws SQLException Databse Error
     */
    public static void deleteCustomer(Customer customer) throws SQLException {
        String sql = "DELETE FROM client_schedule.customers\n" +
                "WHERE Customer_ID =?";

        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setString(1, Integer.toString(customer.getCustomerId()));
        System.out.println(ps);
        ps.execute();
    }
}
