package lk.ijse.gdse71.mobilezone.bo.custom;

import lk.ijse.gdse71.mobilezone.bo.SuperBO;
import lk.ijse.gdse71.mobilezone.dto.EmployeeDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface EmployeeBO extends SuperBO {
    ArrayList<EmployeeDTO> getAllEmployees() throws SQLException, ClassNotFoundException;

    String getNextEmployeeId() throws SQLException, ClassNotFoundException;

    boolean deleteEmployee(String employeeId) throws SQLException, ClassNotFoundException;

    boolean checkUserIds(String selectedUserId) throws SQLException, ClassNotFoundException;

    boolean saveEmployee(EmployeeDTO employeeDTO) throws SQLException, ClassNotFoundException;

    boolean checkUserIds(String selectedUserId, String employeeId) throws SQLException, ClassNotFoundException;

    boolean updateEmployee(EmployeeDTO employeeDTO) throws SQLException, ClassNotFoundException;

    ArrayList<String> getAllEmployeeIds() throws SQLException, ClassNotFoundException;
}
