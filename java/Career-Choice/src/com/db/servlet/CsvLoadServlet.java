package com.db.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.db.dao.CsvDataDAO;

import java.io.InputStream;
import java.io.IOException;

@WebServlet("/loadCsv")
public class CsvLoadServlet extends HttpServlet {
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
        int inserted = 0;

        try (InputStream is1 = getServletContext()
                                   .getResourceAsStream("/WEB-INF/csv/merged_data.csv");
             InputStream is2 = getServletContext()
                                   .getResourceAsStream("/WEB-INF/csv/merged_data.csv")) {

            dao.createTable(is1);
            inserted = dao.loadCsv(is2);
        } catch (Exception e) {
            throw new ServletException("CSV 로드 실패", e);
        }

        resp.getWriter().println("테이블 재생성 완료");
        resp.getWriter().println("총 삽입된 레코드 수: " + inserted);
    }
}
