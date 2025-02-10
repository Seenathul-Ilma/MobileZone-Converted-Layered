package lk.ijse.gdse71.mobilezone.dao.custom;

import lk.ijse.gdse71.mobilezone.dao.CrudDAO;
import lk.ijse.gdse71.mobilezone.dto.ItemDTO;
import lk.ijse.gdse71.mobilezone.dto.OrderDetailDTO;
import lk.ijse.gdse71.mobilezone.dto.PurchaseDetailDTO;
import lk.ijse.gdse71.mobilezone.entity.Item;
import lk.ijse.gdse71.mobilezone.entity.OrderDetail;
import lk.ijse.gdse71.mobilezone.entity.PurchaseDetail;

import java.sql.SQLException;
import java.util.ArrayList;

//public interface ItemDAO extends CrudDAO<ItemDTO> {
public interface ItemDAO extends CrudDAO<Item> {

    //ArrayList<ItemDTO> checkLowStockItems() throws SQLException, ClassNotFoundException;
    ArrayList<Item> checkLowStockItems() throws SQLException, ClassNotFoundException;

    boolean isEmpty() throws SQLException, ClassNotFoundException;

    ArrayList<String> getAllItemIdsInCategory(String selectedCategory) throws SQLException, ClassNotFoundException;

    //boolean increaseQty(PurchaseDetailDTO purchaseDetailDTO) throws SQLException, ClassNotFoundException;
    boolean increaseQty(PurchaseDetail entity) throws SQLException, ClassNotFoundException;

    //boolean reduceQty(PurchaseDetailDTO purchaseDetailDTO) throws SQLException, ClassNotFoundException;
    boolean reduceQty(PurchaseDetail entity) throws SQLException, ClassNotFoundException;

    String confirmItemCategory(String selectedItemId) throws SQLException, ClassNotFoundException;

    //boolean reduceQty(OrderDetailDTO orderDetailsDTO) throws SQLException, ClassNotFoundException;
    boolean reduceQty(OrderDetail entity) throws SQLException, ClassNotFoundException;

    //boolean increaseQty(OrderDetailDTO orderDetailsDTO) throws SQLException, ClassNotFoundException;
    boolean increaseQty(OrderDetail entity) throws SQLException, ClassNotFoundException;
}
