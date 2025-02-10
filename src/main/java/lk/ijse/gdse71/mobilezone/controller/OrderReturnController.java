package lk.ijse.gdse71.mobilezone.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.gdse71.mobilezone.bo.BOFactory;
import lk.ijse.gdse71.mobilezone.bo.custom.*;
import lk.ijse.gdse71.mobilezone.bo.custom.Impl.*;
import lk.ijse.gdse71.mobilezone.dao.custom.QueryDAO;
import lk.ijse.gdse71.mobilezone.dao.custom.impl.QueryDAOImpl;
import lk.ijse.gdse71.mobilezone.dao.custom.CustomerDAO;
import lk.ijse.gdse71.mobilezone.dao.custom.ItemDAO;
import lk.ijse.gdse71.mobilezone.dao.custom.impl.*;
import lk.ijse.gdse71.mobilezone.dto.*;
import lk.ijse.gdse71.mobilezone.dto.tm.ReturnTM;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class OrderReturnController implements Initializable{

    @FXML
    private ComboBox<String> cmbCustomerId;

    @FXML
    private ComboBox<String> cmbItemId;

    @FXML
    private ComboBox<String> cmbOrderId;

    @FXML
    private ComboBox<String> cmbReason;

    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private TableColumn<ReturnTM, String> colItemId;

    @FXML
    private TableColumn<ReturnTM, String> colItemName;

    @FXML
    private TableColumn<ReturnTM, Integer> colRetQty;

    @FXML
    private TableColumn<ReturnTM, Double> colSoldPrice;

    @FXML
    private TableColumn<ReturnTM, Double> colTotalAmount;

    @FXML
    private Label lblCustomerName;

    @FXML
    private Label lblItemName;

    @FXML
    private Label lblOrderDate;

    @FXML
    private Label lblOrderRetId;

    @FXML
    private Label lblQoh;

    @FXML
    private Label lblReturnDate;

    @FXML
    private Label lblSoldPrice;

    @FXML
    private Label lblRtnQty;

    @FXML
    private TableView<ReturnTM> tblReturn;

    //private final OrdersReturnModel ordersReturnModel = new OrdersReturnModel();
    //private final OrdersReturnDAOImpl ordersReturnDAO = new OrdersReturnDAOImpl();
    //private final OrderReturnDAO ordersReturnDAO = new OrderReturnDAOImpl();
    //private final OrderReturnBOImpl orderReturnBO = new OrderReturnBOImpl();
    //private final OrderReturnBO orderReturnBO = new OrderReturnBOImpl();
    OrderReturnBO orderReturnBO = (OrderReturnBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ORDER_RETURN);

    //private final OrdersModel ordersModel = new OrdersModel();
    //private final OrdersDAO ordersDAO = new OrdersDAOImpl();
    //private final OrdersDAOImpl ordersDAO = new OrdersDAOImpl();
    //private final OrderBOImpl orderBO = new OrderBOImpl();
    //private final OrderBO orderBO = new OrderBOImpl();
    OrderBO orderBO = (OrderBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ORDER);

    //private final OrderDetailModel orderDetailModel = new OrderDetailModel();
    //private final OrderDetailDAOImpl orderDetailDAO = new OrderDetailDAOImpl();
    //private final OrderDetailDAO orderDetailDAO = new OrderDetailDAOImpl();

    //private final CustomerModel customerModel = new CustomerModel();
    //private final CustomerDAOImpl customerDAO = new CustomerDAOImpl();
    //private final CustomerDAO customerDAO = new CustomerDAOImpl();
    //private final CustomerBOImpl customerBO = new CustomerBOImpl();
    //private final CustomerBO customerBO = new CustomerBOImpl();
    CustomerBO customerBO = (CustomerBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.CUSTOMER);

    //private final ItemModel itemModel = new ItemModel();
    //private final ItemDAOImpl itemDAO = new ItemDAOImpl();
    //private final ItemDAO itemDAO = new ItemDAOImpl();
    //private final ItemBOImpl itemBO = new ItemBOImpl();
    //private final ItemBO itemBO = new ItemBOImpl();
    ItemBO itemBO = (ItemBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ITEM);

    //private final QueryDAO queryDAO = new QueryDAOImpl();
    //private final QueryBOImpl queryBO = new QueryBOImpl();
    //private final QueryBO queryBO = new QueryBOImpl();
    QueryBO queryBO = (QueryBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.QUERY);

    private final ObservableList<ReturnTM> returnTMS = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellValues();
        // Load data and initialize the page
        try {
            refreshPage();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load data..!").show();
        }
    }

    private void refreshPage() throws SQLException, ClassNotFoundException {
        //lblOrderRetId.setText(ordersReturnModel.getNextReturnId());
        //lblOrderRetId.setText(ordersReturnDAO.getNextId());
        lblOrderRetId.setText(orderReturnBO.getNextOrderReturnId());

        lblReturnDate.setText(LocalDate.now().toString());

        loadOrderId();
        loadCustomerIds();
        loadItemId();
        loadReasons();

        cmbOrderId.getSelectionModel().clearSelection();
        cmbCustomerId.getSelectionModel().clearSelection();
        cmbItemId.getSelectionModel().clearSelection();
        lblOrderDate.setText("");
        lblItemName.setText("");
        lblQoh.setText("");
        lblSoldPrice.setText("");
        lblRtnQty.setText("");
        lblCustomerName.setText("");
        cmbReason.getSelectionModel().clearSelection();

        returnTMS.clear();

        tblReturn.refresh();
    }

    private void setCellValues() {
        colItemId.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        colItemName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        colRetQty.setCellValueFactory(new PropertyValueFactory<>("returnQty"));
        colSoldPrice.setCellValueFactory(new PropertyValueFactory<>("soldPrice"));
        colTotalAmount.setCellValueFactory(new PropertyValueFactory<>("totalAmount"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("cancelReturnBtn"));

        tblReturn.setItems(returnTMS);
    }

    @FXML
    void cmbOrderOnAction(ActionEvent event) throws SQLException {
        //String selectedOrderId = cmbOrderId.getSelectionModel().getSelectedItem();
        String selectedOrderId = cmbOrderId.getSelectionModel().getSelectedItem();
    }

    @FXML
    void cmbCustomerOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String selectedCustomerId = cmbCustomerId.getSelectionModel().getSelectedItem();
        String orderId = cmbOrderId.getValue();

        //CustomerDTO customerDTO = customerModel.findById(selectedCustomerId);
        //CustomerDTO customerDTO = customerDAO.findById(selectedCustomerId);
        CustomerDTO customerDTO = customerBO.findByCustomerId(selectedCustomerId);

        //OrderDTO orderDTO = ordersModel.checkById(orderId);
        //OrderDTO orderDTO = ordersDAO.findById(orderId);
        OrderDTO orderDTO = orderBO.findOrderById(orderId);

        // If customer found (customerDTO not null)
        if (customerDTO != null && orderDTO != null) {
            // FIll customer related labels
            lblCustomerName.setText(customerDTO.getName());
            lblOrderDate.setText(String.valueOf(orderDTO.getOrderDate()));
        }
    }

    @FXML
    void cmbReasonOnAction(ActionEvent event){
        String selectedReason = cmbReason.getSelectionModel().getSelectedItem();
    }

    @FXML
    void cmbItemOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String selectedItemId = cmbItemId.getSelectionModel().getSelectedItem();
        String orderId = cmbOrderId.getValue();

        //ItemDTO itemDTO = itemModel.findById(selectedItemId);
        //ItemDTO itemDTO = itemDAO.findById(selectedItemId);
        ItemDTO itemDTO = itemBO.findByItemId(selectedItemId);

        //OrderDetailDTO orderDetailDTO = orderDetailModel.findById(orderId, selectedItemId);
        //OrderDetailDTO orderDetailDTO = orderDetailDAO.findById(orderId, selectedItemId);
        //OrderDetailDTO orderDetailDTO = ordersDAO.findById(orderId, selectedItemId);
        OrderDetailDTO orderDetailDTO = orderBO.findItemByOrderId(orderId, selectedItemId);

        //String AlreadyFullyReturned = orderDetailModel.getReturnOrNot(orderId, selectedItemId);
        //String AlreadyFullyReturned = orderDetailDAO.getReturnOrNot(orderId, selectedItemId);
        //String AlreadyFullyReturned = ordersDAO.getReturnOrNot(orderId, selectedItemId);
        String AlreadyFullyReturned = orderBO.getOrderReturnOrNot(orderId, selectedItemId);

        // If item found (itemDTO not null)
        if (itemDTO != null && orderDetailDTO != null) {
            // FIll item related labels
            lblItemName.setText(itemDTO.getName());
            lblQoh.setText(String.valueOf(itemDTO.getQtyOnHand()));
            lblRtnQty.setText(String.valueOf(orderDetailDTO.getQuantity()));
            lblSoldPrice.setText(String.valueOf(orderDetailDTO.getUnitPrice()));
        }
        if(AlreadyFullyReturned.equals("YES")){
            lblRtnQty.setText("0");
        }
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

    private void loadOrderId() throws SQLException, ClassNotFoundException {
        //ArrayList<String> orderIds = ordersModel.getAllOrderIds();
        //ArrayList<String> orderIds = ordersDAO.getAllIds();
        ArrayList<String> orderIds = orderBO.getAllOrderIds();

        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(orderIds);
        System.out.println("load OrderId");
        cmbOrderId.setItems(observableList);
    }

    private void loadReasons(){
        ObservableList<String> reasons = FXCollections.observableArrayList(
                "Defective Product",
                "Quality Issues",
                "End Of Life",
                "Change of Mind",
                "Size or Fit Issue",
                "Product Performance Issue",
                "Misleading Description",
                "Color/Model Mismatch",
                "Received Extra Items",
                "Damaged Packaging",
                "Component Wear and Tear",
                "Price Issues Or Better Price Available",
                "Incompatibility Or Obsolescence",
                "Environmental Factors",
                "Warranty Claim",
                "Wrong Quantity Ordered",
                "Product No Longer Needed"
        );
        System.out.println("load reason");
        cmbReason.setItems(reasons);
    }

    @FXML
    void btnResetOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        refreshPage();
    }

    @FXML
    void btnReturnToStoreOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String selectedItemId = cmbItemId.getValue();
        String selectedOrderId = cmbOrderId.getValue();
        String selectedReason = cmbReason.getValue();
        String selectedCustomerId = cmbCustomerId.getValue();

        int rtnQty = 0;
        double soldPrice = 0, total = 0;

        //String confirmItem = orderDetailModel.confirmItemBought(selectedOrderId, selectedItemId);
        //String confirmItem = orderDetailDAO.confirmItemBought(selectedOrderId, selectedItemId);
        //String confirmItem = ordersDAO.confirmItemBought(selectedOrderId, selectedItemId);
        String confirmItem = orderBO.confirmItemBought(selectedOrderId, selectedItemId);

        System.out.println("confirmItem result: " + confirmItem);

        //String confirmCustomer = orderDetailModel.confirmCustomerBought(selectedCustomerId, selectedOrderId, selectedItemId);
        //String confirmCustomer = orderDetailDAO.confirmCustomerBought(selectedCustomerId, selectedOrderId, selectedItemId);
        //String confirmCustomer =ordersDAO.confirmCustomerBought(selectedCustomerId, selectedOrderId, selectedItemId);
        //String confirmCustomer =queryDAO.confirmCustomerBought(selectedCustomerId, selectedOrderId, selectedItemId);
        String confirmCustomer = queryBO.confirmCustomerBought(selectedCustomerId, selectedOrderId, selectedItemId);

        System.out.println("confirmCustomer result: " + confirmCustomer);

        if(confirmItem.equals(selectedItemId)){
            rtnQty = Integer.parseInt(lblRtnQty.getText());
            soldPrice = Double.parseDouble(lblSoldPrice.getText());
            total = rtnQty * soldPrice;
        }else{
            new Alert(Alert.AlertType.ERROR, "This orderId didn't buy this item!").show();
            return;
        }
        if(!confirmCustomer.equals(selectedCustomerId)){
            new Alert(Alert.AlertType.ERROR, "Oops! Customer mismatch.. This orderId didn't buy this item!").show();
            return;
        }

        //String AlreadyFullyReturned = orderDetailModel.getReturnOrNot(selectedOrderId, selectedItemId);
        //String AlreadyFullyReturned = orderDetailDAO.getReturnOrNot(selectedOrderId, selectedItemId);
        //String AlreadyFullyReturned = ordersDAO.getReturnOrNot(selectedOrderId, selectedItemId);
        String AlreadyFullyReturned = orderBO.getOrderReturnOrNot(selectedOrderId, selectedItemId);

        if(AlreadyFullyReturned.equals("YES")){
            new Alert(Alert.AlertType.ERROR, "Item Already fully return in this orderId..!").show();
            return;
        }

        if (cmbOrderId.getSelectionModel().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "You should select the orderId first..!").show();
            return;
        }else if(cmbItemId.getSelectionModel().isEmpty() || cmbReason.getSelectionModel().isEmpty()){
            new Alert(Alert.AlertType.ERROR, "You should select item and returning reason..!").show();
            return;
        }else if(selectedOrderId == null){
            new Alert(Alert.AlertType.ERROR, "Oops.. No such an order!").show();
        }

        String itemName = lblItemName.getText();

        Button btn = new Button("Remove");

        ReturnTM newReturnTM = new ReturnTM(
                selectedItemId,
                itemName,
                rtnQty,
                soldPrice,
                total,
                btn
        );

        btn.setOnAction(actionEvent -> {
            returnTMS.remove(newReturnTM);
            tblReturn.refresh();
        });

        returnTMS.add(newReturnTM);
    }

    @FXML
    void btnProceedReturnOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {

        if (tblReturn.getItems().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "OOps.. You can't return empty values!").show();
            return;
        }
        if (cmbOrderId.getSelectionModel().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please select order to return..!").show();
            return;
        }

        String orderRetId = lblOrderRetId.getText();
        String orderId = cmbOrderId.getValue();
        String customerId = cmbCustomerId.getValue();
        String itemId = cmbItemId.getValue();
        String reason = cmbReason.getValue();
        Date dateOfOrder = Date.valueOf(lblOrderDate.getText());
        Date dateOfReturn = Date.valueOf(lblReturnDate.getText());
        int qtyOnHand = Integer.parseInt(lblQoh.getText());
        double soldPrice = Double.parseDouble(lblSoldPrice.getText());
        int returnQty = Integer.parseInt(lblRtnQty.getText());
        double totalAmount = soldPrice * returnQty;
        String isReturned = "YES";

        ArrayList<OrderDetailDTO> orderDetailDTOS = new ArrayList<>();

        for (ReturnTM returnTM : returnTMS) {

            OrderDetailDTO orderDetailsDTO = new OrderDetailDTO(
                    orderId,
                    returnTM.getItemId(),
                    returnTM.getReturnQty(),
                    isReturned,
                    returnTM.getSoldPrice()
            );
            orderDetailDTOS.add(orderDetailsDTO);
        }

        OrderReturnDTO orderReturnDTO = new OrderReturnDTO(
                orderRetId,
                orderId,
                customerId,
                itemId,
                returnQty,
                reason,
                totalAmount,
                dateOfReturn,
                orderDetailDTOS
        );

        //boolean isSaved = ordersReturnModel.saveReturn(orderReturnDTO);
        //boolean isSaved = ordersReturnDAO.save(orderReturnDTO);
        boolean isSaved = orderReturnBO.saveOrderReturn(orderReturnDTO);

        if (isSaved) {
            new Alert(Alert.AlertType.INFORMATION, "Order Returned..!").show();
            refreshPage();
        } else {
            new Alert(Alert.AlertType.ERROR, "Order return failed..!").show();
        }
    }
}
