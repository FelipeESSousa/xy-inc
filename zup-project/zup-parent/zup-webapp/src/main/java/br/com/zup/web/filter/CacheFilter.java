package br.com.zup.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter("*")
public class CacheFilter implements Filter {

    public CacheFilter() {}

	public void destroy() {}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String page = httpRequest.getRequestURI();
		
		if (!page.contains("fontawesome-webfont.eot")){
			httpResponse.setHeader("Expires", "-1"); 
			httpResponse.setHeader("Cache-Control", "no-store, no-cache, must-revalidate, post-check=0, pre-check=0"); 
			httpResponse.setHeader("Pragma", "no-cache"); 
		}
		
		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {}

}
