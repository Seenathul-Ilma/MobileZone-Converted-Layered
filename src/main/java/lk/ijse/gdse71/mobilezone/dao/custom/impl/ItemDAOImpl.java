package lk.ijse.gdse71.mobilezone.dao.custom.impl;

import lk.ijse.gdse71.mobilezone.dao.custom.ItemDAO;
import lk.ijse.gdse71.mobilezone.dao.SQLUtil;
import lk.ijse.gdse71.mobilezone.dto.ItemDTO;
import lk.ijse.gdse71.mobilezone.dto.OrderDetailDTO;
import lk.ijse.gdse71.mobilezone.dto.PurchaseDetailDTO;
import lk.ijse.gdse71.mobilezone.entity.Item;
import lk.ijse.gdse71.mobilezone.entity.OrderDetail;
import lk.ijse.gdse71.mobilezone.entity.PurchaseDetail;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemDAOImpl implements ItemDAO {
    @Override
    //public ArrayList<ItemDTO> getAll() throws SQLException, ClassNotFoundException {
    public ArrayList<Item> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select * from item");

        //ArrayList<ItemDTO> itemDTOS = new ArrayList<>();
        ArrayList<Item> allItems = new ArrayList<>();

        while (rst.next()) {
            //ItemDTO itemDTO = new ItemDTO(
            Item entity = new Item(
                    rst.getString(1),  // Item Id
                    rst.getString(2),  // Category Id
                    rst.getString(3),  // Name
                    rst.getString(4),  // Brand
                    rst.getString(5),  // Model
                    rst.getInt(6),     // Qty
                    rst.getInt(7),     // ReOrder Level
                    rst.getDouble(8)   // Unit Price
            );
            //itemDTOS.add(itemDTO);
            allItems.add(entity);
        }
        //return itemDTOS;
        return allItems;
    }

    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select itemId from item order by itemId desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1); // Last item ID
            String substring = lastId.substring(1); // Extract the numeric part
            int i = Integer.parseInt(substring); // Convert the numeric part to integer
            int newIdIndex = i + 1; // Increment the number by 1
            return String.format("I%03d", newIdIndex); // Return the new item ID in format Innn
        }
        return "I001";
    }

    @Override
    //public boolean save(ItemDTO itemDTO) throws SQLException, ClassNotFoundException {
    public boolean save(Item entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute(
                "insert into item values (?,?,?,?,?,?,?,?)",
                //itemDTO.getItemId(),
                entity.getItemId(),
                entity.getCategoryId(),
                entity.getName(),
                entity.getBrand(),
                entity.getModel(),
                entity.getQtyOnHand(),
                entity.getReOrderLevel(),
                entity.getUnitPrice()
        );
    }

    @Override
    //public boolean update(ItemDTO itemDTO) throws SQLException, ClassNotFoundException {
    public boolean update(Item entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute(
                "update item set categoryId=?, name=?, brand=?, model=?, qtyOnHand=?, reOrderLevel=?, unitPrice=? where itemId=?",
                //itemDTO.getCategoryId(),
                entity.getCategoryId(),
                entity.getName(),
                entity.getBrand(),
                entity.getModel(),
                entity.getQtyOnHand(),
                entity.getReOrderLevel(),
                entity.getUnitPrice(),
                entity.getItemId()
        );

    }

    @Override
    public boolean delete(String itemId) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("delete from item where itemId=?", itemId);
    }

    @Override
    public ArrayList<String> getAllIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select itemId from item");

        ArrayList<String> itemIds = new ArrayList<>();

        while (rst.next()) {
            itemIds.add(rst.getString(1));
        }

        return itemIds;
    }

    @Override
    //public ItemDTO findById(String selectedItemId) throws SQLException, ClassNotFoundException {
    public Item findById(String selectedItemId) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select * from item where itemId=?", selectedItemId);

        if (rst.next()) {
            //return new ItemDTO(
            return new Item(
                    rst.getString(1),  // Item Id
                    rst.getString(2),  // Category Id
                    rst.getString(3),  // Name
                    rst.getString(4),  // Brand
                    rst.getString(5),  // Model
                    rst.getInt(6),     // Qty
                    rst.getInt(7),     // ReOrder Level
                    rst.getDouble(8)   // Unit Price
            );
        }
        return null;
    }

    @Override
    //public boolean reduceQty(OrderDetailDTO orderDetailsDTO) throws SQLException, ClassNotFoundException {
    public boolean reduceQty(OrderDetail entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute(
                "update item set qtyOnHand = qtyOnHand - ? where itemId = ?",
                //orderDetailsDTO.getQuantity(),   // Quantity to reduce
                entity.getQuantity(),   // Quantity to reduce
                entity.getItemId()      // Item ID
        );
    }

    @Override
    //public ArrayList<ItemDTO> checkLowStockItems() throws SQLException, ClassNotFoundException {
    public ArrayList<Item> checkLowStockItems() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select * from item where qtyOnHand <= reOrderLevel");

        //ArrayList<ItemDTO> itemDTOS = new ArrayList<>();
        ArrayList<Item> allItems = new ArrayList<>();

        while (rst.next()) {
            //ItemDTO itemDTO = new ItemDTO(
            Item items = new Item(
                    rst.getString(1),  // Item Id
                    rst.getString(2),  // Category Id
                    rst.getString(3),  // Name
                    rst.getString(4),  // Brand
                    rst.getString(5),  // Model
                    rst.getInt(6),     // Qty
                    rst.getInt(7),     // ReOrder Level
                    rst.getDouble(8)   // Unit Price
            );
            //itemDTOS.add(itemDTO);
            allItems.add(items);
        }
        //return itemDTOS;
        return allItems;
    }

    @Override
    //public boolean increaseQty(OrderDetailDTO orderDetailsDTO) throws SQLException, ClassNotFoundException {
    public boolean increaseQty(OrderDetail entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute(
                "UPDATE item SET qtyOnHand = qtyOnHand + ? WHERE itemId = ?",
                //orderDetailsDTO.getQuantity(),
                entity.getQuantity(),
                entity.getItemId()
        );
    }

    @Override
    public ArrayList<String> getAllItemIdsInCategory(String selectedCategory) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select itemId from item where categoryId=?", selectedCategory);

        ArrayList<String> itemIds = new ArrayList<>();

        while (rst.next()) {
            itemIds.add(rst.getString(1));
        }

        return itemIds;
    }

    @Override
    //public boolean increaseQty(PurchaseDetailDTO purchaseDetailDTO) throws SQLException, ClassNotFoundException {
    public boolean increaseQty(PurchaseDetail entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute(
                "UPDATE item SET qtyOnHand = qtyOnHand + ? WHERE itemId = ?",
                //purchaseDetailDTO.getQuantity(),
                entity.getQuantity(),
                entity.getItemId()
        );
    }

    @Override
    public String confirmItemCategory(String selectedItemId) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select categoryId from item where itemId=?", selectedItemId);

        if (rst.next()) {
            return rst.getString("categoryId");
        }
        return "";
    }

    @Override
    //public boolean reduceQty(PurchaseDetailDTO purchaseDetailDTO) throws SQLException, ClassNotFoundException {
    public boolean reduceQty(PurchaseDetail entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute(
                "update item set qtyOnHand = qtyOnHand - ? where itemId = ?",
                //purchaseDetailDTO.getQuantity(),   // Quantity to reduce
                entity.getQuantity(),   // Quantity to reduce
                entity.getItemId()      // Item ID
        );
    }

    @Override
    public boolean isEmpty() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("select itemId from item order by itemId asc limit 1");
        if(resultSet.next()){
            return false;
        }
        return true;
    }
}
