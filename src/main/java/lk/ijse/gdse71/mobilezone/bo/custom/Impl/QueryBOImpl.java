package lk.ijse.gdse71.mobilezone.bo.custom.Impl;

import javafx.scene.chart.PieChart;
import lk.ijse.gdse71.mobilezone.bo.custom.QueryBO;
import lk.ijse.gdse71.mobilezone.dao.DAOFactory;
import lk.ijse.gdse71.mobilezone.dao.custom.QueryDAO;
import lk.ijse.gdse71.mobilezone.dao.custom.impl.QueryDAOImpl;

import java.sql.SQLException;
import java.util.Map;

public class QueryBOImpl implements QueryBO {
    //QueryDAO queryDAO = new QueryDAOImpl();
    QueryDAO queryDAO = (QueryDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.QUERY);

    @Override
    public String confirmCustomerBought(String selectedCustomerId, String selectedOrderId, String selectedItemId) throws SQLException, ClassNotFoundException {
        return queryDAO.confirmCustomerBought(selectedCustomerId, selectedOrderId, selectedItemId);
    }

    @Override
    public String confirmBoughtFromSupplier(String selectedSupplierId, String selectedPurchaseId, String selectedItemId) throws SQLException, ClassNotFoundException {
        return queryDAO.confirmBoughtFromSupplier(selectedSupplierId, selectedPurchaseId, selectedItemId);
    }

    @Override
    public void fetchSalesData(PieChart salesByCategoryPieChart) throws SQLException, ClassNotFoundException {
        queryDAO.fetchSalesData(salesByCategoryPieChart);
    }

    @Override
    public Map<String, Map<String, Integer>> getEmployeePerformanceByMonth() throws SQLException, ClassNotFoundException {
        return queryDAO.getEmployeePerformanceByMonth();
    }

}