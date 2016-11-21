package ke.co.technovation.constants;

import java.util.HashMap;

public enum EmailTestStatus {
	
	SUCCESS(0L),INVALID_SMTP(1L),AUTHENTICATION_FAILURE(2L),NETWORK_FAILURE(3L),APPLICATION_FAILURE(4L);
	
	private EmailTestStatus(Long code){
		this.code = code;
	}
	
	private final Long code;
	
	public Long getCode() {
		return code;
	}
	
	private static final HashMap<Long, EmailTestStatus> lookup = new HashMap<Long, EmailTestStatus>();
	
	static {
		for (EmailTestStatus status : EmailTestStatus.values()){
			lookup.put(status.getCode(), status);
		}
	}
	
	public static EmailTestStatus get(String status) {
		return lookup.get(status);
	}

}
