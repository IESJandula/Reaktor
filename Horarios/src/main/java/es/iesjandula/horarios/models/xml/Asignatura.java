package es.iesjandula.horarios.models.xml;

import lombok.AllArgsConstructor;
import lombok.Data;
/**
 * @author Javier Martinez Megias
 */
@Data
@AllArgsConstructor
public class Asignatura
{
	/**Numero de las asignatura */
	private int num_int_as;
	/**Abreviatura de la asignatiura */
	private String abreviatura;
	/**Nombre completo de la asignatura */
	private String nombre;
	
	/**
	 * Constructor por defecto para el rest API
	 */
	public Asignatura()
	{
		//Constructor publico
	}
	
}