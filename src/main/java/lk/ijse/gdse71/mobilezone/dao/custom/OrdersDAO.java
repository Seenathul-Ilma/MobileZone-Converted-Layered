package lk.ijse.gdse71.mobilezone.dao.custom;

import lk.ijse.gdse71.mobilezone.dao.CrudDAO;
import lk.ijse.gdse71.mobilezone.dto.OrderDTO;
import lk.ijse.gdse71.mobilezone.dto.OrderDetailDTO;
import lk.ijse.gdse71.mobilezone.entity.Order;
import lk.ijse.gdse71.mobilezone.entity.OrderDetail;

import java.sql.SQLException;
import java.util.Map;

//public interface OrdersDAO extends CrudDAO<OrderDTO> {
public interface OrdersDAO extends CrudDAO<Order> {
    Map<Integer, Integer> getMonthlyOrderData() throws SQLException, ClassNotFoundException;


    //boolean save(OrderDetailDTO orderDetailsDTO) throws SQLException;
    //boolean save(OrderDetail entity) throws SQLException;

    //OrderDetailDTO findById(String selectedOrderId, String selectedItemId) throws SQLException;
    //OrderDetail findById(String selectedOrderId, String selectedItemId) throws SQLException;

    //boolean update(OrderDetailDTO orderDetailsDTO) throws SQLException;
    //boolean update(OrderDetail entity) throws SQLException;

    //String getReturnOrNot(String orderId, String itemId) throws SQLException;
    //String confirmItemBought(String selectedOrderId, String selectedItemId) throws SQLException;
    //String confirmCustomerBought(String selectedCustomerId, String selectedOrderId, String selectedItemId) throws SQLException;
}
