package com.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class CommonDAO {
	
	private Connection con;
	private PreparedStatement pstmt;
	private DataSource dataFactory;
	
	public CommonDAO() {
		try {
			Context ctx = new InitialContext();
			Context envContext = (Context) ctx.lookup("java:/comp/env");
			dataFactory = (DataSource) envContext.lookup("jdbc/Oracle11g");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<BaseVO<Integer>> getCountData(){
		return getIntData("*", "PredictIndustryCountData", ColumnData.countColumns);
	}
	
	private ArrayList<BaseVO<Integer>> getIntData(String column, String table, String[] ColumnArr) {
		ArrayList<BaseVO<Integer>> container = new ArrayList<BaseVO<Integer>>();
		try {
			con = dataFactory.getConnection();
			String query = "select "+ column +" from " + table;
			System.out.println("prepareStatement" + query);
			
			pstmt = con.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int id = rs.getInt(ColumnData.commonColumns[0]);// -> string?
				int year = rs.getInt(ColumnData.commonColumns[1]);// -> string?
				String industryType = rs.getString(ColumnData.commonColumns[2]);

				BaseVO<Integer> vo = new BaseVO<Integer>();
				vo.setId(id);
				vo.setYear(year);
				vo.setIndustryType(industryType);
				
				int d;
				ArrayList<Integer> dataList = new ArrayList<Integer>();
				for(int i=0; i<ColumnArr.length; ++i) {
					d = rs.getInt(ColumnArr[i]);
					dataList.add(d);
				}
				vo.setData(dataList);
				
				container.add(vo);
			}
			
			rs.close();
			pstmt.close();
			con.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return container;
	}
	private ArrayList<BaseVO<Float>> getFloatData(String column, String table, String[] ColumnArr) {
		ArrayList<BaseVO<Float>> container = new ArrayList<BaseVO<Float>>();
		try {
			con = dataFactory.getConnection();
			String query = "select "+ column +" from " + table;
			System.out.println("prepareStatement" + query);
			
			pstmt = con.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int id = rs.getInt(ColumnData.commonColumns[0]);// -> string?
				int year = rs.getInt(ColumnData.commonColumns[1]);// -> string?
				String industryType = rs.getString(ColumnData.commonColumns[2]);

				BaseVO<Float> vo = new BaseVO<Float>();
				vo.setId(id);
				vo.setYear(year);
				vo.setIndustryType(industryType);
				
				float d;
				ArrayList<Float> dataList = new ArrayList<Float>();
				for(int i=0; i<ColumnArr.length; ++i) {
					d = rs.getInt(ColumnArr[i]);
					dataList.add(d);
				}
				vo.setData(dataList);
				
				container.add(vo);
			}
			
			rs.close();
			pstmt.close();
			con.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return container;
	}
	
	
	public void delMember(String id, String table) {
		try {
			con = dataFactory.getConnection();
			
			String query = "delete from " + table;
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
