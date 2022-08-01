package model;

public class CustomerDao {
    private static final CustomerDao customerDao = new CustomerDao();

    private CustomerDao(){

    }

    public static CustomerDao getCustomerDao(){
        System.out.println("All Singletons instantiated presumably");
        return customerDao;


    }
    public enum Country{

    }

    public enum DivisionId{

    }
}
