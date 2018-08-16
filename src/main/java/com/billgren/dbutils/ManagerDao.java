package com.billgren.dbutils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.sql.DataSource;
import com.billgren.beans.Manager;

public class ManagerDao {
	
	private DataSource dataSource;
	
	public ManagerDao(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public boolean managerExists(String email, String password) throws Exception {
		
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		
		try {
			String sql= "SELECT * FROM managers where password=?";
			connection = dataSource.getConnection();
			statement=connection.prepareStatement(sql);
			statement.setString(1, password);
			
			resultSet=statement.executeQuery();
			
			
			return resultSet.first();
			
			
		} finally {
			close(connection, statement, resultSet);
		}
		

	}
	
	private boolean managerExists(String email) throws Exception {
		
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		
		try {
			String sql= "SELECT * FROM managers where email=?";
			connection = dataSource.getConnection();
			statement=connection.prepareStatement(sql);
			statement.setString(1, email);
			
			resultSet=statement.executeQuery();
			
			
			return resultSet.first();
			
			
		} finally {
			close(connection, statement, resultSet);
		}
		

	}
	
	private void close(Connection connection, Statement statement, ResultSet resultSet) {
		
		try {
			if(resultSet != null)
				resultSet.close();
			if(statement != null)
				statement.close();
			if(connection != null)
				connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void updateManagerTeamId(int manId, int teamId) throws Exception {
		
		Connection connection = null;
		PreparedStatement statement = null;
		
		try {
			connection = dataSource.getConnection();
			
			String sql = "update managers " 
						+ "set Teams_id=? "
						+ "where id=?";
			//prepare statement
			statement = connection.prepareStatement(sql);
			//set params
			statement.setInt(1, teamId);
			statement.setInt(2, manId);
			//execute SQL statement
			statement.execute();
			
		} finally {
			close(connection, statement, null);
		}
		
		
	}
	
	public Manager getManager(int id) throws Exception {
		Manager manager;
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			String sql ="select * from managers where id=?";
			connection = dataSource.getConnection();
			statement = connection.prepareStatement(sql);
			statement.setInt(1, id);
			
			resultSet = statement.executeQuery();
			
			if(resultSet.next()) {
				String firstName = resultSet.getString("first_name");
				String lastName = resultSet.getString("last_name");
				String email = resultSet.getString("email");
				String password = resultSet.getString("password");
				manager = new Manager(id, firstName, lastName, email, password);
				
			}
			else {
				throw new Exception("Could not find manager");
			}
			
			
		}finally {
			close(connection, statement, resultSet);
		}
		
		return manager;
	}
	
	public Manager getManager(String email) throws Exception {
		Manager manager;
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			String sql ="select * from managers where email=?";
			connection = dataSource.getConnection();
			statement = connection.prepareStatement(sql);
			statement.setString(1, email);
			
			resultSet = statement.executeQuery();
			
			if(resultSet.next()) {
				int id = resultSet.getInt("id");
				String firstName = resultSet.getString("first_name");
				String lastName = resultSet.getString("last_name");
				//String mail = resultSet.getString("email");
				String password = resultSet.getString("password");
				manager = new Manager(id, firstName, lastName, email, password);
				
			}
			else {
				throw new Exception("Could not find manager");
			}
			
			
		}finally {
			close(connection, statement, resultSet);
		}
		
		return manager;
	}

	public boolean register(Manager manager) throws Exception {
		
		Connection connection = null;
		PreparedStatement statement = null;
		
		
		if(managerExists(manager.getEmail()))
			return false;
		
		try {
			String sql = "insert into managers "
					+ "(first_name, last_name, email, password) "
					+ "values(?, ?, ?, ?)";
			connection = dataSource.getConnection();
			statement=connection.prepareStatement(sql);
			
			//set params
			statement.setString(1, manager.getFirstName());
			statement.setString(2, manager.getLastName());
			statement.setString(3, manager.getEmail());
			statement.setString(4, manager.getPassword());			
			
			statement.execute();
			
			return true;
			
		} finally {
			close(connection, statement, null);
		}
		
		
	}

}
