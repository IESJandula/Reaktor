package es.reaktor.horarios.models.parse;

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
public class HorariosGrupos
{
	/** Attribute horarioGrup*/
	private List<HorarioGrup> horarioGrup;
}