package br.com.zup.web.exception.provider;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import br.com.zup.negocio.exception.TypeWarnResourceException;


@Provider
public class TypeWarnResourceExceptionMapper extends ExceptionMapperBase implements ExceptionMapper<TypeWarnResourceException>{

    @Context
    HttpServletRequest request;
    
	@Override
	public Response toResponse(TypeWarnResourceException exception) {
		return process(Response.Status.GONE, "warning", exception, request);
	}

}
