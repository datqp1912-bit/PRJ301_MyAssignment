package dal;

import java.sql.*;
import java.util.*;
import model.Request;
import java.sql.Types;
import java.sql.Timestamp;

public class RequestDAO extends DBContext {

    // Lấy danh sách đơn của 1 user
    public List<Request> getRequestsByUser(int userID) {
        List<Request> list = new ArrayList<>();
        String sql = """
                    SELECT rq.*, u.Name, d.DepName, r.RoleName, s.StatusName, ua.Name AS ApprovedName FROM Request rq
                        JOIN [User] u on u.UserID = rq.Create_by
                        JOIN Department d on d.DepID = u.DepID
                        JOIN [Role] r on r.RoleID = u.RoleID
                        JOIN Status s on s.StatusID = rq.StatusID
                        LEFT JOIN [User] ua on ua.UserID = rq.Approved_by
                    WHERE Create_by = ? 
                    ORDER BY Create_time DESC
                     """;

        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, userID);
            ResultSet rs = st.executeQuery();                                                                       

            while (rs.next()) {
                Request r = new Request();
                r.setReqID(rs.getInt("ReqID"));
                r.setCreateBy(rs.getInt("Create_by"));
                r.setCreateTime(rs.getTimestamp("Create_time"));
                r.setFrom(rs.getDate("From"));
                r.setTo(rs.getDate("To"));
                r.setReason(rs.getString("reason"));
                r.setStatusID(rs.getInt("StatusID"));
                r.setApprovedBy(rs.getObject("Approved_by") != null ? rs.getInt("Approved_by") : null);
                r.setApprovedTime(rs.getTimestamp("Approved_time"));
                r.setDepName(rs.getString("DepName"));
                r.setRoleName(rs.getString("RoleName"));
                r.setStatusName(rs.getString("StatusName"));
                r.setName(rs.getString("Name"));
                r.setApprovedName(rs.getString("ApprovedName"));
                list.add(r);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Thêm 1 đơn mới
    public void insertRequest(Request req) {
        String sql = "INSERT INTO Request (Create_by, Create_time, [From], [To], Reason, StatusID, Approved_by, Approved_time) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement st = connection.prepareStatement(sql)) {

            st.setInt(1, req.getCreateBy());
            st.setTimestamp(2, new Timestamp(req.getCreateTime().getTime()));
            st.setDate(3, new java.sql.Date(req.getFrom().getTime()));
            st.setDate(4, new java.sql.Date(req.getTo().getTime()));
            st.setString(5, req.getReason());
            st.setInt(6, req.getStatusID());

            if (req.getApprovedBy() == null) {
                st.setNull(7, Types.INTEGER);
            } else {
                st.setInt(7, req.getApprovedBy());
            }

            if (req.getApprovedTime() == null) {
                st.setNull(8, Types.TIMESTAMP);
            } else {
                st.setTimestamp(8, new Timestamp(req.getApprovedTime().getTime()));
            }

            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
