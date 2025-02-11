package lk.ijse.gdse71.mobilezone.dao.custom.impl;

import lk.ijse.gdse71.mobilezone.dao.custom.ExpenseDAO;
import lk.ijse.gdse71.mobilezone.dao.SQLUtil;
import lk.ijse.gdse71.mobilezone.dto.ExpenseDTO;
import lk.ijse.gdse71.mobilezone.entity.Customer;
import lk.ijse.gdse71.mobilezone.entity.Expense;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ExpenseDAOImpl implements ExpenseDAO {
    @Override
    //public ArrayList<ExpenseDTO> getAll() throws SQLException, ClassNotFoundException {
    public ArrayList<Expense> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select * from expenses");

        //ArrayList<ExpenseDTO> expenseDTOS = new ArrayList<>();
        ArrayList<Expense> allExpenses = new ArrayList<>();

        while (rst.next()) {
            //ExpenseDTO expenseDTO = new ExpenseDTO(
            Expense entity = new Expense(
                    rst.getString(1),  // Expense Id
                    rst.getString(2),  // desc
                    rst.getDouble(3),  // amount
                    rst.getDate(4),   // date
                    rst.getString(5)
            );
            //expenseDTOS.add(expenseDTO);
            allExpenses.add(entity);
        }
        //return expenseDTOS;
        return allExpenses;
    }

    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select exp_Id from expenses order by exp_Id desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(2);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("EX%03d", newIdIndex);
        }
        return "EX001";
    }

    @Override
    public boolean delete(String expId) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("delete from expenses where exp_Id=?", expId);
    }

    @Override
    //public boolean save(ExpenseDTO expenseDTO) throws SQLException, ClassNotFoundException {
    public boolean save(Expense entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute(
                "insert into expenses values (?,?,?,?,?)",
                //expenseDTO.getExp_Id(),
                entity.getExp_Id(),
                entity.getDescription(),
                entity.getAmount(),
                entity.getDate(),
                entity.getExpCategory()
        );
    }

    @Override
    //public boolean update(ExpenseDTO expenseDTO) throws SQLException, ClassNotFoundException {
    public boolean update(Expense entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute(
                "update expenses set description=?, amount=?, date=?, expCategory=? where exp_Id=?",
                //expenseDTO.getDescription(),
                entity.getDescription(),
                entity.getAmount(),
                entity.getDate(),
                entity.getExpCategory(),
                entity.getExp_Id()
        );
    }

    @Override
    public ArrayList<String> getAllIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select exp_Id from expenses");

        ArrayList<String> employeeIds = new ArrayList<>();

        while (rst.next()) {
            employeeIds.add(rst.getString(1));
        }
        return employeeIds;
    }

    @Override
    //public ExpenseDTO findById(String id) throws SQLException, ClassNotFoundException {
    public Expense findById(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select * from expenses where exp_Id=?", id);

        if (rst.next()) {
            //return new ExpenseDTO(
            return new Expense(
                    rst.getString(1),  // expense ID
                    rst.getString(2),  // desc
                    rst.getDouble(3),  // amount
                    rst.getDate(4),  // date
                    rst.getString(5)   // exp_category
            );
        }
        return null;
    }

    @Override
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
}
