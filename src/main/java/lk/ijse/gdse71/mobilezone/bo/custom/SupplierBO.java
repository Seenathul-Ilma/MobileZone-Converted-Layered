package lk.ijse.gdse71.mobilezone.bo.custom;

import lk.ijse.gdse71.mobilezone.bo.SuperBO;
import lk.ijse.gdse71.mobilezone.dto.SupplierDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface SupplierBO extends SuperBO {
    ArrayList<String> getAllSupplierIds() throws SQLException, ClassNotFoundException;

    SupplierDTO findSupplierById(String selectedSupplierId) throws SQLException, ClassNotFoundException;

    ArrayList<SupplierDTO> getAllSuppliers() throws SQLException, ClassNotFoundException;

    String getNextSupplierId() throws SQLException, ClassNotFoundException;

    boolean deleteSupplier(String supplierId) throws SQLException, ClassNotFoundException;

    boolean saveSupplier(SupplierDTO supplierDTO) throws SQLException, ClassNotFoundException;

    boolean updateSupplier(SupplierDTO supplierDTO) throws SQLException, ClassNotFoundException;
}
