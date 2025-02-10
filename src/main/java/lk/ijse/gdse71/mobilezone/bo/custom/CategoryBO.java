package lk.ijse.gdse71.mobilezone.bo.custom;

import lk.ijse.gdse71.mobilezone.bo.SuperBO;
import lk.ijse.gdse71.mobilezone.dto.CategoryDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CategoryBO extends SuperBO {
    ArrayList<CategoryDTO> getAllCategories() throws SQLException, ClassNotFoundException;

    String getNextCategoryId() throws SQLException, ClassNotFoundException;

    boolean deleteCategory(String categoryId) throws SQLException, ClassNotFoundException;

    boolean saveCategory(CategoryDTO categoryDTO) throws SQLException, ClassNotFoundException;

    boolean updateCategory(CategoryDTO categoryDTO) throws SQLException, ClassNotFoundException;

    boolean checkValidCategoryId(String categoryId) throws SQLException, ClassNotFoundException;

    ArrayList<String> getAllCategoryIds() throws SQLException, ClassNotFoundException;
}
