package lk.ijse.gdse71.mobilezone.bo.custom;

import lk.ijse.gdse71.mobilezone.bo.SuperBO;
import lk.ijse.gdse71.mobilezone.dto.LogInCredentialsDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface LogInCredentialsBO extends SuperBO {
    ArrayList<String> getAllUserIds() throws SQLException, ClassNotFoundException;

    String getAdminPassword() throws SQLException, ClassNotFoundException;

    boolean deleteCredential(String userId) throws SQLException, ClassNotFoundException;

    String isExistUsernameAndPassword(String username, String password) throws SQLException, ClassNotFoundException;

    String isExistUsername(String username) throws SQLException, ClassNotFoundException;

    boolean saveCredential(LogInCredentialsDTO credentialDTO) throws SQLException, ClassNotFoundException;

    ArrayList<LogInCredentialsDTO> getAllCredentials() throws SQLException, ClassNotFoundException;

    String getNextUserId() throws SQLException, ClassNotFoundException;

    boolean updateCredential(LogInCredentialsDTO credentialDTO) throws SQLException, ClassNotFoundException;
}
