package es.iesjandula.reaktor.timetable_server.models;

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
public class TeacherMoment
{
	/** Attribute teacher*/
	private Teacher teacher;
	
	/** Attribute subject*/
	private String subject;
	
	/** Attribute classroom*/
	private Classroom classroom;
}
