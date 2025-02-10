package lk.ijse.gdse71.mobilezone.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.gdse71.mobilezone.bo.BOFactory;
import lk.ijse.gdse71.mobilezone.bo.custom.CustomerBO;
import lk.ijse.gdse71.mobilezone.bo.custom.EmployeeBO;
import lk.ijse.gdse71.mobilezone.bo.custom.Impl.CustomerBOImpl;
import lk.ijse.gdse71.mobilezone.bo.custom.Impl.EmployeeBOImpl;
import lk.ijse.gdse71.mobilezone.bo.custom.Impl.ItemBOImpl;
import lk.ijse.gdse71.mobilezone.bo.custom.ItemBO;
import lk.ijse.gdse71.mobilezone.bo.custom.OrderBO;
import lk.ijse.gdse71.mobilezone.bo.custom.Impl.OrderBOImpl;
import lk.ijse.gdse71.mobilezone.dao.custom.CustomerDAO;
import lk.ijse.gdse71.mobilezone.dao.custom.EmployeeDAO;
import lk.ijse.gdse71.mobilezone.dao.custom.ItemDAO;
import lk.ijse.gdse71.mobilezone.dao.custom.OrdersDAO;
import lk.ijse.gdse71.mobilezone.dao.custom.impl.CustomerDAOImpl;
import lk.ijse.gdse71.mobilezone.dao.custom.impl.EmployeeDAOImpl;
import lk.ijse.gdse71.mobilezone.dao.custom.impl.ItemDAOImpl;
import lk.ijse.gdse71.mobilezone.dto.CustomerDTO;
import lk.ijse.gdse71.mobilezone.dto.ItemDTO;
import lk.ijse.gdse71.mobilezone.dto.OrderDTO;
import lk.ijse.gdse71.mobilezone.dto.tm.CartTM;
import lk.ijse.gdse71.mobilezone.dto.OrderDetailDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class OrderController implements Initializable {

    @FXML
    private ComboBox<String> cmbCustomerId;

    @FXML
    private ComboBox<String> cmbEmployeeId;

    @FXML
    private ComboBox<String> cmbItemId;

    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private TableColumn<CartTM, String> colItemId;

    @FXML
    private TableColumn<CartTM, String> colName;

    @FXML
    private TableColumn<CartTM, Double> colPrice;

    @FXML
    private TableColumn<CartTM, Integer> colQuantity;

    @FXML
    private TableColumn<CartTM, Double> colTotal;

    @FXML
    private Label lblCustomerName;

    @FXML
    private Label lblItemName;

    @FXML
    private Label lblOrderDate;

    @FXML
    private Label lblOrderId;

    @FXML
    private Label lblQoh;

    @FXML
    private Label lblUnitPrice;

    @FXML
    private TableView<CartTM> tblCart;

    @FXML
    private TextField txtCartQty;

    //private final OrdersModel ordersModel = new OrdersModel();
    //private final OrdersDAOImpl ordersDAO = new OrdersDAOImpl();
    //private final OrdersDAO ordersDAO = new OrdersDAOImpl();
    //private final OrderBOImpl orderBO = new OrderBOImpl();
    //private final OrderBO orderBO = new OrderBOImpl();
    OrderBO orderBO = (OrderBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ORDER);

    //private final EmployeeModel employeeModel = new EmployeeModel();
    //private final EmployeeDAOImpl employeeDAO = new EmployeeDAOImpl();
    //private final EmployeeDAO employeeDAO = new EmployeeDAOImpl();
    //private final EmployeeBOImpl employeeBO = new EmployeeBOImpl();
    //private final EmployeeBO employeeBO = new EmployeeBOImpl();
    EmployeeBO employeeBO = (EmployeeBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.EMPLOYEE);

    //private final CustomerModel customerModel = new CustomerModel();
    //private final CustomerDAOImpl customerDAO = new CustomerDAOImpl();
    //private final CustomerDAO customerDAO = new CustomerDAOImpl();
    //private final CustomerBOImpl customerBO = new CustomerBOImpl();
    //private final CustomerBO customerBO = new CustomerBOImpl();
    CustomerBO customerBO = (CustomerBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.CUSTOMER);

    //private final ItemModel itemModel = new ItemModel();
    //private final ItemDAOImpl itemDAOImpl = new ItemDAOImpl();
    //private final ItemDAO itemDAO = new ItemDAOImpl();
    //private final ItemBOImpl itemBO = new ItemBOImpl();
    //private final ItemBO itemBO = new ItemBOImpl();
    ItemBO itemBO = (ItemBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ITEM);

    private final ObservableList<CartTM> cartTMS = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellValues();
        try {
            loadEmployeeIds();
            refreshPage();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Fail to load data..!").show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadEmployeeIds() throws SQLException, ClassNotFoundException {
        //ArrayList<String> employeeIds = employeeModel.getAllEmployeeIds();
        //ArrayList<String> employeeIds = employeeDAO.getAllIds();
        ArrayList<String> employeeIds = employeeBO.getAllEmployeeIds();

        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(employeeIds);
        cmbEmployeeId.setItems(observableList);
    }

    private void refreshPage() throws SQLException, ClassNotFoundException {
        //lblOrderId.setText(ordersModel.getNextOrderId());
        //lblOrderId.setText(ordersDAO.getNextId());
        lblOrderId.setText(orderBO.getNextOrderId());

        lblOrderDate.setText(LocalDate.now().toString());

        loadCustomerIds();
        loadItemId();

        cmbCustomerId.getSelectionModel().clearSelection();
        cmbItemId.getSelectionModel().clearSelection();
        lblItemName.setText("");
        lblQoh.setText("");
        lblUnitPrice.setText("");
        txtCartQty.setText("");
        lblCustomerName.setText("");

        cartTMS.clear();

        tblCart.refresh();
    }

    private void loadItemId() throws SQLException, ClassNotFoundException {
        //ArrayList<String> itemIds = itemModel.getAllItemIds();
        //ArrayList<String> itemIds = itemDAO.getAllIds();
        ArrayList<String> itemIds = itemBO.getAllItemIds();

        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(itemIds);
        cmbItemId.setItems(observableList);
    }

    private void loadCustomerIds() throws SQLException, ClassNotFoundException {
        //ArrayList<String> customerIds = customerModel.getAllCustomerIds();
        //ArrayList<String> customerIds = customerDAO.getAllIds();
        ArrayList<String> customerIds = customerBO.getAllCustomerIds();

        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(customerIds);
        cmbCustomerId.setItems(observableList);
    }

    private void setCellValues() {
        colItemId.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("cartQuantity"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("cancelOrderBtn"));

        tblCart.setItems(cartTMS);
    }

    @FXML
    void btnAddToCartOnAction(ActionEvent event) {
        String selectedItemId = cmbItemId.getValue();
        //String selectedEmployeeId = cmbEmployeeId.getValue();

        if (selectedItemId == null) {
            new Alert(Alert.AlertType.ERROR, "Please select an item..!").show();
            return;
        }

        String cartQtyString = txtCartQty.getText();
        String qtyPattern = "^[0-9]+$";

        if (!cartQtyString.matches(qtyPattern)){
            new Alert(Alert.AlertType.ERROR, "Please enter valid quantity..!").show();
            return;
        }

        String itemName = lblItemName.getText();
        int cartQty = Integer.parseInt(cartQtyString);
        int qtyOnHand = Integer.parseInt(lblQoh.getText());

        if (qtyOnHand < cartQty) {
            new Alert(Alert.AlertType.ERROR, "Not enough items..!").show();
            return;
        }

        txtCartQty.setText("");

        double unitPrice = Double.parseDouble(lblUnitPrice.getText());
        double total = unitPrice * cartQty;

        for (CartTM cartTM : cartTMS) {

            if (cartTM.getItemId().equals(selectedItemId)) {
                int newQty = cartTM.getCartQuantity() + cartQty;
                cartTM.setCartQuantity(newQty);
                cartTM.setTotal(unitPrice * newQty);
                tblCart.refresh();
                return;
            }
        }

        Button btn = new Button("Remove");

        CartTM newCartTM = new CartTM(
                selectedItemId,
                itemName,
                cartQty,
                unitPrice,
                total,
                btn
        );

        btn.setOnAction(actionEvent -> {
            cartTMS.remove(newCartTM);
            tblCart.refresh();
        });

        cartTMS.add(newCartTM);
    }

    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        if (tblCart.getItems().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please add items to cart..!").show();
            return;
        }
        if (cmbCustomerId.getSelectionModel().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please select customer for place order..!").show();
            return;
        }
        if (cmbEmployeeId.getSelectionModel().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please select employee id..!").show();
            return;
        }

        String orderId = lblOrderId.getText();
        Date dateOfOrder = Date.valueOf(lblOrderDate.getText());
        String customerId = cmbCustomerId.getValue();
        String employeeId = cmbEmployeeId.getValue();

        String isReturn = "No";
        ArrayList<OrderDetailDTO> orderDetailDTOS = new ArrayList<>();

        for (CartTM cartTM : cartTMS) {


            OrderDetailDTO orderDetailDTO = new OrderDetailDTO(
                    orderId,
                    cartTM.getItemId(),
                    cartTM.getCartQuantity(),
                    isReturn,
                    cartTM.getUnitPrice()
            );
            orderDetailDTOS.add(orderDetailDTO);
        }

        OrderDTO orderDTO = new OrderDTO(
                orderId,
                customerId,
                dateOfOrder,
                employeeId,
                orderDetailDTOS
        );

        //boolean isSaved = ordersModel.saveOrder(orderDTO);
        //boolean isSaved = ordersDAO.save(orderDTO);
        boolean isSaved = orderBO.saveOrder(orderDTO);

        if (isSaved) {
            new Alert(Alert.AlertType.INFORMATION, "Order saved..!").show();
            refreshPage();
        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to save order!").show();
        }
    }

    @FXML
    void btnResetOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        refreshPage();
    }

    @FXML
    void cmbCustomerOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String selectedCustomerId = cmbCustomerId.getSelectionModel().getSelectedItem();

        //CustomerDTO customerDTO = customerModel.findById(selectedCustomerId);
        //CustomerDTO customerDTO = customerDAO.findById(selectedCustomerId);
        CustomerDTO customerDTO = customerBO.findByCustomerId(selectedCustomerId);

        if (customerDTO != null) {
            lblCustomerName.setText(customerDTO.getName());
        }
    }

    @FXML
    void cmbEmployeeOnAction(ActionEvent event){
        String selectedEmployeeId = cmbEmployeeId.getSelectionModel().getSelectedItem();
    }


    @FXML
    void cmbItemOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String selectedItemId = cmbItemId.getSelectionModel().getSelectedItem();
        //ItemDTO itemDTO = itemModel.findById(selectedItemId);
        //ItemDTO itemDTO = itemDAO.findById(selectedItemId);
        ItemDTO itemDTO = itemBO.findByItemId(selectedItemId);

        if (itemDTO != null) {

            // FIll item related labels
            lblItemName.setText(itemDTO.getName());
            lblQoh.setText(String.valueOf(itemDTO.getQtyOnHand()));
            lblUnitPrice.setText(String.valueOf(itemDTO.getUnitPrice()));
        }
    }

}
