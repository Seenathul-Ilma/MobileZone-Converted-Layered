package lk.ijse.gdse71.mobilezone.dao.custom;

import javafx.scene.chart.PieChart;
import lk.ijse.gdse71.mobilezone.dao.SuperDAO;

import java.sql.SQLException;
import java.util.Map;

public interface QueryDAO extends SuperDAO {
    String confirmCustomerBought(String selectedCustomerId, String selectedOrderId, String selectedItemId) throws SQLException, ClassNotFoundException;

    String confirmBoughtFromSupplier(String selectedSupplierId, String selectedPurchaseId, String selectedItemId) throws SQLException, ClassNotFoundException;

    void fetchSalesData(PieChart salesByCategoryPieChart) throws SQLException, ClassNotFoundException;

    Map<String, Map<String, Integer>> getEmployeePerformanceByMonth() throws SQLException, ClassNotFoundException;
}
