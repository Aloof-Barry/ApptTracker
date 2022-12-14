package model;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/***
 * Interface to be implemented by Data Access Objects
 * Contained is an example of LAMBDA expression and streams.
 * This interface did not receive as much code as I thought it would
 */
public interface DAOInterface {

    /***
     * LAMBDA expression is used in this method to convert member fields of the Customer object into Strings.
     * The field values of the customer object are streamed. The lambda expression is applied to each field to change
     * it to string value. LAMDBAS expressions are commonly used to execute functional code on each individual object in a stream.
     * @param customer Customer object to be parsed
     * @return a List of Strings containing the values of the Customer fields
     */
    public static List customerToString(Customer customer){
        List<Object> customerData = Arrays.asList(customer.getCustomerId(), customer.getName(),
                customer.getAddress(), customer.getPostalCode(), customer.getPhone(), customer.getDivisionId());
        return customerData.stream().map( a -> a.toString()).collect(Collectors.toList());
    }
}


























