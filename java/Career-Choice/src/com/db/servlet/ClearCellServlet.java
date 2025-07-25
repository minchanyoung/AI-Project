package com.db.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.db.dao.CsvDataDAO;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/clearCell")
public class ClearCellServlet extends HttpServlet {
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
    protected void doPost(HttpServletRequest req,
                          HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String year         = req.getParameter("year");
        String industryType = req.getParameter("industryType");
        String column       = req.getParameter("column");

        resp.setContentType("text/plain; charset=UTF-8");
        try {
            int count = dao.clearCell(column, year, industryType);
            if (count > 0) {
                resp.getWriter().println(count + "건 삭제(널 처리) 완료");
            } else {
                resp.getWriter().println("0건 삭제됨(조건 불일치)");
            }
        } catch (SQLException e) {
            throw new ServletException("셀 삭제 중 오류", e);
        }
    }
}
