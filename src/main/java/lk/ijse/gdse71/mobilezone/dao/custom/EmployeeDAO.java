package lk.ijse.gdse71.mobilezone.dao.custom;

import lk.ijse.gdse71.mobilezone.dao.CrudDAO;
import lk.ijse.gdse71.mobilezone.dto.EmployeeDTO;
import lk.ijse.gdse71.mobilezone.entity.Employee;

import java.sql.SQLException;

//public interface EmployeeDAO extends CrudDAO<EmployeeDTO> {
public interface EmployeeDAO extends CrudDAO<Employee> {
    boolean checkUserIds(String selectedUserId) throws SQLException, ClassNotFoundException;

    boolean checkUserIds(String selectedUserId, String employeeId) throws SQLException, ClassNotFoundException;
}
