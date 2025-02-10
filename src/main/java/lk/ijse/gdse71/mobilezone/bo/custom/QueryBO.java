package lk.ijse.gdse71.mobilezone.bo.custom;

import javafx.scene.chart.PieChart;
import lk.ijse.gdse71.mobilezone.bo.SuperBO;

import java.sql.SQLException;

public interface QueryBO extends SuperBO {
    String confirmCustomerBought(String selectedCustomerId, String selectedOrderId, String selectedItemId) throws SQLException, ClassNotFoundException;

    String confirmBoughtFromSupplier(String selectedSupplierId, String selectedPurchaseId, String selectedItemId) throws SQLException, ClassNotFoundException;

    void fetchSalesData(PieChart salesByCategoryPieChart) throws SQLException, ClassNotFoundException;
}
