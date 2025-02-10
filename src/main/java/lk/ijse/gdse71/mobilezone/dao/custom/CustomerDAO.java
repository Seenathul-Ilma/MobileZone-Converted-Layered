package lk.ijse.gdse71.mobilezone.dao.custom;

import lk.ijse.gdse71.mobilezone.dao.CrudDAO;
import lk.ijse.gdse71.mobilezone.dto.CustomerDTO;
import lk.ijse.gdse71.mobilezone.entity.Customer;

import java.sql.SQLException;
import java.util.ArrayList;

//public interface CustomerDAO extends CrudDAO<CustomerDTO> {
public interface CustomerDAO extends CrudDAO<Customer> {
    ArrayList<String> getAllCustomerIdsToCombo(String orderId) throws SQLException, ClassNotFoundException;
}
