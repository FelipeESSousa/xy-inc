package br.com.zup.negocio.util.enumerator;

import java.io.File;

/**
 * Enumarator responsavel pelos caminhos relativos do sistema
 * 
 */
public enum EnumPath {
	JBOSS_PATH(System.getProperty("jboss.home.dir")),
	CONF_PATH(JBOSS_PATH.getPath() + File.separator + "conf"),
	INTERACAO_PATH(CONF_PATH.getPath() + File.separator + "aplicacao"),
	APP_PATH(INTERACAO_PATH.getPath() + File.separator + "zup"),
	PROPERTIES_PATH(APP_PATH.getPath() + File.separator + "properties"),
	RELATORIOS_PATH(APP_PATH.getPath() +File.separator + "relatorios"),
	RELATORIO_REPORT_PATH(RELATORIOS_PATH.getPath()+File.separator+ "reports"),
	RELATORIO_PDF_PATH(RELATORIOS_PATH.getPath() +File.separator+ "pdfs" ),
	RELATORIO_XLS_PATH(RELATORIOS_PATH.getPath() +File.separator+ "xls"),
	RELATORIO_CSV_PATH(RELATORIOS_PATH.getPath() + File.separator+"csv"),
	ARQUIVO_MENSAGENS_PATH(EnumPath.PROPERTIES_PATH.getPath() + File.separator + EnumProperties.MSG_PROP.getName());;
	
	private String path;
 
	/**
	 * Construtor privado 
	 * 
	 * @param s
	 */
	private EnumPath(String s) {
		path = s;
	}
 
	/**
	 * Retorna o valor do Enum
	 * 
	 * @return String valor
	 */
	public String getPath() {
		return path;
	}
	
}
