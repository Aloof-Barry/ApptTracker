package model;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Customer {

    private int customerId;

    private String name;

    private String address;

    private String postalCode;

    private String phone;

    private String country;

    private int divisionId;

    private String division;



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

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCountry(){
        return country;
    }

    public void setCountry(String country){
        this.country = country;
    }

    public int getDivisionId(){
        return divisionId;
    }

    public void setDivisionId(int divisionId){
        this.divisionId = divisionId;
    }

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

    public String getDivision() throws SQLException {
        setDivision();
        return division;
    }






}
