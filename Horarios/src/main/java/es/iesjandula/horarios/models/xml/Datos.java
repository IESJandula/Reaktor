package es.iesjandula.horarios.models.xml;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * @author Javier Martinez Megias
 */
@Data
@AllArgsConstructor
public class Datos
{
	/**Asignatura guardada*/
	private Asignatura asignatura;
	/**Grupo guardado */
	private Grupo grupo;
	/**Aula guardada */
	private Aula aula;
	/**Profesor guardado */
	private Profesor profesor;
	/**Tramo guardado */
	private Tramo tramo;
	
	/**
	 * Constructor por defecto para el rest API
	 */
	public Datos() 
	{
		//Constructor publico
	}
	
	
}