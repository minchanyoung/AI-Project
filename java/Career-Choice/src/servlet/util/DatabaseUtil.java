package servlet.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.servlet.ServletContext;

public class DatabaseUtil {

    private static String dbUrl;
    private static String dbUser;
    private static String dbPassword;
    private static boolean isDriverLoaded = false;

    // Private constructor to prevent instantiation
    private DatabaseUtil() {
        throw new IllegalStateException("Utility class");
    }

    // 서블릿 컨텍스트에서 데이터베이스 정보를 로드하는 초기화 메서드
    public static void init(ServletContext context) {
        dbUrl = context.getInitParameter("DB_URL");
        dbUser = context.getInitParameter("DB_USER");
        dbPassword = context.getInitParameter("DB_PASSWORD");
        
        if (dbUrl == null || dbUser == null || dbPassword == null) {
            throw new IllegalStateException("Database configuration is missing in web.xml");
        }
    }

    // 데이터베이스 커넥션을 반환하는 메서드
    public static Connection getConnection() throws SQLException {
        // Oracle 드라이버 로드는 한 번만 수행
        if (!isDriverLoaded) {
            try {
                Class.forName("oracle.jdbc.driver.OracleDriver");
                isDriverLoaded = true;
            } catch (ClassNotFoundException e) {
                throw new SQLException("Oracle JDBC Driver not found", e);
            }
        }
        return DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }
}
