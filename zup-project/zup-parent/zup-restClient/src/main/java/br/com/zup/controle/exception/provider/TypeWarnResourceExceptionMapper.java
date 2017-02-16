package br.com.zup.controle.exception.provider;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.jboss.logging.Logger;

import br.com.zup.negocio.exception.TypeWarnResourceException;


@Provider
public class TypeWarnResourceExceptionMapper extends ExceptionMapperBase implements ExceptionMapper<TypeWarnResourceException>{

    @Context
    HttpServletRequest request;
    
    private static final Logger LOGGER = Logger.getLogger(TypeWarnResourceExceptionMapper.class);
    
	@Override
	public Response toResponse(TypeWarnResourceException exception) {
	    LOGGER.warn(exception.getMessage(), exception);
		return process(Response.Status.GONE, "warning", exception, request);
	}

}
