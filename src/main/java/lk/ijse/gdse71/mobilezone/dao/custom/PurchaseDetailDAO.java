package lk.ijse.gdse71.mobilezone.dao.custom;

import lk.ijse.gdse71.mobilezone.dao.CrudDAO;
import lk.ijse.gdse71.mobilezone.dto.PurchaseDetailDTO;
import lk.ijse.gdse71.mobilezone.entity.PurchaseDetail;

import java.sql.SQLException;

//public interface PurchaseDetailDAO extends CrudDAO<PurchaseDetailDTO> {
public interface PurchaseDetailDAO extends CrudDAO<PurchaseDetail> {
    String getReturnOrNot(String purchaseId, String selectedItemId) throws SQLException, ClassNotFoundException;

    //PurchaseDetailDTO findById(String purchaseId, String selectedItemId) throws SQLException, ClassNotFoundException;
    PurchaseDetail findById(String purchaseId, String selectedItemId) throws SQLException, ClassNotFoundException;

    String confirmItemBought(String selectedPurchaseId, String selectedItemId) throws SQLException, ClassNotFoundException;
}
