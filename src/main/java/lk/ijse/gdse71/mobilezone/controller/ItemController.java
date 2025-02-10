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
import lk.ijse.gdse71.mobilezone.bo.custom.Impl.ItemBOImpl;
import lk.ijse.gdse71.mobilezone.bo.custom.ItemBO;
import lk.ijse.gdse71.mobilezone.dao.custom.CategoryDAO;
import lk.ijse.gdse71.mobilezone.dao.custom.impl.CategoryDAOImpl;
import lk.ijse.gdse71.mobilezone.dao.custom.ItemDAO;
import lk.ijse.gdse71.mobilezone.dao.custom.impl.ItemDAOImpl;
import lk.ijse.gdse71.mobilezone.dto.ItemDTO;
import lk.ijse.gdse71.mobilezone.dto.tm.ItemTM;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class ItemController implements Initializable {

    @FXML
    private Button btnDeleteItem;

    @FXML
    private Button btnCheckLowStock;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnSaveItem;

    @FXML
    private Button btnUpdateItem;

    @FXML
    private TableColumn<ItemTM, String> colBrand;
    @FXML
    private TableColumn<ItemTM, String> colCategoryId;

    @FXML
    private TableColumn<ItemTM, String> colItemId;

    @FXML
    private TableColumn<ItemTM, String> colModel;

    @FXML
    private TableColumn<ItemTM, String> colName;

    @FXML
    private TableColumn<ItemTM, Integer> colReOrderLevel;

    @FXML
    private TableColumn<ItemTM, Double> colPrice;

    @FXML
    private TableColumn<ItemTM, Integer> colQty;

    @FXML
    private Label lblItemId;
    @FXML
    private TableView<ItemTM> tblItem;
    @FXML
    private TextField txtBrand;
    @FXML
    private TextField txtCategoryId;

    @FXML
    private TextField txtModel;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPrice;

    @FXML
    private TextField txtQuantity;

    @FXML
    private TextField txtReOrderLevel;

    //ItemModel itemModel = new ItemModel();
    //ItemDAOImpl itemDAO = new ItemDAOImpl();
    //ItemDAO itemDAO = new ItemDAOImpl();
    //private final ItemBOImpl itemBO = new ItemBOImpl();
    //private final ItemBO itemBO = new ItemBOImpl();
    ItemBO itemBO = (ItemBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ITEM);

    //private final CategoryDAOImpl categoryDAO = new CategoryDAOImpl();
    //private final CategoryDAO categoryDAO = new CategoryDAOImpl();
    //private final CategoryBOImpl categoryBO = new CategoryBOImpl();
    //private final CategoryBO categoryBO = new CategoryBOImpl();
    CategoryBO categoryBO = (CategoryBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.CATEGORY);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colItemId.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        colCategoryId.setCellValueFactory(new PropertyValueFactory<>("categoryId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colBrand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        colModel.setCellValueFactory(new PropertyValueFactory<>("model"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
        colReOrderLevel.setCellValueFactory(new PropertyValueFactory<>("reOrderLevel"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));

        try {
            refreshPage();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load item id").show();
        }
    }

    private void refreshPage() throws SQLException, ClassNotFoundException {
        loadNextItemId();
        loadTableData();

        btnSaveItem.setDisable(false);

        btnUpdateItem.setDisable(true);
        btnDeleteItem.setDisable(true);

        //boolean isEmpty = itemModel.isEmpty();
        //boolean isEmpty = itemDAO.isEmpty();
        boolean isEmpty = itemBO.isItemTableEmpty();

        if(isEmpty){
            btnCheckLowStock.setDisable(true);
        }else{
            btnCheckLowStock.setDisable(false);
        }

        txtCategoryId.setText("");
        txtName.setText("");
        txtBrand.setText("");
        txtModel.setText("");
        txtQuantity.setText("");
        txtReOrderLevel.setText("");
        txtPrice.setText("");
    }

    private void loadTableData() throws SQLException, ClassNotFoundException {
        //ArrayList<ItemDTO> itemDTOS = itemModel.getAllItems();
        //ArrayList<ItemDTO> itemDTOS = itemDAO.getAll();
        ArrayList<ItemDTO> itemDTOS = itemBO.getAllItems();

        ObservableList<ItemTM> itemTMS = FXCollections.observableArrayList();

        for(ItemDTO itemDTO : itemDTOS){
            ItemTM itemTM = new ItemTM(
                    itemDTO.getItemId(),
                    itemDTO.getCategoryId(),
                    itemDTO.getName(),
                    itemDTO.getBrand(),
                    itemDTO.getModel(),
                    itemDTO.getQtyOnHand(),
                    itemDTO.getReOrderLevel(),
                    itemDTO.getUnitPrice()
            );
            itemTMS.add(itemTM);
        }
        tblItem.setItems(itemTMS);
    }

    private void loadNextItemId() throws SQLException, ClassNotFoundException {
        //String nextItemId = itemModel.getNextItemId();
        //String nextItemId = itemDAO.getNextId();
        String nextItemId = itemBO.getNextItemId();

        lblItemId.setText(nextItemId);
    }


    @FXML
    public void btnDeleteItemOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String itemId = lblItemId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {

            //boolean isDeleted = itemModel.deleteItem(itemId);
            //boolean isDeleted = itemDAO.delete(itemId);
            boolean isDeleted = itemBO.deleteItem(itemId);

            if (isDeleted) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Item deleted...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to delete item...!").show();
            }
        }

    }

    @FXML
    public void btnResetOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        refreshPage();
    }

    @FXML
    public void btnSaveItemOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String itemId = lblItemId.getText();
        String categoryId = txtCategoryId.getText();
        String name = txtName.getText();
        String brand = txtBrand.getText();
        String model = txtModel.getText();
        int qty = Math.abs(Integer.parseInt(txtQuantity.getText()));
        int reOrderLevel = Math.abs(Integer.parseInt(txtReOrderLevel.getText()));
        double price = Math.abs(Double.valueOf(txtPrice.getText()));

        txtCategoryId.setStyle(txtCategoryId.getStyle() + ";-fx-border-color: #7367F0;");
        txtName.setStyle(txtName.getStyle() + ";-fx-border-color: #7367F0;");
        txtBrand.setStyle(txtBrand.getStyle() + ";-fx-border-color: #7367F0;");
        txtModel.setStyle(txtModel.getStyle() + ";-fx-border-color: #7367F0;");
        txtQuantity.setStyle(txtQuantity.getStyle() + ";-fx-border-color: #7367F0;");
        txtReOrderLevel.setStyle(txtReOrderLevel.getStyle() + ";-fx-border-color: #7367F0;");
        txtPrice.setStyle(txtPrice.getStyle() + ";-fx-border-color: #7367F0;");

        //boolean isValidCategoryId = categoryDAO.checkValidCategoryId(categoryId);
        boolean isValidCategoryId = categoryBO.checkValidCategoryId(categoryId);

        if(!isValidCategoryId){
            new Alert(Alert.AlertType.ERROR, "Invalid Category Id...!").show();
            return;
        }

        ItemDTO itemDTO = new ItemDTO(
                itemId,
                categoryId,
                name,
                brand,
                model,
                qty,
                reOrderLevel,
                price
        );

        //boolean isSaved = itemModel.saveItem(itemDTO);
        //boolean isSaved = itemDAO.save(itemDTO);
        boolean isSaved = itemBO.saveItem(itemDTO);

        if (isSaved) {
            refreshPage();
            System.out.println("Item save: "+qty);
            new Alert(Alert.AlertType.INFORMATION, "Item saved successfully!").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to save item...!").show();
        }
    }

    @FXML
    public void btnUpdateItemOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String itemId = lblItemId.getText();
        String categoryId = txtCategoryId.getText();
        String name = txtName.getText();
        String brand = txtBrand.getText();
        String model = txtModel.getText();
        int qty = Integer.parseInt(txtQuantity.getText());
        int reOrderLevel = Integer.parseInt(txtReOrderLevel.getText());
        double price = Double.parseDouble(txtPrice.getText());

        txtCategoryId.setStyle(txtCategoryId.getStyle() + ";-fx-border-color: #7367F0;");
        txtName.setStyle(txtName.getStyle() + ";-fx-border-color: #7367F0;");
        txtBrand.setStyle(txtBrand.getStyle() + ";-fx-border-color: #7367F0;");
        txtModel.setStyle(txtModel.getStyle() + ";-fx-border-color: #7367F0;");
        txtQuantity.setStyle(txtQuantity.getStyle() + ";-fx-border-color: #7367F0;");
        txtReOrderLevel.setStyle(txtReOrderLevel.getStyle() + ";-fx-border-color: #7367F0;");
        txtPrice.setStyle(txtPrice.getStyle() + ";-fx-border-color: #7367F0;");

        //boolean isValidCategoryId = categoryDAO.checkValidCategoryId(categoryId);
        boolean isValidCategoryId = categoryBO.checkValidCategoryId(categoryId);

        if(!isValidCategoryId){
            new Alert(Alert.AlertType.ERROR, "Invalid Category Id...!").show();
            return;
        }

        if (qty < 0) {
            qty = Math.abs(qty);
        }else if(reOrderLevel < 0){
            reOrderLevel = Math.abs(reOrderLevel);
        }else if(price < 0){
            price = Math.abs(price);
        }

        ItemDTO itemDTO = new ItemDTO(
                itemId,
                categoryId,
                name,
                brand,
                model,
                qty,
                reOrderLevel,
                price
        );

        //boolean isUpdated = itemModel.updateItem(itemDTO);
        //boolean isUpdated = itemDAO.update(itemDTO);
        boolean isUpdated = itemBO.updateItem(itemDTO);

        if (isUpdated) {
            refreshPage();
            new Alert(Alert.AlertType.INFORMATION, "Item updated successfully!").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to update item...!").show();
        }
    }

    @FXML
    public void checkLowStockItemsOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        //ArrayList<ItemDTO> itemDTOS = itemModel.checkLowStockItems();
        //ArrayList<ItemDTO> itemDTOS = itemDAO.checkLowStockItems();
        ArrayList<ItemDTO> itemDTOS = itemBO.checkLowStockItems();

        ObservableList<ItemTM> itemTMS = FXCollections.observableArrayList();

        for(ItemDTO itemDTO : itemDTOS){
            ItemTM itemTM = new ItemTM(
                    itemDTO.getItemId(),
                    itemDTO.getCategoryId(),
                    itemDTO.getName(),
                    itemDTO.getBrand(),
                    itemDTO.getModel(),
                    itemDTO.getQtyOnHand(),
                    itemDTO.getReOrderLevel(),
                    itemDTO.getUnitPrice()
            );
            itemTMS.add(itemTM);
        }
        tblItem.setItems(itemTMS);
    }

    @FXML
    public void onClickTable(MouseEvent event) {
        ItemTM itemTM = tblItem.getSelectionModel().getSelectedItem();
        if (itemTM != null) {
            lblItemId.setText(itemTM.getItemId());
            txtCategoryId.setText(itemTM.getCategoryId());
            txtName.setText(itemTM.getName());
            txtBrand.setText(itemTM.getBrand());
            txtModel.setText(itemTM.getModel());
            txtQuantity.setText(String.valueOf(itemTM.getQtyOnHand()));
            txtReOrderLevel.setText(String.valueOf(itemTM.getReOrderLevel()));
            txtPrice.setText(String.valueOf(itemTM.getUnitPrice()));

            btnSaveItem.setDisable(true);

            btnDeleteItem.setDisable(false);
            btnUpdateItem.setDisable(false);
        }
    }
}
