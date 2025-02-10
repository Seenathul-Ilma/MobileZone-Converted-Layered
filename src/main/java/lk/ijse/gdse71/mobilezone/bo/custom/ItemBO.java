package lk.ijse.gdse71.mobilezone.bo.custom;

import lk.ijse.gdse71.mobilezone.bo.SuperBO;
import lk.ijse.gdse71.mobilezone.dto.ItemDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ItemBO extends SuperBO {
    boolean isItemTableEmpty() throws SQLException, ClassNotFoundException;

    ArrayList<ItemDTO> getAllItems() throws SQLException, ClassNotFoundException;

    String getNextItemId() throws SQLException, ClassNotFoundException;

    boolean deleteItem(String itemId) throws SQLException, ClassNotFoundException;

    boolean saveItem(ItemDTO itemDTO) throws SQLException, ClassNotFoundException;

    boolean updateItem(ItemDTO itemDTO) throws SQLException, ClassNotFoundException;

    ArrayList<ItemDTO> checkLowStockItems() throws SQLException, ClassNotFoundException;

    ArrayList<String> getAllItemIds() throws SQLException, ClassNotFoundException;

    ItemDTO findByItemId(String selectedItemId) throws SQLException, ClassNotFoundException;

    String confirmItemCategory(String selectedItemId) throws SQLException, ClassNotFoundException;
}
