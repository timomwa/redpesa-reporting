package ke.co.technovation.ejb;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.ejb.Stateless;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.apache.log4j.Logger;

import ke.co.technovation.constants.AppPropertyHolder;

@Stateless
public class ReportSampleEJBImpl implements ReportSampleEJBI {
	
	private static final String REPORT_FOLDER = AppPropertyHolder.REPORT_TEMPLATE_FOLDER + "/";
	
	Logger logger = Logger.getLogger(getClass());

	public ByteArrayOutputStream getSampleReport(){
		
		ByteArrayOutputStream out = null;
		InputStream xsltFile = null;
		InputStream xmltFile = null;
		
		try{
			//File here = new File(".");
			//logger.info("\n\n WE ARE HERE ----> "+here.getAbsolutePath()+"\n\n");
			out = new ByteArrayOutputStream();
			xsltFile = this.getClass().getClassLoader().getResourceAsStream(REPORT_FOLDER+"report_template.xsl");
			xmltFile = this.getClass().getClassLoader().getResourceAsStream(REPORT_FOLDER+"report.xml");
			//File xsltFile = new File(REPORT_FOLDER+"report_template.xsl");
			StreamSource xmlSource = new StreamSource(xmltFile);
			
			FopFactory fopFactory = FopFactory.newInstance(new File(".").toURI());
			FOUserAgent foUserAgent = fopFactory.newFOUserAgent();
			//OutputStream out = new java.io.FileOutputStream("employee.pdf");
			
			Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, out);
			TransformerFactory factory = TransformerFactory.newInstance();
	        Transformer transformer = factory.newTransformer(new StreamSource(xsltFile));

	            // Resulting SAX events (the generated FO) must be piped through to FOP
	        Result res = new SAXResult(fop.getDefaultHandler());

	        // Start XSLT transformation and FOP processing
	        // That's where the XML is first transformed to XSL-FO and then 
	        // PDF is created
	        transformer.transform(xmlSource, res);
	        
		}catch(Exception e){
			logger.error(e.getMessage(),e );
		}finally{
			try {
				if(xmltFile!=null)
					xmltFile.close();
			}catch (Exception e) {
				logger.error(e.getMessage(),e );
			}
			try{
				if(xsltFile!=null)
					xsltFile.close();
			}catch (Exception e) {
				logger.error(e.getMessage(),e );
			}
			try{
				if(out!=null)
					out.close();
			}catch (Exception e) {
				logger.error(e.getMessage(),e );
			}
		}
		
		return out;
	}
}
