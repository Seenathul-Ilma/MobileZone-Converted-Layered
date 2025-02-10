package lk.ijse.gdse71.mobilezone.entity;

import lombok.*;

import java.sql.Date;
import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Purchase {
    private String purchaseId;
    private String supplierId;
    private Date purchaseDate;
    private double totalAmount;
    private ArrayList<PurchaseDetail> purchaseDetailDTOS;

    public Purchase(String purchaseId, String supplierId, Date purchaseDate, double totalAmount) {
        this.purchaseId = purchaseId;
        this.supplierId = supplierId;
        this.purchaseDate = purchaseDate;
        this.totalAmount = totalAmount;
    }
}
