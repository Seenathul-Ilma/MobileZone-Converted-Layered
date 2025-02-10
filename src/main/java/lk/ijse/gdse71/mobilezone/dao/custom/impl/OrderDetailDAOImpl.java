package lk.ijse.gdse71.mobilezone.dao.custom.impl;

import lk.ijse.gdse71.mobilezone.dao.SQLUtil;
import lk.ijse.gdse71.mobilezone.dao.custom.OrderDetailDAO;
import lk.ijse.gdse71.mobilezone.dto.OrderDetailDTO;
import lk.ijse.gdse71.mobilezone.entity.OrderDetail;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailDAOImpl implements OrderDetailDAO {

    @Override
    //public ArrayList<OrderDetailDTO> getAll() throws SQLException, ClassNotFoundException {
    public ArrayList<OrderDetail> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        return "";
    }

    @Override
    //public boolean save(OrderDetailDTO orderDetailsDTO) throws SQLException, ClassNotFoundException {
    public boolean save(OrderDetail entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute(
                "insert into orderDetail values (?,?,?,?,?)",
                //orderDetailsDTO.getOrderId(),
                entity.getOrderId(),
                entity.getItemId(),
                entity.getQuantity(),
                entity.getIsReturned(),
                entity.getUnitPrice()
        );
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    //public boolean update(OrderDetailDTO orderDetailsDTO) throws SQLException, ClassNotFoundException {
    public boolean update(OrderDetail entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute(
                "update orderDetail set isReturned=? where orderId=?",
                //orderDetailsDTO.getIsReturned(),
                entity.getIsReturned(),
                entity.getOrderId()
        );
    }

    @Override
    public ArrayList<String> getAllIds() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    //public OrderDetailDTO findById(String id) throws SQLException, ClassNotFoundException {
    public OrderDetail findById(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    //public OrderDetailDTO findById(String selectedOrderId, String selectedItemId) throws SQLException, ClassNotFoundException {
    public OrderDetail findById(String selectedOrderId, String selectedItemId) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select * from orderDetail where orderId=? AND itemId=?", selectedOrderId, selectedItemId);

        if (rst.next()) {
            //return new OrderDetailDTO(
            return new OrderDetail(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getInt(3),
                    rst.getString(4),
                    rst.getDouble(5)
            );
        }else{
            return null;
        }
    }

    @Override
    public String confirmItemBought(String selectedOrderId, String selectedItemId) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("select itemId from orderDetail where orderId=? AND itemId=?", selectedOrderId, selectedItemId);
        if(resultSet.next()){
            return resultSet.getString("itemId");
        }
        return "";
    }

    @Override
    public String getReturnOrNot(String orderId, String itemId) throws SQLException {
        ResultSet resultSet = SQLUtil.execute("select isReturned from orderDetail where orderId=? AND itemId=?", orderId, itemId);
        if(resultSet.next()){
            return resultSet.getString("isReturned");
        }
        return "NO";
    }
}
