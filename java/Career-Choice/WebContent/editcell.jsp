<%@ page import="java.util.List" %>
<%@ page import="com.db.dao.CsvDataDAO" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%
  CsvDataDAO dao = new CsvDataDAO();
  List<String> years = dao.getDistinctYears();
  List<String> types = dao.getDistinctIndustryTypes();
  List<String> cols  = dao.getColumnNames();
%>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <title>MERGED_DATA 셀 관리</title>
  <style>
    body { font-family: sans-serif; margin: 40px; }
    form { margin-bottom: 30px; padding: 20px; border: 1px solid #ddd; }
    label { display: inline-block; width: 120px; }
    select, input[type="text"] { width: 200px; }
    button { margin-left: 124px; padding: 6px 12px; }
  </style>
</head>
<body>

  <h1>MERGED_DATA 셀 관리</h1>

  <!-- 수정 폼 -->
  <form action="updateCell" method="post">
    <h2>셀 수정</h2>
    <label for="year">Year</label>
    <select id="year" name="year">
      <% for (String y : years) { %>
        <option value="<%= y %>"><%= y %></option>
      <% } %>
    </select><br><br>

    <label for="industryType">IndustryType</label>
    <select id="industryType" name="industryType">
      <% for (String t : types) { %>
        <option value="<%= t %>"><%= t %></option>
      <% } %>
    </select><br><br>

    <label for="column">컬럼명</label>
    <select id="column" name="column">
      <% for (String c : cols) { %>
        <option value="<%= c %>"><%= c %></option>
      <% } %>
    </select><br><br>

    <label for="newValue">새 값</label>
    <input type="text" id="newValue" name="newValue" required><br><br>

    <button type="submit">수정 실행</button>
  </form>

  <!-- 삭제 폼 -->
  <form action="clearCell" method="post">
    <h2>셀 삭제 (NULL 처리)</h2>
    <label for="year2">Year</label>
    <select id="year2" name="year">
      <% for (String y : years) { %>
        <option value="<%= y %>"><%= y %></option>
      <% } %>
    </select><br><br>

    <label for="industryType2">IndustryType</label>
    <select id="industryType2" name="industryType">
      <% for (String t : types) { %>
        <option value="<%= t %>"><%= t %></option>
      <% } %>
    </select><br><br>

    <label for="column2">컬럼명</label>
    <select id="column2" name="column">
      <% for (String c : cols) { %>
        <option value="<%= c %>"><%= c %></option>
      <% } %>
    </select><br><br>

    <button type="submit">삭제 실행</button>
  </form>

</body>
</html>
