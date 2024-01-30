package es.monitoringserver.timetable.models.parse;

import java.util.List;

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
public class Asignaturas
{
	/** Attribute asignatura*/
	private List<Asignatura> asignatura;
	
	/** Attribute totAs*/
	private String totAs;

}