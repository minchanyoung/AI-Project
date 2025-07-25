package com.db.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.db.dao.CsvDataDAO;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/deleteAll")
public class DeleteAllServlet extends HttpServlet {
    private CsvDataDAO dao;

    @Override
    public void init() throws ServletException {
        try {
            dao = new CsvDataDAO();
        } catch (Exception e) {
            throw new ServletException("DAO 초기화 실패", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req,
                         HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("text/plain; charset=UTF-8");
        try {
            dao.deleteAll();
            resp.getWriter().println("모든 데이터가 삭제되었습니다.");
        } catch (SQLException e) {
            throw new ServletException("전체 삭제 실패", e);
        }
    }
}
