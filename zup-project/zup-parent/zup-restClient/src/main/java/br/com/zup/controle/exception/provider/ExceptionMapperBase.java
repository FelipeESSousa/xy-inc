package br.com.zup.controle.exception.provider;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import br.com.zup.controle.bean.ServiceResponse;


public abstract class ExceptionMapperBase
{

    private static final Set<String> ACCEPTEDS = new HashSet<>(Arrays.asList("application/xml", "application/json"));

    private static final String DEFAULT_TYPE = "application/json";

    protected String cleanAccept(String accept)
    {
        String mediaTypes = accept.split(";")[0];

        for (String mediaType : mediaTypes.split(",")) {
            if (ACCEPTEDS.contains(mediaType.trim())) {
                return mediaType.trim();
            }
        }
        return DEFAULT_TYPE;
    }

    protected String getAcceptedType(HttpServletRequest request)
    {

        if (request == null) {
            //log.error("Fail to inject HttpServletRequest. Using default content-type: " + DEFAULT_TYPE);
            return DEFAULT_TYPE;
        }

        String result = request.getHeader("accept");
        if (result == null) {
            //log.info("Value defined for 'accept' header field. Using default content-type: " + DEFAULT_TYPE);
            return DEFAULT_TYPE;
        }

        return cleanAccept(result);
    }

    protected Response process(Response.Status status, String message, Exception exception, HttpServletRequest request)
    {
        ResponseBuilder rb = Response.status(status).entity(new ServiceResponse(status.getStatusCode(), message, exception.getMessage()))
                .type(getAcceptedType(request));
        return rb.build();
    }

    protected Response process(int status, String message, String description, HttpServletRequest request)
    {
        ResponseBuilder rb = Response.status(status).entity(new ServiceResponse(status, message, description)).type(getAcceptedType(request));
        return rb.build();
    }
}