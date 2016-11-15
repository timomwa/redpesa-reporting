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

import ke.co.technovation.ejb.HourToHourEJBI;
@WebServlet("/hourtohour")
public class HourToHourComparison  extends HttpServlet {
	
	@EJB
	private HourToHourEJBI hourtoHourEJB;
	
	private Logger logger = Logger.getLogger(getClass());
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		PrintWriter pw = resp.getWriter();
		
		try{
			String hour_to_hour = hourtoHourEJB.getStats();
			
			logger.info("\n\n\n stats-> "+hour_to_hour+"\n\n\n");
			
			
			resp.setContentType("application/json");
			pw.println(hour_to_hour);
			
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}finally{
			try{
				if(pw!=null)
					pw.close();
			}catch(Exception e){
				logger.error(e.getMessage(), e);
			}
		}
	}

}
