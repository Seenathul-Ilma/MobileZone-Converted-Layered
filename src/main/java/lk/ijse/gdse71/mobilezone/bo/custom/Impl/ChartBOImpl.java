package lk.ijse.gdse71.mobilezone.bo.custom.Impl;

import lk.ijse.gdse71.mobilezone.bo.custom.ChartBO;
import lk.ijse.gdse71.mobilezone.dao.DAOFactory;
import lk.ijse.gdse71.mobilezone.dao.custom.ChartDAO;
import lk.ijse.gdse71.mobilezone.dao.custom.QueryDAO;

import java.sql.SQLException;
import java.util.Map;

public class ChartBOImpl implements ChartBO {
    QueryDAO queryDAO = (QueryDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.QUERY);
    ChartDAO chartDAO = (ChartDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.CHART);

    public Map<Integer, Integer> getMonthlyOrderData() throws SQLException, ClassNotFoundException {
        return chartDAO.getMonthlyOrderData();
    }

    public Map<Integer, Integer> getMonthlyOrderReturnsData() throws SQLException, ClassNotFoundException {
        return chartDAO.getMonthlyOrderReturnsData();
    }

    public Map<Integer, Integer> getMonthlyPurchasesData() throws SQLException, ClassNotFoundException {
        return chartDAO.getMonthlyPurchasesData();
    }

    public Map<Integer, Integer> getMonthlyPurchaseReturnData() throws SQLException, ClassNotFoundException {
        return chartDAO.getMonthlyPurchaseReturnData();
    }

    public Map<String, Map<String, Double>> getMonthlyExpensesByCategory() throws SQLException, ClassNotFoundException {
        return chartDAO.getMonthlyExpensesByCategory();
    }

    /*public Map<String, Map<String, Integer>> getEmployeePerformanceByMonth() throws SQLException, ClassNotFoundException {
        return chartDAO.getEmployeePerformanceByMonth();
    }*/

}
