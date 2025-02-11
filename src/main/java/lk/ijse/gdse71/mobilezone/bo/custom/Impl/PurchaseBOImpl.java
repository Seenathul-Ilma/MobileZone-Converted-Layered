package lk.ijse.gdse71.mobilezone.bo.custom.Impl;

import lk.ijse.gdse71.mobilezone.bo.custom.PurchaseBO;
import lk.ijse.gdse71.mobilezone.dao.DAOFactory;
import lk.ijse.gdse71.mobilezone.dao.custom.ItemDAO;
import lk.ijse.gdse71.mobilezone.dao.custom.PurchaseDAO;
import lk.ijse.gdse71.mobilezone.dao.custom.PurchaseDetailDAO;
import lk.ijse.gdse71.mobilezone.dao.custom.QueryDAO;
import lk.ijse.gdse71.mobilezone.dao.custom.impl.ItemDAOImpl;
import lk.ijse.gdse71.mobilezone.dao.custom.impl.PurchaseDAOImpl;
import lk.ijse.gdse71.mobilezone.dao.custom.impl.QueryDAOImpl;
import lk.ijse.gdse71.mobilezone.db.DBConnection;
import lk.ijse.gdse71.mobilezone.dto.OrderDTO;
import lk.ijse.gdse71.mobilezone.dto.OrderDetailDTO;
import lk.ijse.gdse71.mobilezone.dto.PurchaseDTO;
import lk.ijse.gdse71.mobilezone.dto.PurchaseDetailDTO;
import lk.ijse.gdse71.mobilezone.entity.Order;
import lk.ijse.gdse71.mobilezone.entity.OrderDetail;
import lk.ijse.gdse71.mobilezone.entity.Purchase;
import lk.ijse.gdse71.mobilezone.entity.PurchaseDetail;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

public class PurchaseBOImpl implements PurchaseBO {
    //PurchaseDAOImpl purchaseDAO = new PurchaseDAOImpl();
    //PurchaseDAO purchaseDAO = new PurchaseDAOImpl();
    PurchaseDAO purchaseDAO = (PurchaseDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.PURCHASE);

    //PurchaseDetailDAOImpl purchaseDetailDAO = new PurchaseDetailDAOImpl();
    //PurchaseDetailDAO purchaseDetailDAO = new PurchaseDetailDAOImpl();
    PurchaseDetailDAO purchaseDetailDAO = (PurchaseDetailDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.PURCHASE_DETAIL);

    //QueryDAO queryDAO = new QueryDAOImpl();
    QueryDAO queryDAO = (QueryDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.QUERY);

    //ItemDAO itemDAO = new ItemDAOImpl();
    ItemDAO itemDAO = (ItemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.ITEM);

