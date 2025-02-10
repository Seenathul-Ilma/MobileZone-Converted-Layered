package lk.ijse.gdse71.mobilezone.dao.custom;

import lk.ijse.gdse71.mobilezone.dao.CrudDAO;
import lk.ijse.gdse71.mobilezone.dto.OrderDetailDTO;
import lk.ijse.gdse71.mobilezone.entity.OrderDetail;

import java.sql.SQLException;

//public interface OrderDetailDAO extends CrudDAO<OrderDetailDTO> {
public interface OrderDetailDAO extends CrudDAO<OrderDetail> {

    //OrderDetailDTO findById(String selectedOrderId, String selectedItemId) throws SQLException, ClassNotFoundException;
    OrderDetail findById(String selectedOrderId, String selectedItemId) throws SQLException, ClassNotFoundException;

    String confirmItemBought(String selectedOrderId, String selectedItemId) throws SQLException, ClassNotFoundException;

    String getReturnOrNot(String orderId, String itemId) throws SQLException, ClassNotFoundException;

    /*boolean save(OrderDetailDTO orderDetailsDTO) throws SQLException;
    //boolean save(OrderDetail entity) throws SQLException;

    boolean update(OrderDetailDTO orderDetailsDTO) throws SQLException;
    //boolean update(OrderDetail entity) throws SQLException;*/
}
