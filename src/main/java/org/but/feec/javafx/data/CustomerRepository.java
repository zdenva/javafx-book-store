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

    public CustomerDetails getCustomerDetails(Long customerId){
        try {
            Connection connection = DataSourceConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT customer_id, p.phone_id, first_name, last_name, email, phone_number FROM book_store.customer c LEFT JOIN book_store.customer_phone p ON c.phone_id = p.phone_id WHERE customer_id = ?;", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, customerId);
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

    public void editAddress(CustomerAddress customerAddress) {
        try {
            customerAddress.setCountryId(getOrCreateCountry(customerAddress.getCountry()));
            System.out.println("Id is " + customerAddress.getCountryId());
            Connection connection = DataSourceConfig.getConnection();
            String addressSQL = "UPDATE book_store.address SET street_name = ?, street_number = ?, city = ?, country_id = ? WHERE address_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(addressSQL, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, customerAddress.getStreetName());
            preparedStatement.setInt(2, customerAddress.getStreetNumber());
            preparedStatement.setString(3, customerAddress.getCity());
            preparedStatement.setLong(4, customerAddress.getCountryId());
            preparedStatement.setLong(5, customerAddress.getId());
            try {
                connection.setAutoCommit(false);
                int effectedRows = preparedStatement.executeUpdate();
                if(effectedRows == 0){
                    throw new DataAcessException("Updating customer address failed.");
                }
                connection.commit();
            }
            catch (SQLException ex){
                connection.rollback();
                throw new DataAcessException("Updating customer address failed.", ex);
            }
            finally {
                connection.setAutoCommit(true);
            }
        }catch (SQLException ex){
            throw new DataAcessException("Updating customer address failed.", ex);
        }
    }

    public void createAddress(Long customerId, CustomerAddress customerAddress){
        String createAddress = "INSERT INTO book_store.address (city, street_name, street_number, country_id) VALUES (?, ?, ?, ?)";
        Long address_id;
        ResultSet generatedKey;
        try {
            Connection connection = DataSourceConfig.getConnection();
            customerAddress.setCountryId(getOrCreateCountry(customerAddress.getCountry()));
            PreparedStatement preparedStatement = connection.prepareStatement(createAddress, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, customerAddress.getCity());
            preparedStatement.setString(2, customerAddress.getStreetName());
            preparedStatement.setInt(3, customerAddress.getStreetNumber());
            preparedStatement.setLong(4, customerAddress.getCountryId());
            try {
                connection.setAutoCommit(false);
                int effectedRows = preparedStatement.executeUpdate();
                if(effectedRows == 0){
                    throw new DataAcessException("Zero effected rows");
                }
                connection.commit();
                generatedKey = preparedStatement.getGeneratedKeys();
                if (generatedKey.next()){
                    address_id = generatedKey.getLong(1);
                    addNewAddressToCustomer(address_id, customerId);
                }else {
                    System.out.println("Retrieving generated address_id failed.");
                }
            }
            catch (SQLException ex){
                connection.rollback();
                throw new DataAcessException("Creating new address failed.");
            }
        }
        catch (SQLException ex){
            throw new DataAcessException("Exception.", ex);
        }
    }

    private void addNewAddressToCustomer(Long addressId, Long customerId) {
        String addressToCustomerSQL = "INSERT INTO book_store.customer_address (address_id, customer_id, status_id) VALUES (?, ?, ?)";
        String exception = "Creating connection address to customer failed.";
        try {
            Connection connection = DataSourceConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(addressToCustomerSQL);
            preparedStatement.setLong(1, addressId);
            preparedStatement.setLong(2, customerId);
            preparedStatement.setLong(3, 3);
            try {
                connection.setAutoCommit(false);
                int effectedRows  = preparedStatement.executeUpdate();
                if(effectedRows == 0){
                    throw new DataAcessException(exception);
                }
                connection.commit();
            }catch (SQLException ex){
                connection.rollback();
                throw new DataAcessException(exception, ex);
            }

        }
        catch (SQLException ex){
            throw new DataAcessException(exception, ex);
        }
    }

    public Long getOrCreateCountry(String countryName){
        String countrySQL = "SELECT country_id FROM book_store.country WHERE country_name = ?;";
        String createCountrySQL = "INSERT INTO book_store.country (country_name) VALUES (?)";
        String exception2 = "Creating new country failed.";
        String exception = "Selecting country id failed.";
        long id = 0L;
        try {
            ResultSet generatedKey;
            Connection connection = DataSourceConfig.getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement(countrySQL, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, countryName);
            ResultSet resultSet = preparedStatement.executeQuery();
             if(resultSet.next()){
                 id = resultSet.getLong("country_id");
             }
             if(id == 0){
                 try {

                     PreparedStatement preparedStatement2 = connection.prepareStatement(createCountrySQL, Statement.RETURN_GENERATED_KEYS);
                     preparedStatement2.setString(1, countryName);
                     try {
                         connection.setAutoCommit(false);
                         int effectedRows = preparedStatement2.executeUpdate();
                         if(effectedRows == 0){
                             throw new DataAcessException(exception2);
                         }
                         connection.commit();
                         generatedKey= preparedStatement2.getGeneratedKeys();
                         if (generatedKey.next()) {
                             id = generatedKey.getLong(1 );
                         } else {
                             System.out.println("Retrieving generated country_id failed.");
                         }
                     }
                     catch (SQLException ex){
                         connection.rollback();
                         throw new DataAcessException(exception2, ex);
                     }
                     finally {
                         connection.setAutoCommit(true);
                     }
                 }
                 catch (SQLException ex){
                    throw new DataAcessException(exception2, ex);
                 }
             }
            return id;
        }catch (SQLException ex){
            throw new DataAcessException(exception, ex);
        }
    }
    public void deleteAddress(Long addressId){
        String deleteAddressSQL = "DELETE FROM book_store.address WHERE address_id = ?";
        String deleteAddressCustomerConnectionSQL = "DELETE FROM book_store.customer_address WHERE address_id = ?";
        String exception2 = "Deleting address failed.";
        String exception = "Deleting address connection to customer failed.";
        try {
            Connection connection = DataSourceConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(deleteAddressCustomerConnectionSQL);
            preparedStatement.setLong(1, addressId);
            try {
                connection.setAutoCommit(false);
                int effectedRows = preparedStatement.executeUpdate();
                if (effectedRows == 0){
                    throw new DataAcessException(exception);
                }
                connection.commit();
            }
            catch (SQLException ex){
                connection.rollback();
                throw new DataAcessException(exception, ex);
            }
            PreparedStatement preparedStatement2 = connection.prepareStatement(deleteAddressSQL);
            preparedStatement2.setLong(1, addressId);
            try {
                connection.setAutoCommit(false);
                int effectedRows = preparedStatement2.executeUpdate();
                if (effectedRows == 0){
                    throw new DataAcessException(exception2);
                }
                connection.commit();
            }
            catch (SQLException ex){
                connection.rollback();
                throw new DataAcessException(exception2, ex);
            }
        }catch (SQLException ex){
            throw new DataAcessException(exception, ex);
        }
    }
}