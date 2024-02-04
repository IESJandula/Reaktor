package es.iesjandula.reaktor.timetable_server.models.parse;



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
public class HorarioGrupActividad
{
	/** Attribute gruposActividad*/
	private GruposActividad gruposActividad;
	
	/** Attribute numAct*/
	private String numAct;
	
	/** Attribute numUn*/
	private String numUn;
	
	/** Attribute tramo*/
	private String tramo;
	
	/** Attribute aula*/
	private String aula;
	
	/** Attribute profesor*/
	private String profesor;
	
	/** Attribute asignatura*/
	private String asignatura;

}
