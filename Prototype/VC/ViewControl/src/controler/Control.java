package controler;

import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Control
 */
@WebServlet("/JSPTest/Chat")
public class Control extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ServerClass server;
	private AutoRecv recv;
	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		System.out.println("init 메서드 호출");
		connectToPython();
	}
	// 파이썬 클라이언트와 연결할 서버와 오토 리시버 생성 (안쓰면 init 메소드에서 주석처리할것)
	private void connectToPython() {
		server = new ServerClass();
		recv = new AutoRecv();
		recv.setSocket(server.getSocket());
		recv.start();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		System.out.println("doGet 메서드 호출");
		doHandle(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("doPost 메서드 호출");
		doHandle(request, response);

	}
	
	private void doHandle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//request.getRequestDispatcher("/JSPTest/ChatWithLLM.jsp").forward(request, response);
		request.setCharacterEncoding("utf-8");
		
		String prompt = request.getParameter("prompt");
		System.out.println(prompt);
	    
		if(prompt != null) {
			server.send(prompt); // 아마 여기서부터 REST
		}
	}

	

}
