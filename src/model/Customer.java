package model;

public class Customer {

    private int customerId;

    private String name;

    private String address;

    private String postalCode;

    private String phone;

    private CustomerDao.Country country;

    private CustomerDao.DivisionId divisionId;



    public Customer(int customerId, String name, String address, String postalCode, String phone,
                    CustomerDao.Country country, CustomerDao.DivisionId divisionId){
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

    public CustomerDao.Country getCountry(){
        return country;
    }

    public void setCountry(CustomerDao.Country country){
        this.country = country;
    }

    public CustomerDao.DivisionId getDivisionId(){
        return divisionId;
    }

    public void setDivisionId(CustomerDao.DivisionId divisionId){
        this.divisionId = divisionId;
    }






}
