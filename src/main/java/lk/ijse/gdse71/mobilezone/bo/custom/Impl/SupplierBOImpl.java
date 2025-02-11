package lk.ijse.gdse71.mobilezone.bo.custom.Impl;

import lk.ijse.gdse71.mobilezone.bo.custom.SupplierBO;
import lk.ijse.gdse71.mobilezone.dao.DAOFactory;
import lk.ijse.gdse71.mobilezone.dao.custom.SupplierDAO;
import lk.ijse.gdse71.mobilezone.dao.custom.impl.SupplierDAOImpl;
import lk.ijse.gdse71.mobilezone.dto.CustomerDTO;
import lk.ijse.gdse71.mobilezone.dto.ExpenseDTO;
import lk.ijse.gdse71.mobilezone.dto.SupplierDTO;
import lk.ijse.gdse71.mobilezone.entity.Customer;
import lk.ijse.gdse71.mobilezone.entity.Supplier;

import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierBOImpl implements SupplierBO {
    //SupplierDAO supplierDAO = new SupplierDAOImpl();
    SupplierDAO supplierDAO = (SupplierDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.SUPPLIER);

    @Override
    public ArrayList<SupplierDTO> getAllSuppliers() throws SQLException, ClassNotFoundException {
        ArrayList<SupplierDTO> supplierDTOS = new ArrayList<>();

        //ArrayList<SupplierDTO> suppliers = supplierDAO.getAll();
        ArrayList<Supplier> suppliers = supplierDAO.getAll();

        //for (SupplierDTO supplierDTO : suppliers) {
        for (Supplier supplier : suppliers) {
            //supplierDTOS.add(new SupplierDTO(
            supplierDTOS.add(new SupplierDTO(
                    //supplierDTO.getSupplierId(),
                    supplier.getSupplierId(),
                    supplier.getCompanyName(),
                    supplier.getContactPerson(),
                    supplier.getNic(),
                    supplier.getAddress(),
                    supplier.getEmail(),
                    supplier.getPhone()
            ));
        }
        return supplierDTOS;
    }

    @Override
    public boolean saveSupplier(SupplierDTO supplierDTO) throws SQLException, ClassNotFoundException {
        //return supplierDAO.save(new SupplierDTO(
        return supplierDAO.save(new Supplier(
                supplierDTO.getSupplierId(),
                supplierDTO.getCompanyName(),
                supplierDTO.getContactPerson(),
                supplierDTO.getNic(),
                supplierDTO.getAddress(),
                supplierDTO.getEmail(),
                supplierDTO.getPhone()
        ));
    }

    @Override
    public boolean deleteSupplier(String supplierId) throws SQLException, ClassNotFoundException {
        return supplierDAO.delete(supplierId);
    }

    @Override
    public String getNextSupplierId() throws SQLException, ClassNotFoundException {
        return supplierDAO.getNextId();
    }

    @Override
    public boolean updateSupplier(SupplierDTO supplierDTO) throws SQLException, ClassNotFoundException {
        //return supplierDAO.update(new SupplierDTO(
        return supplierDAO.update(new Supplier(
                supplierDTO.getSupplierId(),
                supplierDTO.getCompanyName(),
                supplierDTO.getContactPerson(),
                supplierDTO.getNic(),
                supplierDTO.getAddress(),
                supplierDTO.getEmail(),
                supplierDTO.getPhone()
        ));
    }

    @Override
    //public SupplierDTO findSupplierById(String selectedSupplierId) throws SQLException, ClassNotFoundException {
    public SupplierDTO findSupplierById(String selectedSupplierId) throws SQLException, ClassNotFoundException {
        //return supplierDAO.findById(selectedSupplierId);

        // Step 1: Retrieve the supplier entity from DAO
        Supplier supplier = supplierDAO.findById(selectedSupplierId);

        // Step 2: Check if the supplier exists
        if (supplier == null) {
            return null; // Return null if no supplier found
        } else {
            // Step 3: Convert Supplier entity to SupplierDTO
            SupplierDTO supplierDTO = new SupplierDTO(
                    supplier.getSupplierId(),
                    supplier.getCompanyName(),
                    supplier.getContactPerson(),
                    supplier.getNic(),
                    supplier.getAddress(),
                    supplier.getEmail(),
                    supplier.getPhone()
            );
            return supplierDTO;   // Step 4: Return the converted DTO
        }
    }

    @Override
    public ArrayList<String> getAllSupplierIds() throws SQLException, ClassNotFoundException {
            ArrayList<String> suppliers =  supplierDAO.getAllIds();
            ArrayList<String> supplierIds = new ArrayList<>();

            for (String supplierId : suppliers) {
                supplierIds.add(supplierId);
            }
            return supplierIds;
    }
}
