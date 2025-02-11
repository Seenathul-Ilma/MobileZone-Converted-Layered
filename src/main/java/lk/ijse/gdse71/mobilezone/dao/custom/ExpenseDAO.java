package lk.ijse.gdse71.mobilezone.dao.custom;

import lk.ijse.gdse71.mobilezone.dao.CrudDAO;
import lk.ijse.gdse71.mobilezone.dto.ExpenseDTO;
import lk.ijse.gdse71.mobilezone.entity.Expense;

import java.sql.SQLException;
import java.util.Map;

//public interface ExpenseDAO extends CrudDAO<ExpenseDTO> {
public interface ExpenseDAO extends CrudDAO<Expense> {
    Map<String, Map<String, Double>> getMonthlyExpensesByCategory() throws SQLException, ClassNotFoundException;

}
