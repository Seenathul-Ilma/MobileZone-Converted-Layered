package lk.ijse.gdse71.mobilezone.dao.custom;

import lk.ijse.gdse71.mobilezone.dao.CrudDAO;
import lk.ijse.gdse71.mobilezone.dto.PurchaseDTO;
import lk.ijse.gdse71.mobilezone.dto.PurchaseDetailDTO;
import lk.ijse.gdse71.mobilezone.entity.Purchase;

import java.sql.SQLException;
import java.util.ArrayList;

//public interface PurchaseDAO extends CrudDAO<PurchaseDTO> {
public interface PurchaseDAO extends CrudDAO<Purchase> {
    /*
    //String getNextId() throws SQLException;

    //boolean save(PurchaseDTO purchaseDTO) throws SQLException;*/
    //boolean save(PurchaseDetailDTO purchaseDetailDTO) throws SQLException;

    //boolean update(PurchaseDetailDTO purchaseDetailDTO) throws SQLException;

    //ArrayList<String> getAllIds() throws SQLException;

    //PurchaseDTO findById(String purchaseId) throws SQLException;

    //PurchaseDetailDTO findById(String purchaseId, String selectedItemId) throws SQLException;

    //String getReturnOrNot(String purchaseId, String selectedItemId) throws SQLException;

    //String confirmItemBought(String selectedPurchaseId, String selectedItemId) throws SQLException;

    //String confirmBoughtFromSupplier(String selectedSupplierId, String selectedPurchaseId, String selectedItemId) throws SQLException;
}
