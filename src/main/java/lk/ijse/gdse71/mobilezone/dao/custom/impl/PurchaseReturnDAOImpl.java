package lk.ijse.gdse71.mobilezone.dao.custom.impl;

import lk.ijse.gdse71.mobilezone.dao.SQLUtil;
import lk.ijse.gdse71.mobilezone.dao.custom.PurchaseReturnDAO;
import lk.ijse.gdse71.mobilezone.dto.PurchaseReturnDTO;
import lk.ijse.gdse71.mobilezone.entity.PurchaseReturn;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PurchaseReturnDAOImpl implements PurchaseReturnDAO {
    @Override
    //public ArrayList<PurchaseReturnDTO> getAll() throws SQLException, ClassNotFoundException {
    public ArrayList<PurchaseReturn> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public String getNextId() throws SQLException {
        ResultSet rst = SQLUtil.execute("select purRet_Id from purReturn order by purRet_Id desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(2);
            int i = Integer.parseInt(substring); // 2
            int newIdIndex = i + 1; // 3
            return String.format("PR%03d", newIdIndex);
        }
        return "PR001";
    }

    @Override
    //public boolean save(PurchaseReturnDTO purchaseReturnDTO) throws SQLException {
    public boolean save(PurchaseReturn entity) throws SQLException {
        return SQLUtil.execute(
                "insert into purReturn values (?,?,?,?,?,?,?,?)",
                //purchaseReturnDTO.getPurRet_Id(),
                entity.getPurRet_Id(),
                entity.getPurchaseId(),
                entity.getSupplierId(),
                entity.getItemId(),
                entity.getRetQuantity(),
                entity.getReason(),
                entity.getRetAmount(),
                entity.getReturnDate()
        );
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    //public boolean update(PurchaseReturnDTO dto) throws SQLException, ClassNotFoundException {
    public boolean update(PurchaseReturn entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public ArrayList<String> getAllIds() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    //public PurchaseReturnDTO findById(String id) throws SQLException, ClassNotFoundException {
    public PurchaseReturn findById(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public Map<Integer, Integer> getMonthlyPurchaseReturnData() throws SQLException, ClassNotFoundException {
        Map<Integer, Integer> returnData = new HashMap<>();
        ResultSet resultSet = SQLUtil.execute("SELECT MONTH(returnDate) AS month, COUNT(*) AS total_returns FROM purReturn GROUP BY MONTH(returnDate)");

        while (resultSet.next()) {
            int month = resultSet.getInt("month");
            int totalReturns = resultSet.getInt("total_returns");
            returnData.put(month, totalReturns);
        }

        return returnData;
    }

    /*public boolean saveReturn(PurchaseReturnDTO purchaseReturnDTO) throws SQLException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        try {
            connection.setAutoCommit(false); // 1

            boolean isReturnSaved = SQLUtil.execute(
                    "insert into purReturn values (?,?,?,?,?,?,?,?)",
                    purchaseReturnDTO.getPurRet_Id(),
                    purchaseReturnDTO.getPurchaseId(),
                    purchaseReturnDTO.getSupplierId(),
                    purchaseReturnDTO.getItemId(),
                    purchaseReturnDTO.getRetQuantity(),
                    purchaseReturnDTO.getReason(),
                    purchaseReturnDTO.getRetAmount(),
                    purchaseReturnDTO.getReturnDate()
            );

            if (isReturnSaved) {
                boolean isPurchaseDetailListSaved = purchaseDetailModel.saveReturnDetailsList(purchaseReturnDTO.getPurchaseDetailDTOS());
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
    }*/
}
