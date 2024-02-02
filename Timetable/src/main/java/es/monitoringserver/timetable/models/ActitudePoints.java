package es.monitoringserver.timetable.models;

import es.monitoringserver.timetable.exceptions.HorariosError;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActitudePoints 
{
	private int points;
	private String description;
}
