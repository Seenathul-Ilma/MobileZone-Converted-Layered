package lk.ijse.gdse71.mobilezone.dao.custom.impl;

import lk.ijse.gdse71.mobilezone.dao.custom.LogInCredentialsDAO;
import lk.ijse.gdse71.mobilezone.dao.SQLUtil;
import lk.ijse.gdse71.mobilezone.dto.LogInCredentialsDTO;
import lk.ijse.gdse71.mobilezone.entity.Category;
import lk.ijse.gdse71.mobilezone.entity.LogInCredentials;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class LogInCredentialsDAOImpl implements LogInCredentialsDAO {
    @Override
    //public ArrayList<LogInCredentialsDTO> getAll() throws SQLException, ClassNotFoundException {
    public ArrayList<LogInCredentials> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select * from logInCredentials");

        //ArrayList<LogInCredentialsDTO> logInCredentialsDTOS = new ArrayList<>();
        ArrayList<LogInCredentials> allCredentials = new ArrayList<>();

        while (rst.next()) {
            //LogInCredentialsDTO logInCredentialsDTO = new LogInCredentialsDTO(
            LogInCredentials entity = new LogInCredentials(
                    rst.getString(1),  // User Id
                    rst.getString(2),  // Role
                    rst.getString(3),  // Username
                    rst.getString(4)   // Password
            );
            //logInCredentialsDTOS.add(logInCredentialsDTO);
            allCredentials.add(entity);
        }
        //return logInCredentialsDTOS;
        return allCredentials;
    }

    @Override
    //public boolean save(LogInCredentialsDTO credentialDTO) throws SQLException, ClassNotFoundException {
    public boolean save(LogInCredentials entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute(
                "insert into logInCredentials values (?,?,?,?)",
                //credentialDTO.getUserId(),
                entity.getUserId(),
                entity.getRole(),
                entity.getUserName(),
                entity.getPassword()
        );
    }

    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select userId from logInCredentials order by userId desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1); // Last User ID
            String substring = lastId.substring(2); // Extract the numeric part
            int i = Integer.parseInt(substring); // Convert the numeric part to integer
            int newIdIndex = i + 1; // Increment the number by 1
            return String.format("US%03d", newIdIndex); // Return the new User ID in format Unnn
        }
        return "US001";
    }

    @Override
    public boolean delete(String userId) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("delete from logInCredentials where userId=?", userId);
    }

    @Override
    //public boolean update(LogInCredentialsDTO credentialDTO) throws SQLException, ClassNotFoundException {
    public boolean update(LogInCredentials entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute(
                "update logInCredentials set role=?, userName=?, password=? where userId=?",
                //credentialDTO.getRole(),
                entity.getRole(),
                entity.getUserName(),
                entity.getPassword(),
                entity.getUserId()
        );
    }

    @Override
    public ArrayList<String> getAllIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select userId from logInCredentials");

        ArrayList<String> userIds = new ArrayList<>();

        while (rst.next()) {
            userIds.add(rst.getString(1));
        }
        return userIds;
    }

    @Override
    //public LogInCredentialsDTO findById(String id) throws SQLException, ClassNotFoundException {
    public LogInCredentials findById(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select * from logInCredentials where userId=?", id);

        if (rst.next()) {
            //return new LogInCredentialsDTO(
            return new LogInCredentials(
                    rst.getString(1),  // User Id
                    rst.getString(2),  // role
                    rst.getString(3),  // UserName
                    rst.getString(4)  // Password
            );
        }
        return null;
    }

    @Override
    public String getAdminPassword() throws SQLException, ClassNotFoundException {
        String role = "Admin";
        ResultSet rst = SQLUtil.execute("select password from logInCredentials where role=?", role);

        if (rst.next()) {
            return rst.getString("password");
        }
        return "";
    }

    @Override
    public String isExistUsernameAndPassword(String username, String password) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select userId from logInCredentials where userName=? AND password=?", username,password);

        if (rst.next()) {
            return rst.getString("userId");
        }
        return "";
    }

    @Override
    public String isExistUsername(String username) throws SQLException {
        ResultSet rst = SQLUtil.execute("select userId from logInCredentials where userName=?", username);

        if (rst.next()) {
            return rst.getString("userId");
        }
        return "";
    }

    /*public boolean isExist(String userId) throws SQLException {
        return SQLUtil.execute("select userId from logInCredentials where userId=?", userId);
    }*/
}
