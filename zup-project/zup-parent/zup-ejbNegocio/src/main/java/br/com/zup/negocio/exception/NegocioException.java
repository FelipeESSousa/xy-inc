package br.com.zup.negocio.exception;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @date 04/09/2015
 * @version 1.0
 * 
 */
public class NegocioException extends Exception {

	/**
	 * @field serialVersionUID
	 * @fieldType long
	 * @date 21/10/2015
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @field enumTypeException
	 * @fieldType EnumTypeException
	 * @date 21/10/2015
	 */
	private final EnumTypeException enumTypeException;

	/**
	 * 
	 * Construtor da classe de excecao do dao com parametro de mensagem para
	 * alterar a mensagem exibida no log.
	 * 
	 * @param msg
	 */
	public NegocioException(String msg, EnumTypeException enumTypeException) {
		super(msg);
		this.enumTypeException = enumTypeException;
	}

	/**
	 * 
	 * Construtor da classe de excecao do dao aonde se pode alterar a mensagem e
	 * a excecao que sera exibida log do servidor.
	 * 
	 * @param msg
	 * @param e
	 */
	public NegocioException(String msg, Exception e, EnumTypeException enumTypeException) {
		super(msg, e);
		this.enumTypeException = enumTypeException;
	}

	/**
	 * @method getEnumTypeException
	 */
	public final EnumTypeException getEnumTypeException() {
		return enumTypeException;
	}

	/**
	 * Metodos para evitar o erro de serialização de objetos que não implementão
	 * a interface de serialização.
	 */
	private void writeObject(ObjectOutputStream stream) throws IOException {
		stream.defaultWriteObject();
	}

	private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
		stream.defaultReadObject();
	}
}
