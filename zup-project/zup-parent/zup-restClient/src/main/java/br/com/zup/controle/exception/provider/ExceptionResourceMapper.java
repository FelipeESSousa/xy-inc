package br.com.zup.controle.exception.provider;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.jboss.logging.Logger;


@Provider
public class ExceptionResourceMapper extends ExceptionMapperBase implements ExceptionMapper<Exception>{

    @Context
    HttpServletRequest request;

    private static final Logger LOGGER = Logger.getLogger(ExceptionResourceMapper.class);
    
	@Override
	public Response toResponse(Exception exception) {
		LOGGER.error(exception.getMessage(), exception);
		return process(Response.Status.GONE, "danger", new Exception("Ocorreu um erro no servi&ccedil;o, entre em contato com o Administrador do sistema!"), request);
	}

}
