package lk.ijse.gdse71.mobilezone.entity;


import lombok.*;

import java.sql.Date;
import java.util.ArrayList;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Order {
    private String orderId;
    private String customerId;
    private Date orderDate;
    private String employeeId;

    private ArrayList<OrderDetail> orderDetailsDTOS;

    public Order(String orderId, String customerId, Date orderDate, String employeeId) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.orderDate = orderDate;
        this.employeeId = employeeId;
    }
}
