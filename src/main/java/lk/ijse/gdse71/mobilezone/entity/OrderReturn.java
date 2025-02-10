package lk.ijse.gdse71.mobilezone.entity;

import lombok.*;

import java.sql.Date;
import java.util.ArrayList;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderReturn {
    private String orderRet_Id;
    private String orderId;
    private String customerId;
    private String itemId;
    private int quantity;
    private String reason;
    private double retAmount;
    private Date returnDate;

    private ArrayList<OrderDetail> orderDetailsDTOS;

    public OrderReturn(String orderRet_Id, String orderId, String customerId, String itemId, int quantity, String reason, double retAmount, Date returnDate) {
            this.orderRet_Id = orderRet_Id;
            this.orderId = orderId;
            this.customerId = customerId;
            this.itemId = itemId;
            this.quantity = quantity;
            this.reason = reason;
            this.retAmount = retAmount;
            this.returnDate = returnDate;
    }
}
