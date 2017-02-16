package br.com.zup.web.heimdall.resource;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.apache.commons.io.IOUtils;
import org.jboss.logging.Logger;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import br.com.zup.model.dao.entity.Usuario;
import br.com.zup.negocio.exception.TypeErrorResourceException;
import br.com.zup.negocio.exception.TypeWarnResourceException;
import br.com.zup.web.bean.ServiceResponse;
import br.com.zup.web.heimdall.bean.Option;
import br.com.zup.web.heimdall.util.FileUtilUpload;

import com.google.gson.Gson;

@Path("/proxy")
public class ProxySecurityResource {

    private static final Logger LOG = Logger.getLogger(ProxySecurityResource.class);

    @Context
    private HttpServletRequest request;

    private static final int BUFFER_SIZE = 4096;
    private static final String CODIFICACAO_PADRAO = "UTF-8";

    /**
     * 
     * @param option
     * @return Response
     * @throws Exception
     */
    @POST
    @Produces("multipart/form-data; charset=UTF-8")
    public Response proxy(Option option) throws Exception {
        if ("DELETE".equals(option.getMethod()) || "GET".equals(option.getMethod())) {
            return enviarGetOuDelete(option);
        }
        if ("POST".equals(option.getMethod()) || "PUT".equals(option.getMethod())) {
            return enviarPutOuPost(option);
        }
        return null;
    }

    /**
     * 
     * @param option
     * @return Response
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    private Response enviarPutOuPost(Option option) throws Exception {

        String response = "";
        Gson gson = new Gson();
        LinkedHashMap<Object, Object> lhmHeaders = new LinkedHashMap<>();
        try {
            URL targetUrl = new URL(option.getUrl());
            HttpURLConnection httpConnection = (HttpURLConnection) targetUrl.openConnection();
            httpConnection.setDoOutput(true);
            httpConnection.setRequestMethod(option.getMethod());
            setarHeaderDoContexto(httpConnection);
            httpConnection.setRequestProperty("Permissoes", permissoesDoUsuarioLogado());
            
            if (getCasAssertionPrincipal() != null && getCasAssertionPrincipal().getIdUsuario() != null){
            	httpConnection.setRequestProperty ("idUsuario", getCasAssertionPrincipal().getIdUsuario().toString());
            }

            // Caso envie o atributo Headers, adicionar o conteudo
            if (option.getHeaders() != null) {
                lhmHeaders = (LinkedHashMap<Object, Object>) option.getHeaders();
                Iterator<?> it = lhmHeaders.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry<String, String> pairs = (Map.Entry<String, String>) it.next();
                    httpConnection.setRequestProperty(pairs.getKey(), pairs.getValue());
                }
            }
            // Abri saida de dados para o data
            OutputStream outputStream = httpConnection.getOutputStream();
            Writer writer = new OutputStreamWriter(outputStream, CODIFICACAO_PADRAO);

            //  if Caso utilizado transformer request para enivar dados como form
            //  else Caso nao utilize transformer request, realiza o post normalente
            if (option.getHeaders() != null
                    && lhmHeaders.containsKey("Content-Type")
                    && ("application/x-www-form-urlencoded".equals(lhmHeaders.get("Content-Type")) || "application/x-www-form-urlencoded; charset=utf-8"
                            .equals(lhmHeaders.get("Content-Type")))) {
                LinkedHashMap<?, ?> lhmData = (LinkedHashMap<?, ?>) option.getData();
                Iterator<?> it = lhmData.entrySet().iterator();

                while (it.hasNext()) {
                    Map.Entry<String, String> pairs = (Map.Entry<String, String>) it.next();
                    String encodeValue = URLEncoder.encode(String.valueOf(pairs.getValue()), CODIFICACAO_PADRAO);
                    writer.write(pairs.getKey());
                    writer.write("=");
                    writer.write(encodeValue);
                    writer.write("&");
                }
                writer.flush();
            }else {
                writer.write(gson.toJson(option.getData()));
                writer.flush();
            }

            if (httpConnection.getResponseCode() != 200) {
                lancarMensagemDeExcecao(httpConnection);
            }

            BufferedReader responseBuffer =
                    new BufferedReader(new InputStreamReader(httpConnection.getInputStream(), CODIFICACAO_PADRAO));

            String output;
            while ((output = responseBuffer.readLine()) != null) {
                response += output;
            }

            writer.close();
            outputStream.close();

            responseBuffer.close();
            httpConnection.disconnect();

        } catch (MalformedURLException e) {
        	LOG.error(e.getMessage(), e);
            StackTraceElement[] ps = e.getStackTrace();
            for (int i = 0; i < ps.length; i++) {
                LOG.error(ps[i]);
            }
            throw e;
        } catch (IOException e) {
        	LOG.error(e.getMessage(), e);
            StackTraceElement[] ps = e.getStackTrace();
            for (int i = 0; i < ps.length; i++) {
                LOG.error(ps[i]);
            }
            throw e;
        }
        return Response.ok().entity(response).build();
    }

    @SuppressWarnings("unchecked")
    public Response enviarGetOuDelete(Option option) throws Exception {
        String response = "";
        try {
            URL targetUrl = new URL(option.getUrl());
            HttpURLConnection httpConnection = (HttpURLConnection) targetUrl.openConnection();
            httpConnection.setRequestMethod(option.getMethod());

            setarHeaderDoContexto(httpConnection);
            httpConnection.setRequestProperty("Permissoes", permissoesDoUsuarioLogado());

            // Caso envie o atributo Headers, adicionar o conteudo
            if (option.getHeaders() != null) {
                LinkedHashMap<?, ?> linkedHashMap = (LinkedHashMap<?, ?>) option.getHeaders();
                Iterator<?> it = linkedHashMap.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry<String, String> pairs = (Map.Entry<String, String>) it.next();
                    httpConnection.setRequestProperty(pairs.getKey(), pairs.getValue());
                }
            }

            if (httpConnection.getResponseCode() != 200 && httpConnection.getResponseCode() != 204) {
                lancarMensagemDeExcecao(httpConnection);
            }

            BufferedReader responseBuffer =
                    new BufferedReader(new InputStreamReader(httpConnection.getInputStream(), CODIFICACAO_PADRAO));

            String output;
            while ((output = responseBuffer.readLine()) != null) {
                response += output;
            }

            responseBuffer.close();
            httpConnection.disconnect();

        } catch (MalformedURLException e) {
        	LOG.error(e.getMessage(), e);
            StackTraceElement[] ps = e.getStackTrace();
            for (int i = 0; i < ps.length; i++) {
                LOG.error(ps[i]);
            }
            throw e;
        } catch (IOException e) {
        	LOG.error(e.getMessage(), e);
            StackTraceElement[] ps = e.getStackTrace();
            for (int i = 0; i < ps.length; i++) {
                LOG.error(ps[i]);
            }
            throw e;
        }
        return Response.ok().entity(response).build();
    }

    /**
     * @description Atribui para o RequestProperty do HttpURLConnection o conteudo do Header de
     *              acordo com o http request enviado.
     * @param httpConnection
     */
    @SuppressWarnings("unchecked")
    private void setarHeaderDoContexto(HttpURLConnection httpConnection) {
        Enumeration<String> enames = request.getHeaderNames();
        while (enames.hasMoreElements()) {
            String name = enames.nextElement();
            String value = request.getHeader(name);
            httpConnection.setRequestProperty(name, value);
        }
    }

