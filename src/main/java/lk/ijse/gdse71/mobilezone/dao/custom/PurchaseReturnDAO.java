package lk.ijse.gdse71.mobilezone.dao.custom;

import lk.ijse.gdse71.mobilezone.dao.CrudDAO;
import lk.ijse.gdse71.mobilezone.dto.PurchaseReturnDTO;
import lk.ijse.gdse71.mobilezone.entity.PurchaseReturn;

import java.sql.SQLException;
import java.util.Map;

//public interface PurchaseReturnDAO extends CrudDAO<PurchaseReturnDTO> {
public interface PurchaseReturnDAO extends CrudDAO<PurchaseReturn> {

    Map<Integer, Integer> getMonthlyPurchaseReturnData() throws SQLException, ClassNotFoundException;

    //String getNextId() throws SQLException;

   // boolean save(PurchaseReturnDTO purchaseReturnDTO) throws SQLException;
}
