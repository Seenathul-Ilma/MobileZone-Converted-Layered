package lk.ijse.gdse71.mobilezone.dao.custom.impl;

import javafx.scene.chart.PieChart;
import lk.ijse.gdse71.mobilezone.dao.custom.QueryDAO;
import lk.ijse.gdse71.mobilezone.dao.SQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class QueryDAOImpl implements QueryDAO {

    @Override
    public String confirmCustomerBought(String selectedCustomerId, String selectedOrderId, String selectedItemId) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute(
                "select customerId from orders o join orderDetail od on o.orderId = od.orderId where customerId = ? AND itemId=? AND od.orderId=? ",
                selectedCustomerId, selectedItemId, selectedOrderId);
        if(resultSet.next()){
            return resultSet.getString("customerId");
        }
        return "";
    }

    @Override
    public String confirmBoughtFromSupplier(String selectedSupplierId, String selectedPurchaseId, String selectedItemId) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute(
                "select supplierId from purchase p join purchaseDetail pd on p.purchaseId = pd.purchaseId where supplierId = ? AND itemId=? AND pd.purchaseId=? ",
                selectedSupplierId, selectedItemId, selectedPurchaseId);
        if(resultSet.next()){
            return resultSet.getString("supplierId");
        }
        return "";
    }

    public void fetchSalesData(PieChart salesByCategoryPieChart) throws SQLException {
        String isReturned = "No";
        try {
            ResultSet resultSet = SQLUtil.execute(
                    "select c.categoryId, SUM(od.quantity) as total_sales from category c join item i on c.categoryId = i.categoryId join orderDetail od on od.itemId = i.itemId where od.isReturned=? group by categoryId", isReturned
            );

            while (resultSet.next()) {
                String category = resultSet.getString("categoryId");
                double totalSales = resultSet.getDouble("total_sales");

                // Add a new slice for each category
                PieChart.Data slice = new PieChart.Data(category, totalSales);
                salesByCategoryPieChart.getData().add(slice);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public Map<String, Map<String, Integer>> getEmployeePerformanceByMonth() throws SQLException, ClassNotFoundException {
        Map<String, Map<String, Integer>> employeeData = new HashMap<>();
        ResultSet rst = SQLUtil.execute("SELECT e.name, DATE_FORMAT(o.orderDate, '%Y-%m') AS month, COUNT(o.orderId) AS total_sales " +
                "FROM employee e " +
                "JOIN orders o ON e.employeeId = o.employeeId " +
                "GROUP BY e.name, month " +
                "ORDER BY month, e.name");

        while (rst.next()) {
            String employeeName = rst.getString("name");
            String month = rst.getString("month");
            int totalSales = rst.getInt("total_sales");

            // Debugging: Print fetched data
            System.out.println("Employee: " + employeeName + ", Month: " + month + ", Sales: " + totalSales);

            employeeData.putIfAbsent(employeeName, new HashMap<>());
            employeeData.get(employeeName).put(month, totalSales);
        }
        return employeeData;
    }
}
