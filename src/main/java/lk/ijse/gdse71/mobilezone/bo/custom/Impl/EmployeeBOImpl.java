package lk.ijse.gdse71.mobilezone.bo.custom.Impl;

import lk.ijse.gdse71.mobilezone.bo.custom.EmployeeBO;
import lk.ijse.gdse71.mobilezone.dao.DAOFactory;
import lk.ijse.gdse71.mobilezone.dao.custom.EmployeeDAO;
import lk.ijse.gdse71.mobilezone.dao.custom.impl.EmployeeDAOImpl;
import lk.ijse.gdse71.mobilezone.dto.EmployeeDTO;
import lk.ijse.gdse71.mobilezone.entity.Employee;

import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeBOImpl implements EmployeeBO {
    //EmployeeDAO employeeDAO = new EmployeeDAOImpl();
    EmployeeDAO employeeDAO = (EmployeeDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.EMPLOYEE);

    public ArrayList<EmployeeDTO> getAllEmployees() throws SQLException, ClassNotFoundException {
        ArrayList<EmployeeDTO> employeeDTOS = new ArrayList<>();

        //ArrayList<EmployeeDTO> employees = employeeDAO.getAll();
        ArrayList<Employee> employees = employeeDAO.getAll();

        //for (EmployeeDTO employeeDTO : employees) {
        for (Employee employee : employees) {
            employeeDTOS.add(new EmployeeDTO(
                    //employeeDTO.getEmployeeId(),
                    employee.getEmployeeId(),
                    employee.getUserId(),
                    employee.getName(),
                    employee.getNic(),
                    employee.getDesignation(),
                    employee.getEmail(),
                    employee.getContact(),
                    employee.getSalary(),
                    employee.getCompensation(),
                    employee.getTotalSalary()
            ));
        }
        return employeeDTOS;
    }

    public String getNextEmployeeId() throws SQLException, ClassNotFoundException {
        return employeeDAO.getNextId();
    }

    public boolean saveEmployee(EmployeeDTO employeeDTO) throws SQLException, ClassNotFoundException {
        //return employeeDAO.save(new EmployeeDTO(
        return employeeDAO.save(new Employee(
                employeeDTO.getEmployeeId(),
                employeeDTO.getUserId(),
                employeeDTO.getName(),
                employeeDTO.getNic(),
                employeeDTO.getDesignation(),
                employeeDTO.getEmail(),
                employeeDTO.getContact(),
                employeeDTO.getSalary(),
                employeeDTO.getCompensation(),
                employeeDTO.getTotalSalary()
        ));
    }

    public boolean deleteEmployee(String employeeId) throws SQLException, ClassNotFoundException {
        return employeeDAO.delete(employeeId);
    }

    public boolean updateEmployee(EmployeeDTO employeeDTO) throws SQLException, ClassNotFoundException {
        //return employeeDAO.update(new EmployeeDTO(
        return employeeDAO.update(new Employee(
                employeeDTO.getEmployeeId(),
                employeeDTO.getUserId(),
                employeeDTO.getName(),
                employeeDTO.getNic(),
                employeeDTO.getDesignation(),
                employeeDTO.getEmail(),
                employeeDTO.getContact(),
                employeeDTO.getSalary(),
                employeeDTO.getCompensation(),
                employeeDTO.getTotalSalary()
        ));
    }

    public boolean checkUserIds(String selectedUserId) throws SQLException, ClassNotFoundException {
        return employeeDAO.checkUserIds(selectedUserId);
    }

    public ArrayList<String> getAllEmployeeIds() throws SQLException, ClassNotFoundException {
        ArrayList<String> employees =  employeeDAO.getAllIds();
        ArrayList<String> employeeIds = new ArrayList<>();

        for (String employeeId : employees) {
            employeeIds.add(employeeId);
        }
        return employeeIds;
    }

    public boolean checkUserIds(String selectedUserId, String currentEmployeeId) throws SQLException, ClassNotFoundException {
        return employeeDAO.checkUserIds(selectedUserId, currentEmployeeId);
    }

    // not used in emp_controller
    /*
    public EmployeeDTO findById(String id) throws SQLException, ClassNotFoundException {
        return null;
    }*/

    /*public boolean checkUserIdExist(String userId) throws SQLException {
        //LogInCredentialModel logInCredentialModel = new LogInCredentialModel();
        //return logInCredentialModel.isExist(userId);
        //return SQLUtil.execute("select userId from logInCredentials where userId=?", userId);
    }*/
}
