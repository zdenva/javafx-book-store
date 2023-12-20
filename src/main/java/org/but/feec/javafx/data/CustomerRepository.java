package org.but.feec.javafx.data;

import org.but.feec.javafx.api.CustomerAddress;
import org.but.feec.javafx.api.CustomerDetails;
import org.but.feec.javafx.config.DataSourceConfig;
import org.but.feec.javafx.exceptions.DataAcessException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerRepository {
    public List<CustomerAddress> getCustomerAddress(long id){
        try {
            Connection connection = DataSourceConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT address_id, street_number, street_name, city, country_name FROM book_store.customer_address NATURAL JOIN book_store.address NATURAL JOIN book_store.country WHERE customer_id = ?;", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<CustomerAddress> customerAddresses = new ArrayList<>();

            while (resultSet.next()){
                customerAddresses.add(mapToCustomerAddress(resultSet));
            }
            return customerAddresses;
        }catch (SQLException ex){
            throw new DataAcessException("Book catalog could not be loaded.", ex);
        }
    }

    public CustomerDetails getCustomerDetails(Long id){
        try {
            Connection connection = DataSourceConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT customer_id, p.phone_id, first_name, last_name, email, phone_number FROM book_store.customer c LEFT JOIN book_store.customer_phone p ON c.phone_id = p.phone_id WHERE customer_id = ?;", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            CustomerDetails customerDetails = new CustomerDetails();

            customerDetails.setId(resultSet.getLong("customer_id"));
            customerDetails.setFirstName(resultSet.getString("first_name"));
            customerDetails.setLastName(resultSet.getString("last_name"));
            customerDetails.setEmail(resultSet.getString("email"));
            customerDetails.setPhoneNumber(resultSet.getString("phone_number"));
            customerDetails.setPhoneId(resultSet.getLong("phone_id"));

            return customerDetails;
        }catch (SQLException ex){
            throw new DataAcessException("Book catalog could not be loaded.", ex);
        }
    }

    private CustomerAddress mapToCustomerAddress(ResultSet rs) throws SQLException{
        CustomerAddress customerAddress = new CustomerAddress();
        customerAddress.setId(rs.getLong("address_id"));
        customerAddress.setCity(rs.getString("city"));
        customerAddress.setCountry(rs.getString("country_name"));
        customerAddress.setStreetName(rs.getString("street_name"));
        customerAddress.setStreetNumber(rs.getInt("street_number"));
        return customerAddress;
    }

    public void editCustomerDetails(CustomerDetails customerDetails) {
        try {
            boolean hasPhoneId = customerDetails.getPhoneId() != 0;
            editPhoneNumber(customerDetails);
            PreparedStatement preparedStatement = null;
            String customerSQL = "";
            Connection connection = DataSourceConfig.getConnection();
            System.out.println("PhoneID = " + customerDetails.getPhoneId());
            if(!hasPhoneId){
                customerSQL = "UPDATE book_store.customer SET first_name = ?, last_name = ?, email = ?, phone_id = ? WHERE customer_id = ?;";
                preparedStatement = connection.prepareStatement(customerSQL, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setLong(4, customerDetails.getPhoneId());
                preparedStatement.setLong(5, customerDetails.getId());
            }
            else {
                customerSQL = "UPDATE book_store.customer SET first_name = ?, last_name = ?, email = ? WHERE customer_id = ?;";
                preparedStatement = connection.prepareStatement(customerSQL, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setLong(4, customerDetails.getId());
            }
            preparedStatement.setString(1, customerDetails.getFirstName());
            preparedStatement.setString(2, customerDetails.getLastName());
            preparedStatement.setString(3, customerDetails.getEmail());

            try {
                connection.setAutoCommit(false);
                int effectedRows = preparedStatement.executeUpdate();
                if(effectedRows == 0){
                    throw new DataAcessException("Updating customer details failed.");
                }
                connection.commit();
            }
            catch (SQLException ex){
                connection.rollback();
                throw new DataAcessException("Updating customer details failed.", ex);
            }
            finally {
                connection.setAutoCommit(true);
            }
        }catch (SQLException ex){
            throw new DataAcessException("Updating customer details failed.", ex);
        }
    }

    private void editPhoneNumber(CustomerDetails customerDetails) {
        String phoneSQL;
        String exception = "Failed";
        PreparedStatement preparedStatement = null;
        ResultSet generatedKeys = null;
        long generatedPhoneId = 0;
        try {
            Connection connection = DataSourceConfig.getConnection();
            if(customerDetails.getPhoneId() == 0){
                phoneSQL = "INSERT INTO book_store.customer_phone (phone_number) VALUES (?)";
                exception = "Creating phone number failed.";
                preparedStatement = connection.prepareStatement(phoneSQL, Statement.RETURN_GENERATED_KEYS);
            }
            else{
                phoneSQL = "UPDATE book_store.customer_phone SET phone_number = ? WHERE phone_id = ?;";
                exception = "Updating phone number failed.";
                preparedStatement = connection.prepareStatement(phoneSQL, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setLong(2, customerDetails.getPhoneId());
            }
            preparedStatement.setString(1, customerDetails.getPhoneNumber());

            try {
                connection.setAutoCommit(false);
                int effectedRows = preparedStatement.executeUpdate();
                if(effectedRows == 0){
                    throw new DataAcessException(exception);
                }
                connection.commit();
                if (customerDetails.getPhoneId() == 0) {
                    generatedKeys = preparedStatement.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        generatedPhoneId = generatedKeys.getLong(1);
                        customerDetails.setPhoneId(generatedPhoneId);
                    } else {
                        System.out.println("Retrieving generated phone_id failed.");
                    }
                }
            }
            catch (SQLException ex){
                connection.rollback();
                throw new DataAcessException(exception, ex);
            }
            finally {
                connection.setAutoCommit(true);
            }
        }catch (SQLException ex){
            throw new DataAcessException(exception, ex);
        }
    }
}