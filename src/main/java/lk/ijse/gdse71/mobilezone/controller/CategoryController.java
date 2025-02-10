package lk.ijse.gdse71.mobilezone.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.gdse71.mobilezone.bo.BOFactory;
import lk.ijse.gdse71.mobilezone.bo.custom.CategoryBO;
import lk.ijse.gdse71.mobilezone.bo.custom.Impl.CategoryBOImpl;
import lk.ijse.gdse71.mobilezone.dao.custom.CategoryDAO;
import lk.ijse.gdse71.mobilezone.dao.custom.impl.CategoryDAOImpl;
import lk.ijse.gdse71.mobilezone.dto.CategoryDTO;
import lk.ijse.gdse71.mobilezone.dto.tm.CategoryTM;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class CategoryController implements Initializable {

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<CategoryTM, String> colCategoryId;

    @FXML
    private TableColumn<CategoryTM, String> colCategoryName;

    @FXML
    private TableColumn<CategoryTM, String> colDescription;

    @FXML
    private TableColumn<CategoryTM, String> colSubCategory;

    @FXML
    private Label lblCategoryId;

    @FXML
    private TableView<CategoryTM> tblCategory;

    @FXML
    private TextField txtCategoryName;

    @FXML
    private TextField txtDescription;

    @FXML
    private TextField txtSubCategory;

    //CategoryModel categoryModel = new CategoryModel();
    //CategoryDAOImpl categoryDAO = new CategoryDAOImpl();
    //CategoryDAO categoryDAO = new CategoryDAOImpl();
    //CategoryBOImpl categoryBO = new CategoryBOImpl();
    //CategoryBO categoryBO = new CategoryBOImpl();
    CategoryBO categoryBO = (CategoryBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.CATEGORY);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colCategoryId.setCellValueFactory(new PropertyValueFactory<>("categoryId"));
        colCategoryName.setCellValueFactory(new PropertyValueFactory<>("categoryName"));
        colSubCategory.setCellValueFactory(new PropertyValueFactory<>("subCategory"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));

        try {
            refreshPage();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load Category Id").show();
        }
    }

    private void refreshPage() throws Exception {
        loadNextCategoryId();
        loadTableData();

        btnSave.setDisable(false);

        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
        btnReset.setDisable(false);

        txtCategoryName.setText("");
        txtSubCategory.setText("");
        txtDescription.setText("");
    }

    private void loadTableData() throws Exception {
        //ArrayList<CategoryDTO> categoryDTOS = categoryModel.getAllCategories();
        //ArrayList<CategoryDTO> categoryDTOS = categoryDAO.getAll();
        ArrayList<CategoryDTO> categoryDTOS = categoryBO.getAllCategories();

        ObservableList<CategoryTM> categoryTMS = FXCollections.observableArrayList();

        for(CategoryDTO categoryDTO : categoryDTOS){
            CategoryTM categoryTM = new CategoryTM(
                    categoryDTO.getCategoryId(),
                    categoryDTO.getCategoryName(),
                    categoryDTO.getSubCategory(),
                    categoryDTO.getDescription()
            );
            categoryTMS.add(categoryTM);
        }
        tblCategory.setItems(categoryTMS);
    }

    private void loadNextCategoryId() throws Exception {
        //String nextCategoryId = categoryModel.getNextCategoryId();
        //String nextCategoryId = categoryDAO.getNextId();
        String nextCategoryId = categoryBO.getNextCategoryId();

        lblCategoryId.setText(nextCategoryId);
    }

    @FXML
    void btnDeleteCategoryOnAction(ActionEvent event) throws Exception {
        String categoryId = lblCategoryId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {

            //boolean isDeleted = categoryModel.deleteCategory(categoryId);
            //boolean isDeleted = categoryDAO.delete(categoryId);
            boolean isDeleted = categoryBO.deleteCategory(categoryId);

            if (isDeleted) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Category deleted...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to delete category...!").show();
            }
        }

    }

    @FXML
    void btnResetOnAction(ActionEvent event) throws Exception {
        refreshPage();
    }

    @FXML
    void btnSaveCategoryOnAction(ActionEvent event) throws Exception {
        String categoryId = lblCategoryId.getText();
        String categoryName = txtCategoryName.getText();
        String subCategory = txtSubCategory.getText();
        String desc = txtDescription.getText();

        txtCategoryName.setStyle(txtCategoryName.getStyle() + ";-fx-border-color: #7367F0;");
        txtSubCategory.setStyle(txtSubCategory.getStyle() + ";-fx-border-color: #7367F0;");
        txtDescription.setStyle(txtDescription.getStyle() + ";-fx-border-color: #7367F0;");

        CategoryDTO categoryDTO = new CategoryDTO(
                categoryId,
                categoryName,
                subCategory,
                desc
        );

        //boolean isSaved = categoryModel.saveCategory(categoryDTO);
        //boolean isSaved = categoryDAO.save(categoryDTO);
        boolean isSaved = categoryBO.saveCategory(categoryDTO);

        if (isSaved) {
            refreshPage();
            new Alert(Alert.AlertType.INFORMATION, "Category saved successfully!").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to save category...!").show();
        }
    }

    @FXML
    void btnSearchCategoryOnAction(ActionEvent event) {

    }

    @FXML
    void btnUpdateCategoryOnAction(ActionEvent event) throws Exception {
        String categoryId = lblCategoryId.getText();
        String categoryName = txtCategoryName.getText();
        String subCategory = txtSubCategory.getText();
        String desc = txtDescription.getText();

        txtCategoryName.setStyle(txtCategoryName.getStyle() + ";-fx-border-color: #7367F0;");
        txtSubCategory.setStyle(txtSubCategory.getStyle() + ";-fx-border-color: #7367F0;");
        txtDescription.setStyle(txtDescription.getStyle() + ";-fx-border-color: #7367F0;");

        CategoryDTO categoryDTO = new CategoryDTO(
                categoryId,
                categoryName,
                subCategory,
                desc
        );

        //boolean isUpdated = categoryModel.updateCategory(categoryDTO);
        //boolean isUpdated = categoryDAO.update(categoryDTO);
        boolean isUpdated = categoryBO.updateCategory(categoryDTO);

        if (isUpdated) {
            refreshPage();
            new Alert(Alert.AlertType.INFORMATION, "Category updated successfully!").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to update category...!").show();
        }
    }

    @FXML
    void onClickTable(MouseEvent event) {
        CategoryTM categoryTM = tblCategory.getSelectionModel().getSelectedItem();
        if (categoryTM != null) {
            lblCategoryId.setText(categoryTM.getCategoryId());
            txtCategoryName.setText(categoryTM.getCategoryName());
            txtSubCategory.setText(categoryTM.getSubCategory());
            txtDescription.setText(categoryTM.getDescription());

            btnSave.setDisable(true);
            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
        }
    }

}
