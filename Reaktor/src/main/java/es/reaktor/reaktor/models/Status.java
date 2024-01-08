package es.reaktor.reaktor.models;

import lombok.AllArgsConstructor;
import lombok.Data;
/**
 * 
 * @author Pablo Ruiz Canovas
 *
 */
@Data
@AllArgsConstructor
public class Status 
{
	/**Atributo que representa el valor de un pc, clase, aula, carrito o planta */
	private String key;
	/**Valor de la key */
	private String value;
	/**Specific pc status, reboot, shut down, install, ... */
	private String info;
	
	/**Status is done or not */
	private boolean done;
	
	/**
	 * Default constructor
	 */
	public Status()
	{
		//Public constructor
	}
}