    public boolean savePurchase(PurchaseDTO purchaseDTO) throws SQLException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        try {
            connection.setAutoCommit(false); // 1

            Purchase purchase = new Purchase(
                    purchaseDTO.getPurchaseId(),
                    purchaseDTO.getSupplierId(),
                    purchaseDTO.getPurchaseDate(),
                    purchaseDTO.getTotalAmount()
            );

            //boolean isPurchaseSaved = purchaseDAO.save(purchaseDTO);
            boolean isPurchaseSaved = purchaseDAO.save(purchase);
            if (isPurchaseSaved) {
                boolean isPurchaseDetailListSaved = savePurchaseDetailsList(purchaseDTO.getPurchaseDetailDTOS());
                if (isPurchaseDetailListSaved) {
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

    public boolean savePurchaseDetailsList(ArrayList<PurchaseDetailDTO> purchaseDetailDTOS) throws SQLException, ClassNotFoundException {
        ArrayList<PurchaseDetail> purchaseDetails = new ArrayList<>();

        // Convert DTOs to Entities
        for (PurchaseDetailDTO dto : purchaseDetailDTOS) {
            PurchaseDetail purchaseDetail = new PurchaseDetail(
                    dto.getPurchaseId(),
                    dto.getItemId(),
                    dto.getQuantity(),
                    dto.getIsReturned(),
                    dto.getUnitPrice(),
                    dto.getTotalPrice()
            );
            purchaseDetails.add(purchaseDetail);
        }

        //for (PurchaseDetailDTO purchaseDetailDTO : purchaseDetailDTOS) {
        for (PurchaseDetail purchaseDetail : purchaseDetails) {
            //boolean isPurchaseDetailsSaved = purchaseDAO.save(purchaseDetailDTO);
            boolean isPurchaseDetailsSaved = purchaseDetailDAO.save(purchaseDetail);
            if (!isPurchaseDetailsSaved) {
                return false;
            }

            //boolean isItemUpdated = itemDAO.increaseQty(purchaseDetailDTO);
            boolean isItemUpdated = itemDAO.increaseQty(purchaseDetail);
            if (!isItemUpdated) {
                // Return false if updating the item quantity fails
                return false;
            }
        }
        return true;
    }

    public ArrayList<String> getAllPurchaseIds() throws SQLException, ClassNotFoundException {
        ArrayList<String> purchases =  purchaseDAO.getAllIds();
        ArrayList<String> purchaseIds = new ArrayList<>();

        for (String purchaseId : purchases) {
            purchaseIds.add(purchaseId);
        }
        return purchaseIds;
    }

    public PurchaseDetailDTO findItemByPurchaseId(String purchaseId, String selectedItemId) throws SQLException, ClassNotFoundException {
        //return purchaseDetailDAO.findById(purchaseId, selectedItemId);

        // Step 1: Retrieve the purchase detail entity from DAO
        PurchaseDetail purchaseDetail = purchaseDetailDAO.findById(purchaseId, selectedItemId);

        // Step 2: Check if the purchase detail exists
        if (purchaseDetail == null) {
            return null; // Return null if no purchase detail found
        } else {
            // Step 3: Convert PurchaseDetail entity to PurchaseDetailDTO
            PurchaseDetailDTO purchaseDetailDTO = new PurchaseDetailDTO(
                    purchaseDetail.getPurchaseId(),
                    purchaseDetail.getItemId(),
                    purchaseDetail.getQuantity(),
                    purchaseDetail.getIsReturned(),
                    purchaseDetail.getUnitPrice(),
                    purchaseDetail.getTotalPrice()
            );
            return purchaseDetailDTO;   // Step 4: Return the converted DTO
        }
    }

    public String getPurchaseReturnOrNot(String purchaseId, String selectedItemId) throws SQLException, ClassNotFoundException {
        return purchaseDetailDAO.getReturnOrNot(purchaseId, selectedItemId);
    }

    public PurchaseDTO findPurchaseById(String purchaseId) throws SQLException, ClassNotFoundException {
        //return purchaseDAO.findById(purchaseId);

        // Step 1: Retrieve the purchase entity from DAO
        Purchase purchase = purchaseDAO.findById(purchaseId);

        // Step 2: Check if the purchase exists
        if (purchase == null) {
            return null; // Return null if no purchase found
        } else {
            // Step 3: Convert Purchase entity to PurchaseDTO
            PurchaseDTO purchaseDTO = new PurchaseDTO(
                    purchase.getPurchaseId(),
                    purchase.getSupplierId(),
                    purchase.getPurchaseDate(),
                    purchase.getTotalAmount()
            );
            return purchaseDTO;   // Step 4: Return the converted DTO
        }
    }

    public String confirmItemBought(String selectedPurchaseId, String selectedItemId) throws SQLException, ClassNotFoundException {
        return purchaseDetailDAO.confirmItemBought(selectedPurchaseId, selectedItemId);
    }

    public String getNextPurchaseId() throws SQLException, ClassNotFoundException {
        return purchaseDAO.getNextId();
    }

    @Override
    public Map<Integer, Integer> getMonthlyPurchasesData() throws SQLException, ClassNotFoundException {
        return purchaseDAO.getMonthlyPurchasesData();
    }

    /*public String confirmBoughtFromSupplier(String selectedSupplierId, String selectedPurchaseId, String selectedItemId) throws SQLException, ClassNotFoundException {
        //return purchaseDAO.confirmBoughtFromSupplier(selectedSupplierId, selectedPurchaseId, selectedItemId);
        return queryDAO.confirmBoughtFromSupplier(selectedSupplierId, selectedPurchaseId, selectedItemId);
    }*/

}
