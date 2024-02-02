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
public class HorarioGrup
{
	/** Attribute actividad*/
	private List<Actividad> actividad;
	
	/** Attribute horNumIntGr*/
	private String horNumIntGr;
	
	/** Attribute totUn*/
	private String totUn;
	
	/** Attribute totAC*/
	private String totAC;

}