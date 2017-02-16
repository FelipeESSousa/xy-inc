package br.com.zup.web.heimdall.bean;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Option {

    private String method;
    private String url;
    private Object data;
    private Object headers;


    private Map<String, String> parameters = new HashMap<String, String>();

    public Option() {
    }

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public Object getHeaders() {
		return headers;
	}

	public void setHeaders(Object headers) {
		this.headers = headers;
	}


	public Map<String, String> getParameters() {
		return parameters;
	}

	public void setParameters(Map<String, String> parameters) {
		this.parameters = parameters;
	}
}