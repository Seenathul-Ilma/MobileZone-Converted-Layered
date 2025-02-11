package lk.ijse.gdse71.mobilezone.bo.custom.Impl;

import lk.ijse.gdse71.mobilezone.bo.custom.CustomerBO;
import lk.ijse.gdse71.mobilezone.dao.DAOFactory;
import lk.ijse.gdse71.mobilezone.dao.custom.CategoryDAO;
import lk.ijse.gdse71.mobilezone.dao.custom.CustomerDAO;
import lk.ijse.gdse71.mobilezone.dao.custom.impl.CustomerDAOImpl;
import lk.ijse.gdse71.mobilezone.dto.CustomerDTO;
import lk.ijse.gdse71.mobilezone.entity.Customer;

import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerBOImpl implements CustomerBO {
    //CustomerDAO customerDAO = new CustomerDAOImpl();
    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.CUSTOMER);

    @Override
    public ArrayList<CustomerDTO> getAllCustomers() throws SQLException, ClassNotFoundException {
        ArrayList<CustomerDTO> customerDTOS = new ArrayList<>();

        //ArrayList<CustomerDTO> customers = customerDAO.getAll();
        ArrayList<Customer> customers = customerDAO.getAll();

        //for (CustomerDTO customerDTO : customers) {
        for (Customer customer : customers) {
            //customerDTOS.add(new CustomerDTO(customerDTO.getCustomerId(), customerDTO.getName(), customerDTO.getNic(), customerDTO.getEmail(), customerDTO.getPhone()));
            customerDTOS.add(new CustomerDTO(customer.getCustomerId(), customer.getName(), customer.getNic(), customer.getEmail(), customer.getPhone()));
        }
        return customerDTOS;
    }

    @Override
    public String getNextCustomerId() throws SQLException, ClassNotFoundException {
        return customerDAO.getNextId();
    }

    @Override
    public boolean saveCustomer(CustomerDTO customerDTO) throws SQLException, ClassNotFoundException {
        //return customerDAO.save(new CustomerDTO(
        return customerDAO.save(new Customer(
                customerDTO.getCustomerId(), customerDTO.getName(), customerDTO.getNic(), customerDTO.getEmail(), customerDTO.getPhone()
        ));
    }

    @Override
    public boolean deleteCustomer(String customerId) throws SQLException, ClassNotFoundException {
        return customerDAO.delete(customerId);
    }

    @Override
    public boolean updateCustomer(CustomerDTO customerDTO) throws SQLException, ClassNotFoundException {
        //return customerDAO.update(new CustomerDTO(
        return customerDAO.update(new Customer(
                customerDTO.getCustomerId(), customerDTO.getName(), customerDTO.getNic(), customerDTO.getEmail(), customerDTO.getPhone()
        ));
    }

    @Override
    public ArrayList<String> getAllCustomerIds() throws SQLException, ClassNotFoundException {
        ArrayList<String> customers =  customerDAO.getAllIds();
        ArrayList<String> customerIds = new ArrayList<>();

        for (String customerId : customers) {
            customerIds.add(customerId);
        }
        return customerIds;
    }

    @Override
    public CustomerDTO findByCustomerId(String selectedCusId) throws SQLException, ClassNotFoundException {
        //return customerDAO.findById(selectedCusId);

        /*Customer customer = customerDAO.findById(selectedCusId);
        return customer != null ? new CustomerDTO(customer.getCustomerId(), customer.getName(), customer.getNic(), customer.getEmail(), customer.getPhone()) : null;*/

        // Step 1: Retrieve the customer entity from DAO
        Customer customer = customerDAO.findById(selectedCusId);

        // Step 2: Check if the customer exists
        if (customer == null) {
            return null; // Return null if no customer found
        } else {
            // Step 3: Convert Customer entity to CustomerDTO
            CustomerDTO customerDTO = new CustomerDTO(
                    customer.getCustomerId(),
                    customer.getName(),
                    customer.getNic(),
                    customer.getEmail(),
                    customer.getPhone()
            );
            return customerDTO;   // Step 4: Return the converted DTO
        }
    }

    // not used in customer_controller
    /*
    public ArrayList<String> getAllCustomerIdsToCombo(String orderId) throws SQLException, ClassNotFoundException {
        return customerDAO.getAllCustomerIdsToCombo(orderId);
    }*/
}
