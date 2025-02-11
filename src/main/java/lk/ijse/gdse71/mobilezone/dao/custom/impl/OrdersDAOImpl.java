package lk.ijse.gdse71.mobilezone.dao.custom.impl;

import lk.ijse.gdse71.mobilezone.dao.custom.OrdersDAO;
import lk.ijse.gdse71.mobilezone.dao.SQLUtil;
import lk.ijse.gdse71.mobilezone.dto.OrderDTO;
import lk.ijse.gdse71.mobilezone.dto.OrderDetailDTO;
import lk.ijse.gdse71.mobilezone.entity.Order;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OrdersDAOImpl implements OrdersDAO {
    //private final OrderDetailModel orderDetailModel = new OrderDetailModel();
    //private final OrderDetailDAOImpl orderDetailDAO = new OrderDetailDAOImpl();

    @Override
    //public ArrayList<OrderDTO> getAll() throws SQLException, ClassNotFoundException {
    public ArrayList<Order> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select * from orders");

        //ArrayList<OrderDTO> orderDTOS = new ArrayList<>();
        ArrayList<Order> allOrders = new ArrayList<>();

        while (rst.next()) {
            //OrderDTO orderDTO = new OrderDTO(
            Order entity = new Order(
                    rst.getString(1),  // Item Id
                    rst.getString(2),  // Category Id
                    rst.getDate(3),  // Name
                    rst.getString(4)  // Brand
            );
            //orderDTOS.add(orderDTO);
            allOrders.add(entity);
        }
        return allOrders;
    }

    @Override
    public String getNextId() throws SQLException {
        ResultSet rst = SQLUtil.execute("select orderId from orders order by orderId desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(1);
            int i = Integer.parseInt(substring); // 2
            int newIdIndex = i + 1; // 3
            return String.format("O%03d", newIdIndex);
        }
        return "O001";
    }

    @Override
    //public boolean save(OrderDTO orderDTO) throws SQLException {
    public boolean save(Order entity) throws SQLException {
        return SQLUtil.execute(
                "insert into orders values (?,?,?,?)",
                //orderDTO.getOrderId(),
                entity.getOrderId(),
                entity.getCustomerId(),
                entity.getOrderDate(),
                entity.getEmployeeId()
                //orderDTO.getOrderDetailsDTOS()    -- old
        );
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("delete from orders where orderId = ?", id);
    }

    @Override
    //public boolean update(OrderDTO orderDTO) throws SQLException, ClassNotFoundException {
    public boolean update(Order entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute(
                "update orders set customerId=?, orderDate=?, employeeId=? where orderId=?",
                //orderDTO.getCustomerId(),
                entity.getCustomerId(),
                entity.getOrderDate(),
                entity.getEmployeeId(),
                entity.getOrderId()
        );
    }

    @Override
    public ArrayList<String> getAllIds() throws SQLException {
        ResultSet rst = SQLUtil.execute("select orderId from orders");

        ArrayList<String> orderIds = new ArrayList<>();

        while (rst.next()) {
            orderIds.add(rst.getString(1));
        }
        return orderIds;
    }

    @Override
    //public OrderDTO findById(String orderId) throws SQLException {
    public Order findById(String orderId) throws SQLException {
        ResultSet rst = SQLUtil.execute("select * from orders where orderId=?", orderId);

        //ArrayList<OrderDTO> orderDTOS = new ArrayList<>();
        ArrayList<Order> orders = new ArrayList<>();
        if (rst.next()) {
            //return new OrderDTO(
            return new Order(
                    rst.getString(1),  // Order ID
                    rst.getString(2),  // Customer Name
                    rst.getDate(3),  // Order Date
                    rst.getString(4)
            );
        }
        return null;
    }

    @Override
    public Map<Integer, Integer> getMonthlyOrderData() throws SQLException, ClassNotFoundException {
        Map<Integer, Integer> orderData = new HashMap<>();
        ResultSet resultSet = SQLUtil.execute("SELECT MONTH(orderDate) AS month, COUNT(*) AS total_orders FROM orders GROUP BY MONTH(orderDate)");

        while (resultSet.next()) {
            int month = resultSet.getInt("month");
            int totalOrders = resultSet.getInt("total_orders");
            orderData.put(month, totalOrders);
        }

        return orderData;
    }

    //@Override
    /*public boolean save(OrderDetailDTO orderDetailsDTO) throws SQLException {
        return SQLUtil.execute(
                "insert into orderDetail values (?,?,?,?,?)",
                orderDetailsDTO.getOrderId(),
                orderDetailsDTO.getItemId(),
                orderDetailsDTO.getQuantity(),
                orderDetailsDTO.getIsReturned(),
                orderDetailsDTO.getUnitPrice()
        );
    }*/

    //@Override
    /*public OrderDetailDTO findById(String selectedOrderId, String selectedItemId) throws SQLException {
        ResultSet rst = SQLUtil.execute("select * from orderDetail where orderId=? AND itemId=?", selectedOrderId, selectedItemId);

        if (rst.next()) {
            return new OrderDetailDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getInt(3),
                    rst.getString(4),
                    rst.getDouble(5)
            );
        }else{
            return null;
        }
    }*/

    //@Override
    /*public boolean update(OrderDetailDTO orderDetailsDTO) throws SQLException {
        return SQLUtil.execute(
                "update orderDetail set isReturned=? where orderId=?",
                orderDetailsDTO.getIsReturned(),
                orderDetailsDTO.getOrderId()
        );
    }*/

    //@Override
    /*public String getReturnOrNot(String orderId, String itemId) throws SQLException {
        ResultSet resultSet = SQLUtil.execute("select isReturned from orderDetail where orderId=? AND itemId=?", orderId, itemId);
        if(resultSet.next()){
            return resultSet.getString("isReturned");
        }
        return "NO";
    }
*/
    //@Override
    /*public String confirmItemBought(String selectedOrderId, String selectedItemId) throws SQLException {
        ResultSet resultSet = SQLUtil.execute("select itemId from orderDetail where orderId=? AND itemId=?", selectedOrderId, selectedItemId);
        if(resultSet.next()){
            return resultSet.getString("itemId");
        }
        return "";
    }*/

    /*@Override
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
