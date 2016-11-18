package ke.co.technovation.report;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.fop.apps.MimeConstants;
import org.apache.log4j.Logger;

import ke.co.technovation.ejb.ReportSampleEJBI;

@WebServlet("/reports/sample/pdf")
public class ReportSample extends HttpServlet {
	
	@EJB
	private ReportSampleEJBI reportSampleEJBI;
	
	private Logger logger = Logger.getLogger(getClass());
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		ByteArrayOutputStream out = null;
		ServletOutputStream pw = null;
		
		try{
			
			pw = resp.getOutputStream();
			out = reportSampleEJBI.getSampleReport();
			resp.setContentType(MimeConstants.MIME_PDF);
		    resp.setContentLengthLong( out.size() );
		    pw.write(out.toByteArray());
		    pw.flush();
		    
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}finally{
			try{
				if(out!=null)
					out.close();
			}catch(Exception e){
				logger.error(e.getMessage(), e);
			}
			try{
				if(pw!=null)
					pw.close();
			}catch(Exception e){
				logger.error(e.getMessage(), e);
			}
		}
	}

}
