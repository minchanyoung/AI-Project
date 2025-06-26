DbTest 코드는 DB 연동이 제대로 되는지 빠르게 확인하기 위한 “기반 테스트용 코드”, 말 그대로 “백업 통신 축” 역할을 해요.

💡 코드의 핵심 의도 요약:
- **기존 또는 새로운 프레임워크(Spring, Spring Boot 등)**에서 문제가 생겼을 때,
- DB와의 연결 자체가 정상인지 아닌지를 독립적으로 테스트할 수 있도록
- 최소한의 서블릿 구조로 통신 및 연결 구간만 미리 검증하는 코드입니다.

✨ 그래서 이 코드는 이런 의미가 있어요:
- ✔️ DB 연결이 된다 → 프레임워크 쪽 설정 문제일 가능성이 높음
- ❌ DB 연결이 안 된다 → 드라이버, URL, 포트, 계정 등 환경 문제일 수 있음

// 패키지 선언
package com.prototype;

// 필요한 라이브러리 import
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// 서블릿 매핑: /dbtest 주소로 호출되면 이 서블릿이 실행됨
@WebServlet("/dbtest")
public class DbTest extends HttpServlet {

    // HTTP GET 요청이 들어왔을 때 실행되는 메서드
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 응답의 콘텐츠 타입 설정 (HTML + UTF-8)
        response.setContentType("text/html;charset=UTF-8");

        // 클라이언트로 데이터를 출력하기 위한 PrintWriter 객체 생성
        PrintWriter out = response.getWriter();

        // Oracle 데이터베이스 연결 정보 설정
        String url = "jdbc:oracle:thin:@localhost:1521:xe";  // 호스트: localhost, 포트: 1521, SID: xe
        String user = "scott";     // DB 사용자 ID
        String password = "tiger"; // DB 비밀번호

        try {
            // Oracle JDBC 드라이버 로딩
            Class.forName("oracle.jdbc.driver.OracleDriver");

            // DB 연결 시도
            Connection conn = DriverManager.getConnection(url, user, password);

            // 연결 성공 시 메시지 출력
            out.println("<h3>DB 연결 성공!</h3>");

            // 연결 닫기
            conn.close();

        } catch (Exception e) {
            // 연결 실패 시 메시지 출력 + 예외 출력
            out.println("<h3>DB 연결 실패 😢</h3>");
            e.printStackTrace(out);
        }
    }
}
