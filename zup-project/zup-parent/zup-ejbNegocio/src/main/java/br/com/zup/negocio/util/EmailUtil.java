package br.com.zup.negocio.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.jboss.logging.Logger;
import org.json.JSONObject;

import br.com.zup.negocio.exception.EnumTypeException;
import br.com.zup.negocio.exception.NegocioException;

/**
 * @description Classe Utils responsavel por prover metodos de para envio de email
 */
public class EmailUtil {

    private static final Logger LOGGER = Logger.getLogger(EmailUtil.class);
    private static final String CODIFICACAO_PADRAO = "UTF-8";
    private static final String MODELO_EMAIL_SERVICE = "/modeloEmails/pesquisarPorNome";
    private static final String URL_SUITE_CORP_CONF = "URL_SUITE_CORP";

    private EmailUtil(){
        super();
    }

    /**
     * Metodo para buscar o email principal pelo id de uma pessoa
     * 
     * @param id
     * @return String
     * @throws NegocioException
     */
    public static String getPessoaEmailPrincipal(Long id) throws NegocioException{
        return enviarGet(PropertiesLoad.getValueFromConfig(URL_SUITE_CORP_CONF) + "/pessoaEmail/buscarEmailPrincipal/", id.toString());
    }

    /**
     * Metodo para buscar o texto do modelo de email a partir do nome do modelo de email
     * 
     * @param nmModeloEmail
     * @return String
     * @throws NegocioException
     */
    public static String getTextoModeloEmail(String nmModeloEmail) throws NegocioException{
        try{
            String modeloEmail = EmailUtil.enviarPost(PropertiesLoad.getValueFromConfig(URL_SUITE_CORP_CONF) + MODELO_EMAIL_SERVICE, "\"" + nmModeloEmail + "\"", true);
            return new JSONObject(modeloEmail).getString("txHtmlModeloEmail");
        }
        catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * Metodo para enviar um email
     * 
     * @param dateFormat
     * @param date
     * @param assuntoEmail
     * @param textoEmail
     * @param emailDestinatario
     * @throws NegocioException
     */
    public static void enviarEmail(Date date, String assuntoEmail, String textoEmail,
            String emailDestinatario) throws NegocioException{
        String mensagemEmail = montarMensagemEmail(date, assuntoEmail, textoEmail, emailDestinatario);
        enviarPost(PropertiesLoad.getValueFromConfig(URL_SUITE_CORP_CONF) + "/email/envio", mensagemEmail, false);
    }

    /**
     * @param dateFormat
     * @param dataAtual
     * @param assuntoEmail
     * @param textoEmail
     * @param emailDestinatario
     * @return String
     */
    private static String montarMensagemEmail(Date dataAtual, String assuntoEmail, String textoEmail,
            String emailDestinatario) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        JSONObject mensagemEmail = new JSONObject();
        mensagemEmail.put("idAplicacao", Long.valueOf(PropertiesLoad.getValueFromConfig("ID_APLICACAO")));
        mensagemEmail.put("nmAssunto", assuntoEmail);
        mensagemEmail.put("nmRemetente", PropertiesLoad.getValueFromConfig("NOME_REMETENTE"));
        mensagemEmail.put("nmDestinatario", emailDestinatario);
        mensagemEmail.put("dhPrevistoEnvioEmail", dateFormat.format(dataAtual));
        mensagemEmail.put("dhCadastro", dateFormat.format(dataAtual));
        mensagemEmail.put("stAtivo", "S");
        mensagemEmail.put("txMensagemEmail", textoEmail);
        mensagemEmail.put("smtpEmail", new JSONObject().put("nmUsuarioSmtp", PropertiesLoad.getValueFromConfig("EMAIL_REMETENTE")));
        System.out.println(new JSONObject().put("nmUsuarioSmtp", PropertiesLoad.getValueFromConfig("EMAIL_REMETENTE")));
        System.out.println(PropertiesLoad.getValueFromConfig("EMAIL_REMETENTE"));
        System.out.println(mensagemEmail);
        return mensagemEmail.toString();
    }
    

    /**
     * 
     * @param url
     * @param data
     * @throws NegocioException 
     * @throws Exception
     */
    private static String enviarPost(String url, String data, Boolean comResposta) throws NegocioException{
        URL targetUrl;
        try {
            targetUrl = new URL(url);
            HttpURLConnection httpConnection = (HttpURLConnection) targetUrl.openConnection();
            httpConnection.setDoOutput(true);
            httpConnection.setRequestMethod("POST");
            httpConnection.setRequestProperty("Content-Type", "application/json; charset=utf-8");

            OutputStream outputStream = httpConnection.getOutputStream();
            Writer writer = new OutputStreamWriter(outputStream, "UTF-8");
            writer.write(data);
            writer.flush();

            if (httpConnection.getResponseCode() != 200 && httpConnection.getResponseCode() != 204) {
                LOGGER.info("Falha ao realizar request. "+ "Código : " + httpConnection.getResponseCode()
                        + " Mensagem: " + httpConnection.getResponseMessage()
                        + " URL: " + httpConnection.getURL());
                throw new NegocioException(httpConnection.getResponseMessage(), EnumTypeException.ERROR);
            }
            String output;
            String response = "";
            if(comResposta){
                BufferedReader br = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
                while ((output = br.readLine()) != null) {
                    response += output;
                }
                br.close();
            }
            writer.close();
            outputStream.close();
            httpConnection.disconnect();

            return response;
        } catch (Exception e) {
            throw new NegocioException(e.getMessage(), e, EnumTypeException.ERROR);
        }
    }


    /**
     * @method enviarGet
     * @date 25/11/2015
     * @returnType String
     * @param url
     * @param data
     * @return
     * @throws IOException
     * @description 
     */
    private static String enviarGet(String url, String data) throws NegocioException{
        try {
            String response = "";
            URL targetUrl = new URL(url + data);
            HttpURLConnection httpConnection = (HttpURLConnection) targetUrl.openConnection();
            httpConnection.setRequestMethod("GET");
            httpConnection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            if (httpConnection.getResponseCode() != 200 && httpConnection.getResponseCode() != 204) {
                LOGGER.info("Falha ao realizar request. "+ "Código : " + httpConnection.getResponseCode()
                        + " Mensagem: " + httpConnection.getResponseMessage()
                        + " URL: " + httpConnection.getURL());
            }
            BufferedReader responseBuffer =
                    new BufferedReader(new InputStreamReader(httpConnection.getInputStream(), CODIFICACAO_PADRAO));
            String output;
            while ((output = responseBuffer.readLine()) != null) {
                response += output;
            }
            responseBuffer.close();
            httpConnection.disconnect();
            return response;
        } catch (Exception e){
            throw new NegocioException(e.getMessage(), e, EnumTypeException.ERROR);
        }
    }
}
