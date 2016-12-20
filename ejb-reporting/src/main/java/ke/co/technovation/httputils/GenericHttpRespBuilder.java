package ke.co.technovation.httputils;


public class GenericHttpRespBuilder {
	
	private static GenericHttpResp instance = new GenericHttpResp();
	
	public  GenericHttpRespBuilder respCode(int respCode_){
		instance.setResp_code(respCode_);
		return this;
	}
	public  GenericHttpRespBuilder body(String body_){
		instance.setBody(body_);
		return this;
	}
	
	public  GenericHttpRespBuilder contenttype(String contenttype){
		instance.setContenttype(contenttype);
		return this;
	}
	
	public GenericHttpResp build(){
		assert instance.getResp_code() >-1;
		return instance;
	}
	public static GenericHttpRespBuilder create() {
		return new GenericHttpRespBuilder();
	}

}
