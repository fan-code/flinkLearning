package myflink.demo.asynclo;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MysqlClient {

    private static String jdbcUrl = "jdbc:mysql://localhost:3306?useSSL=false&allowPublicKeyRetrieval=true";
    private static String username = "root";
    private static String password = "123456";
    private static String driverName = "com.mysql.jdbc.Driver";
    private static java.sql.Connection conn;
    private static PreparedStatement ps;

    static {
        try {
            Class.forName(driverName);
            conn = DriverManager.getConnection(jdbcUrl, username, password);
            ps = conn.prepareStatement("select phone from ke.async_test where id = ?");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * execute query
     *
     * @param user
     * @return
     */
    public AsyncUser query1(AsyncUser user) {

        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String phone = "0000";
        try {
            ps.setString(1, user.getId());
            ResultSet rs = ps.executeQuery();
            if (!rs.isClosed() && rs.next()) {
                phone = rs.getString(1);
                user.setPhone(phone);
//                rs.close();
//                ps.close();
//                conn.close();
            }
//            System.out.println("execute query : " + user.getId() + "-2-" + "phone : " + phone + "-time" + System.currentTimeMillis());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
}