    /**
     * @description Lanca mensagem de erro em um ServiceResponse para os casos de codigo http seja
     *              diferente de 200.
     * @param httpConnection
     * @throws Exception
     */
    private void lancarMensagemDeExcecao(HttpURLConnection httpConnection) throws Exception {
        Scanner s;
        s = new Scanner(httpConnection.getErrorStream());
        s.useDelimiter("\\Z");
        String response = s.next();
        s.close();
        Gson gson = new Gson();
        ServiceResponse serviceResponse = gson.fromJson(response, ServiceResponse.class);
        if ("warning".equals(serviceResponse.getStatus())) {
            throw new TypeWarnResourceException(serviceResponse.getDescription());
        } else if ("danger".equals(serviceResponse.getStatus())) {
            throw new TypeErrorResourceException(serviceResponse.getDescription());
        } else {
            throw new Exception(serviceResponse.getDescription());
        }
    }
    
    private Usuario getCasAssertionPrincipal(){
        Usuario usuario = (Usuario) request.getSession().getAttribute("usuario"); 
        if(request.getSession().getAttribute("usuario") != null){
            return  usuario;
        }
        return null;
    }

    private String permissoesDoUsuarioLogado() {
        return getCasAssertionPrincipal() != null ? getCasAssertionPrincipal().getNmPerfil() : "";
    }

    /**
     * 
     * @param input
     * @return Response
     * @throws Exception
     */
    @POST
    @Path("/upload")
    @Consumes("multipart/form-data")
    public Response proxyUpload(MultipartFormDataInput input) throws Exception {

        try {
            Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
            String uploadPath = uploadForm.get("uploadPath").get(0).getBodyAsString();

            File file = new File(uploadPath);
            if (!file.exists()) {
                file.mkdirs();
            }

            String fileName = "";
            List<InputPart> inputParts = uploadForm.get("file");
            String nomeArquivo = uploadForm.get("fileName").get(0).getBodyAsString();

            for (InputPart inputPart : inputParts) {
                MultivaluedMap<String, String> header = inputPart.getHeaders();

                fileName = FileUtilUpload.getFileName(header);

                // convert the uploaded file to inputstream
                InputStream inputStream = inputPart.getBody(InputStream.class, null);

                byte[] bytes = IOUtils.toByteArray(inputStream);

                // constructs upload file path
                fileName = uploadPath + nomeArquivo;
                FileUtilUpload.writeFile(bytes, fileName);
                LOG.debug("Done");
            }
            return Response.status(200).entity(fileName).build();

        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            StackTraceElement[] ps = e.getStackTrace();
            for (int i = 0; i < ps.length; i++) {
                LOG.error(ps[i]);
            }
            throw new TypeErrorResourceException();
        }
    }

