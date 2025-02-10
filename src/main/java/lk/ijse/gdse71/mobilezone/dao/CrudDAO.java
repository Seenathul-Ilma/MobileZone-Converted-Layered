package lk.ijse.gdse71.mobilezone.dao;

import lk.ijse.gdse71.mobilezone.dto.CustomerDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CrudDAO<T> extends SuperDAO {
    ArrayList<T> getAll() throws SQLException, ClassNotFoundException;      // Returns a 'DTO' Arraylist

    String getNextId() throws SQLException, ClassNotFoundException;         // Returns a String value

    boolean save(T dto) throws SQLException, ClassNotFoundException;        // a DTO as a parameter & Returns a boolean

    boolean delete(String id) throws SQLException, ClassNotFoundException;  // Returns a single attribute/variable

    boolean update(T dto) throws SQLException, ClassNotFoundException;      // a DTO as a parameter& Returns a boolean

    ArrayList<String> getAllIds() throws SQLException, ClassNotFoundException;  // Returns a 'String' ArrayList

    T findById(String id) throws SQLException, ClassNotFoundException;      // A String value as parameter, & Returns a 'DTO'
}