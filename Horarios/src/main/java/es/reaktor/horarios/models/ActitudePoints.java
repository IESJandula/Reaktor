package es.reaktor.horarios.models;

import es.reaktor.horarios.exceptions.HorariosError;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class ActitudePoints 
{
	private int points;
	private String description;
}
