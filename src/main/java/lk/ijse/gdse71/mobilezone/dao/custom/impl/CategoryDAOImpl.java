package lk.ijse.gdse71.mobilezone.dao.custom.impl;

import lk.ijse.gdse71.mobilezone.dao.custom.CategoryDAO;
import lk.ijse.gdse71.mobilezone.dto.CategoryDTO;
import lk.ijse.gdse71.mobilezone.dao.SQLUtil;
import lk.ijse.gdse71.mobilezone.entity.Category;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CategoryDAOImpl implements CategoryDAO {
    @Override
    //public ArrayList<CategoryDTO> getAll() throws SQLException, ClassNotFoundException {
    public ArrayList<Category> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select * from category");

        //ArrayList<CategoryDTO> categoryDTOS = new ArrayList<>();
        ArrayList<Category> allCategories = new ArrayList<>();

        while (rst.next()) {
            //CategoryDTO categoryDTO = new CategoryDTO(
            Category entity = new Category(
                    rst.getString(1),  // Category Id
                    rst.getString(2),  // Category Name
                    rst.getString(3),  // Sub Category
                    rst.getString(4)   // Description
            );
            //categoryDTOS.add(categoryDTO);
            allCategories.add(entity);
        }
        //return categoryDTOS;
        return allCategories;
    }

    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select categoryId from category order by categoryId desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1); // Last Category ID
            String substring = lastId.substring(2); // Extract the numeric part
            int i = Integer.parseInt(substring); // Convert the numeric part to integer
            int newIdIndex = i + 1; // Increment the number by 1
            return String.format("IC%03d", newIdIndex); // Return the new Category ID in format ICnnn
        }
        return "IC001";
    }

    @Override
    public boolean delete(String categoryId) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("delete from category where categoryId=?", categoryId);
    }

    @Override
    //public boolean save(CategoryDTO categoryDTO) throws SQLException, ClassNotFoundException {
    public boolean save(Category entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute(
                "insert into category values (?,?,?,?)",
                //categoryDTO.getCategoryId(),
                entity.getCategoryId(),
                entity.getCategoryName(),
                entity.getSubCategory(),
                entity.getDescription()
        );
    }

    @Override
    //public boolean update(CategoryDTO categoryDTO) throws SQLException, ClassNotFoundException {
    public boolean update(Category entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute(
                "update category set categoryName=?, subCategory=?, description=? where categoryId=?",
                //categoryDTO.getCategoryName(),
                entity.getCategoryName(),
                entity.getSubCategory(),
                entity.getDescription(),
                entity.getCategoryId()
        );
    }

    @Override
    public ArrayList<String> getAllIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select categoryId from category");

        ArrayList<String> categoryIds = new ArrayList<>();

        while (rst.next()) {
            categoryIds.add(rst.getString(1));
        }

        return categoryIds;
    }

    @Override
    //public CategoryDTO findById(String id) throws SQLException, ClassNotFoundException {
    public Category findById(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select * from category where categoryId=?", id);

        if (rst.next()) {
            //return new CategoryDTO(
            return new Category(
                    rst.getString(1),  // Item Id
                    rst.getString(2),  // Category Id
                    rst.getString(3),  // Name
                    rst.getString(4)  // Brand
            );
        }
        return null;
    }

    @Override
    public boolean checkValidCategoryId(String categoryId) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select categoryId from category where categoryId=?", categoryId);

        if (rst.next()) {
            return true;
        }
        return false;
    }
}
