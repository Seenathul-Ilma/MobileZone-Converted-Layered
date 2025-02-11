package lk.ijse.gdse71.mobilezone.bo.custom.Impl;

import lk.ijse.gdse71.mobilezone.bo.custom.ExpenseBO;
import lk.ijse.gdse71.mobilezone.dao.DAOFactory;
import lk.ijse.gdse71.mobilezone.dao.custom.ExpenseDAO;
import lk.ijse.gdse71.mobilezone.dao.custom.impl.ExpenseDAOImpl;
import lk.ijse.gdse71.mobilezone.dto.ExpenseDTO;
import lk.ijse.gdse71.mobilezone.entity.Expense;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

public class ExpenseBOImpl implements ExpenseBO {
    //ExpenseDAO expenseDAO = new ExpenseDAOImpl();
    ExpenseDAO expenseDAO = (ExpenseDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.EXPENSE);

    public ArrayList<ExpenseDTO> getAllExpenses() throws SQLException, ClassNotFoundException{
        ArrayList<ExpenseDTO> expenseDTOS = new ArrayList<>();

        //ArrayList<ExpenseDTO> expenses = expenseDAO.getAll();
        ArrayList<Expense> expenses = expenseDAO.getAll();

        //for (ExpenseDTO expenseDTO : expenses) {
        for (Expense expense : expenses) {
            //expenseDTOS.add(new ExpenseDTO(expenseDTO.getExp_Id(), expenseDTO.getDescription(), expenseDTO.getAmount(), expenseDTO.getDate(), expenseDTO.getExpCategory()));
            expenseDTOS.add(new ExpenseDTO(expense.getExp_Id(), expense.getDescription(), expense.getAmount(), expense.getDate(), expense.getExpCategory()));
        }
        return expenseDTOS;
    }

    public String getNextExpenseId() throws SQLException, ClassNotFoundException {
        return expenseDAO.getNextId();
    }

    public boolean deleteExpense(String expId) throws SQLException, ClassNotFoundException {
        return expenseDAO.delete(expId);
    }

    @Override
    public Map<String, Map<String, Double>> getMonthlyExpensesByCategory() throws SQLException, ClassNotFoundException {
        return expenseDAO.getMonthlyExpensesByCategory();
    }

    public boolean saveExpense(ExpenseDTO expenseDTO) throws SQLException, ClassNotFoundException {
        //return expenseDAO.save(new ExpenseDTO(
        return expenseDAO.save(new Expense(
                expenseDTO.getExp_Id(),
                expenseDTO.getDescription(),
                expenseDTO.getAmount(),
                expenseDTO.getDate(),
                expenseDTO.getExpCategory()
        ));
    }

    public boolean updateExpense(ExpenseDTO expenseDTO) throws SQLException, ClassNotFoundException {
        //return expenseDAO.update(new ExpenseDTO(
        return expenseDAO.update(new Expense(
                expenseDTO.getExp_Id(),
                expenseDTO.getDescription(),
                expenseDTO.getAmount(),
                expenseDTO.getDate(),
                expenseDTO.getExpCategory()
        ));
    }

    // not used in exp_controller
    /*public ArrayList<String> getAllIds() throws SQLException, ClassNotFoundException {
        return expenseDAO.getAllIds();
    }

    public ExpenseDTO findById(String id) throws SQLException, ClassNotFoundException {
        return expenseDAO.findById(id);
    }*/
}
