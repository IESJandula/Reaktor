package es.reaktor.horarios.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author David Martinez
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student implements Comparable<Student>
{
	/** Attribute name*/
	private String name;
	
	/** Attribute lastName*/
	private String lastName;
	
	/** Attribute course*/
	private Course course;

	/**
	 * Method compareTo with personal preferences (by lastName)
	 * @param other
	 * @return int with preference
	 */
	@Override
	public int compareTo(Student other) 
	{
		if(this.lastName.compareTo(other.lastName)==0) 
		{
			return this.name.compareTo(other.name);
		}
		else 
		{
			return this.lastName.compareTo(other.lastName);
		}
	}
}
