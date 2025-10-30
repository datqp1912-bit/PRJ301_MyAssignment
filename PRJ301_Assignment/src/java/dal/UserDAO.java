package dal;

import java.sql.*;
import model.User;
import model.Role;

public class UserDAO extends DBContext {

    public User login(String username, String password) {
        try {
            String sql = """
                SELECT u.UserID, u.Username, u.Password, u.Display, u.Active,
                       r.RoleID, r.RoleName
                FROM [User] u
                JOIN UserRole ur ON u.UserID = ur.UserID
                JOIN Role r ON ur.RoleID = r.RoleID
                WHERE u.Username = ? AND u.Password = ?
            """;

            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, username);
            st.setString(2, password);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                User u = new User();
                u.setUserID(rs.getInt("UserID"));
                u.setUsername(rs.getString("Username"));
                u.setPassword(rs.getString("Password"));
                u.setDisplay(rs.getString("Display"));
                u.setActive(rs.getBoolean("Active"));

                Role r = new Role();
                r.setRoleID(rs.getInt("RoleID"));
                r.setRoleName(rs.getString("RoleName"));
                u.setRole(r);

                return u;
            }

        } catch (Exception e) {
            e.printStackTrace(); // hiện lỗi nếu có
        }
        return null;
    }
}
