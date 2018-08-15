package com.billgren.dbutils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.sql.DataSource;

import com.billgren.beans.Team;


public class TeamDao {
	
private DataSource dataSource;
	
	public TeamDao(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	private boolean teamExists(String name) throws Exception {
		
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		
		try {
			String sql= "SELECT * FROM teams where team_name=?";
			connection = dataSource.getConnection();
			statement=connection.prepareStatement(sql);
			statement.setString(1, name);
			
			resultSet=statement.executeQuery();
			
			
			return resultSet.first();
			
			
		} finally {
			close(connection, statement, resultSet);
		}
		

	}
	
	public boolean addTeam(Team team) throws Exception {
		Connection connection = null;
		PreparedStatement statement = null;
		
		if(teamExists(team.getTeamName()))
			return false;
		
		try {
			String sql="insert into teams (team_name) values (?)";
			connection = dataSource.getConnection();
			statement = connection.prepareStatement(sql);
			statement.setString(1, team.getTeamName());
			
			statement.execute();
			
			return true;
			
			
		} finally {
			close(connection, statement, null);
		}
	}
	
	public Team getTeamByName(String name) throws Exception {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Team team;
		try {

			String sql = "select * from teams where team_name=?";
			connection = dataSource.getConnection();
			statement = connection.prepareStatement(sql);
			statement.setString(1, name);
			
			resultSet = statement.executeQuery();
			
			if(resultSet.next()) {
				int id = resultSet.getInt("id");
				String teamName = resultSet.getString("team_name");
				team = new Team(id, teamName);
			}
			else {
				throw new Exception("No such team exists");
			}
			
			
		} finally {
			close(connection, statement, resultSet);
		}
		
		return team;
		
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

}
