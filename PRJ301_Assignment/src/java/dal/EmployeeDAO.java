package dal;

import java.sql.*;
import model.Employee;
import model.Department;

public class EmployeeDAO extends DBContext {

    public Employee getEmployeeByUserID(int userID) {
        try {
            String sql = """
                SELECT e.EmpID, e.EmpName, e.Email, e.Phone, e.DOB,
                       d.DepID, d.DepName
                FROM Employee e
                JOIN Department d ON e.DepID = d.DepID
                WHERE e.UserID = ?
            """;

            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, userID);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                Employee e = new Employee();
                e.setEmpID(rs.getInt("EmpID"));
                e.setEmpName(rs.getString("EmpName"));
                e.setEmail(rs.getString("Email"));
                e.setPhone(rs.getString("Phone"));
                e.setDob(rs.getDate("DOB"));

                Department d = new Department();
                d.setDepID(rs.getInt("DepID"));
                d.setDepName(rs.getString("DepName"));
                e.setDepartment(d);

                return e;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
