package ke.co.technovation.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//import net.bootsfaces.component.button.Button;
@WebServlet("/testme")
public class HelloWorld extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4293115131085942523L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.getWriter().println(".. Mzuri sana ..");
	}
	
	

}
