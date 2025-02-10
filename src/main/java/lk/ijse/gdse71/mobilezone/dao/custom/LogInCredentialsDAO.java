package lk.ijse.gdse71.mobilezone.dao.custom;

import lk.ijse.gdse71.mobilezone.dao.CrudDAO;
import lk.ijse.gdse71.mobilezone.dto.LogInCredentialsDTO;
import lk.ijse.gdse71.mobilezone.entity.LogInCredentials;

import java.sql.SQLException;

//public interface LogInCredentialsDAO extends CrudDAO<LogInCredentialsDTO> {
public interface LogInCredentialsDAO extends CrudDAO<LogInCredentials> {
    String getAdminPassword() throws SQLException, ClassNotFoundException;
    String isExistUsernameAndPassword(String username, String password) throws SQLException, ClassNotFoundException;
    String isExistUsername(String username) throws SQLException;
}
