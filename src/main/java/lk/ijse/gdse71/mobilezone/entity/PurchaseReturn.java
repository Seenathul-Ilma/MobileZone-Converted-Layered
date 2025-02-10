package lk.ijse.gdse71.mobilezone.entity;

import lombok.*;

import java.sql.Date;
import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PurchaseReturn {
    private String purRet_Id;
    private String purchaseId;
    private String supplierId;
    private String itemId;
    private int retQuantity;
    private String reason;
    private double retAmount;
    private Date returnDate;

    private ArrayList<PurchaseDetail> purchaseDetailDTOS;

    public PurchaseReturn(String purRetId, String purchaseId, String supplierId, String itemId, int retQuantity, String reason, double retAmount, Date returnDate) {
        this.purRet_Id = purRetId;
        this.purchaseId = purchaseId;
        this.supplierId = supplierId;
        this.itemId = itemId;
        this.retQuantity = retQuantity;
        this.reason = reason;
        this.retAmount = retAmount;
        this.returnDate = returnDate;
    }
}