    /**
     * Esse methodo busca o arquivo xls gerado.
     * 
     * @param urlDecodificada
     * @return Response
     * @throws Exception
     */
    @GET
    @Path("/downloadXls/{url}")
    @Produces("application/vnd.ms-excel")
    public Response proxyBuscarXls(@PathParam("url") String url) throws Exception {
        return proxyBuscarArquivo("xls", url);
    }

    /**
     * Esse methodo busca o arquivo csv gerado.
     * 
     * @param urlDecodificada
     * @return Response
     * @throws Exception
     */
    @GET
    @Path("/downloadCsv/{url}")
    @Produces("application/vnd.ms-excel")
    public Response proxyBuscarCsv(@PathParam("url") String url) throws Exception {
        return proxyBuscarArquivo("csv", url);
    }

    /**
     * 
     * @param url
     * @return Response
     * @throws Exception
     */
    @GET
    @Path("/downloadPdf/{url}")
    @Produces({"application/pdf"})
    public Response proxyBuscarPdf(@PathParam("url") String url) throws Exception {
        return proxyBuscarArquivo("pdfs", url);
    }
    
    /**
     * 
     * @param url
     * @return Response
     * @throws Exception
     */
    @GET
    @Path("/downloadTxt/{url}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response proxyBuscarTxt(@PathParam("url") String url) throws Exception {
        return proxyBuscarArquivo("pdfs", url);
    }

    /**
     * 
     * @param pasta
     * @param url
     * @return Response
     * @throws Exception
     */
    public Response proxyBuscarArquivo(String pasta, String url) throws Exception {
        HttpURLConnection httpConnection = null;
        FileOutputStream outputStream = null;
        InputStream inputStream = null;

        try {
            String urlDecodificada = urlDecode(url);
            URL targetUrl = new URL(urlDecodificada);
            httpConnection = (HttpURLConnection) targetUrl.openConnection();
            httpConnection.setRequestMethod("GET");
            httpConnection.setRequestProperty("Permissoes", permissoesDoUsuarioLogado());

            int responseCode = httpConnection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                String nomeArquivo = "";
                String disposition = httpConnection.getHeaderField("Content-Disposition");

                if (disposition != null) {
                    int index = disposition.indexOf("filename=");
                    if (index > 0) {
                        nomeArquivo = disposition.substring(index + 9, disposition.length());
                    }
                } else {
                    nomeArquivo =
                            urlDecodificada.substring(urlDecodificada.lastIndexOf("/") + 1, urlDecodificada.length());
                }

                inputStream = httpConnection.getInputStream();
                String caminhoDiretorio =  System.getProperty("jboss.home.dir") + File.separator + "conf" + File.separator + "aplicacao"
                		+ File.separator + "heimdall" + File.separator + "exports";
                FileUtilUpload.verifyAndCreateDir(caminhoDiretorio);
                String caminhoArquivoSalvo = caminhoDiretorio + File.separator + "new" + nomeArquivo;
                
                outputStream = new FileOutputStream(caminhoArquivoSalvo);

                int bytesRead = -1;
                byte[] buffer = new byte[BUFFER_SIZE];
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }

                ResponseBuilder response = Response.ok();
                response.entity(new File(caminhoArquivoSalvo));
                response.header("Content-Disposition", disposition);
                return response.build();
            } else {
                LOG.debug("No file to download. Server replied HTTP code: " + responseCode);
            }

        } catch (MalformedURLException e) {
        	LOG.error(e.getMessage(), e);
            StackTraceElement[] ps = e.getStackTrace();
            for (int i = 0; i < ps.length; i++) {
                LOG.error(ps[i]);
            }
            throw e;
        } catch (IOException e) {
        	LOG.error(e.getMessage(), e);
            StackTraceElement[] ps = e.getStackTrace();
            for (int i = 0; i < ps.length; i++) {
                LOG.error(ps[i]);
            }
            throw e;
        } finally {
            if (httpConnection != null) {
                httpConnection.disconnect();
            }
            if (outputStream != null) {
                outputStream.close();
            }

            if (inputStream != null) {
                inputStream.close();
            }
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }

    /**
     * 
     * @param url
     * @return String
     */
    private String urlDecode(String url) {
        String urlDecodificada = url.replace("*", "/");
        return urlDecodificada;
    }
}
