package lk.ijse.gdse71.mobilezone.dao.custom;

import lk.ijse.gdse71.mobilezone.dao.CrudDAO;
import lk.ijse.gdse71.mobilezone.dto.CategoryDTO;
import lk.ijse.gdse71.mobilezone.entity.Category;

import java.sql.SQLException;

//public interface CategoryDAO extends CrudDAO<CategoryDTO> {
public interface CategoryDAO extends CrudDAO<Category> {

    public boolean checkValidCategoryId(String categoryId) throws SQLException, ClassNotFoundException;
}

