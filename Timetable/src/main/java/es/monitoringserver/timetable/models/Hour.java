package es.monitoringserver.timetable.models;

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
public class Hour
{
	/** Attribute hour*/
	private String hour;
	
	/** Attribute start*/
	private String start;
	
	/** Attribute end*/
	private String end;
}
