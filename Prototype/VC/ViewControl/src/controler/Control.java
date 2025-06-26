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
		System.out.println("init �޼��� ȣ��");
		connectToPython();
	}
	// ���̽� Ŭ���̾�Ʈ�� ������ ������ ���� ���ù� ���� (�Ⱦ��� init �޼ҵ忡�� �ּ�ó���Ұ�)
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

		System.out.println("doGet �޼��� ȣ��");
		doHandle(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("doPost �޼��� ȣ��");
		doHandle(request, response);

	}
	
	private void doHandle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//request.getRequestDispatcher("/JSPTest/ChatWithLLM.jsp").forward(request, response);
		request.setCharacterEncoding("utf-8");
		
		String prompt = request.getParameter("prompt");
		System.out.println(prompt);
	    
		if(prompt != null) {
			server.send(prompt); // �Ƹ� ���⼭���� REST
		}
	}

	

}
