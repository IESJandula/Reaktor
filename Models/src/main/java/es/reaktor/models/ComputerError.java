package es.reaktor.models;

public class ComputerError 
{

	
	private int code;
	private String text;
	
	/**
	 * 
	 */
	public ComputerError() {
		
	}
	
	
	/**
	 * 
	 * @param code
	 * @param text
	 */
	public ComputerError(int code, String text) {
		super();
		this.code = code;
		this.text = text;
	}

	/**
	 * 
	 * @return code error
	 */
	public int getCode() {
		return code;
	}

	/**
	 * 
	 * @param code
	 */
	public void setCode(int code) {
		this.code = code;
	}

	/**
	 * 
	 * @return error text
	 */
	public String getText() {
		return text;
	}

	/**
	 * 
	 * @param text
	 */
	public void setText(String text) {
		this.text = text;
	}
	
	
}
