package ke.co.technovation.entity;

import java.util.HashMap;

public enum ProcessingStatus {
	
	JUST_IN(3),PROCESSING(2),PROCESSED_IN_ERROR(1),PROCESSED_SUCCESSFULLY(0);
	
	private final int code;
	
	private ProcessingStatus(int code){
		this.code = code;
	}
	
	public int getCode() {
		return code;
	}
	
	
	private static final HashMap<Integer, ProcessingStatus> lookup = new HashMap<Integer, ProcessingStatus>();
	
	static {
		for (ProcessingStatus status : ProcessingStatus.values()){
			lookup.put(status.getCode(), status);
		}
	}
	
	public static ProcessingStatus get(int code) {
		return lookup.get(code);
	}

}
