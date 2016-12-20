package ke.co.technovation.httputils;

import java.io.Serializable;

public class GenericHttpResp implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6997787223363010902L;

	private int resp_code = 0;
	
	private String body;
	
	private String contenttype;

	public int getResp_code() {
		return resp_code;
	}

	public void setResp_code(int resp_code) {
		this.resp_code = resp_code;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getContenttype() {
		return contenttype;
	}

	public void setContenttype(String contenttype) {
		this.contenttype = contenttype;
	}
	
	

}
