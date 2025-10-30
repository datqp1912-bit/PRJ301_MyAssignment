package dal;

import java.sql.*;
import java.util.*;
import model.Department;

public class DepartmentDAO extends DBContext {

    public List<Department> getAllDepartments() {
        List<Department> list = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Department";
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Department d = new Department();
                d.setDepID(rs.getInt("DepID"));
                d.setDepName(rs.getString("DepName"));
                list.add(d);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
