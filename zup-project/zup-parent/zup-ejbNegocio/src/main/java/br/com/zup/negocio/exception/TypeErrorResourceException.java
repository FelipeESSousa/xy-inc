package br.com.zup.negocio.exception;

public class TypeErrorResourceException extends Exception {

	private static final long serialVersionUID = 1L;
	public TypeErrorResourceException() {
        super();
    }
    public TypeErrorResourceException(String msg)   {
        super(msg);
    }
    public TypeErrorResourceException(String msg, Exception e)  {
        super(msg, e);
    }
}
