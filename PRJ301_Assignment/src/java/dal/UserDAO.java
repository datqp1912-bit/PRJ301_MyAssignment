package dal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Department;
import model.Employee;
import model.Role;
import model.User;

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

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();

        String sql = """
        SELECT 
            u.UserID,
            u.Username,
            u.Password,
            u.Display,
            d.DepName,
            r.RoleName,
            u.Active
        FROM [User] u
            JOIN UserRole ur ON u.UserID = ur.UserID
            JOIN Role r ON ur.RoleID = r.RoleID
            JOIN EmployeeUser eu ON u.UserID = eu.UserID
            JOIN Employee e ON eu.EmpID = e.EmpID
            JOIN Department d ON e.DepID = d.DepID
    """;

        try (PreparedStatement st = connection.prepareStatement(sql); ResultSet rs = st.executeQuery()) {

            while (rs.next()) {
                Role role = new Role();
                role.setRoleName(rs.getString("RoleName"));

                Department dep = new Department();
                dep.setDepName(rs.getString("DepName"));

                User u = new User();
                u.setUserID(rs.getInt("UserID"));
                u.setUsername(rs.getString("Username"));
                u.setPassword(rs.getString("Password"));
                u.setDisplay(rs.getString("Display"));
                u.setRole(role);
                u.setDepartment(dep);
                u.setActive(rs.getBoolean("Active"));

                list.add(u);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

}
