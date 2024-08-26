package Users;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.naming.NamingException;
import utils.DBUtils;

/**
 *
 * @author Admins
 */
public class UserDAO {

    private static final String CHECK_LOGIN = "SELECT fullName, roleID, status FROM Users WHERE userID=? and password=?";
    private static final String CHECK_USER_EXISTS = "SELECT fullName, roleID, status FROM Users WHERE userID=?";
    private static final String INSERT_USER = "INSERT INTO Users(userID, fullName, password, roleID, status) VALUES(?, ?, ?, ?, ?)";
    private static final String GET_USER = "SELECT fullName, password, roleID, status FROM Users WHERE userID=?";
    private static final String UPDATE = "UPDATE Users SET fullName = ? , roleID = ? , password = ? WHERE userID = ?";

    public UserDTO checkLogin(String userID, String password) throws SQLException {
        UserDTO userDTO = null;
        Connection connection = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;

        try {
            connection = DBUtils.getConnection();
            if (connection != null) {
                ptm = connection.prepareStatement(CHECK_LOGIN);
                ptm.setString(1, userID);
                ptm.setString(2, password);
                rs = ptm.executeQuery();
                if (rs.next()) {
                    String fullName = rs.getString("fullName");
                    String roleID = rs.getString("roleID");
                    boolean status = rs.getBoolean("status");
                    userDTO = new UserDTO(userID, fullName, password, roleID, status);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ptm != null) {
                ptm.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        return userDTO;
    }

    // Check if the user with the given userID exists
    public boolean checkUserExists(String userID) throws SQLException {
        boolean exists = false;
        Connection connection = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;

        try {
            connection = DBUtils.getConnection();
            if (connection != null) {
                ptm = connection.prepareStatement(CHECK_USER_EXISTS);
                ptm.setString(1, userID);
                rs = ptm.executeQuery();
                if (rs.next()) {
                    exists = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ptm != null) {
                ptm.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        return exists;
    }

    // Register the user in the database
    public boolean registerUser(UserDTO user) throws SQLException {
        Connection connection = null;
        PreparedStatement ptm = null;
        boolean isSuccess = false;

        try {
            connection = DBUtils.getConnection();
            if (connection != null) {
                ptm = connection.prepareStatement(INSERT_USER);
                ptm.setString(1, user.getUserID());
                ptm.setString(2, user.getFullName());
                ptm.setString(3, user.getPassword());
                ptm.setString(4, user.getRoleID()); // Default role, e.g., "US"
                ptm.setBoolean(5, user.isStatus()); // Default status, e.g., true

                int rowCount = ptm.executeUpdate();
                if (rowCount > 0) {
                    isSuccess = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ptm != null) {
                ptm.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        return isSuccess;
    }

    public UserDTO getUser(String userID) throws SQLException {
        UserDTO userDTO = null;
        Connection connection = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;

        try {
            connection = DBUtils.getConnection();
            if (connection != null) {
                ptm = connection.prepareStatement(GET_USER);
                ptm.setString(1, userID);
                rs = ptm.executeQuery();
                if (rs.next()) {
                    String fullName = rs.getString("fullName");
                    String password = rs.getString("password");
                    String roleID = rs.getString("roleID");
                    boolean status = rs.getBoolean("status");
                    userDTO = new UserDTO(userID, fullName, password, roleID, status);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ptm != null) {
                ptm.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        return userDTO;
    }

    public boolean update(UserDTO user) throws SQLException, ClassNotFoundException, NamingException {
        boolean check = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(UPDATE);
                ptm.setString(1, user.getFullName());
                ptm.setString(2, user.getRoleID());
                ptm.setString(3, user.getPassword());
                ptm.setString(4, user.getUserID());
                check = ptm.executeUpdate() > 0 ? true : false;

            }
        } finally {
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return check;
    }
    private final static String GET_ALL = "SELECT * FROM Users";

    public List<UserDTO> getAppoinments() throws SQLException {
        List<UserDTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;

        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(GET_ALL);
                rs = ptm.executeQuery();
                while (rs.next()) {
                    String userID = rs.getString("userID");
                    String fullName = rs.getString("fullName");
                    String roleID = rs.getString("roleID");
                    String password = rs.getString("password");
                    boolean status = rs.getBoolean("status");
                    list.add(new UserDTO(userID, fullName, roleID, password, status));

                }
            }
        } catch (Exception e) {
            e.getStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return list;
    }

    public List<UserDTO> searchUsers(String search) throws SQLException, ClassNotFoundException {
        List<UserDTO> list = new ArrayList<>();
        String sql = "SELECT userID, fullName, password, roleID, status FROM Users WHERE fullName LIKE ?";
        try (Connection conn = DBUtils.getConnection();
                PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, "%" + search + "%");
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    String userID = rs.getString("userID");
                    String fullName = rs.getString("fullName");
                    String password = rs.getString("password");
                    String roleID = rs.getString("roleID");
                    boolean status = rs.getBoolean("status");
                    list.add(new UserDTO(userID, fullName, password, roleID, status));
                }
            }
        }
        return list;
    }
    private static final String DELETE = "DELETE Users WHERE userID = ? ";

    public boolean delete(String userID) throws SQLException, ClassNotFoundException {
        boolean check = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(DELETE);
                ptm.setString(1, userID);
                check = ptm.executeUpdate() > 0 ? true : false;
            }
        } finally {
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }

        }
        return check;
    }
}
