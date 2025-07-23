package servlet;

import com.db.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

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
		context.setAttribute("mainType", industryType);
		
		// 컬럼을 보고 테이블이 몇개 쓰이는지 체크
		// 같이 쓰이는 테이블을 자연조인하고
		// 필요한것만 셀렉하도록 재설정
		GeneralDAO dao = new GeneralDAO();
		
		String columns = ColumnData.getServiceType(detail);
		String selectQuery = "select " + ColumnData.getServiceType(detail);
		
		ArrayList<String> lString = new ArrayList<String>();
		String[] sliceColumn = columns.split(", ");
		for(int i = 0;i<sliceColumn.length;++i) {
			if(lString.contains(sliceColumn[i]) == false)
				lString.add(sliceColumn[i]);
		}

		String fromQuery = "from ";
		if(lString.size() >= 2)
			for(int i = 0;i<lString.size()-1;++i) {
			fromQuery += dao.JoinTable(ColumnData.getPredictTableName(lString.get(i)),);
		else
			fromQuery += lString.get(0) + " ";
		
		String whereQuery = "where dataYear >= " + String.valueOf(startYear) + " AND " + "dataYear >= " + String.valueOf(endYear);
		
		ArrayList<BaseVO<Float>> dataList = dao.getFloatData(selectQuery, fromQuery, whereQuery);
		
		ArrayList<JSONObject> convertData = new ArrayList<JSONObject>();
		JSONObject objMap = new JSONObject();

		for(int i=0;i<dataList.size();++i) {
			objMap.put("year", dataList.get(0).getYear());
			objMap.put("type", dataList.get(0).getIndustryType());
			
			BaseVO<Float> vo = dataList.get(i);
			ArrayList<Float> data = vo.getData();
			JSONObject dataMap = new JSONObject();
			for(int j=0;j<data.size();++j) {

			}
			objMap.put("data", dataList.get(0).getIndustryType());
		}
	}
	
	private void ReformData(String targetInfo){


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

