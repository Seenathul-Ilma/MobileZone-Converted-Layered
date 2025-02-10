package lk.ijse.gdse71.mobilezone.entity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PurchaseDetail {
    private String purchaseId;
    private String itemId;
    private int quantity;
    private String isReturned;
    private double unitPrice;
    private double totalPrice;
}
