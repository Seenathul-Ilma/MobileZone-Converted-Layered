package lk.ijse.gdse71.mobilezone.entity;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Expense {
    private String exp_Id;
    private String description;
    private double amount;
    private Date date;
    private String expCategory;
}
