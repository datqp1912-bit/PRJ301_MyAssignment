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

    public List<User> searchUsers(String keyword) {
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
        WHERE u.Username LIKE ? OR u.Display LIKE ?
        """;

        try (Connection con = new DBContext().connection; PreparedStatement ps = con.prepareStatement(sql)) {

            String kw = "%" + keyword + "%";
            ps.setString(1, kw);
            ps.setString(2, kw);

            ResultSet rs = ps.executeQuery();
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // ==============================
// THÊM NGƯỜI DÙNG
// ==============================
    public void addUser(User u) {
        String insertUserSQL = "INSERT INTO [User] (Username, Password, Display, Active, Email, Phone)VALUES (?, ?, ?, ?, ?, ?)";
        String insertUserRoleSQL = "INSERT INTO UserRole (UserID, RoleID) VALUES (?, ?)";
        String insertEmployeeSQL = "INSERT INTO Employee (DepID) VALUES (?)";
        String insertEmpUserSQL = "INSERT INTO EmployeeUser (EmpID, UserID) VALUES (?, ?)";

        PreparedStatement stUser = null;
        PreparedStatement stRole = null;
        PreparedStatement stEmp = null;
        PreparedStatement stEmpUser = null;
        ResultSet rsUser = null;
        ResultSet rsEmp = null;

        try {
            connection.setAutoCommit(false);

            // 1️⃣ Thêm vào bảng [User]
            stUser = connection.prepareStatement(insertUserSQL, Statement.RETURN_GENERATED_KEYS);
            stUser.setString(1, u.getUsername());
            stUser.setString(2, u.getPassword());
            stUser.setString(3, u.getDisplay());
            stUser.setBoolean(4, u.isActive());
            stUser.setString(5, u.getEmail());
            stUser.setString(6, u.getPhone());
            stUser.executeUpdate();

            rsUser = stUser.getGeneratedKeys();
            int newUserID = 0;
            if (rsUser.next()) {
                newUserID = rsUser.getInt(1);
            }

            // 2️⃣ Thêm vào bảng UserRole
            stRole = connection.prepareStatement(insertUserRoleSQL);
            stRole.setInt(1, newUserID);
            stRole.setInt(2, u.getRole().getRoleID());
            stRole.executeUpdate();

            // 3️⃣ Tạo bản ghi Employee (gắn phòng ban)
            stEmp = connection.prepareStatement(insertEmployeeSQL, Statement.RETURN_GENERATED_KEYS);
            stEmp.setInt(1, u.getDepartment().getDepID());
            stEmp.executeUpdate();

            rsEmp = stEmp.getGeneratedKeys();
            int newEmpID = 0;
            if (rsEmp.next()) {
                newEmpID = rsEmp.getInt(1);
            }

            // 4️⃣ Liên kết Employee ↔ User
            stEmpUser = connection.prepareStatement(insertEmpUserSQL);
            stEmpUser.setInt(1, newEmpID);
            stEmpUser.setInt(2, newUserID);
            stEmpUser.executeUpdate();

            connection.commit();
            System.out.println("✅ Thêm người dùng thành công!");

        } catch (SQLException e) {
            try {
                connection.rollback();
                System.err.println("⚠️ Lỗi thêm người dùng — rollback!");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                if (rsUser != null) {
                    rsUser.close();
                }
                if (rsEmp != null) {
                    rsEmp.close();
                }
                if (stUser != null) {
                    stUser.close();
                }
                if (stRole != null) {
                    stRole.close();
                }
                if (stEmp != null) {
                    stEmp.close();
                }
                if (stEmpUser != null) {
                    stEmpUser.close();
                }
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // ==============================
// CẬP NHẬT NGƯỜI DÙNG
// ==============================
    public void updateUser(User u) {
        String updateUserSQL = """
        UPDATE [User]
        SET Username = ?, Password = ?, Display = ?, Active = ?, Email = ?, Phone = ?
        WHERE UserID = ?
    """;

        String updateUserRoleSQL = """
        UPDATE UserRole
        SET RoleID = ?
        WHERE UserID = ?
    """;

        String updateEmployeeSQL = """
        UPDATE Employee
        SET DepID = ?
        WHERE EmpID = (
            SELECT e.EmpID
            FROM Employee e
            JOIN EmployeeUser eu ON e.EmpID = eu.EmpID
            WHERE eu.UserID = ?
        )
    """;

        try {
            connection.setAutoCommit(false);

            // 1️⃣ Cập nhật bảng User
            try (PreparedStatement st = connection.prepareStatement(updateUserSQL)) {
                st.setString(1, u.getUsername());
                st.setString(2, u.getPassword());
                st.setString(3, u.getDisplay());
                st.setBoolean(4, u.isActive());
                st.setString(5, u.getEmail());
                st.setString(6, u.getPhone());
                st.setInt(7, u.getUserID());
                st.executeUpdate();
            }

            // 2️⃣ Cập nhật Role
            try (PreparedStatement stRole = connection.prepareStatement(updateUserRoleSQL)) {
                stRole.setInt(1, u.getRole().getRoleID());
                stRole.setInt(2, u.getUserID());
                stRole.executeUpdate();
            }

            // 3️⃣ Cập nhật Department
            try (PreparedStatement stDep = connection.prepareStatement(updateEmployeeSQL)) {
                stDep.setInt(1, u.getDepartment().getDepID());
                stDep.setInt(2, u.getUserID());
                stDep.executeUpdate();
            }

            connection.commit();
            System.out.println("✅ Cập nhật người dùng thành công!");

        } catch (SQLException e) {
            try {
                connection.rollback();
                System.err.println("⚠️ Lỗi cập nhật người dùng — rollback!");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // ========================================
    // XOÁ NGƯỜI DÙNG
    // ========================================
    public void deleteUser(int userID) {
        try {
            connection.setAutoCommit(false);

            // Xóa liên kết phụ trước
            PreparedStatement st1 = connection.prepareStatement("DELETE FROM UserRole WHERE UserID = ?");
            st1.setInt(1, userID);
            st1.executeUpdate();

            PreparedStatement st2 = connection.prepareStatement("DELETE FROM EmployeeUser WHERE UserID = ?");
            st2.setInt(1, userID);
            st2.executeUpdate();

            // Sau đó xóa user
            PreparedStatement st3 = connection.prepareStatement("DELETE FROM [User] WHERE UserID = ?");
            st3.setInt(1, userID);
            st3.executeUpdate();

            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // ========================================
    // LẤY NGƯỜI DÙNG THEO ID
    // ========================================
    public User getUserById(int id) {
        String sql = """
            SELECT 
                u.UserID,
                u.Username,
                u.Password,
                u.Display,
                e.Email,
                e.Phone,
                d.DepID,
                d.DepName,
                r.RoleID,
                r.RoleName,
                u.Active
            FROM [User] u
                JOIN UserRole ur ON u.UserID = ur.UserID
                JOIN Role r ON ur.RoleID = r.RoleID
                JOIN EmployeeUser eu ON u.UserID = eu.UserID
                JOIN Employee e ON eu.EmpID = e.EmpID
                JOIN Department d ON e.DepID = d.DepID
            WHERE u.UserID = ?
        """;
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                Role role = new Role();
                role.setRoleID(rs.getInt("RoleID"));
                role.setRoleName(rs.getString("RoleName"));

                Department dep = new Department();
                dep.setDepID(rs.getInt("DepID"));
                dep.setDepName(rs.getString("DepName"));
                
                Employee emp = new Employee();
                emp.setEmail(rs.getString("Email"));
                emp.setPhone(rs.getString("Phone"));

                User u = new User();
                u.setUserID(rs.getInt("UserID"));
                u.setUsername(rs.getString("Username"));
                u.setPassword(rs.getString("Password"));
                u.setDisplay(rs.getString("Display"));
                u.setEmail(rs.getString("Email"));
                u.setPhone(rs.getString("Phone"));
                u.setRole(role);
                u.setDepartment(dep);
                u.setActive(rs.getBoolean("Active"));
                return u;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
