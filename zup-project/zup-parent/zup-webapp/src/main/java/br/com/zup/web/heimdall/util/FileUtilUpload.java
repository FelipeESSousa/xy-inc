package br.com.zup.web.heimdall.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.ws.rs.core.MultivaluedMap;

import org.jboss.logging.Logger;



public class FileUtilUpload {
    
	private static final Logger LOG = Logger.getLogger(FileUtilUpload.class);
    
	private FileUtilUpload(){
        
    }
    
	/**
	 * Get uploaded filename, is there a easy way in RESTEasy?
     * header sample
     * {
     * 	Content-Type=[image/png], 
     * 	Content-Disposition=[form-data; name="file"; filename="filename.extension"]
     * }
     *
     * @param header
     * @return
     * @throws Exception
     */
    public static String getFileName(MultivaluedMap<String, String> header) throws Exception{
        try{
        String[] contentDisposition = header.getFirst("Content-Disposition").split(";");

        for (String filename : contentDisposition) {
            if (filename.trim().startsWith("filename")) {

                String[] name = filename.split("=");

                 
                return name[1].trim().replaceAll("\"", "");
            }
        }
        return "unknown";
        }catch (Exception e) {
            StackTraceElement[] ps = e.getStackTrace();
            for(int i = 0; i < ps.length; i++){
                LOG.error(ps[i]);
            }
            throw e;
        }
    }

    /**
     * Escreve o arquivo gerado
     * @param content
     * @param filename
     * @throws IOException
     */
    public static void writeFile(byte[] content, String filename) throws IOException {
        try{
        File file = new File(filename);

        if (!file.exists()) {
            file.createNewFile();
        }

        FileOutputStream fop = new FileOutputStream(file);

        fop.write(content);
        fop.flush();
        fop.close();
        }catch (IOException e) {
        	LOG.error(e.getMessage(), e);
            StackTraceElement[] ps = e.getStackTrace();
            for(int i = 0; i < ps.length; i++){
                LOG.error(ps[i]);
            }
            throw e;
        }
    }
    
    /**
     * Cria o diretorio do arquivo caso nao exista.
     * @param String pathDir
     */
    public static void verifyAndCreateDir(String pathDir) throws Exception{
    	File f = null;
    	try {
    		f = new File(pathDir);
    		if(!f.exists()){
    			f.mkdirs();
    		}			
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
            throw e;
		}
	}
   
}
