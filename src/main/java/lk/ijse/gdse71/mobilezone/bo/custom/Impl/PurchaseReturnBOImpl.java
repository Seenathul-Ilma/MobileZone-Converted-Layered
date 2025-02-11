package lk.ijse.gdse71.mobilezone.bo.custom.Impl;

import lk.ijse.gdse71.mobilezone.bo.custom.PurchaseReturnBO;
import lk.ijse.gdse71.mobilezone.dao.DAOFactory;
import lk.ijse.gdse71.mobilezone.dao.custom.ItemDAO;
import lk.ijse.gdse71.mobilezone.dao.custom.PurchaseDAO;
import lk.ijse.gdse71.mobilezone.dao.custom.PurchaseDetailDAO;
import lk.ijse.gdse71.mobilezone.dao.custom.PurchaseReturnDAO;
import lk.ijse.gdse71.mobilezone.dao.custom.impl.ItemDAOImpl;
import lk.ijse.gdse71.mobilezone.dao.custom.impl.PurchaseDAOImpl;
import lk.ijse.gdse71.mobilezone.dao.custom.impl.PurchaseReturnDAOImpl;
import lk.ijse.gdse71.mobilezone.db.DBConnection;
import lk.ijse.gdse71.mobilezone.dto.PurchaseDetailDTO;
import lk.ijse.gdse71.mobilezone.dto.PurchaseReturnDTO;
import lk.ijse.gdse71.mobilezone.entity.PurchaseDetail;
import lk.ijse.gdse71.mobilezone.entity.PurchaseReturn;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

public class PurchaseReturnBOImpl implements PurchaseReturnBO {
    //private final PurchaseDAOImpl purchaseDAO = new PurchaseDAOImpl();
    //private final PurchaseDAO purchaseDAO = new PurchaseDAOImpl();
    PurchaseDAO purchaseDAO = (PurchaseDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.PURCHASE);

    //private final PurchaseReturnDAOImpl purchaseReturnDAO = new PurchaseReturnDAOImpl();
    //private final PurchaseReturnDAO purchaseReturnDAO = new PurchaseReturnDAOImpl();
    PurchaseReturnDAO purchaseReturnDAO = (PurchaseReturnDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.PURCHASE_RETURN);

    //private final ItemDAO itemDAO = new ItemDAOImpl();
    ItemDAO itemDAO = (ItemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.ITEM);

    //PurchaseDetailDAOImpl purchaseDetailDAO = new PurchaseDetailDAOImpl();
    //PurchaseDetailDAO purchaseDetailDAO = new PurchaseDetailDAOImpl();
    PurchaseDetailDAO purchaseDetailDAO = (PurchaseDetailDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.PURCHASE_DETAIL);

    @Override
    public boolean saveReturnDetailsList(ArrayList<PurchaseDetailDTO> purchaseDetailDTOS) throws SQLException, ClassNotFoundException {
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
            //boolean isReturnDetailsSaved = purchaseDetailDAO.update(purchaseDetailDTO);
            boolean isReturnDetailsSaved = purchaseDetailDAO.update(purchaseDetail);
            if (!isReturnDetailsSaved) {
                return false;
            }

            //boolean isItemUpdated = itemDAO.reduceQty(purchaseDetailDTO);
            boolean isItemUpdated = itemDAO.reduceQty(purchaseDetail);
            if (!isItemUpdated) {
                // Return false if updating the item quantity fails
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean savePurchaseReturn(PurchaseReturnDTO purchaseReturnDTO) throws SQLException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        try {
            connection.setAutoCommit(false); // 1

            //Replaced to PurchaseReturnDAOImpl
            /*boolean isReturnSaved = SQLUtil.execute(
                    "insert into purReturn values (?,?,?,?,?,?,?,?)",
                    purchaseReturnDTO.getPurRet_Id(),
                    purchaseReturnDTO.getPurchaseId(),
                    purchaseReturnDTO.getSupplierId(),
                    purchaseReturnDTO.getItemId(),
                    purchaseReturnDTO.getRetQuantity(),
                    purchaseReturnDTO.getReason(),
                    purchaseReturnDTO.getRetAmount(),
                    purchaseReturnDTO.getReturnDate()
            );*/

            // Convert DTO to Entity
            PurchaseReturn purchaseReturn = new PurchaseReturn(
                    purchaseReturnDTO.getPurRet_Id(),
                    purchaseReturnDTO.getPurchaseId(),
                    purchaseReturnDTO.getSupplierId(),
                    purchaseReturnDTO.getItemId(),
                    purchaseReturnDTO.getRetQuantity(),
                    purchaseReturnDTO.getReason(),
                    purchaseReturnDTO.getRetAmount(),
                    purchaseReturnDTO.getReturnDate()
            );

            //boolean isReturnSaved = purchaseReturnDAO.save(purchaseReturnDTO);
            boolean isReturnSaved = purchaseReturnDAO.save(purchaseReturn);

            if (isReturnSaved) {
                boolean isPurchaseDetailListSaved = saveReturnDetailsList(purchaseReturnDTO.getPurchaseDetailDTOS());
                if (isPurchaseDetailListSaved) {
                    connection.commit();
                    return true;
                }
            }
            connection.rollback();
            return false;
        } catch (Exception e) {
            connection.rollback();
            return false;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    @Override
    public String getNextPurchaseReturnId() throws SQLException, ClassNotFoundException {
        return purchaseReturnDAO.getNextId();
    }

    public Map<Integer, Integer> getMonthlyPurchaseReturnData() throws SQLException, ClassNotFoundException {
        return purchaseReturnDAO.getMonthlyPurchaseReturnData();
    }
}
