package servlet;

import com.db.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.time.LocalDate;

import org.json.JSONObject;

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
	// 다음에 UID기반으로 데이터 겹침 방지할 것
	private void doHandle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        
		String rangeLimit = request.getParameter("rangeLimit");
		String industryType = request.getParameter("industry");
		String detail = request.getParameter("detail");
		
		LocalDate now = LocalDate.now();
		
		int startYear = now.getYear();
		int endYear = startYear + Integer.parseInt(rangeLimit) + 1;

		ServletContext context = getServletContext();
		context.setAttribute("rangeLimit", rangeLimit);
		context.setAttribute("mainType", industryType);
		
		// 컬럼을 보고 테이블이 몇개 쓰이는지 체크
		// 같이 쓰이는 테이블을 자연조인하고
		// 필요한것만 셀렉하도록 재설정
		GeneralDAO dao = new GeneralDAO();
		
		String columns = ColumnData.getServiceLegacyType(detail);
		String selectQuery = columns;
		
//		ArrayList<String> lString = new ArrayList<String>();
//		String[] sliceColumn = columns.split(", ");
//		for(int i = 0;i<sliceColumn.length;++i) {
//			if(lString.contains(sliceColumn[i]) == false)
//				lString.add(sliceColumn[i]);
//		}

//		String table = ColumnData.getServiceTypeToTable(detail);
		String fromQuery = "MERGED_DATA";
//		if(lString.size() >= 2)
//			for(int i = 0;i<lString.size()-1;++i) {
//			fromQuery += dao.JoinTable(ColumnData.getPredictTableName(lString.get(i)),);
//		else
//		fromQuery += lString.get(0) + " ";
		
		String whereQuery = "year >= " + String.valueOf(startYear) + " AND " + "year <= " + String.valueOf(endYear);
		
		ArrayList<BaseVO<String>> dataList = dao.getLegacyData(selectQuery, fromQuery, whereQuery);


		for(int i=0;i<dataList.size();++i) {
			JSONObject objMap = new JSONObject();
			BaseVO<String> vo = dataList.get(i);
			objMap.put("year", vo.getYear());
			objMap.put("type", vo.getIndustryType());
			ArrayList<String> data = vo.getData();
			switch(detail) {
//			case "1":
//			case "2":
//			case "5":
//				ArrayList<BaseVO<Integer>> rscData = dao.getIntData("*", "REALINDUSTRYCOUNTDATA");
//				System.out.println(rscData);
//				float d = data.get(1)/data.get(0); 
//				objMap.put("data", d);
//				System.out.println(d);
//				break;
			default:
				objMap.put("data", data.get(0));
//				System.out.println(data.get(0));
				break;
			}
//			objMap.put(String.valueOf(vo.getYear()) + "_" + vo.getIndustryType(), byYearMap);
//			System.out.println(String.valueOf(vo.getYear()) + "_" + vo.getIndustryType());
//			System.out.println(objMap);
			context.setAttribute(String.valueOf(vo.getYear()) + "_" + vo.getIndustryType(), objMap);
			System.out.println(context.getAttribute(String.valueOf(vo.getYear()) + "_" + vo.getIndustryType()));
		}
		
		RequestDispatcher dispatch = request.getRequestDispatcher("IndustryResult.jsp");
		dispatch.forward(request, response);
		
	}
	

	private void CallAndPrint(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print("<html><body>");
		ServletContext context = getServletContext();
		GeneralDAO dao = new GeneralDAO();
		ArrayList<BaseVO<Float>> dataList = dao.getFloatData(ColumnData.commonColumns[1]+", "+ColumnData.commonColumns[2]+", "+"companyCount", "PredictIndustryCountData", "dataId < 5");
		
		context.setAttribute("contextData", dataList);
//		dataList.size()
		for(int i=0;i<dataList.size();++i) {
			BaseVO<Float> vo = dataList.get(i);
			int id = vo.getId();
			out.print("<br>");
			out.print("<td> " + id + " </td>");
			int year = vo.getYear();
			out.print("<br>");
			out.print("<td> " + year + " </td>");
			String type = vo.getIndustryType();
			out.print("<br>");
			out.print("<td> " + type + " </td>");
			ArrayList<Float> data = vo.getData();
			for(int j=0;j<data.size();++j) {
				context.setAttribute("data_" + String.valueOf(i) + String.valueOf(j), type);
				out.print("<br>");
				out.print("<tr><td>"+ data.get(j) + "</td>");
			}
		}
		out.print("<br>");
		out.print("<body><html>");
	}
}

