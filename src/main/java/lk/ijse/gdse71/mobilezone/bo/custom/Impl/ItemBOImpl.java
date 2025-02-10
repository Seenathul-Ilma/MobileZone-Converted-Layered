package lk.ijse.gdse71.mobilezone.bo.custom.Impl;

import lk.ijse.gdse71.mobilezone.bo.custom.ItemBO;
import lk.ijse.gdse71.mobilezone.dao.DAOFactory;
import lk.ijse.gdse71.mobilezone.dao.custom.ItemDAO;
import lk.ijse.gdse71.mobilezone.dao.custom.impl.ItemDAOImpl;
import lk.ijse.gdse71.mobilezone.dto.CustomerDTO;
import lk.ijse.gdse71.mobilezone.dto.ItemDTO;
import lk.ijse.gdse71.mobilezone.entity.Customer;
import lk.ijse.gdse71.mobilezone.entity.Item;

import java.sql.SQLException;
import java.util.ArrayList;

public class ItemBOImpl implements ItemBO {
    //ItemDAO itemDAO = new ItemDAOImpl();
    ItemDAO itemDAO = (ItemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.ITEM);

    public ArrayList<ItemDTO> getAllItems() throws SQLException, ClassNotFoundException {
        ArrayList<ItemDTO> itemDTOS = new ArrayList<>();

        //ArrayList<ItemDTO> items = itemDAO.getAll();
        ArrayList<Item> items = itemDAO.getAll();

        //for (ItemDTO itemDTO : items) {
        for (Item item : items) {
            itemDTOS.add(new ItemDTO(
                    //itemDTO.getItemId(),
                    item.getItemId(),
                    item.getCategoryId(),
                    item.getName(),
                    item.getBrand(),
                    item.getModel(),
                    item.getQtyOnHand(),
                    item.getReOrderLevel(),
                    item.getUnitPrice()
            ));
        }
        return itemDTOS;
    }

    public String getNextItemId() throws SQLException, ClassNotFoundException {
        return itemDAO.getNextId();
    }

    public boolean saveItem(ItemDTO itemDTO) throws SQLException, ClassNotFoundException {
        //return itemDAO.save(new ItemDTO(
        return itemDAO.save(new Item(
                itemDTO.getItemId(),
                itemDTO.getCategoryId(),
                itemDTO.getName(),
                itemDTO.getBrand(),
                itemDTO.getModel(),
                itemDTO.getQtyOnHand(),
                itemDTO.getReOrderLevel(),
                itemDTO.getUnitPrice()
        ));
    }

    public boolean updateItem(ItemDTO itemDTO) throws SQLException, ClassNotFoundException {
        //return itemDAO.update(new ItemDTO(
        return itemDAO.update(new Item(
                itemDTO.getItemId(),
                itemDTO.getCategoryId(),
                itemDTO.getName(),
                itemDTO.getBrand(),
                itemDTO.getModel(),
                itemDTO.getQtyOnHand(),
                itemDTO.getReOrderLevel(),
                itemDTO.getUnitPrice()
        ));
    }

    public boolean deleteItem(String itemId) throws SQLException, ClassNotFoundException {
        return itemDAO.delete(itemId);
    }

    public ArrayList<ItemDTO> checkLowStockItems() throws SQLException, ClassNotFoundException {
        //return itemDAO.checkLowStockItems();

        //ArrayList<ItemDTO> items = itemDAO.checkLowStockItems();
        ArrayList<Item> items = itemDAO.checkLowStockItems();

        ArrayList<ItemDTO> itemDTOS = new ArrayList<>();

        //for (ItemDTO itemDTO : items) {
        for (Item item : items) {
            itemDTOS.add(new ItemDTO(
                    //itemDTO.getItemId(),
                    item.getItemId(),
                    item.getCategoryId(),
                    item.getName(),
                    item.getBrand(),
                    item.getModel(),
                    item.getQtyOnHand(),
                    item.getReOrderLevel(),
                    item.getUnitPrice()
            ));
        }
        return itemDTOS;
    }

    public boolean isItemTableEmpty() throws SQLException, ClassNotFoundException {
        return itemDAO.isEmpty();
    }

    public ArrayList<String> getAllItemIds() throws SQLException, ClassNotFoundException {
        ArrayList<String> items =  itemDAO.getAllIds();
        ArrayList<String> itemIds = new ArrayList<>();

        for (String itemId : items) {
            itemIds.add(itemId);
        }
        return itemIds;
    }

    public ItemDTO findByItemId(String selectedItemId) throws SQLException, ClassNotFoundException {
        //return itemDAO.findById(selectedItemId);

        // Step 1: Retrieve the customer entity from DAO
        Item item = itemDAO.findById(selectedItemId);

        // Step 2: Check if the item exists
        if (item == null) {
            return null; // Return null if no item found
        } else {
            // Step 3: Convert Item entity to ItemDTO
            ItemDTO itemDTOS = new ItemDTO(
                    item.getItemId(),
                    item.getCategoryId(),
                    item.getName(),
                    item.getBrand(),
                    item.getModel(),
                    item.getQtyOnHand(),
                    item.getReOrderLevel(),
                    item.getUnitPrice());

            return itemDTOS;   // Step 4: Return the converted DTO
        }
    }

    public String confirmItemCategory(String selectedItemId) throws SQLException, ClassNotFoundException {
        return itemDAO.confirmItemCategory(selectedItemId);
    }

    // not used in item_controller
    /*public ItemDTO findById(String selectedItemId) throws SQLException, ClassNotFoundException {
        return itemDAO.findById(selectedItemId);
    }

    public boolean reduceQty(OrderDetailDTO orderDetailsDTO) throws SQLException, ClassNotFoundException {
        return itemDAO.reduceQty(orderDetailsDTO);
    }

    public boolean increaseQty(OrderDetailDTO orderDetailsDTO) throws SQLException, ClassNotFoundException {
        return itemDAO.increaseQty(orderDetailsDTO);
    }

    public boolean increaseQty(PurchaseDetailDTO purchaseDetailDTO) throws SQLException, ClassNotFoundException {
        return itemDAO.increaseQty(purchaseDetailDTO);
    }

    public boolean reduceQty(PurchaseDetailDTO purchaseDetailDTO) throws SQLException, ClassNotFoundException {
        return itemDAO.reduceQty(purchaseDetailDTO);
    }*/

    /*public ArrayList<String> getAllItemIdsInCategory(String selectedCategory) throws SQLException, ClassNotFoundException {
        return itemDAO.getAllIds();
    }*/
}
