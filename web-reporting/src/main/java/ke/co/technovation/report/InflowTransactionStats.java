package ke.co.technovation.report;

import java.io.IOException;
import java.io.PrintWriter;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ke.co.technovation.ejb.InflowTransactionStatsEJBI;

import org.apache.log4j.Logger;

@WebServlet("/transactions")
public class InflowTransactionStats extends HttpServlet  {

	/**
	 * 
	 */
	private static final long serialVersionUID = -205458247115472420L;
	
	private Logger logger = Logger.getLogger(getClass());

	
	@EJB
	private InflowTransactionStatsEJBI inflowTrxEJB;

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
				
				response_ = inflowTrxEJB.getDayilyStats();
				
			}else if(stats.equalsIgnoreCase("weekly")){
				
				response_ = inflowTrxEJB.getWeeklyStats();
				
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
