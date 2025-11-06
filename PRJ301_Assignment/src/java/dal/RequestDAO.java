package dal;

import java.sql.*;
import java.util.*;
import model.Request;

public class RequestDAO extends DBContext {

    /**
     * Lấy danh sách tất cả các đơn của 1 người dùng cụ thể.
     */
    public List<Request> getRequestsByUser(int userID) {
        List<Request> list = new ArrayList<>();
        String sql = """
            SELECT rq.*, 
                   u.Name, 
                   d.DepName, 
                   r.RoleName, 
                   s.StatusName, 
                   ua.Name AS ApprovedName 
            FROM Request rq
            JOIN [User] u ON u.UserID = rq.Create_by
            JOIN Department d ON d.DepID = u.DepID
            JOIN [Role] r ON r.RoleID = u.RoleID
            JOIN Status s ON s.StatusID = rq.StatusID
            LEFT JOIN [User] ua ON ua.UserID = rq.Approved_by
            WHERE rq.Create_by = ? 
            ORDER BY rq.Create_time DESC
        """;

        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, userID);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    Request r = new Request();
                    r.setReqID(rs.getInt("ReqID"));
                    r.setCreateBy(rs.getInt("Create_by"));
                    r.setCreateTime(rs.getTimestamp("Create_time"));
                    r.setFrom(rs.getDate("From"));
                    r.setTo(rs.getDate("To"));
                    r.setReason(rs.getString("Reason"));
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
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Thêm mới 1 đơn nghỉ phép.
     */
    public void insertRequest(Request req) {
        String sql = """
            INSERT INTO Request (Create_by, Create_time, [From], [To], Reason, StatusID, Approved_by, Approved_time)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)
        """;

        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, req.getCreateBy());
            st.setTimestamp(2, new Timestamp(req.getCreateTime().getTime()));
            st.setDate(3, new java.sql.Date(req.getFrom().getTime()));
            st.setDate(4, new java.sql.Date(req.getTo().getTime()));
            st.setString(5, req.getReason());
            st.setInt(6, req.getStatusID());

            // Nếu chưa được duyệt thì để NULL
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

    /**
     * Lấy chi tiết 1 đơn dựa theo ID.
     */
    public Request getRequestById(int id) {
        String sql = "SELECT * FROM Request WHERE ReqID = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, id);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    Request r = new Request();
                    r.setReqID(rs.getInt("ReqID"));
                    r.setCreateBy(rs.getInt("Create_by"));
                    r.setCreateTime(rs.getTimestamp("Create_time"));
                    r.setFrom(rs.getDate("From"));
                    r.setTo(rs.getDate("To"));
                    r.setReason(rs.getString("Reason"));
                    r.setStatusID(rs.getInt("StatusID"));
                    r.setApprovedBy((Integer) rs.getObject("Approved_by"));
                    r.setApprovedTime(rs.getTimestamp("Approved_time"));
                    return r;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // trong RequestDAO
    public boolean updateRequest(Request req) {
        String sql = """
        UPDATE Request
        SET [From] = ?, [To] = ?, Reason = ?, StatusID = 1, Approved_by = NULL, Approved_time = NULL
        WHERE ReqID = ?
    """;
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            if (req.getFrom() != null) {
                st.setDate(1, new java.sql.Date(req.getFrom().getTime()));
            } else {
                st.setNull(1, java.sql.Types.DATE);
            }
            if (req.getTo() != null) {
                st.setDate(2, new java.sql.Date(req.getTo().getTime()));
            } else {
                st.setNull(2, java.sql.Types.DATE);
            }
            st.setString(3, req.getReason());
            st.setInt(4, req.getReqID());
            return st.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Xoá đơn theo ID.
     */
    public boolean deleteRequest(int id) {
        String sql = "DELETE FROM Request WHERE ReqID = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, id);
            return st.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
