package lk.ijse.gdse71.mobilezone.entity;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LogInCredentials {
    private String userId;
    private String role;
    private String userName;
    private String password;
}
