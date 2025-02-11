package lk.ijse.gdse71.mobilezone.dao;

import lk.ijse.gdse71.mobilezone.dao.custom.impl.*;

public class DAOFactory {
    private static DAOFactory daoFactory;
    private DAOFactory() {
        super();
    }

    public static DAOFactory getDaoFactory() {
        if (daoFactory == null) {
            return daoFactory = new DAOFactory();
        }else{
            return daoFactory;
        }
    }

    public enum DAOType{
        CATEGORY, CUSTOMER, EMPLOYEE, EXPENSE, ITEM, LOG_IN_CREDENTIALS, ORDERS, ORDER_RETURN, ORDER_DETAIL,
        PURCHASE, PURCHASE_RETURN, PURCHASE_DETAIL, SUPPLIER, QUERY
    }

    public SuperDAO getDAO(DAOType type) {
        switch (type) {
            case CATEGORY:
                return new CategoryDAOImpl();
            case CUSTOMER:
                return new CustomerDAOImpl();
            case EMPLOYEE:
                return new EmployeeDAOImpl();
            case EXPENSE:
                return new ExpenseDAOImpl();
            case ITEM:
                return new ItemDAOImpl();
            case LOG_IN_CREDENTIALS:
                return new LogInCredentialsDAOImpl();
            case ORDERS:
                return new OrdersDAOImpl();
            case ORDER_RETURN:
                return new OrderReturnDAOImpl();
            case ORDER_DETAIL:
                return new OrderDetailDAOImpl();
            case PURCHASE:
                return new PurchaseDAOImpl();
            case PURCHASE_RETURN:
                return new PurchaseReturnDAOImpl();
            case PURCHASE_DETAIL:
                return new PurchaseDetailDAOImpl();
            case SUPPLIER:
                return new SupplierDAOImpl();
            case QUERY:
                return new QueryDAOImpl();
            default:
                return null;
        }
    }
}
