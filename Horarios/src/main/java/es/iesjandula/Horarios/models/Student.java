package es.iesjandula.Horarios.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
/**
 * @author Alejandro Cazalla Pérez
 */
public class Student implements Comparable<Student>
{
	private String name;
	
	private String lastname;
	
	private String dni;
	
	private String course;

	@Override
	public int compareTo(Student otroEstudiante)
	{
		int comparacionLastname = this.lastname.compareTo(otroEstudiante.lastname);
        return comparacionLastname;
	}

	
}
