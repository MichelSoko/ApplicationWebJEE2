package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class DBConnection implements Servlet {
	
	private ServletConfig config;

	@Override
	public void init(ServletConfig config) throws ServletException {
		this.config = config;

		String url = config.getServletContext().getInitParameter("db-url");
		String login = config.getServletContext().getInitParameter("db-login");
		String passwd = config.getServletContext().getInitParameter("db-passwd");

		try {
			Class.forName("org.postgresql.Driver");
			Connection con = DriverManager.getConnection(url, login, passwd);
			config.getServletContext().setAttribute("db-connection", con);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void destroy() {
	} // do nothing

	@Override
	public ServletConfig getServletConfig() {
		return config;
	}

	@Override
	public String getServletInfo() {
		return "DB Connection";
	}

	@Override
	public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
	} // do nothing

}