package es.iesjandula.horarios.models.xml;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * @author Javier Martinez Megias
 */
@Data
@AllArgsConstructor
public class Aula
{
	/**Numero de las aulas */
	private int num_int_au;
	/**Abreviatura de las aulas */
	private String abreviatura;
	/**Nombre de las aulas */
	private String nombre;
	/**
	 * Constructor publico para el rest API
	 */
	public Aula()
	{
		//Constructor publico
	}
}