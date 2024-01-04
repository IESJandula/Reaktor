package es.iesjandula.horarios.models;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * @author Pablo Ruiz Canovas
 */
@Data
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
	/**Numero de veces que el alumno ha ido al baño */
	private int numBaño;
	
	/**
	 * Constructor completo que crea un alumno e inicializa las veces que ha ido al baño a 0
	 * @param apellido
	 * @param nombre
	 * @param dni
	 * @param curso
	 */
	public Alumno(String apellido, String nombre, String dni, String curso) {
		super();
		this.apellido = apellido;
		this.nombre = nombre;
		this.dni = dni;
		this.curso = curso;
		this.numBaño = 0;
	}
	
	@Override
	public int compareTo(Alumno otro) 
	{
		//Se compara por los apellidos del alumno
		return this.apellido.compareTo(otro.apellido);
	}

}
