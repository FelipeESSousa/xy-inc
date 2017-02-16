package br.com.zup.web.exception.provider;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import br.com.zup.negocio.exception.TypeErrorResourceException;


@Provider
public class TypeErrorResourceExceptionMapper extends ExceptionMapperBase implements ExceptionMapper<TypeErrorResourceException>{

    @Context
    HttpServletRequest request;
    
	@Override
	public Response toResponse(TypeErrorResourceException exception) {
		return process(Response.Status.GONE, "danger", exception, request);
	}

}
