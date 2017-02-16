package br.com.zup.controle.exception.provider;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.jboss.logging.Logger;

import br.com.zup.negocio.exception.TypeErrorResourceException;


@Provider
public class TypeErrorResourceExceptionMapper extends ExceptionMapperBase implements ExceptionMapper<TypeErrorResourceException>{

    @Context
    HttpServletRequest request;

    private static final Logger LOGGER = Logger.getLogger(TypeErrorResourceExceptionMapper.class);
    
	@Override
	public Response toResponse(TypeErrorResourceException exception) {
        LOGGER.error(exception.getMessage(), exception);
		return process(Response.Status.GONE, "danger", exception, request);
	}

}
