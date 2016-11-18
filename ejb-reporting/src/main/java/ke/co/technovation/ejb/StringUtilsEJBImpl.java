package ke.co.technovation.ejb;

import javax.ejb.Stateless;

@Stateless
public class StringUtilsEJBImpl implements StringUtilsEJBI {
	
	private static final String KE_COUNTRY_CODE = "254";
	private static final String ZERO = "0";
	
	@Override
	public String convertToMsisdnFormat(String msisdn) {
		if(msisdn==null || msisdn.isEmpty() || msisdn.trim().length()<1)
			return msisdn;
		if(msisdn.trim().startsWith(KE_COUNTRY_CODE))
			return msisdn;
		if(msisdn.trim().startsWith(ZERO) && (msisdn.trim().length()<10)){
			msisdn = msisdn.substring(1);
			msisdn = KE_COUNTRY_CODE.concat(msisdn);
		}else if((msisdn.trim().length()<10) && !msisdn.trim().startsWith(ZERO)){
			msisdn = KE_COUNTRY_CODE.concat(msisdn);
		}
		return msisdn;
	}

}
