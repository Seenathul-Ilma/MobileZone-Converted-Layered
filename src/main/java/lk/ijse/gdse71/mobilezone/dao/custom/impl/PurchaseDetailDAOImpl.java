package lk.ijse.gdse71.mobilezone.dao.custom.impl;

import lk.ijse.gdse71.mobilezone.dao.SQLUtil;
import lk.ijse.gdse71.mobilezone.dao.custom.PurchaseDetailDAO;
import lk.ijse.gdse71.mobilezone.dto.PurchaseDetailDTO;
import lk.ijse.gdse71.mobilezone.entity.PurchaseDetail;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PurchaseDetailDAOImpl implements PurchaseDetailDAO {

    @Override
    //public ArrayList<PurchaseDetailDTO> getAll() throws SQLException, ClassNotFoundException {
    public ArrayList<PurchaseDetail> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        return "";
    }

    @Override
    //public boolean save(PurchaseDetailDTO purchaseDetailDTO) throws SQLException {
    public boolean save(PurchaseDetail entity) throws SQLException {
        return SQLUtil.execute(
                "insert into purchaseDetail values (?,?,?,?,?,?)",
                //purchaseDetailDTO.getPurchaseId(),
                entity.getPurchaseId(),
                entity.getItemId(),
                entity.getQuantity(),
                entity.getIsReturned(),
                entity.getUnitPrice(),
                entity.getTotalPrice()
        );
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    //public boolean update(PurchaseDetailDTO purchaseDetailDTO) throws SQLException {
    public boolean update(PurchaseDetail entity) throws SQLException {
        return SQLUtil.execute(
                "update purchaseDetail set isReturned=? where purchaseId=?",
                //purchaseDetailDTO.getIsReturned(),
                entity.getIsReturned(),
                entity.getPurchaseId()
        );
    }

    @Override
    public ArrayList<String> getAllIds() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    //public PurchaseDetailDTO findById(String id) throws SQLException, ClassNotFoundException {
    public PurchaseDetail findById(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    //public PurchaseDetailDTO findById(String purchaseId, String selectedItemId) throws SQLException {
    public PurchaseDetail findById(String purchaseId, String selectedItemId) throws SQLException {
        ResultSet rst = SQLUtil.execute("select * from purchaseDetail where purchaseId=? AND itemId=?", purchaseId, selectedItemId);

        if (rst.next()) {
            //return new PurchaseDetailDTO(
            return new PurchaseDetail(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getInt(3),
                    rst.getString(4),
                    rst.getDouble(5),
                    rst.getDouble(6)
            );
        }else{
            return null;
        }
    }

    @Override
    public String getReturnOrNot(String purchaseId, String selectedItemId) throws SQLException {
        ResultSet resultSet = SQLUtil.execute("select isReturned from purchaseDetail where purchaseId=? AND itemId=?", purchaseId, selectedItemId);
        if(resultSet.next()){
            return resultSet.getString("isReturned");
        }
        return "NO";
    }

    @Override
    public String confirmItemBought(String selectedPurchaseId, String selectedItemId) throws SQLException {
        ResultSet resultSet = SQLUtil.execute("select itemId from purchaseDetail where purchaseId=? AND itemId=?", selectedPurchaseId, selectedItemId);
        if(resultSet.next()){
            return resultSet.getString("itemId");
        }
        return "";
    }
}
