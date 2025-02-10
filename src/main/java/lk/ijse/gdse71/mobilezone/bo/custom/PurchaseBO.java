package lk.ijse.gdse71.mobilezone.bo.custom;

import lk.ijse.gdse71.mobilezone.bo.SuperBO;
import lk.ijse.gdse71.mobilezone.dto.PurchaseDTO;
import lk.ijse.gdse71.mobilezone.dto.PurchaseDetailDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface PurchaseBO extends SuperBO {
    String getNextPurchaseId() throws SQLException, ClassNotFoundException;

    boolean savePurchase(PurchaseDTO purchaseDTO) throws SQLException, ClassNotFoundException;

    ArrayList<String> getAllPurchaseIds() throws SQLException, ClassNotFoundException;

    PurchaseDetailDTO findItemByPurchaseId(String purchaseId, String selectedItemId) throws SQLException, ClassNotFoundException;

    String getPurchaseReturnOrNot(String purchaseId, String selectedItemId) throws SQLException, ClassNotFoundException;

    PurchaseDTO findPurchaseById(String purchaseId) throws SQLException, ClassNotFoundException;

    String confirmItemBought(String selectedPurchaseId, String selectedItemId) throws SQLException, ClassNotFoundException;
}
