package br.com.zup.negocio.util.enumerator;

/**
 * Enumarator responsavel pelos properties do sistema
 * 
 */
public enum EnumProperties {
    MSG_PROP("msg.properties"),
    TXT_PROP("txt.properties"),
    CONFIG_PROP("zup-config.properties"),
    SERVER_PROP("server.properties");

    private String name;

    private EnumProperties(String s) {
        name = s;
    }

    /**
     * Retorna o valor do Enum
     * 
     * @return String valor
     */
    public String getName() {
        return name;
    }
}
