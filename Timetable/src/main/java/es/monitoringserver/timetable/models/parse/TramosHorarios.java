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
public class TramosHorarios
{
	/** Attribute tramo*/
	private List<TimeSlot> tramo;
	
	/** Attribute totTr*/
	private String totTr;
}
