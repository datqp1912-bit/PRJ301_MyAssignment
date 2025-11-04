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

    public void addUser(User user) {
            String query = "INSERT INTO [User] (Username, Password, Name, Email, Phone, DepID, RoleID, Active) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            
            PreparedStatement st = connection.prepareStatement(query);
            st.setString(1, user.getUsername());
            st.setString(2, user.getPassword());
            st.setString(3, user.getName());
            st.setString(4, user.getEmail());
            st.setString(5, user.getPhone());
            st.setInt(6, user.getDepID());     // Liên kết sang bảng Department
            st.setInt(7, user.getRoleID());    // Liên kết sang bảng Role
            st.setBoolean(8, user.isActive()); // Trạng thái hoạt động
            st.executeUpdate();
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Cập nhật thông tin người dùng
    public void updateUser(User user) {
        String sql = """
            UPDATE [User]
            SET Username = ?, 
                Password = ?, 
                Name = ?, 
                Email = ?, 
                Phone = ?, 
                DepID = ?, 
                RoleID = ?, 
                Active = ?
            WHERE UserID = ?
        """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getName());
            ps.setString(4, user.getEmail());
            ps.setString(5, user.getPhone());
            ps.setInt(6, user.getDepID());
            ps.setInt(7, user.getRoleID());
            ps.setBoolean(8, user.isActive());
            ps.setInt(9, user.getUserID());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Xóa người dùng theo ID
    public void deleteUser(int id) {
        String sql = "DELETE FROM [User] WHERE UserID = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Lấy thông tin người dùng theo ID
    public User getUserByID(int id) {
        User user = null;
        String sql = """
            SELECT 
                u.UserID,
                u.Username,
                u.Password,
                u.Name,
                u.Email,
                u.Phone,
                u.DepID,
                u.RoleID,
                d.DepName,
                r.RoleName,
                u.Active
            FROM [User] u
                JOIN Role r ON u.RoleID = r.RoleID
                JOIN Department d ON u.DepID = d.DepID
            WHERE u.UserID = ?
        """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                user = new User();
                user.setUserID(rs.getInt("UserID"));
                user.setUsername(rs.getString("Username"));
                user.setPassword(rs.getString("Password"));
                user.setName(rs.getString("Name"));
                user.setEmail(rs.getString("Email"));
                user.setPhone(rs.getString("Phone"));
                user.setDepID(rs.getInt("DepID"));
                user.setRoleID(rs.getInt("RoleID"));
                user.setDepName(rs.getString("DepName"));
                user.setRoleName(rs.getString("RoleName"));
                user.setActive(rs.getBoolean("Active"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
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
