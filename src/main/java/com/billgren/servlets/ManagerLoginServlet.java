package com.billgren.servlets;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.billgren.beans.Manager;
import com.billgren.dbutils.ManagerDao;
import com.billgren.dbutils.TeamDao;


/**
 * Servlet implementation class ManagerLoginServlet
 */
public class ManagerLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	//TODO: create sql user with credentials specified in context.xml
	@Resource(name="jdbc/build_a_team")
	private DataSource dataSource;
	
	private ManagerDao managerDao;
       
    @Override
	public void init() throws ServletException {
		super.init();
		
		//create a manager db util and pass in dataSoruce
		try {
			managerDao = new ManagerDao(dataSource);
		} catch (Exception e) {
			throw new ServletException();
		}
	}

	/**
     * @see HttpServlet#HttpServlet()
     */
    public ManagerLoginServlet() {
        super();
        // TODO Auto-generated constructor stub
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
			case "LOGIN":
				login(request,response);
				break;
			case "MAIN":
				main(request,response);
				break;
			case "REGISTER":
				registerManager(request, response);
			default:
				break;
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	private void registerManager(HttpServletRequest request, HttpServletResponse response) throws Exception {
		

		//get variabels
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		Manager manager = new Manager(firstName, lastName, email, password);
		
		if(managerDao.register(manager)) {
			Manager theManger = managerDao.getManager(email);
			request.setAttribute("manager", theManger);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/team-registration.jsp");
			dispatcher.forward(request, response);
		}
		else {
			System.out.println("BLÄÄÄÄÄÄ");
			//send back to signup if not completed
			response.sendRedirect(request.getContextPath()+ "/sign-up.html");	
		}
		
		
	}

	private void main(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.sendRedirect(request.getContextPath() +"/index.html");
		
	}

	private void login(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		if(managerDao.managerExists(email, password)) {
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("/home.jsp");
			dispatcher.forward(request, response);
		}
		else{
			response.sendRedirect(request.getContextPath() + "/index.html");
		}
		
		
	}

}
