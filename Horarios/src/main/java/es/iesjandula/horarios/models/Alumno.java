package es.iesjandula.horarios.models;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * @author Pablo Ruiz Canovas
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Alumno implements Comparable<Alumno>
{
	/**Apellido del estudiante */
	private String apellido;
	/**Nombre del estudiante */
	private String nombre;
	/**Identificador del alumno */
	private String dni;
	/**Curso en el que se encuentra el alumno */
	private String curso;
	
	@Override
	public int compareTo(Alumno otro) 
	{
		//Se compara por los apellidos del alumno
		return this.apellido.compareTo(otro.apellido);
	}
}
