package org.but.feec.javafx.services;

import org.but.feec.javafx.api.CustomerAddress;
import org.but.feec.javafx.api.CustomerDetails;
import org.but.feec.javafx.data.CustomerRepository;

import java.util.List;

public class CustomerService {
    private CustomerRepository customerRepository;
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }
    public List<CustomerAddress> getCustomerAddress(Long id){
        return customerRepository.getCustomerAddress(id);
    }

    public CustomerDetails getCustomerDetails(Long id){
        return customerRepository.getCustomerDetails(id);
    }

    public void editCustomerDetails(CustomerDetails customerDetails) {
        customerRepository.editCustomerDetails(customerDetails);
    }
}
