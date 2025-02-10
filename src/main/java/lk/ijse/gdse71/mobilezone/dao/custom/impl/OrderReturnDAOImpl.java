package lk.ijse.gdse71.mobilezone.dao.custom.impl;

import lk.ijse.gdse71.mobilezone.dao.custom.ItemDAO;
import lk.ijse.gdse71.mobilezone.dao.custom.OrderReturnDAO;
import lk.ijse.gdse71.mobilezone.dao.SQLUtil;
import lk.ijse.gdse71.mobilezone.dto.OrderReturnDTO;
import lk.ijse.gdse71.mobilezone.entity.OrderReturn;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderReturnDAOImpl implements OrderReturnDAO {

    @Override
    //public ArrayList<OrderReturnDTO> getAll() throws SQLException, ClassNotFoundException {
    public ArrayList<OrderReturn> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public String getNextId() throws SQLException {
        ResultSet rst = SQLUtil.execute("select orderRet_Id from orderRet order by orderRet_Id desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(2);
            int i = Integer.parseInt(substring); // 2
            int newIdIndex = i + 1; // 3
            return String.format("OR%03d", newIdIndex);
        }
        return "OR001";
    }

    @Override
    //public boolean save(OrderReturnDTO orderReturnDTO) throws SQLException {
    public boolean save(OrderReturn entity) throws SQLException {
        return SQLUtil.execute(
                "insert into orderRet values (?,?,?,?,?,?,?,?)",
                //orderReturnDTO.getOrderRet_Id(),
                entity.getOrderRet_Id(),
                entity.getOrderId(),
                entity.getCustomerId(),
                entity.getItemId(),
                entity.getQuantity(),
                entity.getReason(),
                entity.getRetAmount(),
                entity.getReturnDate()
        );
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    //public boolean update(OrderReturnDTO dto) throws SQLException, ClassNotFoundException {
    public boolean update(OrderReturn entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public ArrayList<String> getAllIds() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    //public OrderReturnDTO findById(String id) throws SQLException, ClassNotFoundException {
    public OrderReturn findById(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    /*public boolean saveReturnDetailsList(ArrayList<OrderDetailDTO> orderDetailsDTOS) throws SQLException, ClassNotFoundException {
        for (OrderDetailDTO orderDetailsDTO : orderDetailsDTOS) {
            boolean isReturnDetailsSaved = updateOrderDetail(orderDetailsDTO);
            if (!isReturnDetailsSaved) {
                return false;
            }

            // @isItemUpdated: Updates the item quantity in the stock for the corresponding order detail
            boolean isItemUpdated = itemDAO.increaseQty(orderDetailsDTO);
            if (!isItemUpdated) {
                // Return false if updating the item quantity fails
                return false;
            }
        }
        return true;
    }*/

    /*public boolean update(OrderDetailDTO orderDetailsDTO) throws SQLException {
        return SQLUtil.execute(
                "update orderDetail set isReturned=? where orderId=?",
                orderDetailsDTO.getIsReturned(),
                orderDetailsDTO.getOrderId()
        );
    }*/

    /*public boolean saveReturn(OrderReturnDTO orderReturnDTO) throws SQLException {
        // @connection: Retrieves the current connection instance for the database
        Connection connection = DBConnection.getDbConnection().getConnection();
        try {
            // @autoCommit: Disables auto-commit to manually control the transaction
            connection.setAutoCommit(false); // 1

            // @isOrderSaved: Saves the order details into the orders table
            *//*boolean isReturnSaved = SQLUtil.execute(
                    "insert into orderRet values (?,?,?,?,?,?,?,?)",
                    orderReturnDTO.getOrderRet_Id(),
                    orderReturnDTO.getOrderId(),
                    orderReturnDTO.getCustomerId(),
                    orderReturnDTO.getItemId(),
                    orderReturnDTO.getQuantity(),
                    orderReturnDTO.getReason(),
                    orderReturnDTO.getRetAmount(),
                    orderReturnDTO.getReturnDate()
            );*//*
            // If the return is saved successfully
            if (isReturnSaved) {

                // @isOrderDetailListSaved: Saves the list of order details
                boolean isOrderDetailListSaved = orderDetailsModel.saveReturnDetailsList(orderReturnDTO.getOrderDetailsDTOS());
                if (isOrderDetailListSaved) {
                    // @commit: Commits the transaction if both order and details are saved successfully
                    connection.commit(); // 2
                    return true;
                }
            }
            // @rollback: Rolls back the transaction if order details saving fails
            connection.rollback(); // 3
            return false;
        } catch (Exception e) {
            // @catch: Rolls back the transaction in case of any exception
            connection.rollback();
            return false;
        } finally {
            // @finally: Resets auto-commit to true after the operation
            connection.setAutoCommit(true); // 4
        }
    }*/

    /*public String getReturnOrNot(String orderId, String itemId) throws SQLException {
        ResultSet resultSet = SQLUtil.execute("select isReturned from orderDetail where orderId=? AND itemId=?", orderId, itemId);
        if(resultSet.next()){
            return resultSet.getString("isReturned");
        }
        return "NO";
    }

    public String confirmItemBought(String selectedOrderId, String selectedItemId) throws SQLException {
        ResultSet resultSet = SQLUtil.execute("select itemId from orderDetail where orderId=? AND itemId=?", selectedOrderId, selectedItemId);
        if(resultSet.next()){
            return resultSet.getString("itemId");
        }
        return "";
    }

    public String confirmCustomerBought(String selectedCustomerId, String selectedOrderId, String selectedItemId) throws SQLException {
        ResultSet resultSet = SQLUtil.execute(
                "select customerId from orders o join orderDetail od on o.orderId = od.orderId where customerId = ? AND itemId=? AND od.orderId=? ",
                selectedCustomerId, selectedItemId, selectedOrderId);
        if(resultSet.next()){
            return resultSet.getString("customerId");
        }
        return "";
    }*/
}
