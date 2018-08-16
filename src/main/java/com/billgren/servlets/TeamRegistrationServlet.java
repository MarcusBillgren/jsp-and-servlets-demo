package com.billgren.servlets;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.billgren.beans.Team;
import com.billgren.dbutils.ManagerDao;
import com.billgren.dbutils.TeamDao;

/**
 * Servlet implementation class TeamRegistrationServlet
 */
public class TeamRegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Resource(name="jdbc/build_a_team")
	private DataSource dataSource;
	
	private TeamDao teamDao;
	private ManagerDao managerDao;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TeamRegistrationServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	public void init() throws ServletException {
		try {
			teamDao = new TeamDao(dataSource);
			managerDao = new ManagerDao(dataSource);
		}catch (Exception e) {
			throw new ServletException();
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			String currentCommand = request.getParameter("command");
			//if the command is missing, the default to return to main page
			if(currentCommand == null)
				currentCommand= "MAIN";
			
			switch (currentCommand) {
				case "MAIN":
					main(request,response);
					break;
			case "REGISTER":
				registerTeam(request, response);
				default:
				break;
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	private void main(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		
	}

	private void registerTeam(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String teamName = request.getParameter("teamName");
		int manID = Integer.parseInt(request.getParameter("managerId"));
		Team team = new Team(teamName);
		
		if(teamDao.addTeam(team)) {
			Team t = teamDao.getTeamByName(teamName);
			//Set team id in managers database
			managerDao.updateManagerTeamId(manID, t.getId());
			request.setAttribute("manId", manID);
			request.setAttribute("team", t);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/home.jsp");
			dispatcher.forward(request, response);
		}
		else {
			response.sendRedirect(request.getContextPath()+"team-registration.jsp");
		}
	}

}
