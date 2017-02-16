package br.com.zup.negocio.exception;

/**
 * @date 21/10/2015
 * @class EnumTypeException
 * @description
 */
public enum EnumTypeException {
    ERROR("danger"), WARN("warning");

    /**
     * @field type
     * @fieldType String
     * @date 21/10/2015
     */
    private String type;

    /**
     * @constructor EnumTypeException
     * @date 21/10/2015
     * @param s
     */
    private EnumTypeException(String s) {
        type = s;
    }

    /**
     * @method getType
     * @date 21/10/2015
     * @returnType String
     * @return
     * @description
     */
    public String getType() {
        return type;
    }
}
