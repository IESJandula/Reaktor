package es.iesjandula.horarios.models.xml;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
/**
 * @author Javier Martinez Megias
 */
@Data
@AllArgsConstructor
public class Datos
{
	/**Asignatura guardada*/
	private List<Asignatura> asignatura;
	/**Grupo guardado */
	private List<Grupo> grupo;
	/**Aula guardada */
	private List<Aula> aula;
	/**Profesor guardado */
	private List<Profesor> profesor;
	/**Tramo guardado */
	private List<Tramo> tramo;
	/**Horarios guardado */
	private Horarios horarios;
	/**
	 * Constructor por defecto para el rest API
	 */
	public Datos() 
	{
		//Constructor publico
	}
	
	
}