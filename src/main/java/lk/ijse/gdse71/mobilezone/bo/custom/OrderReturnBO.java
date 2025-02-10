package lk.ijse.gdse71.mobilezone.bo.custom;

import lk.ijse.gdse71.mobilezone.bo.SuperBO;
import lk.ijse.gdse71.mobilezone.dto.OrderDetailDTO;
import lk.ijse.gdse71.mobilezone.dto.OrderReturnDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface OrderReturnBO extends SuperBO {
    String getNextOrderReturnId() throws SQLException, ClassNotFoundException;

    boolean saveReturnDetailsList(ArrayList<OrderDetailDTO> orderDetailsDTOS) throws SQLException, ClassNotFoundException;

    boolean saveOrderReturn(OrderReturnDTO orderReturnDTO) throws SQLException;
}
