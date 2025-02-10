package lk.ijse.gdse71.mobilezone.bo.custom;

import lk.ijse.gdse71.mobilezone.bo.SuperBO;
import lk.ijse.gdse71.mobilezone.dto.OrderDTO;
import lk.ijse.gdse71.mobilezone.dto.OrderDetailDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface OrderBO extends SuperBO {
    boolean saveOrderDetailsList(ArrayList<OrderDetailDTO> orderDetailsDTOS) throws SQLException, ClassNotFoundException;

    String getNextOrderId() throws SQLException, ClassNotFoundException;

    boolean saveOrder(OrderDTO orderDTO) throws SQLException;

    OrderDTO findOrderById(String orderId) throws SQLException, ClassNotFoundException;

    OrderDetailDTO findItemByOrderId(String orderId, String selectedItemId) throws SQLException, ClassNotFoundException;

    String getOrderReturnOrNot(String orderId, String selectedItemId) throws SQLException, ClassNotFoundException;

    ArrayList<String> getAllOrderIds() throws SQLException, ClassNotFoundException;

    String confirmItemBought(String selectedOrderId, String selectedItemId) throws SQLException, ClassNotFoundException;

    //String confirmCustomerBought(String selectedCustomerId, String selectedOrderId, String selectedItemId) throws SQLException;
}
