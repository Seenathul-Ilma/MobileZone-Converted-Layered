package lk.ijse.gdse71.mobilezone.bo.custom;

import lk.ijse.gdse71.mobilezone.bo.SuperBO;
import lk.ijse.gdse71.mobilezone.dto.ExpenseDTO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

public interface ExpenseBO extends SuperBO {
    ArrayList<ExpenseDTO> getAllExpenses() throws SQLException, ClassNotFoundException;

    String getNextExpenseId() throws SQLException, ClassNotFoundException;

    boolean saveExpense(ExpenseDTO expenseDTO) throws SQLException, ClassNotFoundException;

    boolean updateExpense(ExpenseDTO expenseDTO) throws SQLException, ClassNotFoundException;

    boolean deleteExpense(String expId) throws SQLException, ClassNotFoundException;

    Map<String, Map<String, Double>> getMonthlyExpensesByCategory() throws SQLException, ClassNotFoundException;
}
