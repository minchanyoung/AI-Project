package com.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class commonDAO {

	private Connection con;
	private PreparedStatement pstmt;
	private DataSource dataFactory;
	
	public commonDAO() {
		try {
			Context ctx = new InitialContext();
			Context envContext = (Context) ctx.lookup("java:/comp/env");
			dataFactory = (DataSource) envContext.lookup("jdbc/Oracle11g");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<dev2VO> listMembers() {
		List<dev2VO> list = new ArrayList<dev2VO>();
		try {
			con = dataFactory.getConnection();
			String query = "select * from t_member";
			System.out.println("prepareStatement" + query);
			
			pstmt = con.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				String id = rs.getString("id");
				String pwd = rs.getString("pwd");
				String name = rs.getString("name");
				String email = rs.getString("email");
				Date joinDate = rs.getDate("joinDate");
				
				dev2VO vo = new dev2VO();
				vo.setId(id);
				vo.setPwd(pwd);
				vo.setName(name);
				vo.setEmail(email);
				vo.setJoinDate(joinDate);
				
				list.add(vo);
			}
			
			rs.close();
			pstmt.close();
			con.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	public void addMember(dev2VO dev2VO) {
		try {
			con = dataFactory.getConnection();
			List<String> data = dev2VO.getAll();
			
			String query = "insert into dev2Data";
			query += "(id, companyCount, workerCount, ownerMaleRate, ownerFemaleRate, singlePropCompanyRate, multiBusinessCompanyRate, ";
			query += "U1D5CompanyRate, U5D10CompanyRate, U10D20CompanyRate, U20D50CompanyRate, U50D100CompanyRate, U100D300CompanyRate, U300CompanyRate, ";
			query += "workerMaleRate, workerFemaleRate, singlePropWorkerRate, multiBusinessWorkerRate, selfEmpFamilyWorkerRate, fulltimeWorkerRate, ";
			query += "dayWorkerRate, etcWorkerRate, U1D5WorkerRate, U5D10WorkerRate, U10D20WorkerRate, U20D50WorkerRate, U50D100WorkerRate, ";
			query += "U100D300WorkerRate, U300WorkerRate, avgAge, avgServYear, avgWorkDay, avgTotalWorkTime, avgRegularWorkDay, avgOverWorkDay, ";
			query += "avgSalary, avgFixedSalary, avgOvertimeSalary, avgBonusSalary)";
			query += " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			
			System.out.println("prepareStatement" + query);
			
			pstmt = con.prepareStatement(query);
			
			for(int i =0;i<data.size();++i) {
				pstmt.setString(i+1, data.get(i));
			}
			
			pstmt.executeUpdate();
			pstmt.close();
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void delMember(String id) {
		try {
			con = dataFactory.getConnection();
			
			String query = "delete from t_member";
			query += " where id=?";
			
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, id);
			pstmt.executeUpdate();
			pstmt.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
