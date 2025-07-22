package servlet;

import com.db.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class SetIndustryDataServlet
 */
@WebServlet("/SetIndustryDataServlet")
public class SetIndustryDataServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doHandle(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doHandle(request, response);
	}

	private void doHandle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		
		CommonDAO dao = new CommonDAO();
		ArrayList<BaseVO<Integer>> dataList = dao.getCountData();
		

		out.print("<html><body>");

		
		for(int i=0;i<dataList.size();++i) {
			BaseVO<Integer> vo = dataList.get(i);
			int id = vo.getId();
			out.print("<tr>");
			out.print("<td> " + id + " </td>");
			int year = vo.getYear();
			out.print("<tr>");
			out.print("<td> " + year + " </td>");
			String type = vo.getIndustryType();
			out.print("<tr>");
			out.print("<td> " + type + " </td>");
			
			
			ArrayList<Integer> data = vo.getData();
			int dataLen = data.size();
			for(int j=0;j<data.size();++j) {
				out.print("<tr>");
				out.print("<tr><td>"+ data.get(j) + "</td>");
				
			}
		}
			out.print("<tr>");	
		out.print("</table>");
		out.print("<body><html>");
	}
}
