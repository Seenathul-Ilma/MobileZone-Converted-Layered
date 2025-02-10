package lk.ijse.gdse71.mobilezone.entity;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Customer {
    private String customerId;
    private String name;
    private String nic;
    private String email;
    private String phone;
}
