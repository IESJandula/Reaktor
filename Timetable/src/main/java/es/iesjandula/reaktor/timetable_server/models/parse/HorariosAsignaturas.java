package es.iesjandula.reaktor.timetable_server.models.parse;

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
public class HorariosAsignaturas
{
	/** Attribute horarioAsig*/
	private List<HorarioAsig> horarioAsig;
}
