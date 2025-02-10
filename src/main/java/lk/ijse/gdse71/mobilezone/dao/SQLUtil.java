package lk.ijse.gdse71.mobilezone.dao;

import javafx.scene.control.Alert;
import lk.ijse.gdse71.mobilezone.db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;


public class SQLUtil {

    public static <T>T execute(String sql, Object... args) throws SQLException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        PreparedStatement pst = connection.prepareStatement(sql);

        for (int i=0; i< args.length; i++){
            pst.setObject((i+1), args[i]);
        }

        if(sql.startsWith("select") || sql.startsWith("SELECT")) {
            ResultSet resultSet = pst.executeQuery();
            return (T) resultSet;
        }else{
            int i = pst.executeUpdate();
            boolean isSaved = i > 0;
            return (T) ((Boolean) isSaved);
        }
    }
}

