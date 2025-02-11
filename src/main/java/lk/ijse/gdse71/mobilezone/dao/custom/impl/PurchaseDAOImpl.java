package lk.ijse.gdse71.mobilezone.dao.custom.impl;

import lk.ijse.gdse71.mobilezone.dao.SQLUtil;
import lk.ijse.gdse71.mobilezone.dao.custom.PurchaseDAO;
import lk.ijse.gdse71.mobilezone.dto.OrderDTO;
import lk.ijse.gdse71.mobilezone.dto.PurchaseDTO;
import lk.ijse.gdse71.mobilezone.dto.PurchaseDetailDTO;
import lk.ijse.gdse71.mobilezone.entity.Purchase;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PurchaseDAOImpl implements PurchaseDAO {
    @Override
    //public ArrayList<PurchaseDTO> getAll() throws SQLException, ClassNotFoundException {
    public ArrayList<Purchase> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select * from purchase");

        //ArrayList<PurchaseDTO> purchaseDTOS = new ArrayList<>();
        ArrayList<Purchase> allPurchase = new ArrayList<>();

        while (rst.next()) {
            //PurchaseDTO purchaseDTO = new PurchaseDTO(
            Purchase entity = new Purchase(
                    rst.getString(1),  // Item Id
                    rst.getString(2),  // Category Id
                    rst.getDate(3),  // Name
                    rst.getDouble(4)  // Brand
            );
            //purchaseDTOS.add(purchaseDTO);
            allPurchase.add(entity);
        }
        //return purchaseDTOS;
        return allPurchase;
    }

    @Override
    public String getNextId() throws SQLException {
        ResultSet rst = SQLUtil.execute("select purchaseId from purchase order by purchaseId desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(1);
            int i = Integer.parseInt(substring); // 2
            int newIdIndex = i + 1; // 3
            return String.format("P%03d", newIdIndex);
        }
        return "P001";
    }

    @Override
    //public boolean save(PurchaseDTO purchaseDTO) throws SQLException {
    public boolean save(Purchase entity) throws SQLException {
        return SQLUtil.execute(
                "insert into purchase values (?,?,?,?)",
                //purchaseDTO.getPurchaseId(),
                entity.getPurchaseId(),
                entity.getSupplierId(),
                entity.getPurchaseDate(),
                entity.getTotalAmount()
                //orderDTO.getOrderDetailsDTOS()
        );
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    //public boolean update(PurchaseDTO purchaseDTO) throws SQLException, ClassNotFoundException {
    public boolean update(Purchase entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute(
                "update purchase set supplierId=?, purchaseDate=?, totalAmount=? where purchaseId=?",
                //purchaseDTO.getSupplierId(),
                entity.getSupplierId(),
                entity.getPurchaseDate(),
                entity.getTotalAmount(),
                entity.getPurchaseId()
        );
    }

    @Override
    public ArrayList<String> getAllIds() throws SQLException {
        ResultSet rst = SQLUtil.execute("select purchaseId from purchase");

        ArrayList<String> purchaseIds = new ArrayList<>();

        while (rst.next()) {
            purchaseIds.add(rst.getString(1));
        }
        return purchaseIds;
    }

    @Override
    //public PurchaseDTO findById(String purchaseId) throws SQLException {
    public Purchase findById(String purchaseId) throws SQLException {
        ResultSet rst = SQLUtil.execute("select * from purchase where purchaseId=?", purchaseId);

        //ArrayList<PurchaseDTO> purchaseDTOS = new ArrayList<>();
        ArrayList<Purchase> purchase = new ArrayList<>();
        if (rst.next()) {
            //return new PurchaseDTO(
            return new Purchase(
                    rst.getString(1),  // Order ID
                    rst.getString(2),  // Customer Name
                    rst.getDate(3),  // Order Date
                    rst.getDouble(4)
            );
        }
        return null;
    }

    public Map<Integer, Integer> getMonthlyPurchasesData() throws SQLException, ClassNotFoundException {
        Map<Integer, Integer> purchaseData = new HashMap<>();
        ResultSet resultSet = SQLUtil.execute("SELECT MONTH(purchaseDate) AS month, COUNT(*) AS total_purchases FROM purchase GROUP BY MONTH(purchaseDate)");

        while (resultSet.next()) {
            int month = resultSet.getInt("month");
            int totalPurchases = resultSet.getInt("total_purchases");
            purchaseData.put(month, totalPurchases);
        }

        return purchaseData;
    }


    /*@Override
    public boolean save(PurchaseDetailDTO purchaseDetailDTO) throws SQLException {
        return SQLUtil.execute(
                "insert into purchaseDetail values (?,?,?,?,?,?)",
                purchaseDetailDTO.getPurchaseId(),
                purchaseDetailDTO.getItemId(),
                purchaseDetailDTO.getQuantity(),
                purchaseDetailDTO.getIsReturned(),
                purchaseDetailDTO.getUnitPrice(),
                purchaseDetailDTO.getTotalPrice()
        );
    }*/


    /*public boolean save(PurchaseDTO purchaseDTO) throws SQLException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        try {
            connection.setAutoCommit(false); // 1

            boolean isPurchaseSaved = SQLUtil.execute(
                    "insert into purchase values (?,?,?,?)",
                    purchaseDTO.getPurchaseId(),
                    purchaseDTO.getSupplierId(),
                    purchaseDTO.getPurchaseDate(),
                    purchaseDTO.getTotalAmount()
                    //orderDTO.getOrderDetailsDTOS()
            );
            if (isPurchaseSaved) {
                boolean isPurchaseDetailListSaved = purchaseDetailModel.savePurchaseDetailsList(purchaseDTO.getPurchaseDetailDTOS());
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
    }*/

    /*@Override
    public boolean update(PurchaseDetailDTO purchaseDetailDTO) throws SQLException {
        return SQLUtil.execute(
                "update purchaseDetail set isReturned=? where purchaseId=?",
                purchaseDetailDTO.getIsReturned(),
                purchaseDetailDTO.getPurchaseId()
        );
    }*/

    /*@Override
    public PurchaseDetailDTO findById(String purchaseId, String selectedItemId) throws SQLException {
        ResultSet rst = SQLUtil.execute("select * from purchaseDetail where purchaseId=? AND itemId=?", purchaseId, selectedItemId);

        if (rst.next()) {
            return new PurchaseDetailDTO(
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
    }*/

    /*@Override
    public String getReturnOrNot(String purchaseId, String selectedItemId) throws SQLException {
        ResultSet resultSet = SQLUtil.execute("select isReturned from purchaseDetail where purchaseId=? AND itemId=?", purchaseId, selectedItemId);
        if(resultSet.next()){
            return resultSet.getString("isReturned");
        }
        return "NO";
    }*/

    /*@Override
    public String confirmItemBought(String selectedPurchaseId, String selectedItemId) throws SQLException {
        ResultSet resultSet = SQLUtil.execute("select itemId from purchaseDetail where purchaseId=? AND itemId=?", selectedPurchaseId, selectedItemId);
        if(resultSet.next()){
            return resultSet.getString("itemId");
        }
        return "";
    }*/

    /*@Override
    public String confirmBoughtFromSupplier(String selectedSupplierId, String selectedPurchaseId, String selectedItemId) throws SQLException {
        ResultSet resultSet = SQLUtil.execute(
                "select supplierId from purchase p join purchaseDetail pd on p.purchaseId = pd.purchaseId where supplierId = ? AND itemId=? AND pd.purchaseId=? ",
                selectedSupplierId, selectedItemId, selectedPurchaseId);
        if(resultSet.next()){
            return resultSet.getString("supplierId");
        }
        return "";
    }*/
}
