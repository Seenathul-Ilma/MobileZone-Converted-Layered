package lk.ijse.gdse71.mobilezone.bo.custom.Impl;

import lk.ijse.gdse71.mobilezone.bo.custom.CategoryBO;
import lk.ijse.gdse71.mobilezone.dao.DAOFactory;
import lk.ijse.gdse71.mobilezone.dao.custom.CategoryDAO;
import lk.ijse.gdse71.mobilezone.dao.custom.impl.CategoryDAOImpl;
import lk.ijse.gdse71.mobilezone.dto.CategoryDTO;
import lk.ijse.gdse71.mobilezone.entity.Category;

import java.sql.SQLException;
import java.util.ArrayList;

public class CategoryBOImpl implements CategoryBO {
    //CategoryDAO categoryDAO = new CategoryDAOImpl();
    CategoryDAO categoryDAO = (CategoryDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.CATEGORY);

    @Override
    public ArrayList<CategoryDTO> getAllCategories() throws SQLException, ClassNotFoundException {
        ArrayList<CategoryDTO> categoryDTOS = new ArrayList<>();

        //ArrayList<CategoryDTO> categories = categoryDAO.getAll();
        ArrayList<Category> categories = categoryDAO.getAll();

        //for (CategoryDTO categoryDTO : categories) {
        for (Category category : categories) {
            //categoryDTOS.add(new CategoryDTO(categoryDTO.getCategoryId(), categoryDTO.getCategoryName(), categoryDTO.getSubCategory(), categoryDTO.getDescription()));
            categoryDTOS.add(new CategoryDTO(category.getCategoryId(), category.getCategoryName(), category.getSubCategory(), category.getDescription()));
        }
        return categoryDTOS;
    }

    @Override
    public String getNextCategoryId() throws SQLException, ClassNotFoundException {
        return categoryDAO.getNextId();
    }

    @Override
    public boolean deleteCategory(String categoryId) throws SQLException, ClassNotFoundException {
        return categoryDAO.delete(categoryId);
    }

    @Override
    public boolean saveCategory(CategoryDTO categoryDTO) throws SQLException, ClassNotFoundException {
        //return categoryDAO.save(new CategoryDTO(categoryDTO.getCategoryId(),categoryDTO.getCategoryName(),categoryDTO.getSubCategory(),categoryDTO.getDescription()));
        return categoryDAO.save(new Category(categoryDTO.getCategoryId(),categoryDTO.getCategoryName(),categoryDTO.getSubCategory(),categoryDTO.getDescription()));
    }

    @Override
    public boolean updateCategory(CategoryDTO categoryDTO) throws SQLException, ClassNotFoundException {
        //return categoryDAO.update(new CategoryDTO(categoryDTO.getCategoryId(),categoryDTO.getCategoryName(),categoryDTO.getSubCategory(),categoryDTO.getDescription()));
        return categoryDAO.update(new Category(categoryDTO.getCategoryId(),categoryDTO.getCategoryName(),categoryDTO.getSubCategory(),categoryDTO.getDescription()));
    }

    @Override
    public boolean checkValidCategoryId(String categoryId) throws SQLException, ClassNotFoundException {
        return categoryDAO.checkValidCategoryId(categoryId);
    }

    @Override
    public ArrayList<String> getAllCategoryIds() throws SQLException, ClassNotFoundException {
        ArrayList<String> categories =  categoryDAO.getAllIds();
        ArrayList<String> categoryIds = new ArrayList<>();

        for (String categoryId : categories) {
            categoryIds.add(categoryId);
        }
        return categoryIds;
    }

}
