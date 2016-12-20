package ke.co.technovation.ejb;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Init;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.HttpMethod;

import org.apache.log4j.Logger;

import ke.co.technovation.dao.SMSDAOI;
import ke.co.technovation.entity.ProcessingStatus;
import ke.co.technovation.entity.SMS;
import ke.co.technovation.entity.SMSConfig;
import ke.co.technovation.httputils.GenericHTTPClient;
import ke.co.technovation.httputils.GenericHTTPParam;
import ke.co.technovation.httputils.GenericHttpResp;

@Stateless
@Remote(OutgoingSMSEJBI.class)
public class OutgoingSMSEJBImpl implements OutgoingSMSEJBI {
	
	private Logger logger = Logger.getLogger(getClass());
	
	@Inject
	private SMSDAOI smsDAOI;
	
	private GenericHTTPClient httpclient;
	private GenericHTTPParam generic_http_parameters;
	
	@EJB
	private SMSConfigEJBI smsconfig;
	
	private Map<String,String> headerParams;
	
	
	private Map<String,String> initParams(){
		
		SMSConfig config = smsconfig.getFirstConfig();
		
		if(config!=null){
			 headerParams = new HashMap<String, String>();
			 headerParams.put("username", config.getUsername());
			 headerParams.put("password", config.getPassword());
			 headerParams.put("apiKey", config.getApiKey());
			 headerParams.put("Content-Type", "application/json");
		}
		
		return headerParams;
	}
	
	
	
	@PostConstruct
	@Init
	public void init(){
		
		try {
			initParams();
			httpclient = new GenericHTTPClient("http");
			generic_http_parameters = new GenericHTTPParam();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	
	@Override
	public GenericHttpResp sendSMS(SMS sms){
		
		GenericHttpResp resp = null;
		
		try{
			SMSConfig config = smsconfig.getFirstConfig();
			if(config==null)
				throw new Exception("No configuration ");
			 String xml = config.getPayloadTemplate();
			 xml = xml.replaceAll("\\$\\{MSISDN\\}", sms.getMsisdn());
			 xml = xml.replaceAll("\\$\\{SMS\\}", sms.getSms());
			 
			 generic_http_parameters.setStringentity( xml );
			 generic_http_parameters.setHttpmethod(HttpMethod.POST);
			 generic_http_parameters.setHeaderParams(headerParams);
			 generic_http_parameters.setUrl( config.getApiURL() );
			 try{
				resp = httpclient.call(generic_http_parameters);
			 }catch(Exception exp){
				logger.error(exp.getMessage(), exp);
			 }

			 logger.info("RESP_CODE :  "+resp.getResp_code());
			 logger.info("RESP_BODY :  "+resp.getBody());
			 ProcessingStatus status = ProcessingStatus.PROCESSED_IN_ERROR;
			 if(resp.getResp_code()==201){
				 status = ProcessingStatus.PROCESSED_SUCCESSFULLY;
			 }
			 
			 sms.setStatus(status);
			 sms.setResp(resp.getBody());
			 sms.setResp_status(resp.getResp_code());
			 
			 smsDAOI.save(sms);
			 
			
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}
		
		return resp;
	}

}
