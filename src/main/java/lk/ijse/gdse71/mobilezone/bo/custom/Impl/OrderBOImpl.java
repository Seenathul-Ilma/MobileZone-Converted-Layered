package lk.ijse.gdse71.mobilezone.bo.custom.Impl;

import lk.ijse.gdse71.mobilezone.bo.custom.OrderBO;
import lk.ijse.gdse71.mobilezone.dao.DAOFactory;
import lk.ijse.gdse71.mobilezone.dao.custom.CustomerDAO;
import lk.ijse.gdse71.mobilezone.dao.custom.ItemDAO;
import lk.ijse.gdse71.mobilezone.dao.custom.OrderDetailDAO;
import lk.ijse.gdse71.mobilezone.dao.custom.OrdersDAO;
import lk.ijse.gdse71.mobilezone.dao.custom.impl.CustomerDAOImpl;
import lk.ijse.gdse71.mobilezone.dao.custom.impl.ItemDAOImpl;
import lk.ijse.gdse71.mobilezone.dao.custom.impl.OrdersDAOImpl;
import lk.ijse.gdse71.mobilezone.db.DBConnection;
import lk.ijse.gdse71.mobilezone.dto.CustomerDTO;
import lk.ijse.gdse71.mobilezone.dto.ItemDTO;
import lk.ijse.gdse71.mobilezone.dto.OrderDTO;
import lk.ijse.gdse71.mobilezone.dto.OrderDetailDTO;
import lk.ijse.gdse71.mobilezone.entity.Customer;
import lk.ijse.gdse71.mobilezone.entity.Item;
import lk.ijse.gdse71.mobilezone.entity.Order;
import lk.ijse.gdse71.mobilezone.entity.OrderDetail;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderBOImpl implements OrderBO {
    //OrdersDAO ordersDAO = new OrdersDAOImpl();
    OrdersDAO ordersDAO = (OrdersDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.ORDERS);

    //OrderDetailDAO orderDetailDAO = new OrderDetailDAOImpl();
    OrderDetailDAO orderDetailDAO = (OrderDetailDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.ORDER_DETAIL);

    //ItemDAO itemDAO = new ItemDAOImpl();
    ItemDAO itemDAO = (ItemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.ITEM);

    //CustomerDAO customerDAO = new CustomerDAOImpl();
    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.CUSTOMER);


    @Override
    public boolean saveOrder(OrderDTO orderDTO) throws SQLException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        try {
            connection.setAutoCommit(false); // 1

            //Replaced to OrdersDAOImpl
           /* boolean isOrderSaved = SQLUtil.execute(
                    "insert into orders values (?,?,?,?)",
                    orderDTO.getOrderId(),
                    orderDTO.getCustomerId(),
                    orderDTO.getOrderDate(),
                    orderDTO.getEmployeeId()
                    //orderDTO.getOrderDetailsDTOS()
            );*/

            // Convert DTO to Entity
            Order order = new Order(
                    orderDTO.getOrderId(),
                    orderDTO.getCustomerId(),
                    orderDTO.getOrderDate(),
                    orderDTO.getEmployeeId()
                    //orderDTO.getOrderDetailsDTOS()
            );

            //boolean isOrderSaved = ordersDAO.save(orderDTO);
            boolean isOrderSaved = ordersDAO.save(order);

            if (isOrderSaved) {
                boolean isOrderDetailListSaved = saveOrderDetailsList(orderDTO.getOrderDetailsDTOS());
                if (isOrderDetailListSaved) {
                    connection.commit(); // 2
                    return true;
                }
            }
            connection.rollback(); // 3
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            connection.rollback();
            return false;
        } finally {
            connection.setAutoCommit(true); // 4
        }
    }

    @Override
    public boolean saveOrderDetailsList(ArrayList<OrderDetailDTO> orderDetailsDTOS) throws SQLException, ClassNotFoundException {
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
            //boolean isOrderDetailsSaved = ordersDAO.save(orderDetailsDTO);
            boolean isOrderDetailsSaved = orderDetailDAO.save(orderDetail);
            if (!isOrderDetailsSaved) {
                return false;
            }

            // @isItemUpdated: Updates the item quantity in the stock for the corresponding order detail
            boolean isItemUpdated = itemDAO.reduceQty(orderDetail);
            if (!isItemUpdated) {
                // Return false if updating the item quantity fails
                return false;
            }
        }
        // Return true if all order details are saved and item quantities updated successfully
        return true;
    }

    public String getNextOrderId() throws SQLException, ClassNotFoundException {
        return ordersDAO.getNextId();
    }

    @Override
    public OrderDetailDTO findItemByOrderId(String orderId, String selectedItemId) throws SQLException, ClassNotFoundException {
        //return ordersDAO.findById(orderId, selectedItemId);
        //return orderDetailDAO.findById(orderId, selectedItemId);

        // Step 1: Retrieve the order detail entity from DAO
        OrderDetail orderDetail = orderDetailDAO.findById(orderId, selectedItemId);

        // Step 2: Check if the orderDetail exists
        if (orderDetail == null) {
            return null; // Return null if no item found
        } else {
            // Step 3: Convert OrderDetail entity to OrderDetailDTO
            OrderDetailDTO orderDetailDTOS = new OrderDetailDTO(
                    orderDetail.getOrderId(),
                    orderDetail.getItemId(),
                    orderDetail.getQuantity(),
                    orderDetail.getIsReturned(),
                    orderDetail.getUnitPrice()
            );

            return orderDetailDTOS;   // Step 4: Return the converted DTO
        }
    }

    @Override
    public String getOrderReturnOrNot(String orderId, String selectedItemId) throws SQLException, ClassNotFoundException {
        //return ordersDAO.getReturnOrNot(orderId, selectedItemId);
        return orderDetailDAO.getReturnOrNot(orderId, selectedItemId);
    }

    @Override
    public OrderDTO findOrderById(String orderId) throws SQLException, ClassNotFoundException {
        //return ordersDAO.findById(orderId);

        // Step 1: Retrieve the order entity from DAO
        Order order = ordersDAO.findById(orderId);

        // Step 2: Check if the order exists
        if (order == null) {
            return null; // Return null if no order found
        } else {
            // Step 3: Convert Order entity to OrderDTO
            OrderDTO orderDTO = new OrderDTO(
                    order.getOrderId(),
                    order.getCustomerId(),
                    order.getOrderDate(),
                    order.getEmployeeId()
            );
            return orderDTO;   // Step 4: Return the converted DTO
        }
    }

    @Override
    public ArrayList<String> getAllOrderIds() throws SQLException, ClassNotFoundException {
        ArrayList<String> orders =  ordersDAO.getAllIds();
        ArrayList<String> orderIds = new ArrayList<>();

        for (String orderId : orders) {
            orderIds.add(orderId);
        }
        return orderIds;
    }

    @Override
    public String confirmItemBought(String selectedOrderId, String selectedItemId) throws SQLException, ClassNotFoundException {
        System.out.println("confirm item bought");
        //return ordersDAO.confirmItemBought(selectedOrderId, selectedItemId);
        return orderDetailDAO.confirmItemBought(selectedOrderId, selectedItemId);
    }

    /*@Override
    public String confirmCustomerBought(String selectedCustomerId, String selectedOrderId, String selectedItemId) throws SQLException {
        return ordersDAO.confirmCustomerBought(selectedCustomerId, selectedOrderId, selectedItemId);
    }*/
}
