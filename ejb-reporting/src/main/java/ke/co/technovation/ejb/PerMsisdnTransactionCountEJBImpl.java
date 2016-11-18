package ke.co.technovation.ejb;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
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

import ke.co.technovation.dao.MpesaInDAOI;
import ke.co.technovation.dto.MsisdnTransactionsDTO;
import ke.co.technovation.dto.QueryDTO;
import ke.co.technovation.dto.Transactions;

@Stateless
public class PerMsisdnTransactionCountEJBImpl implements PerMsisdnTransactionCountEJBI {

	private Logger logger = Logger.getLogger(getClass());
	
	@Inject
	private MpesaInDAOI mpesaDAOI;
	
	@Override
	public List<MsisdnTransactionsDTO> getTransactions(QueryDTO queryDTO){
		List<MsisdnTransactionsDTO> transactions = mpesaDAOI.getTransactions(queryDTO);
		return transactions;
	}
	
	
	@Override
	public ByteArrayOutputStream generatePDF(QueryDTO queryDTO) throws Exception{
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();;
		Transactions transactions_root = new Transactions();
		transactions_root.setSearchString(queryDTO.getMsisdn());
		List<MsisdnTransactionsDTO> transactions = getTransactions(queryDTO);
		transactions_root.setTransactions(transactions);
		
		String marshalled_str = null;
		
		try {
			marshalled_str = marshall(transactions_root, Transactions.class);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		StringReader stringReader = new StringReader(marshalled_str);
		StreamSource xmlSource = new StreamSource(stringReader);
		InputStream xsltFile = this.getClass().getClassLoader().getResourceAsStream(ReportSampleEJBImpl.REPORT_FOLDER+"per_msisdn_tx_template.xsl");
		
		FopFactory fopFactory = FopFactory.newInstance(new File(".").toURI());
		FOUserAgent foUserAgent = fopFactory.newFOUserAgent();
		Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, out);
		TransformerFactory factory = TransformerFactory.newInstance();
		Transformer transformer = factory.newTransformer(new StreamSource(xsltFile));
		Result res = new SAXResult(fop.getDefaultHandler());

	    // Start XSLT transformation and FOP processing
	    // That's where the XML is first transformed to XSL-FO and then 
	    // PDF is created
	    transformer.transform(xmlSource, res);
		
		return out;
	}


	private String marshall(Object data, Class<?> class1) throws Exception {
		String xml_string = "";
		java.io.StringWriter stringWriter = new java.io.StringWriter();
        
		try{
			
			JAXBContext context = JAXBContext.newInstance(class1);
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
		    marshaller.marshal(data, stringWriter);  
	        xml_string = stringWriter.toString();
	        
		}catch(Exception e){
			throw e;
		}finally{
			try{
				if(stringWriter!=null)
					stringWriter.close();
			}catch(Exception e){}
		}
		
		return xml_string;
	}
}
