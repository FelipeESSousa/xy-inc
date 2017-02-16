package br.com.zup.negocio.exception;

public class TypeWarnResourceException extends Exception {

	private static final long serialVersionUID = 1L;
	public TypeWarnResourceException() {
        super();
    }
    public TypeWarnResourceException(String msg)   {
        super(msg);
    }
    public TypeWarnResourceException(String msg, Exception e)  {
        super(msg, e);
    }
}
