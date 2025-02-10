package lk.ijse.gdse71.mobilezone.dao.custom.impl;

import lk.ijse.gdse71.mobilezone.dao.custom.CustomerDAO;
import lk.ijse.gdse71.mobilezone.dao.SQLUtil;
import lk.ijse.gdse71.mobilezone.dto.CustomerDTO;
import lk.ijse.gdse71.mobilezone.entity.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerDAOImpl implements CustomerDAO {
    @Override
    //public ArrayList<CustomerDTO> getAll() throws SQLException, ClassNotFoundException {
    public ArrayList<Customer> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select * from customer");

        //ArrayList<CustomerDTO> customerDTOS = new ArrayList<>();
        ArrayList<Customer> allCustomers = new ArrayList<>();

        while (rst.next()) {
            //CustomerDTO customerDTO = new CustomerDTO(
            Customer entity = new Customer(
                    rst.getString(1),  // Customer ID
                    rst.getString(2),  // Name
                    rst.getString(3),  // NIC
                    rst.getString(4),  // Email
                    rst.getString(5)   // Phone
            );
            //customerDTOS.add(customerDTO);
            allCustomers.add(entity);
        }
        return allCustomers;
    }

    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select customerId from customer order by customerId desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(1);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("C%03d", newIdIndex);
        }
        return "C001";
    }

    @Override
    //public boolean save(CustomerDTO customerDTO) throws SQLException, ClassNotFoundException {
    public boolean save(Customer entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute(
                "insert into customer values (?,?,?,?,?)",
                //customerDTO.getCustomerId(),
                entity.getCustomerId(),
                entity.getName(),
                entity.getNic(),
                entity.getEmail(),
                entity.getPhone()
        );
    }

    @Override
    public boolean delete(String customerId) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("delete from customer where customerId=?", customerId);
    }

    @Override
    //public boolean update(CustomerDTO customerDTO) throws SQLException, ClassNotFoundException {
    public boolean update(Customer entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute(
                "update customer set name=?, nic=?, email=?, phone=? where customerId=?",
                //customerDTO.getName(),
                entity.getName(),
                entity.getNic(),
                entity.getEmail(),
                entity.getPhone(),
                entity.getCustomerId()
        );
    }

    @Override
    //public CustomerDTO findById(String selectedCusId) throws SQLException, ClassNotFoundException {
    public Customer findById(String selectedCusId) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select * from customer where customerId=?", selectedCusId);

        if (rst.next()) {
            //return new CustomerDTO(
            return new Customer(
                    rst.getString(1),  // Customer ID
                    rst.getString(2),  // Name
                    rst.getString(3),  // NIC
                    rst.getString(4),  // Email
                    rst.getString(5)   // Phone
            );
        }
            return null;
    }

    @Override
    public ArrayList<String> getAllIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select customerId from customer");

        ArrayList<String> customerIds = new ArrayList<>();

        while (rst.next()) {
            customerIds.add(rst.getString(1));
        }
        return customerIds;
    }

    @Override
    public ArrayList<String> getAllCustomerIdsToCombo(String orderId) throws SQLException, ClassNotFoundException {
    //public String getCustomerId(String orderId) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select customerId from orders where orderId=? ", orderId);

        ArrayList<String> customerIds = new ArrayList<>();

        while (rst.next()) {
            customerIds.add(rst.getString(1));
        }

        return customerIds;
    }
}
