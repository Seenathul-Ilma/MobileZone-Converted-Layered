package lk.ijse.gdse71.mobilezone.dao.custom.impl;

import lk.ijse.gdse71.mobilezone.dao.SQLUtil;
import lk.ijse.gdse71.mobilezone.dao.custom.SupplierDAO;
import lk.ijse.gdse71.mobilezone.dto.SupplierDTO;
import lk.ijse.gdse71.mobilezone.entity.Supplier;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierDAOImpl implements SupplierDAO {
    @Override
    //public ArrayList<SupplierDTO> getAll() throws SQLException, ClassNotFoundException {
    public ArrayList<Supplier> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select * from supplier");

        //ArrayList<SupplierDTO> supplierDTOS = new ArrayList<>();
        ArrayList<Supplier> allSuppliers = new ArrayList<>();

        while (rst.next()) {
            //SupplierDTO supplierDTO = new SupplierDTO(
            Supplier entity = new Supplier(
                    rst.getString(1),  // Supplier ID
                    rst.getString(2),  // Company Name
                    rst.getString(3),  // Contact Person
                    rst.getString(4),  // NIC
                    rst.getString(5),  // Address
                    rst.getString(6),  // Email
                    rst.getString(7)   // Phone
            );
            //supplierDTOS.add(supplierDTO);
            allSuppliers.add(entity);
        }
        //return supplierDTOS;
        return allSuppliers;
    }

    @Override
    //public boolean save(SupplierDTO supplierDTO) throws SQLException, ClassNotFoundException {
    public boolean save(Supplier entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute(
                "insert into supplier values (?,?,?,?,?,?,?)",
                //supplierDTO.getSupplierId(),
                entity.getSupplierId(),
                entity.getCompanyName(),
                entity.getContactPerson(),
                entity.getNic(),
                entity.getAddress(),
                entity.getEmail(),
                entity.getPhone()
        );
    }

    @Override
    public boolean delete(String supplierId) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("delete from supplier where supplierId=?", supplierId);
    }

    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select supplierId from supplier order by supplierId desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(1);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("S%03d", newIdIndex);
        }
        return "S001";
    }

    @Override
    //public boolean update(SupplierDTO supplierDTO) throws SQLException, ClassNotFoundException {
    public boolean update(Supplier entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute(
                "update supplier set companyName=?, contactPerson=?, nic=?, address=?, email=?, phone=? where supplierId=?",
                //supplierDTO.getCompanyName(),
                entity.getCompanyName(),
                entity.getContactPerson(),
                entity.getNic(),
                entity.getAddress(),
                entity.getEmail(),
                entity.getPhone(),
                entity.getSupplierId()
        );
    }

    @Override
    //public SupplierDTO findById(String selectedSupplierId) throws SQLException, ClassNotFoundException {
    public Supplier findById(String selectedSupplierId) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select * from supplier where supplierId=?", selectedSupplierId);

        if (rst.next()) {
            //return new SupplierDTO(
            return new Supplier(
                    rst.getString(1),  // Customer ID
                    rst.getString(2),  // Name
                    rst.getString(3),  // NIC
                    rst.getString(4),  // Email
                    rst.getString(5),   // Phone
                    rst.getString(6),
                    rst.getString(7)
            );
        }
        return null;
    }

    @Override
    public ArrayList<String> getAllIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select supplierId from supplier");

        ArrayList<String> supplierIds = new ArrayList<>();

        while (rst.next()) {
            supplierIds.add(rst.getString(1));
        }

        return supplierIds;
    }
}
