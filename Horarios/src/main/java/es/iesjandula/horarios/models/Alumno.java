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
public class Alumno 
{
	/**Nombre del estudiante */
	private String nombre;
	/**Apellido del estudiante */
	private String apellido;
	/**Curso en el que se encuentra el alumno */
	private String curso;
}
