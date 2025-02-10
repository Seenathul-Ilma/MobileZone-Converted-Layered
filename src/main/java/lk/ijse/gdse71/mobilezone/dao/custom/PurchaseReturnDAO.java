package lk.ijse.gdse71.mobilezone.dao.custom;

import lk.ijse.gdse71.mobilezone.dao.CrudDAO;
import lk.ijse.gdse71.mobilezone.dto.PurchaseReturnDTO;
import lk.ijse.gdse71.mobilezone.entity.PurchaseReturn;

import java.sql.SQLException;

//public interface PurchaseReturnDAO extends CrudDAO<PurchaseReturnDTO> {
public interface PurchaseReturnDAO extends CrudDAO<PurchaseReturn> {

    //String getNextId() throws SQLException;

   // boolean save(PurchaseReturnDTO purchaseReturnDTO) throws SQLException;
}
