package lk.ijse.gdse71.mobilezone.entity;

import lombok.*;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetail {
    private String orderId;
    private String itemId;
    private int quantity;
    private String isReturned;
    private double unitPrice;
}
