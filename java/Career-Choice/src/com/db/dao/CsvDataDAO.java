package com.db.dao;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.*;
import javax.naming.*;
import javax.sql.DataSource;
import com.opencsv.CSVReader;

public class CsvDataDAO {
    private static final String TABLE = "MERGED_DATA";
    private final DataSource ds;

    public CsvDataDAO() throws NamingException {
        Context ctx = new InitialContext();
        ds = (DataSource) ctx.lookup("java:comp/env/jdbc/OracleDB");
    }

    // 1) 테이블 DROP & CREATE (CSV 헤더로 컬럼 생성)
    public void createTable(InputStream csvStream) throws Exception {
        try (InputStreamReader isr = new InputStreamReader(csvStream, StandardCharsets.UTF_8);
             BufferedReader br = new BufferedReader(isr);
             CSVReader reader = new CSVReader(br);
             Connection conn = ds.getConnection();
             Statement stmt = conn.createStatement()) {

            String[] headers = reader.readNext();
            if (headers == null) throw new IllegalStateException("CSV에 헤더가 없습니다.");

            // BOM 제거
            if (headers[0].startsWith("\uFEFF")) {
                headers[0] = headers[0].substring(1);
            }

            StringBuilder cols = new StringBuilder();
            for (int i = 0; i < headers.length; i++) {
                String colName = headers[i];
                if (colName.startsWith("\uFEFF")) {
                    colName = colName.substring(1);
                }

                if ("YEAR".equalsIgnoreCase(colName)) {
                    cols.append(colName).append(" NUMBER");
                } else {
                    cols.append(colName).append(" VARCHAR2(100)");
                }

                if (i < headers.length - 1) {
                    cols.append(",\n    ");
                }
            }


            String dropSql =
              "BEGIN\n" +
              "  EXECUTE IMMEDIATE 'DROP TABLE " + TABLE + "';\n" +
              "EXCEPTION WHEN OTHERS THEN NULL;\n" +
              "END;";

            String createSql =
              "CREATE TABLE " + TABLE + " (\n    " +
              cols + "\n)";

            stmt.execute(dropSql);
            stmt.execute(createSql);
        }
    }

    // 2) CSV 전체 로드
    public int loadCsv(InputStream csvStream) throws Exception {
        try (InputStreamReader isr = new InputStreamReader(csvStream, StandardCharsets.UTF_8);
             BufferedReader br = new BufferedReader(isr);
             CSVReader reader = new CSVReader(br);
             Connection conn = ds.getConnection()) {

            String[] headers = reader.readNext();
            if (headers == null) return 0;
            if (headers[0].startsWith("\uFEFF")) {
                headers[0] = headers[0].substring(1);
            }

            String colList = String.join(", ", headers);
            String placeholders = String.join(", ",
                Collections.nCopies(headers.length, "?"));
            String sql = "INSERT INTO " + TABLE +
                         " (" + colList + ") VALUES (" + placeholders + ")";

            conn.setAutoCommit(false);
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                String[] row;
                int total = 0;
                while ((row = reader.readNext()) != null) {
                    for (int i = 0; i < row.length; i++) {
                        ps.setString(i + 1, row[i]);
                    }
                    ps.addBatch();
                }
                for (int cnt : ps.executeBatch()) {
                    total += cnt;
                }
                conn.commit();
                return total;
            }
        }
    }

    // 3) 전체 데이터 삭제
    public void deleteAll() throws SQLException {
        try (Connection conn = ds.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("DELETE FROM " + TABLE);
        }
    }

    // 4) 셀 수정 (영향 행 수 반환)
    public int updateCell(String column,
                          String year,
                          String industryType,
                          String newValue) throws SQLException {
        String sql = String.format(
            "UPDATE %s SET %s = ? WHERE YEAR = ? AND INDUSTRYTYPE = ?",
            TABLE, column);
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, newValue.trim());
            ps.setString(2, year.trim());
            ps.setString(3, industryType.trim());
            return ps.executeUpdate();
        }
    }

    // 5) 셀 삭제(NULL 처리, 영 영향 행 수 반환)
    public int clearCell(String column,
                         String year,
                         String industryType) throws SQLException {
        String sql = String.format(
            "UPDATE %s SET %s = NULL WHERE YEAR = ? AND INDUSTRYTYPE = ?",
            TABLE, column);
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, year.trim());
            ps.setString(2, industryType.trim());
            return ps.executeUpdate();
        }
    }

    // 6) 드롭다운용 DISTINCT 값 조회
    public List<String> getDistinctValues(String column) throws SQLException {
        String sql = String.format(
            "SELECT DISTINCT %s FROM %s ORDER BY %s", column, TABLE, column);
        List<String> list = new ArrayList<>();
        try (Connection conn = ds.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(rs.getString(1));
            }
        }
        return list;
    }

    public List<String> getDistinctYears() throws SQLException {
        return getDistinctValues("YEAR");
    }

    public List<String> getDistinctIndustryTypes() throws SQLException {
        return getDistinctValues("INDUSTRYTYPE");
    }

    // 7) 컬럼명 목록 조회
    public List<String> getColumnNames() throws SQLException {
        String sql = "SELECT * FROM " + TABLE + " WHERE ROWNUM = 1";
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            ResultSetMetaData md = rs.getMetaData();
            List<String> cols = new ArrayList<>();
            for (int i = 1; i <= md.getColumnCount(); i++) {
                cols.add(md.getColumnName(i));
            }
            return cols;
        }
    }
}
