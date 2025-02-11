package lk.ijse.gdse71.mobilezone.bo;

import lk.ijse.gdse71.mobilezone.bo.custom.*;
import lk.ijse.gdse71.mobilezone.bo.custom.Impl.*;
import lk.ijse.gdse71.mobilezone.entity.LogInCredentials;

import java.lang.ref.PhantomReference;

public class BOFactory {
    private static BOFactory boFactory;

    private BOFactory() {

    }

    public static BOFactory getBoFactory(){
        if (boFactory == null){
            boFactory = new BOFactory();
        }
        return boFactory;
    }

    public enum BOTypes{
        CATEGORY, CUSTOMER, EMPLOYEE, EXPENSES, ITEM, LOG_IN_CREDENTIALS,
        ORDER, ORDER_RETURN, PURCHASE, PURCHASE_RETURN, QUERY, SUPPLIER
    }

    public SuperBO getBO(BOTypes boType){
        switch (boType){
            case CATEGORY:
                return new CategoryBOImpl();
            case CUSTOMER:
                return new CustomerBOImpl();
            case EMPLOYEE:
                return new EmployeeBOImpl();
            case EXPENSES:
                return new ExpenseBOImpl();
            case ITEM:
                return new ItemBOImpl();
            case LOG_IN_CREDENTIALS:
                return new LogInCredentialsBOImpl();
            case ORDER:
                return new OrderBOImpl();
            case ORDER_RETURN:
                return new OrderReturnBOImpl();
            case PURCHASE:
                return new PurchaseBOImpl();
            case PURCHASE_RETURN:
                return new PurchaseReturnBOImpl();
            case QUERY:
                return new QueryBOImpl();
            case SUPPLIER:
                return new SupplierBOImpl();
            default:
                return null;
        }
    }
}

