package br.com.zup.negocio.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.jboss.logging.Logger;

/*
 * Classe criada com utilidade de codificar uma palavra para criptografia md5.
 */
public class Md5 {
	private static final Logger LOGGER = Logger.getLogger(Md5.class);

    private static String convert(String value){  
        String val = "";  
        MessageDigest md = null;  
        try {  
            md = MessageDigest.getInstance("MD5");  
        } catch (NoSuchAlgorithmException e) {  
            LOGGER.error(e.getMessage(),e); 
        }  
        BigInteger hash = new BigInteger(1, md.digest(value.getBytes()));  
        val = hash.toString(16);           
        return val;  
    }


    public static String encriptSenha(String senha){
        
        String senhaEncript = senha;
        for(int i = 0; i <= 35; i++){
            senhaEncript += convert(senhaEncript);
        }
        return senhaEncript.substring(senha.length());
    }
}
