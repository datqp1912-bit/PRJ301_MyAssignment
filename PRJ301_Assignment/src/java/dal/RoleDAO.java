package dal;

import java.sql.*;
import java.util.*;
import model.Role;

public class RoleDAO extends DBContext {

    public List<Role> getAllRoles() {
        List<Role> list = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Role";
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Role r = new Role();
                r.setRoleID(rs.getInt("RoleID"));
                r.setRoleName(rs.getString("RoleName"));
                list.add(r);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
