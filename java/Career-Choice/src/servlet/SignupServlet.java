package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import servlet.util.DatabaseUtil;

@WebServlet("/signup")
public class SignupServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(SignupServlet.class.getName());

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        DatabaseUtil.init(config.getServletContext());
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");

        String sql = "INSERT INTO members (username, password, email) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setString(3, email);

            int result = pstmt.executeUpdate();
            if (result > 0) {
                LOGGER.log(Level.INFO, "New user signed up: {0}", username);
                response.sendRedirect("login.jsp");
            } else {
                // This case should ideally not happen if DB is working correctly
                throw new SQLException("Insert failed, no rows affected.");
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            LOGGER.log(Level.WARNING, "Signup failed due to constraint violation for user: {0}", username);
            request.setAttribute("error", "이미 사용 중인 아이디 또는 이메일입니다.");
            request.getRequestDispatcher("signup.jsp").forward(request, response);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Database error during signup", e);
            request.setAttribute("error", "데이터베이스 처리 중 오류가 발생했습니다.");
            request.getRequestDispatcher("signup.jsp").forward(request, response);
        }
    }
}
