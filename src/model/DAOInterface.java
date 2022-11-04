package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public interface DAOInterface {

    /***
     * LAMBDA expression is used in this method
     * The field values of the customer object are streamed. The lambda expression is applied to each field to change
     * it to string value.
     * @param customer Customer object to be parsed
     * @return a List of Strings containing the values of the Customer fields
     */
    public static List customerToString(Customer customer){
        List<Object> customerData = Arrays.asList(customer.getCustomerId(), customer.getName(),
                customer.getAddress(), customer.getPostalCode(), customer.getPhone(), customer.getDivisionId());
        return customerData.stream().map( a -> a.toString()).collect(Collectors.toList());
    }


}
