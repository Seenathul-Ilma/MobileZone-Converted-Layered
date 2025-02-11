package lk.ijse.gdse71.mobilezone.bo.custom.Impl;

import lk.ijse.gdse71.mobilezone.bo.custom.LogInCredentialsBO;
import lk.ijse.gdse71.mobilezone.dao.DAOFactory;
import lk.ijse.gdse71.mobilezone.dao.custom.LogInCredentialsDAO;
import lk.ijse.gdse71.mobilezone.dao.custom.impl.LogInCredentialsDAOImpl;
import lk.ijse.gdse71.mobilezone.dto.LogInCredentialsDTO;
import lk.ijse.gdse71.mobilezone.entity.LogInCredentials;

import java.sql.SQLException;
import java.util.ArrayList;

public class LogInCredentialsBOImpl implements LogInCredentialsBO {
    //LogInCredentialsDAO logInCredentialsDAO = new LogInCredentialsDAOImpl();
    LogInCredentialsDAO logInCredentialsDAO = (LogInCredentialsDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.LOG_IN_CREDENTIALS);

    @Override
    public ArrayList<LogInCredentialsDTO> getAllCredentials() throws SQLException, ClassNotFoundException {
        ArrayList<LogInCredentialsDTO> logInCredentialsDTOS = new ArrayList<>();

        //ArrayList<LogInCredentialsDTO> logInCredentials = logInCredentialsDAO.getAll();
        ArrayList<LogInCredentials> logInCredentials = logInCredentialsDAO.getAll();

        //for (LogInCredentialsDTO logInCredentialsDTO : logInCredentials) {
        for (LogInCredentials logInCredential : logInCredentials) {
            logInCredentialsDTOS.add(new LogInCredentialsDTO(
                    //logInCredentialsDTO.getUserId(),
                    logInCredential.getUserId(),
                    logInCredential.getRole(),
                    logInCredential.getUserName(),
                    logInCredential.getPassword()
            ));
        }
        return logInCredentialsDTOS;
    }

    @Override
    public boolean saveCredential(LogInCredentialsDTO credentialDTO) throws SQLException, ClassNotFoundException {
        //return logInCredentialsDAO.save(new LogInCredentialsDTO(
        return logInCredentialsDAO.save(new LogInCredentials(
                credentialDTO.getUserId(),
                credentialDTO.getRole(),
                credentialDTO.getUserName(),
                credentialDTO.getPassword()
        ));
    }

    @Override
    public String getNextUserId() throws SQLException, ClassNotFoundException {
        return logInCredentialsDAO.getNextId();
    }

    @Override
    public boolean deleteCredential(String userId) throws SQLException, ClassNotFoundException {
        return logInCredentialsDAO.delete(userId);
    }

    @Override
    public boolean updateCredential(LogInCredentialsDTO credentialDTO) throws SQLException, ClassNotFoundException {
        //return logInCredentialsDAO.update(new LogInCredentialsDTO(
        return logInCredentialsDAO.update(new LogInCredentials(
                credentialDTO.getUserId(),
                credentialDTO.getRole(),
                credentialDTO.getUserName(),
                credentialDTO.getPassword()
        ));
    }

    @Override
    public String getAdminPassword() throws SQLException, ClassNotFoundException {
        return logInCredentialsDAO.getAdminPassword();
    }

    @Override
    public String isExistUsernameAndPassword(String username, String password) throws SQLException, ClassNotFoundException {
        return logInCredentialsDAO.isExistUsernameAndPassword(username, password);
    }

    @Override
    public ArrayList<String> getAllUserIds() throws SQLException, ClassNotFoundException {
        ArrayList<String> users =  logInCredentialsDAO.getAllIds();
        ArrayList<String> userIds = new ArrayList<>();

        for (String userId : users) {
            userIds.add(userId);
        }
        return userIds;
    }

    @Override
    public String isExistUsername(String username) throws SQLException {
        return logInCredentialsDAO.isExistUsername(username);
    }


    // not used in log_controller
    /*
    public LogInCredentialsDTO findById(String id) throws SQLException, ClassNotFoundException {
        return logInCredentialsDAO.findById(id);
    }*/

    /*public boolean isExist(String userId) throws SQLException {
        return SQLUtil.execute("select userId from logInCredentials where userId=?", userId);
    }*/
}
