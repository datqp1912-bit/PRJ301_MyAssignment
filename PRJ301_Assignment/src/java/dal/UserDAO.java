package dal;

import java.sql.*;
import model.User;

public class UserDAO extends DBContext {

    public User checkLogin(String username, String password) {
        String sql = "SELECT * FROM [User] WHERE Username = ? AND [Password] = ? AND Active = 1";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                User u = new User();
                u.setUserID(rs.getInt("UserID"));
                u.setUsername(rs.getString("Username"));
                u.setPassword(rs.getString("Password"));
                u.setDisplay(rs.getString("Display"));
                u.setActive(rs.getBoolean("Active"));
                return u;
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
