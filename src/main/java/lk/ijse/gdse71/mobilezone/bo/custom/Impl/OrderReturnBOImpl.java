package lk.ijse.gdse71.mobilezone.bo.custom.Impl;

import lk.ijse.gdse71.mobilezone.bo.custom.OrderReturnBO;
import lk.ijse.gdse71.mobilezone.dao.DAOFactory;
import lk.ijse.gdse71.mobilezone.dao.custom.ItemDAO;
import lk.ijse.gdse71.mobilezone.dao.custom.OrderDetailDAO;
import lk.ijse.gdse71.mobilezone.dao.custom.OrderReturnDAO;
import lk.ijse.gdse71.mobilezone.dao.custom.OrdersDAO;
import lk.ijse.gdse71.mobilezone.dao.custom.impl.ItemDAOImpl;
import lk.ijse.gdse71.mobilezone.dao.custom.impl.OrderReturnDAOImpl;
import lk.ijse.gdse71.mobilezone.dao.custom.impl.OrdersDAOImpl;
import lk.ijse.gdse71.mobilezone.db.DBConnection;
import lk.ijse.gdse71.mobilezone.dto.OrderDetailDTO;
import lk.ijse.gdse71.mobilezone.dto.OrderReturnDTO;
import lk.ijse.gdse71.mobilezone.entity.OrderDetail;
import lk.ijse.gdse71.mobilezone.entity.OrderReturn;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

public class OrderReturnBOImpl implements OrderReturnBO {
    //OrdersDAOImpl ordersDAO = new OrdersDAOImpl();
    //OrdersDAO ordersDAO = new OrdersDAOImpl();
    OrdersDAO ordersDAO = (OrdersDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.ORDERS);

    //OrderReturnDAOImpl orderReturnDAO = new OrderReturnDAOImpl();
    //OrderReturnDAO orderReturnDAO = new OrderReturnDAOImpl();
    OrderReturnDAO orderReturnDAO = (OrderReturnDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.ORDER_RETURN);

    //ItemDAO itemDAO = new ItemDAOImpl();
    ItemDAO itemDAO = (ItemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.ITEM);

    //OrderDetailDAO orderDetailDAO = new OrderDetailDAOImpl();
    OrderDetailDAO orderDetailDAO = (OrderDetailDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.ORDER_DETAIL);

    @Override
    public boolean saveReturnDetailsList(ArrayList<OrderDetailDTO> orderDetailsDTOS) throws SQLException, ClassNotFoundException {
        ArrayList<OrderDetail> orderDetails = new ArrayList<>();

        // Convert DTOs to Entities
        for (OrderDetailDTO dto : orderDetailsDTOS) {
            OrderDetail orderDetail = new OrderDetail(
                    dto.getOrderId(),
                    dto.getItemId(),
                    dto.getQuantity(),
                    dto.getIsReturned(),
                    dto.getUnitPrice()
            );
            orderDetails.add(orderDetail);
        }

        //for (OrderDetailDTO orderDetailsDTO : orderDetailsDTOS) {
        for (OrderDetail orderDetail : orderDetails) {
            //boolean isReturnDetailsSaved = ordersDAO.update(orderDetailsDTO);
            boolean isReturnDetailsSaved = orderDetailDAO.update(orderDetail);
            if (!isReturnDetailsSaved) {
                return false;
            }

            // @isItemUpdated: Updates the item quantity in the stock for the corresponding order detail
            //boolean isItemUpdated = itemDAO.increaseQty(orderDetailsDTO);
            boolean isItemUpdated = itemDAO.increaseQty(orderDetail);
            if (!isItemUpdated) {
                // Return false if updating the item quantity fails
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean saveOrderReturn(OrderReturnDTO orderReturnDTO) throws SQLException {
        // @connection: Retrieves the current connection instance for the database
        Connection connection = DBConnection.getDbConnection().getConnection();
        try {
            // @autoCommit: Disables auto-commit to manually control the transaction
            connection.setAutoCommit(false); // 1

            // Replaced to OrdersReturnDAOImpl
            // @isOrderSaved: Saves the order details into the orders table
            /*boolean isReturnSaved = SQLUtil.execute(
                    "insert into orderRet values (?,?,?,?,?,?,?,?)",
                    orderReturnDTO.getOrderRet_Id(),
                    orderReturnDTO.getOrderId(),
                    orderReturnDTO.getCustomerId(),
                    orderReturnDTO.getItemId(),
                    orderReturnDTO.getQuantity(),
                    orderReturnDTO.getReason(),
                    orderReturnDTO.getRetAmount(),
                    orderReturnDTO.getReturnDate()
            );*/


            System.out.println("Final Order Return ID before saving: " + orderReturnDTO.getOrderRet_Id()); // Debugging log

            // Convert DTO to Entity
            OrderReturn orderReturn = new OrderReturn(
                    orderReturnDTO.getOrderRet_Id(),
                    orderReturnDTO.getOrderId(),
                    orderReturnDTO.getCustomerId(),
                    orderReturnDTO.getItemId(),
                    orderReturnDTO.getQuantity(),
                    orderReturnDTO.getReason(),
                    orderReturnDTO.getRetAmount(),
                    orderReturnDTO.getReturnDate()
            );

            System.out.println("Saving Order Return with ID: " + orderReturn.getOrderRet_Id()); // Debugging log

            //boolean isReturnSaved = orderReturnDAO.save(orderReturnDTO);
            boolean isReturnSaved = orderReturnDAO.save(orderReturn);

            // If the return is saved successfully
            if (isReturnSaved) {

                // @isOrderDetailListSaved: Saves the list of order details
                boolean isOrderDetailListSaved = saveReturnDetailsList(orderReturnDTO.getOrderDetailsDTOS());
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
            e.printStackTrace();
            // @catch: Rolls back the transaction in case of any exception
            connection.rollback();
            return false;
        } finally {
            // @finally: Resets auto-commit to true after the operation
            connection.setAutoCommit(true); // 4
        }
    }

    @Override
    public String getNextOrderReturnId() throws SQLException, ClassNotFoundException {
        return orderReturnDAO.getNextId();
    }

    @Override
    public Map<Integer, Integer> getMonthlyOrderReturnsData() throws SQLException, ClassNotFoundException {
        return orderReturnDAO.getMonthlyOrderReturnsData();
    }

}
