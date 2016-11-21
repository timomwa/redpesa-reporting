package ke.co.technovation.report;

import java.io.IOException;
import java.io.PrintWriter;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
//getaverageDailyRevenue
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ke.co.technovation.ejb.AverageDailyRevenueEJBI;
@WebServlet("/dailyaverage")
public class DailyAverageRevenue extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1178597438943252781L;

	private Logger logger = Logger.getLogger(getClass());
	
	@EJB
	private AverageDailyRevenueEJBI averageDailyRevenueEJB;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		PrintWriter pw = resp.getWriter();
		
		try{
			String averageDaily = averageDailyRevenueEJB.getaverageDailyRevenue();
			resp.setContentType("application/json");
			pw.println(averageDaily);
			
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
