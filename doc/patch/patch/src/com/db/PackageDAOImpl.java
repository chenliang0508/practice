package com.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PackageDAOImpl {
	Connection con = null;
	ResultSet rs = null;
	PreparedStatement pstmt = null;
	
	private void closeDB() {
		try {
			if(rs != null){
			    rs.close();
				rs = null;
			}
			if(pstmt != null){
				pstmt.close();
				pstmt = null;
			}
			if(con != null){
				con.close();
				con = null;
			}	
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public List<String> getAllProject() {
		String sql = "SELECT PROJECT_NAME FROM TP_CUL_PACKAGE ORDER BY PROJECT_ORDER";
		List<String> list = new ArrayList<String>();
		try{
			con = DBUtil.getInstance().getConnection();
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()){
				list.add(rs.getString(1));
			}
		} catch (Exception e){
			e.printStackTrace();
		} finally {
		    closeDB();
		}
		return list;
	}
	
	public Project getProjectByName(String name) {
		String sql = "SELECT * FROM TP_CUL_PACKAGE WHERE PROJECT_NAME = ?";
		Project project = new Project();
		try{
			con = DBUtil.getInstance().getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, name);
			rs = pstmt.executeQuery();
			if(rs.next()){
				project.setProjectName(rs.getString(1));
				project.setWebContent(rs.getString(2));
				project.setJavaContent(rs.getString(3));
				project.setJavaParent(rs.getString(4));
				project.setJavaNoCompiler(rs.getString(5));
				project.setClassPath(rs.getString(6));
				project.setSvn(rs.getString(7));
				project.setBranchUrl(rs.getString(8));
				project.setIsMaven(rs.getString(9));
				project.setTargetVer(rs.getString(10));
				project.setSourceVer(rs.getString(11));
			}
		} catch (Exception e){
			e.printStackTrace();
		} finally {
		    closeDB();
		}
		return project;
	}
	
	public static PackageDAOImpl getInstance(){
		return new PackageDAOImpl();
	}

}
