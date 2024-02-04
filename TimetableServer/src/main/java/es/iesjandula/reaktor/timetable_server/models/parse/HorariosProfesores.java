package es.iesjandula.reaktor.timetable_server.models.parse;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HorariosProfesores
{
	/** Attribute horarioProf*/
	private List<HorarioProf> horarioProf;
}
