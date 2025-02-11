package lk.ijse.gdse71.mobilezone.dao.custom;

import lk.ijse.gdse71.mobilezone.dao.CrudDAO;
import lk.ijse.gdse71.mobilezone.dto.OrderReturnDTO;
import lk.ijse.gdse71.mobilezone.entity.OrderReturn;

import java.sql.SQLException;
import java.util.Map;

//public interface OrderReturnDAO extends CrudDAO<OrderReturnDTO> {
public interface OrderReturnDAO extends CrudDAO<OrderReturn> {
    Map<Integer, Integer> getMonthlyOrderReturnsData() throws SQLException, ClassNotFoundException;

}
