package ke.co.technovation.entity;

import java.util.HashMap;

public enum AccountStatus {
	
	NEW(0L),ACTIVE(1L),WAITING_ACTIVATION(2L),SUSPENDED(3L),DELETED(4L);
	
	private AccountStatus(Long code){
		this.code = code;
	}
	
	private final Long code;
	
	public Long getCode() {
		return code;
	}
	
	private static final HashMap<Long, AccountStatus> lookup = new HashMap<Long, AccountStatus>();
	
	static {
		for (AccountStatus status : AccountStatus.values()){
			lookup.put(status.getCode(), status);
		}
	}
	
	public static AccountStatus get(String status) {
		return lookup.get(status);
	}
}
