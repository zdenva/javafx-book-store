package org.but.feec.javafx.services;

import org.but.feec.javafx.api.AuthenticationDetails;
import org.but.feec.javafx.api.CustomerAddress;
import org.but.feec.javafx.api.CustomerDetails;
import org.but.feec.javafx.data.CustomerRepository;

import java.util.List;

public class CustomerService {
    private CustomerRepository customerRepository;
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public AuthenticationDetails getSaltHashByEmail(String email){
        return customerRepository.getSaltHashByEmail(email);
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

    public void editAddress(CustomerAddress customerAddress) {
        customerRepository.editAddress(customerAddress);
    }
    public void createAddress(Long customerId, CustomerAddress customerAddress){
        customerRepository.createAddress(customerId, customerAddress);
    }

    public void deleteAddress(Long addressId){
        customerRepository.deleteAddress(addressId);
    }
}
