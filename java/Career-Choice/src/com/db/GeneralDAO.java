package com.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class GeneralDAO {
	
	private Connection con;
	private PreparedStatement pstmt;
	private DataSource dataFactory;
	
	public GeneralDAO() {
		try {
			Context ctx = new InitialContext();
			Context envContext = (Context) ctx.lookup("java:/comp/env");
			dataFactory = (DataSource) envContext.lookup("jdbc/Oracle11g");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 함수만 있고 실적용은 안된 상태이니 디벨롭시킬떄 반드시 같이 사용할 것
	public String JoinTable(String TableA, String[] ColumnsA,String TableB, String[] ColumnsB) {
		String query = "(select ";
		query += "dataId, ";
		query += "dataYear, ";
		query += "industryType, ";
		for(int i=0;i<ColumnsA.length;++i) {
			query += TableA + "." + ColumnsA[i] + ", ";
		}
		for(int i=0;i<ColumnsB.length;++i) {
			query += TableB + "." + ColumnsB[i];
			if(i < ColumnsA.length-1)
				query += ", ";
		}
		query += " from " + TableA + " ";
		query += " join " +  TableB + " using " + "(dataId, dataYear, industryType)";
		
		query += ")";
		System.out.println(query);
		return query;
	}

	public ArrayList<BaseVO<String>> getLegacyData(String table){
		return getLegacyData("*", table, "");
	}
	public ArrayList<BaseVO<String>> getLegacyData(String column, String table){
		return getLegacyData(column, table, "", "");
	}
	public ArrayList<BaseVO<String>> getLegacyData(String column, String table,  String where){
		return getLegacyData(column, table, where, "");
	}
	public ArrayList<BaseVO<String>> getLegacyData(String column, String table, String where, String groupBy){
		ArrayList<BaseVO<String>> container = new ArrayList<BaseVO<String>>();
		try {
			con = dataFactory.getConnection();
			String query = "select "+ column +" from " + table;
//			if(where.isEmpty() == false)
//				query += " where " + where;
//			if(groupBy.isEmpty() == false)
//				query += " groupBy " + groupBy;
			
			System.out.println("prepareStatement: " + query);
			
			pstmt = con.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
			String[] columns = column.split(", ");
			
			while(rs.next()) {
				int year = 0;// -> string?
				String industryType = "";
				
				BaseVO<String> vo = new BaseVO<String>();

				year = rs.getInt("year");// -> string?
				vo.setYear(year);
				industryType = rs.getString("INDUSTRYTYPE");// -> string?
				vo.setIndustryType(industryType);
				String d;
				ArrayList<String> dataList = new ArrayList<String>();
				for(int i=0; i<columns.length; ++i) {
					d = rs.getString(columns[i]);
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
	
	public ArrayList<BaseVO<Integer>> getIntData(){
		return getIntData("RealIndustryCountData");
	}
	public ArrayList<BaseVO<Integer>> getIntData(String table){
		return getIntData("*", table, "", "");
	}

	public ArrayList<BaseVO<Integer>> getIntData(String column, String table){
		return getIntData(column, table, "", "");
	}
	public ArrayList<BaseVO<Integer>> getIntData(String column, String table,  String where){
		return getIntData(column, table, where, "");
	}
	public ArrayList<BaseVO<Integer>> getIntData(String column, String table, String where, String groupBy) {
		ArrayList<BaseVO<Integer>> container = new ArrayList<BaseVO<Integer>>();
		try {
			con = dataFactory.getConnection();
			String query = "select "+ column +" from " + table;
			
			
			if(where.isEmpty() == false)
				query += " where " + where;
			if(groupBy.isEmpty() == false)
				query += " groupBy " + groupBy;
			System.out.println("prepareStatement: " + query);
			
			pstmt = con.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
			String[] columns = column.split(", ");
			
			while(rs.next()) {
				int id = 0;
				int year = 0;// -> string?
				String industryType = "";
				
				for(int i=0; i<3; ++i) {
					String s = columns[i];
					if(columns[i].equals( ColumnData.commonColumns[0]))
						id = rs.getInt(ColumnData.commonColumns[0]);// -> string?
					else if(columns[i].equals( ColumnData.commonColumns[1]))
						year = rs.getInt(ColumnData.commonColumns[1]);// -> string?
					else if(columns[i].equals( ColumnData.commonColumns[2]))
						industryType = rs.getString(ColumnData.commonColumns[2]);// -> string?
				}

				BaseVO<Integer> vo = new BaseVO<Integer>();
				vo.setId(id);
				vo.setYear(year);
				vo.setIndustryType(industryType);
				
				int d;
				ArrayList<Integer> dataList = new ArrayList<Integer>();
				for(int i=0; i<columns.length; ++i) {
					if(columns[i].equals( ColumnData.commonColumns[0]) || columns[i].equals( ColumnData.commonColumns[1]) || columns[i].equals( ColumnData.commonColumns[2]))
						continue;
					d = rs.getInt(columns[i]);
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

	public ArrayList<BaseVO<Float>> getFloatData(){
		return getFloatData("PredictIndustryCountData");
	}
	public ArrayList<BaseVO<Float>> getFloatData(String table){
		return getFloatData("*", table, "");
	}
	public ArrayList<BaseVO<Float>> getFloatData(String column, String table){
		return getFloatData(column, table, "", "");
	}
	public ArrayList<BaseVO<Float>> getFloatData(String column, String table,  String where){
		return getFloatData(column, table, where, "");
	}
	public ArrayList<BaseVO<Float>> getFloatData(String column, String table, String where, String groupBy) {
		ArrayList<BaseVO<Float>> container = new ArrayList<BaseVO<Float>>();
		try {
			con = dataFactory.getConnection();
			String query = "select "+ column +" from " + table;
			
			
			if(where.isEmpty() == false)
				query += " where " + where;
			if(groupBy.isEmpty() == false)
				query += " groupBy " + groupBy;
			System.out.println("prepareStatement: " + query);
			
			pstmt = con.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
			String[] columns = column.split(", ");
			
			while(rs.next()) {
				int id = 0;
				int year = 0;// -> string?
				String industryType = "";
				
				for(int i=0; i<3; ++i) {
//					if(columns[i].equals( ColumnData.commonColumns[0]))
//						id = rs.getInt(ColumnData.commonColumns[0]);// -> string?
					if(columns[i].equals( ColumnData.commonColumns[1]))
						year = rs.getInt(ColumnData.commonColumns[1]);// -> string?
					else if(columns[i].equals( ColumnData.commonColumns[2]))
						industryType = rs.getString(ColumnData.commonColumns[2]);// -> string?
				}

				BaseVO<Float> vo = new BaseVO<Float>();
				vo.setId(id);
				vo.setYear(year);
				vo.setIndustryType(industryType);
				
				float d;
				ArrayList<Float> dataList = new ArrayList<Float>();
				for(int i=0; i<columns.length; ++i) {
					if(columns[i].equals( ColumnData.commonColumns[0]) || columns[i].equals( ColumnData.commonColumns[1]) || columns[i].equals( ColumnData.commonColumns[2]))
						continue;
					d = rs.getFloat(columns[i]);
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
//		try {
//			con = dataFactory.getConnection();
//			
//			String query = "delete from " + table;
//			query += " where id=?";
//			
//			pstmt = con.prepareStatement(query);
//			pstmt.setString(1, id);
//			pstmt.executeUpdate();
//			pstmt.close();
//		}
//		catch (Exception e) {
//			e.printStackTrace();
//		}
	}
	
}
