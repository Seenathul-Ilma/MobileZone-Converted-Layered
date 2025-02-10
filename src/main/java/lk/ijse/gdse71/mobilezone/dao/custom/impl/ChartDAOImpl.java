package lk.ijse.gdse71.mobilezone.dao.custom.impl;

import javafx.scene.chart.Chart;
import javafx.scene.chart.PieChart;
import lk.ijse.gdse71.mobilezone.dao.SQLUtil;
import lk.ijse.gdse71.mobilezone.dao.custom.ChartDAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChartDAOImpl implements ChartDAO {

    public Map<Integer, Integer> getMonthlyOrderData() throws SQLException, ClassNotFoundException {
        Map<Integer, Integer> orderData = new HashMap<>();
        ResultSet resultSet = SQLUtil.execute("SELECT MONTH(orderDate) AS month, COUNT(*) AS total_orders FROM orders GROUP BY MONTH(orderDate)");

        while (resultSet.next()) {
            int month = resultSet.getInt("month");
            int totalOrders = resultSet.getInt("total_orders");
            orderData.put(month, totalOrders);
        }

        return orderData;
    }

    public Map<Integer, Integer> getMonthlyOrderReturnsData() throws SQLException, ClassNotFoundException {
        Map<Integer, Integer> returnData = new HashMap<>();
        ResultSet resultSet = SQLUtil.execute("SELECT MONTH(returnDate) AS month, COUNT(*) AS total_returns FROM orderRet GROUP BY MONTH(returnDate)");

        while (resultSet.next()) {
            int month = resultSet.getInt("month");
            int totalReturns = resultSet.getInt("total_returns");
            returnData.put(month, totalReturns);
        }

        return returnData;
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

    /*public void fetchSalesData(PieChart salesByCategoryPieChart) throws SQLException {
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
    }*/

    public Map<String, Map<String, Double>> getMonthlyExpensesByCategory() throws SQLException, ClassNotFoundException {
        Map<String, Map<String, Double>> expenseData = new HashMap<>();
        ResultSet rst = SQLUtil.execute("select expCategory, DATE_FORMAT(date, '%Y-%m') AS month, SUM(amount) as total_expense from expenses group by expCategory, month order by month, expCategory");

        while (rst.next()) {
            String expCategory = rst.getString("expCategory");
            String month = rst.getString("month");
            double totalExpense = rst.getDouble("total_expense");

            expenseData.putIfAbsent(expCategory, new HashMap<>());
            expenseData.get(expCategory).put(month, totalExpense);
        }
        return expenseData;
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

    @Override
    public ArrayList<Chart> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        return "";
    }

    @Override
    public boolean save(Chart dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(Chart dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public ArrayList<String> getAllIds() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public Chart findById(String id) throws SQLException, ClassNotFoundException {
        return null;
    }
}
