package lk.ijse.gdse71.mobilezone.dao.custom.impl;

import lk.ijse.gdse71.mobilezone.dao.custom.EmployeeDAO;
import lk.ijse.gdse71.mobilezone.dao.SQLUtil;
import lk.ijse.gdse71.mobilezone.dto.EmployeeDTO;
import lk.ijse.gdse71.mobilezone.entity.Customer;
import lk.ijse.gdse71.mobilezone.entity.Employee;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeDAOImpl implements EmployeeDAO {
    @Override
    //public ArrayList<EmployeeDTO> getAll() throws SQLException, ClassNotFoundException {
    public ArrayList<Employee> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select * from employee");

        //ArrayList<EmployeeDTO> employeeDTOS = new ArrayList<>();
        ArrayList<Employee> allEmployees = new ArrayList<>();

        while (rst.next()) {
            //EmployeeDTO employeeDTO = new EmployeeDTO(
            Employee entity = new Employee(
                    rst.getString(1),  // Customer ID
                    rst.getString(2),  // Name
                    rst.getString(3),  // NIC
                    rst.getString(4),  // Email
                    rst.getString(5),  // Phone
                    rst.getString(6),
                    rst.getString(7),
                    rst.getString(8),
                    rst.getString(9),
                    rst.getString(10)
            );
            //employeeDTOS.add(employeeDTO);
            allEmployees.add(entity);
        }
        //return employeeDTOS;
        return allEmployees;
    }

    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select employeeId from employee order by employeeId desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(2);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("EM%02d", newIdIndex);
        }
        return "EM01";
    }

    @Override
    //public boolean save(EmployeeDTO employeeDTO) throws SQLException, ClassNotFoundException {
    public boolean save(Employee entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute(
                "insert into employee values (?,?,?,?,?,?,?,?,?,?)",
                //employeeDTO.getEmployeeId(),
                entity.getEmployeeId(),
                entity.getUserId(),
                entity.getName(),
                entity.getNic(),
                entity.getDesignation(),
                entity.getEmail(),
                entity.getContact(),
                entity.getSalary(),
                entity.getCompensation(),
                entity.getTotalSalary()
        );
    }

    @Override
    public boolean delete(String employeeId) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("delete from employee where employeeId=?", employeeId);
    }

    @Override
    //public boolean update(EmployeeDTO employeeDTO) throws SQLException, ClassNotFoundException {
    public boolean update(Employee entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute(
                "update employee set userId=?, name=?, nic=?, designation=?, email=?, contact=?, salary=?, compensation=?, totalSalary=? where employeeId=?",
                //employeeDTO.getUserId(),
                entity.getUserId(),
                entity.getName(),
                entity.getNic(),
                entity.getDesignation(),
                entity.getEmail(),
                entity.getContact(),
                entity.getSalary(),
                entity.getCompensation(),
                entity.getTotalSalary(),
                entity.getEmployeeId()
        );
    }

    @Override
    public boolean checkUserIds(String selectedUserId) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("select employeeId, count(employeeId) from employee where userId=? group by employeeId;", selectedUserId);
        if(resultSet.next()){
            return true;
        }
        return false;
    }

    @Override
    public ArrayList<String> getAllIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select employeeId from employee");

        ArrayList<String> employeeIds = new ArrayList<>();

        while (rst.next()) {
            employeeIds.add(rst.getString(1));
        }
        return employeeIds;
    }

    @Override
    //public EmployeeDTO findById(String id) throws SQLException, ClassNotFoundException {
    public Employee findById(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select * from customer where employeeId=?", id);

        if (rst.next()) {
            //return new EmployeeDTO(
            return new Employee(
                    rst.getString(1),  // Employee ID
                    rst.getString(2),  // UserId
                    rst.getString(3),  // name
                    rst.getString(4),  // nic
                    rst.getString(5),   // designation
                    rst.getString(6),
                    rst.getString(7),
                    rst.getString(8),
                    rst.getString(9),
                    rst.getString(10)
            );
        }
        return null;
    }

    @Override
    public boolean checkUserIds(String selectedUserId, String currentEmployeeId) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute(
                "SELECT COUNT(*) FROM employee WHERE userId = ? AND employeeId != ?;",
                selectedUserId, currentEmployeeId
        );

        if (resultSet.next()) {
            return resultSet.getInt(1) > 0; // If count > 0, userId is duplicated
        }
        return false;
    }

    /*public boolean checkUserIdExist(String userId) throws SQLException {
        //LogInCredentialModel logInCredentialModel = new LogInCredentialModel();
        //return logInCredentialModel.isExist(userId);
        //return SQLUtil.execute("select userId from logInCredentials where userId=?", userId);
    }*/
}
