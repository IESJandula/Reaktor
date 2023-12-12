package es.iesjandula.horarios.models.xml;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Javier Martinez Megias
 */
@Data
@AllArgsConstructor
public class Profesor
{
	/**Numero del profesor */
	private int num_int_pr;
	/**Abreviatura del profesor */
	private String abreviatura;
	/**Nomrbe completo del profesor */
	private String nombre;
	/**
	 * Constructor por defecto para la rest API
	 */
	public Profesor() 
	{
		//Constructor publico
	}
}