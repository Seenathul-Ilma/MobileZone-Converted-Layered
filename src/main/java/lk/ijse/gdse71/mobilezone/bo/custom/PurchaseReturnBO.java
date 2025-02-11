package lk.ijse.gdse71.mobilezone.bo.custom;

import lk.ijse.gdse71.mobilezone.bo.SuperBO;
import lk.ijse.gdse71.mobilezone.dto.PurchaseDetailDTO;
import lk.ijse.gdse71.mobilezone.dto.PurchaseReturnDTO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

public interface PurchaseReturnBO extends SuperBO {
    String getNextPurchaseReturnId() throws SQLException, ClassNotFoundException;

    boolean saveReturnDetailsList(ArrayList<PurchaseDetailDTO> purchaseDetailDTOS) throws SQLException, ClassNotFoundException;

    boolean savePurchaseReturn(PurchaseReturnDTO purchaseReturnDTO) throws SQLException;

    Map<Integer, Integer> getMonthlyPurchaseReturnData() throws SQLException, ClassNotFoundException;

}
