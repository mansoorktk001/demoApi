package com.demoApi.demoApi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class Database {

	private Connection conn;
	
	Database() {
		try {
			this.conn = DriverManager
					.getConnection("jdbc:mysql://localhost/demoapi?" + "user=root&password=khan1234");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	public ResultSet getStudents() {
		ResultSet res = null;
		try {
			Statement stmt = this.conn.createStatement();
			res = stmt.executeQuery("Select * from students");
			System.out.println(res);
			return res;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}

	public void addStudent(String gpa, String name, String courses) {
		// TODO Auto-generated method stub
		
		System.out.println(gpa+name+courses);
		System.out.println(courses);
		
		String query = "Insert into students values (null,'"+name+"', '"+gpa+"')";
		String query2 = "Select count(*) AS rowCount from students";
		int id;
		
		ResultSet res = null;
		try {
			Statement stmt = this.conn.createStatement();
			stmt.executeUpdate(query);
			res = stmt.executeQuery(query2);
			res.next();
			id = res.getInt("rowCount");
			res.close();
			String query3 = "Insert into courses values('"+courses+"', "+id+")";
			System.out.println(query3);
			stmt.executeUpdate(query3);
			System.out.println(res);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Student getByFullName(String name) {
		// TODO Auto-generated method stub
		ResultSet res = this.getStudents();
		try {
			 
			while(res.next()) {
				System.out.println(res.getString("name"));
				if(res.getString("name").equals(name) ) {
					System.out.println("FOUND");
					Student s = new Student();
					s.setName(res.getString("name"));
					s.setGpa(Float.toString(res.getFloat("gpa")));
					return s;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public int totalSubjects(int id) {
		// TODO Auto-generated method stub
		String qry = "Select count(*) as rowcount from courses where student_id="+id;

		try {
			Statement stmt = this.conn.createStatement();
			ResultSet res = stmt.executeQuery(qry);
			System.out.println(res);
			int rowCount = 0;
			res.next();
			rowCount = res.getInt("rowcount");
			res.close();
			return rowCount;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
}
