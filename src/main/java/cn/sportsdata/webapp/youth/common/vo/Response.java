package cn.sportsdata.webapp.youth.common.vo;

import org.json.JSONObject;


public class Response {
	
	private boolean status;
	private int errorCode;
	private String errorMessage;
	private Object result;
	private int objId;

	private Response() {}

	public static Response toSussess(Object result) {
		Response o = new Response();
		o.setStatus(true);
		o.setResult(result);
		return o;
	}

	public static Response toSussess(Object result, String errorMessage) {
		Response o = new Response();
		o.setStatus(true);
		o.setResult(result);
		o.setErrorMessage(errorMessage);
		return o;
	}

	public static Response toFailure(int errorCode, String errorMessage) {
		Response o = new Response();
		o.setStatus(false);
		o.setErrorCode(errorCode);
		o.setErrorMessage(errorMessage);
		return o;
	}

	public boolean getStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	public int getObjId() {
		return objId;
	}

	public void setObjId(int objId) {
		this.objId = objId;
	}
	
	public String toString() {
		JSONObject obj = new JSONObject();
		obj.put("status", status);
		obj.put("errorCode", errorCode);
		obj.put("errorMessage", errorMessage);
		obj.put("result", result);
		obj.put("objId", objId);
		return obj.toString();
	}
}
