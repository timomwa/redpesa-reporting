package ke.co.technovation.report;

import java.io.IOException;
import java.io.PrintWriter;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ke.co.technovation.ejb.OutflowStatsEJBI;

@WebServlet("/outflow/daytoday")
public class OutflowStats  extends HttpServlet{
	
	private Logger logger = Logger.getLogger(getClass());

	/**
	 * 
	 */
	private static final long serialVersionUID = 7462831487561749724L;
	
	@EJB
	private OutflowStatsEJBI outflowStatsEJB;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		PrintWriter pw = null;
		
		try{
			
			pw = resp.getWriter();
			
			String response_ = "";
			
			String stats = req.getParameter("stats");
			
			if(stats.equalsIgnoreCase("sameday")){
				
				response_ = outflowStatsEJB.getDayilyStats();
				
			}else if(stats.equalsIgnoreCase("weekly")){
				
				response_ = outflowStatsEJB.getWeeklyStats();
				
			}
			
			resp.setContentType("application/json");
			pw.println(response_);
			
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}finally{
			try{
				pw.close();
			}catch(Exception e){}
		}
		
	}
	
	
	

}
