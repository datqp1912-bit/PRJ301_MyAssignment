package dal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.User;

public class UserDAO extends DBContext {

    //Hàm kiểm tra đăng nhập và trả về đối tượng User nếu hợp lệ
    public User checkLogin(String username, String password) {
        User user = null;
        String sql = "SELECT * FROM [User] WHERE Username = ? AND Password = ? AND Active = 1";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int userID = rs.getInt("UserID");
                String name = rs.getString("Name");
                String email = rs.getString("Email");
                String phone = rs.getString("Phone");
                int depID = rs.getInt("DepID");
                int roleID = rs.getInt("RoleID");
                boolean active = rs.getBoolean("Active");

                //Tạo đối tượng User và trả về
                user = new User(userID, name, email, phone, depID, roleID, active);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        String sql = """
        SELECT 
            u.UserID,
            u.Username,
            u.Name,
            u.Email,
            u.Phone,
            d.DepName,
            r.RoleName,
            u.Active
        FROM [User] u
            JOIN Role r ON u.RoleID = r.RoleID
            JOIN Department d ON u.DepID = d.DepID
    """;

        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                User u = new User();
                u.setUserID(rs.getInt("UserID"));
                u.setUsername(rs.getString("Username"));
                u.setName(rs.getString("Name"));
                u.setEmail(rs.getString("Email"));
                u.setPhone(rs.getString("Phone"));
                u.setDepName(rs.getString("DepName"));
                u.setRoleName(rs.getString("RoleName"));
                u.setActive(rs.getBoolean("Active"));

                list.add(u);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<User> searchByName(String name) {
        List<User> list = new ArrayList<>();
        String sql = """
            SELECT 
                u.UserID,
                u.Username,
                u.Name,
                u.Email,
                u.Phone,
                d.DepName,
                r.RoleName,
                u.Active
            FROM [User] u
                JOIN Role r ON u.RoleID = r.RoleID
                JOIN Department d ON u.DepID = d.DepID
            WHERE u.Name LIKE ?
        """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, "%" + name + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                User u = new User();
                u.setUserID(rs.getInt("UserID"));
                u.setUsername(rs.getString("Username"));
                u.setName(rs.getString("Name"));
                u.setEmail(rs.getString("Email"));
                u.setPhone(rs.getString("Phone"));
                u.setDepName(rs.getString("DepName"));
                u.setRoleName(rs.getString("RoleName"));
                u.setActive(rs.getBoolean("Active"));
                list.add(u);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public static void main(String[] args) {
        UserDAO dao = new UserDAO();

        // Nhập thông tin test (đổi lại cho đúng dữ liệu trong DB)
        String username = "admin";
        String password = "123";

        List<User> user = dao.getAllUsers();

        if (user != null) {
            System.out.println("✅ Đăng nhập thành công!");
            System.out.println("u" + user);
        } else {
            System.out.println("❌ Sai tên đăng nhập hoặc mật khẩu!");
        }
    }
}
