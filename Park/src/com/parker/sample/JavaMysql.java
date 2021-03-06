package com.parker.sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// mysql：connection， select, insert, update, delete
public class JavaMysql {

	static class Student {
		private String Id;
		private String Name;
		private String Sex;
		private String Age;

		Student(String Name, String Sex, String Age) {
			this.Id = null; // default
			this.Name = Name;
			this.Sex = Sex;
			this.Age = Age;
		}

		public String getId() {
			return Id;
		}

		public void setId(String Id) {
			this.Id = Id;
		}

		public String getName() {
			return Name;
		}

		public void setName(String Name) {
			this.Name = Name;
		}

		public String getSex() {
			return Sex;
		}

		public void setSex(String Sex) {
			this.Sex = Sex;
		}

		public String getAge() {
			return Age;
		}

		public void setage(String Age) {
			this.Age = Age;
		}
	}

	// 连接mysql
	private static Connection getConn() {
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/sdgp";
		String username = "sdgp";
		String password = "sdgp";
		Connection conn = null;
		try {
			Class.forName(driver); // classLoader,加载对应驱动
			conn = (Connection) DriverManager.getConnection(url, username, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	// insert
	private static int insert(Student student) {
		Connection conn = getConn();
		int i = 0;
		String sql = "insert into students (Name,Sex,Age) values(?,?,?)";
		PreparedStatement pstmt;
		try {
			pstmt = (PreparedStatement) conn.prepareStatement(sql);
			pstmt.setString(1, student.getName());
			pstmt.setString(2, student.getSex());
			pstmt.setString(3, student.getAge());
			i = pstmt.executeUpdate();
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return i;
	}

	// update
	private static int update(Student student) {
		Connection conn = getConn();
		int i = 0;
		String sql = "update students set Age='" + student.getAge() + "' where Name='" + student.getName() + "'";
		PreparedStatement pstmt;
		try {
			pstmt = (PreparedStatement) conn.prepareStatement(sql);
			i = pstmt.executeUpdate();
			System.out.println("resutl: " + i);
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return i;
	}

	// select
	private static Integer getAll() {
		Connection conn = getConn();
		String sql = "select * from students";
		PreparedStatement pstmt;
		try {
			pstmt = (PreparedStatement) conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			int col = rs.getMetaData().getColumnCount();
			System.out.println("============================");
			while (rs.next()) {
				for (int i = 1; i <= col; i++) {
					System.out.print(rs.getString(i) + "\t");
					if ((i == 2) && (rs.getString(i).length() < 8)) {
						System.out.print("\t");
					}
				}
				System.out.println("");
			}
			System.out.println("============================");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	// delete
	private static int delete(String name) {
		Connection conn = getConn();
		int i = 0;
		String sql = "delete from students where Name='" + name + "'";
		PreparedStatement pstmt;
		try {
			pstmt = (PreparedStatement) conn.prepareStatement(sql);
			i = pstmt.executeUpdate();
			System.out.println("resutl: " + i);
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return i;
	}

	public static void main(String args[]) {
		JavaMysql.getAll();
		JavaMysql.insert(new Student("Achilles", "Male", "14"));
		JavaMysql.getAll();
		JavaMysql.update(new Student("Bean", "", "7"));
		JavaMysql.delete("Achilles");
		JavaMysql.getAll();
	}

}
