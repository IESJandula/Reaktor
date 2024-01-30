package es.monitoringserver.timetable.models.parse;

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
public class Horarios
{
	/** Attribute horariosAsignaturas*/
	private HorariosAsignaturas horariosAsignaturas;
	
	/** Attribute horariosGrupos*/
	private HorariosGrupos horariosGrupos;
	
	/** Attribute horariosAulas*/
	private HorariosAulas horariosAulas;
	
	/** Attribute horariosProfesores*/
	private HorariosProfesores horariosProfesores;

}